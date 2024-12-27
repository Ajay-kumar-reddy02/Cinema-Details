package com.cinemaDetails.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponseDTo {
    private String token;
    private String tokenType = "Bearer";
}
