package ru.itis.semesterwork.repository;

import ru.itis.semesterwork.entity.User;

import java.util.List;
import java.util.Optional;

public class OptionalSingleResult {
    public static <T> Optional<T> optionalSingleResult(List<T> objects) {
        if (objects == null) {
            return Optional.empty();
        } else if (objects.size() > 1) {
            throw new IllegalArgumentException();
        } else {
            return objects.stream().findAny();
        }
    }
}
