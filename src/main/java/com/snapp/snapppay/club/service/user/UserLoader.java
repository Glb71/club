package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;

public interface UserLoader {

    User load(Long id);

    User loadByUsername(String username);

    User current();

}
