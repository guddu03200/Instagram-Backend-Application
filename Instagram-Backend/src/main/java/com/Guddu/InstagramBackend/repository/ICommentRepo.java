package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.Admin;
import com.Guddu.InstagramBackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepo extends JpaRepository<Comment, Integer> {
}
