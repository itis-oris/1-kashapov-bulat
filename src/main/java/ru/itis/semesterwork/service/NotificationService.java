package ru.itis.semesterwork.service;

import lombok.AllArgsConstructor;
import ru.itis.semesterwork.entity.Notification;
import ru.itis.semesterwork.entity.User;
import ru.itis.semesterwork.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class NotificationService {
    private NotificationRepository notificationRepository;

    public boolean add(Notification notification) {
        return notificationRepository.insert(notification);
    }
    public List<Notification> getByUser(User user) {
        return notificationRepository.findByUser(user);
    }
    public List<Notification> getByUser(User user, int offset, int limit) {
        return notificationRepository.findByUser(user, offset, limit);
    }
    public Integer countByUser(User user) {
        return getByUser(user).size();
    }
    public boolean update(Integer id, String attribute, Object value) {
        return notificationRepository.update(id, attribute, value);
    }
    public Optional<Notification> getById(int id) {
        return notificationRepository.findByAttribute("id", id);
    }
}
