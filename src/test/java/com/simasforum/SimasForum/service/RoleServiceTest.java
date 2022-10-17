package com.simasforum.SimasForum.service;

import com.simasforum.SimasForum.model.Role;
import com.simasforum.SimasForum.model.User;
import com.simasforum.SimasForum.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void getRoleById_ok(){
        Optional<Role> mockRole= Optional.of(new Role("admin"));
        mockRole.get().setId(1L);
        when(roleRepository.findById(anyLong())).thenReturn(mockRole);
        Optional<Role> existingRole = roleService.getRoleById(1L);
        verify(roleRepository, times(1)).findById(any(Long.class));
        assertTrue(existingRole.get().getRoleName().equals("admin"));
    }
}
