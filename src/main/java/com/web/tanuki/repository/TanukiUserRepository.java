package com.web.tanuki.repository;

import com.web.tanuki.model.TanukiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TanukiUserRepository extends JpaRepository<TanukiUser, Long> {

    Set<TanukiUser> findByName(String name);
    Set<TanukiUser> findByLastname(String lastname);
    Set<TanukiUser> findByUsername(String username);
    Set<TanukiUser> findByEmail(String email);

}
