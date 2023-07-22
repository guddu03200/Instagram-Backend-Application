package com.Guddu.InstagramBackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpOutput {
    private boolean singUpStatus;
   private String signUpStatusMessage;

}
