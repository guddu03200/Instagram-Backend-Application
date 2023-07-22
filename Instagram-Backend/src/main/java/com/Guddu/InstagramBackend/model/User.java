package com.Guddu.InstagramBackend.model;

import com.Guddu.InstagramBackend.model.Enums.AccountType;
import com.Guddu.InstagramBackend.model.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private String userHandle;
    private String userBio;
    @Email
    @Column(unique = true)
    private String userEmail;
    @NotBlank
    private String userPassword;
    @Enumerated(EnumType.STRING)
    private Gender userGender;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean blueTick;

    private AccountType accountType;


}
