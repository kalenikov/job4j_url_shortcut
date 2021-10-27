package ru.job4j.links.service;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.links.model.Role;
import ru.job4j.links.model.User;
import ru.job4j.links.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private PasswordGenerator passwordGenerator;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return repo
                .findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getBySite(String site) {
        var user = repo.findBySite(site);
        if (user == null) {
            user = new User();
            user.setSite(site);
            user.setUsername(generateUsername());
            user.setRole(new Role(1L, "ROLE_USER"));
            user.setNew();
        }
        var password = generatePassword();
        user.setPassword(encoder.encode(password));
        user.setPasswordPlainText(password);
        repo.save(user);
        return user;
    }

    private String generatePassword() {
        return passwordGenerator.generatePassword(10,
                new CharacterRule(EnglishCharacterData.Digit),
                new CharacterRule(EnglishCharacterData.LowerCase));
    }

    private String generateUsername() {
        return java.util.UUID.randomUUID().toString();
    }
}