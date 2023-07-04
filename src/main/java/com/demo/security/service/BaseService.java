package com.demo.security.service;

import java.util.List;

public interface BaseService<T, Key> {
    T add(T a);

    List<T> findAll();

    T findById(Key id);

    T edit(T a);

    boolean remove(Key id);
}
