package me.potato.finaltodo.store.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userNum;

    @NotNull
    private String userLoginId;

    @NotNull
    private String userName;

    @ColumnDefault("' '")
    private String profileIntro;

    @ColumnDefault("0")
    private Long contentCount;

    @ColumnDefault("0")
    private Long friendCount;

    @ColumnDefault("'default'")
    private String originalFileName;

    @ColumnDefault("'default'")
    private String storedFileName;
}
