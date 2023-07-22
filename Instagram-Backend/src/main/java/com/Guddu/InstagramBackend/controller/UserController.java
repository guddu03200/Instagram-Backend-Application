package com.Guddu.InstagramBackend.controller;

import com.Guddu.InstagramBackend.model.*;
import com.Guddu.InstagramBackend.model.dto.SignInInput;
import com.Guddu.InstagramBackend.model.dto.SignUpOutput;
import com.Guddu.InstagramBackend.service.AuthenticationService;
import com.Guddu.InstagramBackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@Validated
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    //signup, signIn and signOut a particular insta user
    @PostMapping("user/signUp")
    public SignUpOutput signUpInstaUser(@RequestBody User user) throws NoSuchAlgorithmException {
        return userService.signUpUser(user);
    }


    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput) {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutUser(String email, String token) {
        if (authenticationService.authenticate(email, token)) {
            return userService.signOutUser(email);
        } else {
            return "Sign out not allowed for non authenticated user.";
        }

    }


    //adding content on instagram

    @PostMapping("post")
    public String createInstaPost(@RequestBody Post post, @RequestParam String email, @RequestParam String token) {
        if (authenticationService.authenticate(email, token)) {
            return userService.createInstaPost(post, email);

        } else {
            return "Not an authenticated user activity!!!";
        }

    }


    @DeleteMapping("post")
    public String removeInstaPost(@RequestParam Integer postId, @RequestParam String email, @RequestParam String token) {
        if (authenticationService.authenticate(email, token)) {
            return userService.removeInstaPost(postId, email);

        } else {
            return "Not an authenticated user activity!!!";
        }

    }


    //commenting functionality on instagram
    @PostMapping("comment")
    public String addComment(@RequestBody Comment comment, @RequestParam String commenterEmail, @RequestParam String commenterToken) {
        if (authenticationService.authenticate(commenterEmail, commenterToken)) {
            return userService.addComment(comment, commenterEmail);
        } else {
            return "Not an authenticated user activity!!!";
        }

    }


    @DeleteMapping("comment")
    public String removeInstaComment(@RequestParam Integer commentId, @RequestParam String email, @RequestParam String token) {
        if (authenticationService.authenticate(email, token)) {
            return userService.removeInstaComment(commentId, email);

        } else {
            return "Not an authenticated user activity!!!";
        }

    }

    //like functionality in instagram
    @PostMapping("like")
    public String addLike(@RequestBody Like like, @RequestParam String likerEmail, @RequestParam String likerAuthToken) {
        if (authenticationService.authenticate(likerEmail, likerAuthToken)) {
            return userService.addLike(like, likerEmail);
        } else {
            return "Not an authenticated user activity!!!";
        }

    }


    @GetMapping("like/count/post/{postId}")
    public String getLikeCountByPost(@PathVariable Integer postId, @RequestParam String userEmail, @RequestParam String authToken) {
        if (authenticationService.authenticate(userEmail, authToken)) {
            return userService.getLikeCountByPost(postId, userEmail);
        } else {
            return "Not an authenticated user activity!!!";
        }

    }



    @DeleteMapping("like")
    public String removeInstaLike(@RequestParam Integer likeId, @RequestParam String email, @RequestParam String token) {
        if (authenticationService.authenticate(email, token)) {
            return userService.removeInstaLike(likeId, email);

        } else {
            return "Not an authenticated user activity!!!";
        }

    }


    //follow functionality in instagram
    @PostMapping("follow")
    public String followUser(@RequestBody Follow follow, @RequestParam String followerEmail, @RequestParam String followerAuthToken) {
        if (authenticationService.authenticate(followerEmail, followerAuthToken)) {
            return userService.followUser(follow, followerEmail);
        } else {
            return "Not an authenticated user activity!!!";
        }

    }


    @DeleteMapping("unfollow/target/{followingId}")
    public String unfollowUser(@PathVariable Integer followingId, @RequestParam String followerEmail, @RequestParam String followerAuthToken) {
        if (authenticationService.authenticate(followerEmail, followerAuthToken)) {
            return userService.unFollowUser(followingId, followerEmail);
        } else {
            return "Not an authenticated user activity!!!";
        }

    }
}

