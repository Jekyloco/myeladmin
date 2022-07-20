package com.jekyloco.rest;


import cn.hutool.core.util.IdUtil;
import com.jekyloco.config.RsaProperties;
import com.jekyloco.service.dto.AuthUserDto;
import com.jekyloco.service.dto.JwtUserDto;
import com.jekyloco.utils.RedisUtils;
import com.jekyloco.utils.RsaUtils;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final RedisUtils redisUtils;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //@Validated 对传送的参数进行数据校验
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUserDto.getPassword());
        //查询验证码
        //通过authUserDto的uuid从Redis中获取验证码
        String code = (String) redisUtils.get(authUserDto.getUuid());
        //清除验证码
        redisUtils.del(authUserDto.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new Exception("验证码不存在或已过期");
        }

        //认证授权
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUserDto.getUsername(), password);
        System.out.println(authUserDto.getUsername());
        System.out.println(authUserDto.getPassword());
        System.out.println(password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        Map<String, Object> authInfo = new HashMap<String, Object>() {{
            put("token", "token");
            put("user", jwtUserDto);
        }};
//        //用户输入验证码为空或用户验证码不等于缓存中的验证码
//        if (StringUtils.isBlank(authUserDto.getCode()) || !authUserDto.getCode().equalsIgnoreCase(code)) {
//            throw new Exception("验证码错误");
//        }
        //验证码校验通过

        return ResponseEntity.ok(authInfo);
    }


    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        Captcha captcha = new ArithmeticCaptcha(111, 36);

        //算数结果长度
        captcha.setLen(2);
        String uuid = "code-key-" + IdUtil.simpleUUID();

        //计算数值结果
        String captchaValue = captcha.text();

        //Redis缓存
        redisUtils.set(uuid, captchaValue, 2L, TimeUnit.MINUTES);

        //验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>() {{
            //图片转Base64存储
            put("img", captcha.toBase64());
            //图片唯一表示uuid
            put("uuid", uuid);
        }};
        //将图片信息返回给前端
        return ResponseEntity.ok(imgResult);
    }
}
