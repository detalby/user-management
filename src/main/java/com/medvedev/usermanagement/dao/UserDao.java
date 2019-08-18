package com.medvedev.usermanagement.dao;

import com.medvedev.usermanagement.model.Role;
import com.medvedev.usermanagement.model.UserEntity;

import java.util.List;

public interface UserDao {

    UserEntity save(UserEntity user);

    UserEntity get(int id);

    List<UserEntity> getAll();

    List<UserEntity> getAllbyUserRole(Role role);

    UserEntity getByUserName(String name);

    List<UserEntity> filterByUserName(String name);

    List<UserEntity> filterByUserNameAndRole(String name, Role role);

}
