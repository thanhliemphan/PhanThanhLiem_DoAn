package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
