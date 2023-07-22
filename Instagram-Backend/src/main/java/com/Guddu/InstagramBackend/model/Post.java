package com.Guddu.InstagramBackend.model;

import com.Guddu.InstagramBackend.model.Enums.PostType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String postContent;
    private String postCaption;
    private String postLocation;
    private PostType postType;
    //this hide postCreatedTimeStamp in json but not in database
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime postCreatedTimeStamp;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fk_post_user_id")
    private User postOwner;


}
