package com.web.tanuki.repository;

import com.web.tanuki.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Set<Channel> findByName(String name);
}
