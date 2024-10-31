package com.codegym.gotodemospringboot.service.mapper;

import com.codegym.gotodemospringboot.dto.UpdateUserDto;
import com.codegym.gotodemospringboot.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUserFromUserDto(UpdateUserDto dto, @MappingTarget User user);
}
