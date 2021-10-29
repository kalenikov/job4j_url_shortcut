package ru.job4j.links.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.links.model.Link;
import ru.job4j.links.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
    Optional<Link> findByCode(String code);

    Optional<Link> findByUrl(String url);

    List<Link> findByOwner(User owner);

    @Modifying
    @Query("UPDATE Link l SET l.count = l.count + 1  WHERE l.id = :id")
    void incCounter(Integer id);
}