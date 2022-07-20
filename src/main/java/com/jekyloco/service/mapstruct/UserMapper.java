package com.jekyloco.service.mapstruct;

import com.jekyloco.base.BaseMapper;
import com.jekyloco.domain.User;
import com.jekyloco.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDto, User> {
}