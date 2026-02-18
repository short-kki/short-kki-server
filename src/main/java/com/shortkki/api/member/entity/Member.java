package com.shortkki.api.member.entity;

import com.shortkki.api.file.entity.FileMetadata;
import com.shortkki.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "oauth_id")
    private String oauthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oauthProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_img_file_id")
    private FileMetadata profileImgFile;

    private LocalDateTime deletedAt;

    public static Member create(
            String email, String name, String oauthId, OAuthProvider oauthProvider,
            FileMetadata profileImgFile
    ) {
        return Member.builder()
                .email(email)
                .name(name)
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .role(Role.USER)
                .profileImgFile(profileImgFile)
                .build();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateProfileImgFile(FileMetadata profileImgFile) {
        this.profileImgFile = profileImgFile;
    }

    public void removeProfileImgFile() {
        this.profileImgFile = null;
    }

    public void updateOAuthInfo(String oauthId, OAuthProvider oauthProvider) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }

    public String getProfileImgUrl() {
        return profileImgFile != null ? profileImgFile.getUrl() : null;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
