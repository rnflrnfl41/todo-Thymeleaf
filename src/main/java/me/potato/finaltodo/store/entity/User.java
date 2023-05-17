package me.potato.finaltodo.store.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(unique = true)
    private String loginId;
    private String name;
    private String password;

}
