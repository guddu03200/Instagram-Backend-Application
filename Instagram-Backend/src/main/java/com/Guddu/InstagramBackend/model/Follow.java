package com.Guddu.InstagramBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Integer followId;



    @ManyToOne
    @JoinColumn(name = "fk_actual_user_id")
    User currentUser;


    @ManyToOne
    @JoinColumn(name = "fk_follower_of_current_user_id")
    User follower;
}
