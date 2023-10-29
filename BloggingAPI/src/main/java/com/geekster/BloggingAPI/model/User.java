package com.geekster.BloggingAPI.model;

import com.geekster.BloggingAPI.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String userHandle;
    @Column(unique = true)  //new thing  -
    private String userEmail;

    private String userPassword;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is required")
    private Gender userGender;
}
