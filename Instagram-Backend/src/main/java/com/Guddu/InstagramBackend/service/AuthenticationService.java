package com.Guddu.InstagramBackend.service;

import com.Guddu.InstagramBackend.model.AuthenticationToken;
import com.Guddu.InstagramBackend.model.User;
import com.Guddu.InstagramBackend.repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    IAuthenticationRepo authenticationRepo;

    public boolean authenticate(String email, String authToken){
        AuthenticationToken authenticationToken = authenticationRepo.findFirstByTokenValue(authToken);

        if(authenticationToken == null){
            return false;
        }
        String tokenConnectedEmail = authenticationToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);

    }


    public void saveAuthToken(AuthenticationToken authenticationToken){
        authenticationRepo.save(authenticationToken);
    }

    public AuthenticationToken findFirstByUser(User user) {
       return authenticationRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken authenticationToken) {
        authenticationRepo.delete(authenticationToken);
    }
}
