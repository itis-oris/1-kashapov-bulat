package ru.itis.semesterwork.service;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Lesson;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.LessonRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LessonService {
    private LessonRepository lessonRepository;

    public List<Lesson> getBySkillAndStudent(Skill skill, User student) {
        return lessonRepository.findBySkillAndStudent(skill, student);
    }
    public Optional<Lesson> getById(int id) {
        return lessonRepository.findByAttribute("id", id);
    }
    public List<Lesson> getByUser(User user) {
        return lessonRepository.findByUser(user);
    }
    public List<Lesson> getByTeacher(User user) {
        return lessonRepository.findByTeacher(user);
    }
    public List<Lesson> getByStudent(User user) {
        return lessonRepository.findByStudent(user);
    }

    public List<Lesson> getByUser(User user, int offset, int limit) {
        return lessonRepository.findByUser(user, offset, limit);
    }
    public List<Lesson> getByTeacher(User user, int offset, int limit) {
        return lessonRepository.findByTeacher(user, offset, limit);
    }
    public List<Lesson> getByStudent(User user, int offset, int limit) {
        return lessonRepository.findByStudent(user, offset, limit);
    }

    public Integer countByUser(User user) {
        return getByUser(user).size();
    }
    public Integer countByTeacher(User user) {
        return getByTeacher(user).size();
    }
    public Integer countByStudent(User user) {
        return getByStudent(user).size();
    }

    public boolean addLesson(Lesson lesson) {
        return lessonRepository.insert(lesson);
    }
    public boolean updateAttribute(Integer id, String attribute, Object value) {
        return lessonRepository.updateAttribute(id, attribute, value);
    }
}
