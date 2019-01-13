package com.web.tanuki.repository;

import com.web.tanuki.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Set<Content> findByTitle(String title);
}
