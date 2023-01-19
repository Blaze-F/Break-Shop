package com.project.breakshop.service;

import com.project.breakshop.models.DTO.UserDTO;

public interface UserService {

    public UserDTO create_user();

    public UserDTO update_user();

    public String delete_user();

    public UserDTO get_user();

    public UserDTO find_user(); //pagination?

}
