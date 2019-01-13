package com.web.tanuki.repository;

import com.web.tanuki.model.*;
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
public class ContentRepositoryUnitTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private TanukiUserRepository userRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private AudioRepository audioRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ImageRepository imageRepository;

    private List<Content> contents;
    private Channel channel;
    private TanukiUser user;

    @BeforeEach
    void setup() {
        userRepository.deleteAllInBatch();
        channelRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();

        user = new TanukiUser("name", "lastname", "mail", "username");
        userRepository.save(user);

        channel = new Channel("channel name", "channel desc", user);
        channelRepository.save(channel);

        contents = new LinkedList<>();
        contents.add(new Audio(channel, "music", "short audio track", "/tmp/music.mp3", new Date(), "0.5MB", "mp3", 0));
        contents.add(new Image(channel, "drawing", "paper drawing","/tmp/drawing.jpg", new Date(), "0.5MB", "jpg", 0));
        contents.add(new Video(channel, "video", "short video", "/tmp/video.mp4", new Date(), "0.5MB", "mp4", 0));

        contentRepository.saveAll(contents);
    }

    // Tests the polymorphic nature of the entity
    @Test
    public void createRead(){

        assertTrue(contentRepository.existsById(contents.get(0).getId()));
        assertTrue(contentRepository.existsById(contents.get(1).getId()));
        assertTrue(contentRepository.existsById(contents.get(2).getId()));

        assertEquals("music", contents.get(0).getTitle());
        assertEquals("drawing", contents.get(1).getTitle());
        assertEquals("video", contents.get(2).getTitle());

        Content a = audioRepository.findById(contents.get(0).getId()).get();
        Content b = imageRepository.findById(contents.get(1).getId()).get();
        Content c = videoRepository.findById(contents.get(2).getId()).get();

        assertTrue(audioRepository.existsById(a.getId()));
        assertTrue(imageRepository.existsById(b.getId()));
        assertTrue(videoRepository.existsById(c.getId()));

        List<Content> allContent = contentRepository.findAll();
        assertEquals(3, allContent.size());

        List<Audio> allAudio = audioRepository.findAll();
        assertEquals(1, allAudio.size());

        List<Image> allImage = imageRepository.findAll();
        assertEquals(1, allImage.size());

        List<Video> allVideo = videoRepository.findAll();
        assertEquals(1, allVideo.size());

    }

    @Test
    public void updateContent(){
        Content u = contents.get(0);

        u.setTitle("reuploaded music");
        u.setPath("/tmp/music2.mp3");
        u = contentRepository.save(u);

        contentRepository.flush();
        entityManager.clear();

        Content new_u = contentRepository.findById(u.getId()).get();

        assertEquals("reuploaded music", new_u.getTitle());
        assertEquals("/tmp/music2.mp3", new_u.getPath());

        assertTrue(new_u.getCreationDate().compareTo(new_u.getLastModifiedDate()) < 0);

    }

    @Test
    public void delete(){
        assertEquals(3, contentRepository.findAll().size());
        contentRepository.deleteById(contents.get(0).getId());
        assertEquals(2, contentRepository.findAll().size());

        contentRepository.deleteAll();
        assertEquals(0, contentRepository.findAll().size());

    }

    @Test
    public void searchByTitle(){
        Set<Content> results = contentRepository.findByTitle("drawing");
        assertEquals(1, results.size());
        for(Content u : results) {
            assertEquals("drawing", u.getTitle());
        }

    }



}