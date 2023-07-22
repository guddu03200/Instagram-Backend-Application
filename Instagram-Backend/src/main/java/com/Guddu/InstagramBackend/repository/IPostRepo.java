package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.Admin;
import com.Guddu.InstagramBackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepo extends JpaRepository<Post, Integer> {
}
