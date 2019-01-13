package com.web.tanuki.repository;

import com.web.tanuki.model.Board;
import com.web.tanuki.model.Post;
import com.web.tanuki.model.Thread;
import com.web.tanuki.model.TanukiUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostRepositoryUnitTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TanukiUserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private BoardRepository boardRepository;

    private List<TanukiUser> users;
    private List<Post> posts;
    private Thread thread;
    private Board board;

    @BeforeEach
    void setup() {
        userRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        threadRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();

        users = new LinkedList<>();
        users.add(new TanukiUser("name 1", "lastname", "mail", "username"));
        users.add(new TanukiUser("name 2", "lastname", "mail", "username"));
        userRepository.saveAll(users);

        board = new Board("cooking");
        boardRepository.save(board);

        thread = new Thread(false, "new recipe", board);
        threadRepository.save(thread);

        Date yesterdayTime = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
        Calendar yesterdayDate = Calendar.getInstance();
        yesterdayDate.add(Calendar.DATE, -1);
        posts = new LinkedList<>();
        posts.add(new Post("opening post", yesterdayDate, yesterdayTime, users.get(0), thread));

        Date now = new Date();
        Calendar today = Calendar.getInstance();
        posts.add(new Post("nice recipe", today, now, users.get(1), thread));

        postRepository.saveAll(posts);
    }

    @Test
    public void createRead(){
        assert(postRepository.existsById(posts.get(0).getId()));
        assertEquals("opening post", posts.get(0).getText());

        assertTrue(postRepository.existsById(posts.get(1).getId()));
        assertEquals("nice recipe", posts.get(1).getText());

        assertNotNull(posts.get(0).getCreationDate());
        assertNotNull(posts.get(0).getLastModifiedDate());

        assertNotNull(posts.get(1).getCreationDate());
        assertNotNull(posts.get(1).getLastModifiedDate());
    }

    @Test
    public void updatePost(){
        Post b = posts.get(1);

        b.setText("edit: not so nice after all");

        b = postRepository.save(b);

        postRepository.flush();
        entityManager.clear();

        Post new_b = postRepository.findById(b.getId()).get();

        assertEquals("edit: not so nice after all", new_b.getText());
        assertTrue(new_b.getCreationDate().compareTo(new_b.getLastModifiedDate()) < 0);
    }

    @Test
    public void searchByDate(){
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.MILLISECOND);
        Set<Post> results = postRepository.findAllByDate(today);
        assertEquals(1, results.size());
        for(Post p : results) {
            Calendar d = p.getDate();
            d.clear(Calendar.MILLISECOND);
            assertEquals(0, today.compareTo(d));
        }

    }

}
