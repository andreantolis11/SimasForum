package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> getRoleById(Long id){
       return roleRepository.findById(id);
    }
}
