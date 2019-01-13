package com.web.tanuki.repository;

import com.web.tanuki.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Set<Board> findByTitle(String title);
}
