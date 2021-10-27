package ru.job4j.links.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.links.exception.LinkException;
import ru.job4j.links.model.Link;
import ru.job4j.links.model.User;
import ru.job4j.links.repo.LinkRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Service
public class LinkService {
    @Autowired
    private LinkRepository links;
    @Autowired
    private LinkConvertor convertor;

    public Link getByUrl(String url, User user) {
        return links.findByUrl(url)
                .orElseGet(() -> {
                    var code = convertor.urlToCode(url);
                    return links.save(Link.of(url, code, user));
                });
    }

    public Link getByCode(@Valid @NotBlank String code) throws LinkException {
        Link link = links.findByCode(code)
                .orElseThrow(() -> new LinkException("code not found"));
        link.setCount(link.getCount() + 1);
        links.save(link);
        return link;
    }

    public List<Link> findByOwner(User user) {
        return links.findByOwner(user);
    }
}