package com.snapp.snapppay.club.service.userAccount;

import com.snapp.snapppay.club.domain.entity.UserAccount;

public interface UserAccountProvider {

    UserAccount findOrCreate(String username);

}
