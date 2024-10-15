package com.snapp.snapppay.club.service.user;

import com.snapp.snapppay.club.domain.entity.User;
import com.snapp.snapppay.club.domain.request.PageRequest;
import com.snapp.snapppay.club.domain.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponse> search(String search, PageRequest pageRequest);

    User getCurrentUser();

}
