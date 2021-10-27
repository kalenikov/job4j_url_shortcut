package ru.job4j.links.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class RegResponse {
    private boolean registration;
    private String login;
    private String password;
}