package com.Guddu.InstagramBackend.service;

import com.Guddu.InstagramBackend.model.Like;
import com.Guddu.InstagramBackend.model.Post;
import com.Guddu.InstagramBackend.model.User;
import com.Guddu.InstagramBackend.repository.ILikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    ILikeRepo likeRepo;

    public String addLike(Like like) {
        likeRepo.save(like);
        return "Insta post liked successfully";
    }

    public boolean isLikeAllowedOnThisPost(Post instaPost, User liker) {
        List<Like> likeList = likeRepo.findByInstaPostAndLiker(instaPost, liker);

      return likeList != null && likeList.isEmpty();
    }

    public Integer getLikeCountForPost(Post post) {
       return  likeRepo.findByInstaPost(post).size();
    }

    public Like findLike(Integer likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }

    public void removeLike(Like like) {
        likeRepo.delete(like);
    }
}
