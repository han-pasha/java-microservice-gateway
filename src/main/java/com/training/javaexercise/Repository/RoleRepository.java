package com.training.javaexercise.Repository;

import com.training.javaexercise.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleCode(String roleCode);
}
