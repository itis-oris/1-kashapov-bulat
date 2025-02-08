package ru.itis.semesterwork.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Lesson {
    private int id;
    private User teacher;
    private User student;
    private Skill teacherSkill;
    private String status;
}
