package ru.job4j.links.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.links.exception.LinkException;
import ru.job4j.links.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
class LinkServiceTest {

    @Autowired
    private LinkService linkService;

    @Test
    @WithMockUser
    void whenLinkToCode(Authentication auth) {
        var url = "http://localhost:8080";
        var link = linkService.getByUrl(url, (User) auth.getPrincipal());
        assertEquals("ebced246", link.getCode());
    }

    @Test
    void whenGetLinkByCodeAndNotFound() {
        var ex = assertThrows(
                LinkException.class,
                () -> linkService.getByCode("ebced246")
        );
        assertEquals("code not found", ex.getMessage());
    }

    @Test
    @WithMockUser
    void whenGetLinkByCodeAndOK(Authentication auth) throws LinkException {
        var url = "http://localhost:8080";
        var code = linkService.getByUrl(url, (User) auth.getPrincipal()).getCode();
        var link = linkService.getByCode(code);
        assertEquals(url, link.getUrl());
    }
}