package com.medvedev.usermanagement.service;

import com.medvedev.usermanagement.model.Role;
import com.medvedev.usermanagement.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserEntity getById(int id);

    UserEntity getByName(String name);

    List<UserEntity> getAll();

    List<UserEntity> getAllByRole(Role role);

    List<UserEntity> filterByNameAndRole(String name, Role role);

    List<UserEntity> filterByName(String name);

    Page<UserEntity> getUserPage(PageRequest pageRequest);

    UserEntity save(UserEntity user);


}
