package ru.job4j.links.service;

import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

@Component
public class MurmurLinkConvertor implements LinkConvertor {
    @Override
    public String urlToCode(String link) {
        return Hashing.murmur3_32()
                .hashString(link, StandardCharsets.UTF_8).toString();
    }
}