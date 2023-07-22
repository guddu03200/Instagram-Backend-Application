package com.Guddu.InstagramBackend.service;

import com.Guddu.InstagramBackend.model.*;
import com.Guddu.InstagramBackend.model.dto.SignInInput;
import com.Guddu.InstagramBackend.model.dto.SignUpOutput;
import com.Guddu.InstagramBackend.repository.IUserRepo;
import com.Guddu.InstagramBackend.service.utility.EmailHandler;
import com.Guddu.InstagramBackend.service.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;


    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;


    public SignUpOutput signUpUser(User user) throws NoSuchAlgorithmException {
        boolean singUpStatus = true;
        String signUpStatusMessage = null;


        String newEmail = user.getUserEmail();


        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            singUpStatus = false;
            return new SignUpOutput(singUpStatus, signUpStatusMessage);

        }

        //check if this user email is already exist
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registerd";
            singUpStatus = false;
            return new SignUpOutput(singUpStatus, signUpStatusMessage);
        }

        //hash the password : encrypt the password
        String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

        //save the user with the new encrypted password
        user.setUserPassword(encryptedPassword);
        userRepo.save(user);


        return new SignUpOutput(singUpStatus, "User registerd successfully");


    }




    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;
        boolean singInStatus = true;

        String signInEmail = signInInput.getEmail();


        if (signInEmail == null) {
            signInStatusMessage = "Invalid email";
            singInStatus = false;
        }

        //check if this user email is already exist
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if (existingUser == null) {
            signInStatusMessage = "Email not registerd";
            singInStatus = false;
        }


        //match password

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingUser.getUserPassword().equals(encryptedPassword)) {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail("guddukumar032002@gmail.com", "email testing", authToken.getTokenValue());
                return "Token sent to your email";
            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }



    public String signOutUser(String email) {
        User user = userRepo.findFirstByUserEmail(email);
       AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);

        return "user signOut successfully";
    }

    public String createInstaPost(Post post, String email) {
        User postOwner  = userRepo.findFirstByUserEmail(email);
        post.setPostOwner(postOwner);
        return postService.createInstaPost(post);
    }

    public String removeInstaPost(Integer postId, String email) {
        User user  = userRepo.findFirstByUserEmail(email);
        return postService.removeInstaPost(postId, user);
    }

    public String addComment(Comment comment, String commenterEmail) {


        boolean isPostValid = postService.validatePost(comment.getInstaPost());
        if(isPostValid) {
            User commenter = userRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }else {
            return "Cannot comment on invalid post";
        }
    }

    boolean authorizeCommentRemover(String email, Comment comment){

        String  commentOwnerEmail = comment.getCommenter().getUserEmail();
        String  postOwnerEmail = comment.getInstaPost().getPostOwner().getUserEmail();

        return (postOwnerEmail.equals(email) || commentOwnerEmail.equals(email));

    }
    public String removeInstaComment(Integer commentId, String email) {
        Comment comment = commentService.findComment(commentId);
        if(comment !=null){
            if(authorizeCommentRemover(email, comment)){
                commentService.removeComment(comment);
                return "Comment deleted successfully";
            }else {
                return "Un-authorized delete not allowed";
            }
        }else {
            return "Invalid comment";
        }
    }

    public String addLike(Like like, String likerEmail) {
        Post instaPost = like.getInstaPost();
        boolean isPostValid = postService.validatePost(instaPost);

        if (isPostValid) {

            User liker = userRepo.findFirstByUserEmail(likerEmail);
            if(likeService.isLikeAllowedOnThisPost(instaPost, liker)){
                like.setLiker(liker);
                return likeService.addLike(like);
            }else {
                return "Already liked";
            }

        }else {
            return "Cannot like on invalid post";
        }

    }

    public String getLikeCountByPost(Integer postId, String userEmail) {

        Post validPost = postService.getPostById(postId);

        if(validPost != null){

            Integer likeCountForPost = likeService.getLikeCountForPost(validPost);
            return String.valueOf(likeCountForPost);
        }else {
            return "Cannot count like on invalid postId";
        }
    }


    private boolean authorizeLikeRemover(String email, Like like) {

        String likeOwnerEmail = like.getLiker().getUserEmail();
        return email.equals(likeOwnerEmail);
    }

    public String removeInstaLike(Integer likeId, String email) {
        Like like = likeService.findLike(likeId);
        if(like !=null){
            if(authorizeLikeRemover(email, like)){
                likeService.removeLike(like);
                return "Like deleted successfully";
            }else {
                return "Un-authorized delete not allowed";
            }
        }else {
            return "Invalid like";
        }
    }


    public String followUser(Follow follow, String followerEmail) {

        User followTargeUser = userRepo.findById(follow.getCurrentUser().getUserId()).orElse(null);

        User follower = userRepo.findFirstByUserEmail(followerEmail);
        if(followTargeUser != null){
            if(followService.isFollowAllowed(followTargeUser, follower)){
                followService.startFollowing(follow);

                return follower.getUserHandle() + "is now following" + followTargeUser.getUserHandle();
            }else{
                return follower.getUserHandle() + "already follows" + followTargeUser.getUserHandle();
            }


        }else {
            return "User to be followed is invalid";
        }
    }


    private boolean authorizeUnfollow(String email, Follow follow) {
        String targetEmail = follow.getCurrentUser().getUserEmail();
        String followerEmail = follow.getCurrentUser().getUserEmail();

        return targetEmail.equals(email) || followerEmail.equals(email);
    }


    public String unFollowUser(Integer followId, String followerEmail) {
        Follow follow = followService.findFollow(followId);
        if(follow != null){
            if(authorizeUnfollow(followerEmail, follow)){
                followService.unfollow(follow);
                return follow.getCurrentUser().getUserHandle() + " not followed by " + followerEmail;
            }else {
                return "Un-authorized unfollow not allowed";
            }
        }else {
            return "Invalid follow mapping";
        }
    }


}
