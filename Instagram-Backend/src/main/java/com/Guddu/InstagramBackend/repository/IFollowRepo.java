package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.Admin;
import com.Guddu.InstagramBackend.model.Follow;
import com.Guddu.InstagramBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFollowRepo extends JpaRepository<Follow, Integer> {

    List<Follow> findByCurrentUserAndFollower(User targetUser, User follower);
}
