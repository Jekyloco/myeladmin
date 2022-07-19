package com.jekyloco.rest;


import cn.hutool.core.util.IdUtil;
import com.jekyloco.utils.RedisUtils;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final RedisUtils redisUtils;

    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        Captcha captcha = new ArithmeticCaptcha(111, 36);
        captcha.setLen(2);
        String uuid = "code-key-" + IdUtil.simpleUUID();
        String captchaValue = captcha.text();
        ;
        redisUtils.set(uuid, captchaValue, 2L, TimeUnit.MINUTES);
        Map<String, Object> imgResult = new HashMap<String, Object>() {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }
}
