package net.binout.restangular.server.dao;

import net.binout.restangular.server.model.Todo;

import java.util.List;

public interface TodoRepository {

    List<Todo> list();

    Todo get(Long id);

    void create(Todo todo);

    void update(Todo todo);

    boolean delete(Long id);
}
