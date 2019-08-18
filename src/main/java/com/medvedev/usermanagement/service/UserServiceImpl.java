package com.medvedev.usermanagement.service;

import com.medvedev.usermanagement.dao.UserDao;
import com.medvedev.usermanagement.model.Role;
import com.medvedev.usermanagement.model.Status;
import com.medvedev.usermanagement.model.UserEntity;
import com.medvedev.usermanagement.utile.UserNotActiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Vladimir Medvedev 15.08.2019
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public List<UserEntity> getAll() {
        return userDao.getAll();
    }

    @Override
    public List<UserEntity> filterByNameAndRole(String name, Role role) {
        return userDao.filterByUserNameAndRole(name, role);
    }

    @Override
    public List<UserEntity> filterByName(String name) {
        return userDao.filterByUserName(name);
    }

    @Override
    public List<UserEntity> getAllByRole(Role role) {
        return userDao.getAllbyUserRole(role);
    }

    @Override
    public UserEntity getById(int id) {
        return userDao.get(id);
    }

    @Override
    public UserEntity getByName(String name) {
        return userDao.getByUserName(name);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userDao.getByUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
