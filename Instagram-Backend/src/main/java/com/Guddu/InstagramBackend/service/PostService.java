package com.Guddu.InstagramBackend.service;

import com.Guddu.InstagramBackend.model.Post;
import com.Guddu.InstagramBackend.model.User;
import com.Guddu.InstagramBackend.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {
    @Autowired
    IPostRepo postRepo;

    public String createInstaPost(Post post) {
        post.setPostCreatedTimeStamp(LocalDateTime.now());
        postRepo.save(post);
        return "Post Uploaded";
    }

    public String removeInstaPost(Integer postId, User user) {
        Post post = postRepo.findById(postId).orElse(null);
        if (post != null && post.getPostOwner().equals(user)) {

            postRepo.deleteById(postId);
            return "removed Successfully";
        }else if(post == null){
            return "post to be deleted doesn't exist";
        }else {
            return "Un-Authorized user can not delete post";
        }
    }

    public boolean validatePost(Post instaPost) {

        return (instaPost != null && postRepo.existsById(instaPost.getPostId()));
    }


    public Post getPostById(Integer  postId) {

        return (postRepo.findById(postId)).orElse(null);
    }
}
