package com.smarthouse.authentication.service;

import com.smarthouse.commonutil.entities.Role;
import com.smarthouse.commonutil.service.CRUDServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CRUDServiceImpl<Role, Long> {
    @Autowired
    public RoleServiceImpl(final JpaRepository<Role, Long> repository) {
        super(repository);
    }
}

