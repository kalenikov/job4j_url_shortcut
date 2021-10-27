package ru.job4j.links.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}