package ru.itis.semesterwork.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Notification {
    private int id;
    private User from;
    private User to;
    private Skill skill;
    private String type;
    private String message;
    private String status;
}
