package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.Admin;
import com.Guddu.InstagramBackend.model.Like;
import com.Guddu.InstagramBackend.model.Post;
import com.Guddu.InstagramBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ILikeRepo extends JpaRepository<Like, Integer> {


    List<Like> findByInstaPostAndLiker(Post instaPost, User liker);

    List<Like> findByInstaPost(Post post);
}
