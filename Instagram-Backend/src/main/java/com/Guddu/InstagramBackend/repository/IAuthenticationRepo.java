package com.Guddu.InstagramBackend.repository;

import com.Guddu.InstagramBackend.model.AuthenticationToken;
import com.Guddu.InstagramBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findFirstByTokenValue(String authToken);

    AuthenticationToken findFirstByUser(User user);
}
