package ru.itis.semesterwork.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Rate {
    private Integer id;
    private Skill skill;
    private User user;
    private Integer rate;
    private String message;
}
