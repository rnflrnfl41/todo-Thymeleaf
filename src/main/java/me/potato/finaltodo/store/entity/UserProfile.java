package me.potato.finaltodo.store.entity;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;
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

    private String profileIntro;

    private Long contentCount;

    private Long friendCount;

    private String originalFileName;

    private String storedFileName;

    @PrePersist
    public void prePersist(){
        this.storedFileName = this.storedFileName == null ? "default" : this.storedFileName;
        this.originalFileName = this.originalFileName == null ? "default" : this.originalFileName;
        this.friendCount = this.friendCount == null ? 0 : this.friendCount;
        this.contentCount = this.contentCount == null ? 0 : this.contentCount;
        this.profileIntro = this.profileIntro == null ? "프로필 소개란" : this.profileIntro;
    }
}
