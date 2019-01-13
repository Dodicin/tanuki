package com.web.tanuki.repository;

import com.web.tanuki.model.Audio;
import com.web.tanuki.model.Channel;
import com.web.tanuki.model.Content;
import com.web.tanuki.model.TanukiUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ChannelRepositoryUnitTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private TanukiUserRepository userRepository;

    @Autowired
    private ContentRepository contentRepository;

    private List<TanukiUser> users;
    private Channel channel;


    @BeforeEach
    void setup() {
        userRepository.deleteAllInBatch();
        users = new LinkedList<>();
        users.add(new TanukiUser("name", "lastanem", "mail", "username"));
        users.add(new TanukiUser("name", "lastanem", "mail", "username"));

        channel = new Channel("channel name", "channel desc");
        channel.setUser(users.get(0));
        userRepository.saveAll(users);
        channelRepository.save(channel);
    }

    @Test
    public void createRead(){

        assertTrue(channelRepository.existsById(channel.getId()));

        assertEquals("channel name", channel.getName());
        assertEquals("channel desc", channel.getDescription());

        assertEquals(users.get(0).getId(), channel.getId());

        assertNotNull(channel.getCreationDate());
        assertNotNull(channel.getLastModifiedDate());
    }

    @Test
    public void updateChannel(){

        channel.setName("new channel name");
        channel.setDescription("new channel desc");

        channelRepository.save(channel);

        channelRepository.flush();
        entityManager.clear();

        Channel new_channel = channelRepository.findById(channel.getId()).get();

        assertEquals("new channel name", new_channel.getName());
        assertEquals("new channel desc", new_channel.getDescription());

        assertTrue(new_channel.getCreationDate().compareTo(new_channel.getLastModifiedDate()) < 0);

    }

    @Test
    public void delete(){
        assertEquals(1, channelRepository.findAll().size());
        channelRepository.deleteById(channel.getId());
        assertEquals(0, channelRepository.findAll().size());
    }

    @Test
    public void searchByName(){
        Channel a = new Channel("channel name 2", "channel desc 2");
        a.setUser(users.get(1));
        channelRepository.save(a);

        Set<Channel> results = channelRepository.findByName("channel name 2");
        assertEquals(1, results.size());
        for(Channel u : results) {
            assertEquals("channel name 2", a.getName());
        }

    }

    @Test
    public void addDeleteContent(){
        Content t = new Audio(channel, "music", "short audio track", "/tmp/music.mp3", new Date(), "0.5MB", "mp3", 0);

        channel.addContent(t);
        channelRepository.save(channel);
        contentRepository.save(t);

        assertEquals(1, channel.getContent().size());
        assertEquals(1, contentRepository.findAll().size());

        contentRepository.delete(t);
        channel.getContent().clear();

        channelRepository.saveAndFlush(channel);
        assertEquals(0, channel.getContent().size());
        assertEquals(0, contentRepository.findAll().size());
    }


}