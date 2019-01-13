package com.web.tanuki.repository;

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
public class TanukiUserRepositoryUnitTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TanukiUserRepository userRepository;

    private List<TanukiUser> users;

    @BeforeEach
    void setup() {
        userRepository.deleteAllInBatch();
        users = new LinkedList<>();
        users.add(new TanukiUser("name", "lastname", "mail", "username"));
        users.add(new TanukiUser("name", "lastname", "mail", "username"));
        users.add(new TanukiUser("name", "lastname", "mail", "username"));

        userRepository.saveAll(users);
    }

    @Test
    public void createRead(){
        for(TanukiUser u : users) {
            assertTrue(userRepository.existsById(u.getId()));

            assertEquals("name", u.getName());
            assertEquals("lastname", u.getLastname());
            assertEquals("mail", u.getEmail());
            assertEquals("username", u.getUsername());

            assertNotNull(u.getCreationDate());
            assertNotNull(u.getLastModifiedDate());
        }
    }

    @Test
    public void updateUser(){
        TanukiUser u = users.get(0);

        u.setName("new name");
        u.setLastname("new lastname");
        u.setEmail("new mail");
        u.setUsername("new username");

        u = userRepository.save(u);

        userRepository.flush();
        entityManager.clear();

        TanukiUser new_u = userRepository.findById(u.getId()).get();

        assertEquals("new name", new_u.getName());
        assertEquals("new lastname", new_u.getLastname());
        assertEquals("new mail", new_u.getEmail());
        assertEquals("new username", new_u.getUsername());

        assertTrue(new_u.getCreationDate().compareTo(new_u.getLastModifiedDate()) < 0);

    }

    @Test
    public void delete(){
        assertEquals(3, userRepository.findAll().size());
        userRepository.deleteById(users.get(0).getId());
        assertEquals(2, userRepository.findAll().size());

        userRepository.deleteAll();
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    public void searchByName(){
        TanukiUser a = new TanukiUser("specific name", "specific lastname", "specific mail", "specific username");
        userRepository.save(a);

        Set<TanukiUser> results = userRepository.findByName("specific name");
        assertEquals(1, results.size());
        for(TanukiUser u : results) {
            assertEquals("specific name", u.getName());
        }

    }

    @Test
    void addRemoveFollower(){
        TanukiUser user1 = users.get(0);
        TanukiUser user2 = users.get(1);

        // user2 follows user1
        user2.addFollowing(user1);
        assertEquals(1, user1.getFollowers().size());
        assertTrue(user1.getFollowers().contains(user2));
        assertEquals(1, user2.getFollowing().size());
        assertTrue(user2.getFollowing().contains(user1));

        // user1 follows user2
        user1.addFollowing(user2);
        assertEquals(1, user2.getFollowers().size());
        assertTrue(user2.getFollowers().contains(user1));
        assertEquals(1, user1.getFollowing().size());
        assertTrue(user1.getFollowing().contains(user2));

        // user2 unfollows user1
        user2.removeFollowing(user1);
        assertEquals(0, user1.getFollowers().size());
        assertFalse(user1.getFollowers().contains(user2));
        assertEquals(0, user2.getFollowing().size());
        assertFalse(user2.getFollowing().contains(user1));
    }


}