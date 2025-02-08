package ru.itis.semesterwork.service;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Rate;
import ru.itis.semesterwork.entity.Skill;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.RateRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RateService {
    private RateRepository rateRepository;
    public boolean add(Rate rate) {
        return rateRepository.insert(rate);
    }
    public Optional<Rate> getByUserAndSkill(User user, Skill skill) {
        return rateRepository.findByUserAndSkill(user, skill);
    }
    public List<Rate> getBySkill(Skill skill) {
        return rateRepository.findAllByAttribute("user_skill_id", skill.getId());
    }
    public Integer getCountBySkill(Skill skill) {
        return getBySkill(skill).size();
    }
}
