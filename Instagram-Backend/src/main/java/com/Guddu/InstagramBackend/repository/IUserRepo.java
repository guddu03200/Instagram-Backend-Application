package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.Admin;
import com.Guddu.InstagramBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Integer> {
    User findFirstByUserEmail(String newEmail);
}
