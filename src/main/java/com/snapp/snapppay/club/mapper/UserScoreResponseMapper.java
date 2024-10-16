package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.domain.entity.UserScore;
import com.snapp.snapppay.club.domain.response.UserScoreResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserScoreResponseMapper {

    UserScoreResponse map(UserScore userScore);

}
