package ru.job4j.links.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    private String code;
    @CreationTimestamp
    private Timestamp created;
    private int count;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public static Link of(String url, String code, User owner) {
        Link link = new Link();
        link.setUrl(url);
        link.setCode(code);
        link.setOwner(owner);
        return link;
    }
}
