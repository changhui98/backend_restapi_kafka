package com.gbg.userservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "p_user")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_no")
    private UUID id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_pw")
    private String password;

    public static User of(String username, String password) {
        User user = new User();
        user.username = username;
        user.password = password;
        return user;
    }

    private User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
