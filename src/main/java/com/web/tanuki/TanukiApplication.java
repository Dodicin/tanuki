package com.web.tanuki;

import com.web.tanuki.model.User;
import com.web.tanuki.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TanukiApplication implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository u_repository;
    public static void main(String[] args) {
        SpringApplication.run(TanukiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Student ID 1 -> ", u_repository.findById((long) 1));
        logger.info("Inserting -> ", u_repository.save(new User( "Tanuki", "Tanuki", "t@tanuki.tk", "saburou")));
        logger.info("All Students -> ", u_repository.findAll());

    }

}

