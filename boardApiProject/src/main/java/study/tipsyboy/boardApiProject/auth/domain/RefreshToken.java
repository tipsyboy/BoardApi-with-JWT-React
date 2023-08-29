package study.tipsyboy.boardApiProject.auth.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {

    @Id
    @Column(name = "rt_key")
    private String email;

    private String token; // refreshToken 값

    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }
}