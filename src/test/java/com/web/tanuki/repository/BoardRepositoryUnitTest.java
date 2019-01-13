package com.web.tanuki.repository;

import com.web.tanuki.model.Board;
import com.web.tanuki.model.TanukiUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BoardRepositoryUnitTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BoardRepository boardRepository;

    private List<Board> boards;

    @BeforeEach
    void setup() {
        boardRepository.deleteAllInBatch();
        boards = new LinkedList<>();
        boards.add(new Board("cooking"));
        boards.add(new Board("gaming"));

        boardRepository.saveAll(boards);
    }

    @Test
    public void createRead(){
        assertTrue(boardRepository.existsById(boards.get(0).getId()));

        assertEquals("cooking", boards.get(0).getTitle());

        assertTrue(boardRepository.existsById(boards.get(1).getId()));

        assertEquals("gaming", boards.get(1).getTitle());

        assertNotNull(boards.get(0).getCreationDate());
        assertNotNull(boards.get(0).getLastModifiedDate());

        assertNotNull(boards.get(1).getCreationDate());
        assertNotNull(boards.get(1).getLastModifiedDate());
    }

    @Test
    public void updateBoard(){
        Board b = boards.get(0);

        b.setTitle("new cooking");

        b = boardRepository.save(b);

        boardRepository.flush();
        entityManager.clear();

        Board new_b = boardRepository.findById(b.getId()).get();

        assertEquals("new cooking", new_b.getTitle());
        assertTrue(new_b.getCreationDate().compareTo(new_b.getLastModifiedDate()) < 0);

    }

    @Test
    public void delete(){
        assertEquals(2, boardRepository.findAll().size());
        boardRepository.deleteById(boards.get(0).getId());
        assertEquals(1, boardRepository.findAll().size());

        boardRepository.deleteAll();
        assertEquals(0, boardRepository.findAll().size());
    }

    @Test
    public void searchByTitle(){

        Set<Board> results = boardRepository.findByTitle("gaming");
        assertEquals(1, results.size());
        for(Board b : results) {
            assertEquals("gaming", b.getTitle());
        }

    }

}
