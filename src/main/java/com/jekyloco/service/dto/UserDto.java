package com.jekyloco.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jekyloco.base.BaseDTO;
import com.jekyloco.service.dto.small.DeptSmallDto;
import com.jekyloco.service.dto.small.JobSmallDto;
import com.jekyloco.service.dto.small.RoleSmallDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class UserDto extends BaseDTO implements Serializable {

    private Long id;

    private Set<RoleSmallDto> roles;

    private Set<JobSmallDto> jobs;

    private DeptSmallDto dept;

    private Long deptId;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

    private String avatarName;

    private String avatarPath;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    @JsonIgnore
    private Boolean isAdmin= false;

    private Date pwdResetTime;

}
