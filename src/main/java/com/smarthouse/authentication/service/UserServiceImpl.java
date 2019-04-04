package com.smarthouse.authentication.service;

import com.smarthouse.commonutil.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<User, Long> {
    @Autowired
    public UserServiceImpl(final JpaRepository<User, Long> repository) {
        super(repository);
    }
}