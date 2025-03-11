package com.cts.ecommerce.service.interf;

import com.cts.ecommerce.dto.LoginRequest;
import com.cts.ecommerce.dto.Response;
import com.cts.ecommerce.dto.UserDto;
import com.cts.ecommerce.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
