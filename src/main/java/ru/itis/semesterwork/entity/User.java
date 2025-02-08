package ru.itis.semesterwork.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer skillpoints;
    private String session_id;
    private String remember_id;
    private String avatar;
    private Float avg_rating;
    private String description;
}
