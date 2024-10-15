package com.snapp.snapppay.club.service.userAccount;

import com.snapp.snapppay.club.domain.entity.Provider;
import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.entity.UserAccount;

public interface UserAccountProvider {

    UserAccount findOrCreate(Provider provider, User user);

}
