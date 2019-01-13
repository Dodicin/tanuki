package com.web.tanuki.repository;

import com.web.tanuki.model.Board;
import com.web.tanuki.model.Post;
import com.web.tanuki.model.Thread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ThreadRepositoryUnitTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private BoardRepository boardRepository;

    private List<Thread> threads;

    private Board board;

    @BeforeEach
    void setup() {
        threadRepository.deleteAllInBatch();
        board = new Board("cooking");
        boardRepository.save(board);

        threads = new LinkedList<>();

        threads.add(new Thread(false, "aa", board));
        threads.add(new Thread(true, "bb", board));

        threadRepository.saveAll(threads);
    }

    @Test
    public void createRead(){
        assertTrue(threadRepository.existsById(threads.get(0).getId()));
        assertFalse(threads.get(0).isArchived());

        assertTrue(threadRepository.existsById(threads.get(1).getId()));
        assertTrue(threads.get(1).isArchived());

        assertNotNull(threads.get(0).getCreationDate());
        assertNotNull(threads.get(0).getLastModifiedDate());

        assertNotNull(threads.get(1).getCreationDate());
        assertNotNull(threads.get(1).getLastModifiedDate());
    }

    @Test
    public void updateThread(){
        Thread b = threads.get(0);

        b.setArchived(true);
        b.setTitle("cc");

        b = threadRepository.save(b);

        threadRepository.flush();
        entityManager.clear();

        Thread new_b = threadRepository.findById(b.getId()).get();

        assertTrue(new_b.isArchived());
        assertEquals("cc", new_b.getTitle());
        assertTrue(new_b.getCreationDate().compareTo(new_b.getLastModifiedDate()) < 0);
    }

    @Test
    public void delete(){
        assertEquals(2, threadRepository.findAll().size());
        threadRepository.deleteById(threads.get(0).getId());
        assertEquals(1, threadRepository.findAll().size());

        threadRepository.deleteAll();
        assertEquals(0, threadRepository.findAll().size());
    }

}
