package com.Guddu.InstagramBackend.service;

import com.Guddu.InstagramBackend.model.Follow;
import com.Guddu.InstagramBackend.model.User;
import com.Guddu.InstagramBackend.repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    IFollowRepo followRepo;

    public void startFollowing(Follow follow) {
        followRepo.save(follow);
    }

    public boolean isFollowAllowed(User targetUser, User follower) {
       List<Follow> followList =  followRepo.findByCurrentUserAndFollower(targetUser, follower);
       return followList != null && followList.isEmpty() && !targetUser.equals(follower);
    }

    public Follow findFollow(Integer followId) {
        return followRepo.findById(followId).orElse(null);
    }

    public void unfollow(Follow follow) {
        followRepo.delete(follow);
    }
}
