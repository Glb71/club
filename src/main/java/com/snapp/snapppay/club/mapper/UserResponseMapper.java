package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse map(User user);

}
