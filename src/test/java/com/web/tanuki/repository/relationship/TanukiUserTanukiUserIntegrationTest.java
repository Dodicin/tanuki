package com.web.tanuki.repository.relationship;

import com.web.tanuki.model.TanukiUser;
import com.web.tanuki.repository.TanukiUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

@SpringBootTest
@Transactional
public class TanukiUserTanukiUserIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TanukiUserRepository userRepository;

    private List<TanukiUser> users;

    @BeforeEach
    void setup() {
        userRepository.deleteAllInBatch();
        users = new LinkedList<>();
        users.add(new TanukiUser("name 1", "lastname", "mail", "username"));
        users.add(new TanukiUser("name 2", "lastname", "mail", "username"));
        users.add(new TanukiUser("name 3", "lastname", "mail", "username"));
        users.add(new TanukiUser("name 4", "lastname", "mail", "username"));

        userRepository.saveAll(users);
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
