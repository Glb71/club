package com.snapp.snapppay.club.mapper;

import com.snapp.snapppay.club.service.user.UserLoader;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperUtil {

    private final UserLoader userLoader;

    @Named("userIdToUsername")
    public String insertUserToCreator(Long userId) {
        return userId != null ? userLoader.load(userId).getUsername() : null;
    }

}
