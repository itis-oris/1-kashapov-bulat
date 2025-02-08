package ru.itis.semesterwork.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Skill {
    private Integer id;
    private String name;
    private User user;
    private String category;
    private Float rating;
    private String description;
}
