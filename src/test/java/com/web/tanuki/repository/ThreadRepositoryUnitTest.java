package com.web.tanuki.repository;

import com.web.tanuki.model.Board;
import com.web.tanuki.model.Post;
import com.web.tanuki.model.TanukiUser;
import com.web.tanuki.model.Thread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ThreadRepositoryUnitTest {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TanukiUserRepository userRepository;

    private List<Thread> threads;

    private Board board;

    @BeforeEach
    void setup() {
        threadRepository.deleteAllInBatch();
        board = new Board("cooking");
        boardRepository.save(board);

        threads = new LinkedList<>();

        threads.add(new Thread(false, "aa"));
        threads.add(new Thread(true, "bb"));

        for(Thread t : threads){
            board.addThread(t);
        }

        threadRepository.saveAll(threads);
        threadRepository.flush();
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

        threadRepository.saveAndFlush(b);

        Thread new_b = threadRepository.findById(b.getId()).get();

        assertTrue(new_b.isArchived());
        assertEquals("cc", new_b.getTitle());
        assertTrue(new_b.getCreationDate().compareTo(new_b.getLastModifiedDate()) < 0);
    }

    @Test
    public void deleteThread(){
        assertEquals(2, threadRepository.findAll().size());
        threadRepository.deleteById(threads.get(0).getId());
        assertEquals(1, threadRepository.findAll().size());

        threadRepository.deleteAll();
        assertEquals(0, threadRepository.findAll().size());
    }

    @Test
    public void addDeletePosts(){
        TanukiUser u = new TanukiUser("a", "b", "c", "d");
        userRepository.save(u);

        Thread t = threads.get(0);
        Post a = new Post("x", new GregorianCalendar(), new Date());
        Post b = new Post("y", new GregorianCalendar(), new Date());
        u.addPost(a);
        u.addPost(b);
        userRepository.save(u);
        t.addPost(a);
        t.addPost(b);
        threadRepository.save(t);
        postRepository.save(a);
        postRepository.save(b);

        assertEquals(2, postRepository.findAll().size());
        assertEquals(2, t.getPosts().size());

        t.getPosts().clear();
        postRepository.deleteAllInBatch();
        threadRepository.saveAndFlush(t);
        assertEquals(0, t.getPosts().size());
    }


}
