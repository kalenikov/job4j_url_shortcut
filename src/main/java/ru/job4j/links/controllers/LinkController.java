package ru.job4j.links.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.job4j.links.dto.LinkRequest;
import ru.job4j.links.dto.LinkResponse;
import ru.job4j.links.dto.LinkStatisticResponse;
import ru.job4j.links.exception.LinkException;
import ru.job4j.links.model.User;
import ru.job4j.links.service.LinkService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/links/")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping("/convert")
    public LinkResponse convert(@Valid @RequestBody LinkRequest linkRequest, Authentication auth) {
        var link = linkService.getByUrl(
                linkRequest.getUrl(),
                (User) auth.getPrincipal()
        );
        return new LinkResponse(link.getCode());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> get(@PathVariable String code) throws LinkException {
        var link = linkService.getByCode(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(link.getUrl()))
                .build();
    }

    @GetMapping("/statistic")
    public List<LinkStatisticResponse> getStatistic(Authentication auth) throws LinkException {
        return linkService
                .findByOwner((User) auth.getPrincipal())
                .stream().map(link -> new LinkStatisticResponse(link.getUrl(), link.getCount()))
                .collect(Collectors.toList());
    }
}