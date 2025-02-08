package ru.itis.semesterwork.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll();
    List<T> findAll(int offset, int limit);
    Optional<T> findByAttribute(String attribute, Object value);
    boolean update(T object);
    public boolean insert(T object);
}
