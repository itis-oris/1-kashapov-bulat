package ru.itis.semesterwork.service;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.SkillRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SkillService {
    private SkillRepository skillRepository;

    public List<Skill> getByUser(User user) {
        return skillRepository.findByUser(user);
    }
    public Optional<Skill> getById(Integer id) {
        return  skillRepository.findByAttribute("id", id);
    }
    public Optional<Skill> getByUserAndName(User user,String name) {
        return skillRepository.findByUserAndName(user,name);
    }
    public Integer countByUser(User user) {
        return getByUser(user).size();
    }
    public List<Skill> getByUser(User user, int offset, int limit) {
        return skillRepository.findByUser(user, offset, limit);
    }
    public boolean addSkill(Skill skill) {
        return skillRepository.insert(skill);
    }
    public boolean update(Skill skill, String attribute, Object value) {
        return skillRepository.updateAttribute(skill, attribute, value);
    }

    public List<String> getAllCategories() {
        return skillRepository.getAllCategories();
    }
}
