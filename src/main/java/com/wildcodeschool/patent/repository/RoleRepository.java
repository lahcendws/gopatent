package com.wildcodeschool.patent.repository;

import com.wildcodeschool.patent.entity.ERole;
import com.wildcodeschool.patent.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

