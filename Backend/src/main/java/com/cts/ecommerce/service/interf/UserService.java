package com.cts.ecommerce.service.interf;

import com.cts.Ecommerce.dto.LoginRequest;
import com.cts.Ecommerce.dto.Response;
import com.cts.Ecommerce.dto.UserDto;
import com.cts.Ecommerce.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
