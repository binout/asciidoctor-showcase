/*
 * Copyright 2013 Olivier Croisier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.binout.restangular.server.dao;

import net.binout.restangular.server.model.Todo;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InMemoryTodoRepository implements TodoRepository {

    private final Map<Long, Todo> repository = new ConcurrentSkipListMap<>();
    private final AtomicLong IDS = new AtomicLong(0);

    @PostConstruct
    public void init() {
        Arrays.asList(
                new Todo(1L, "Learn AngularJS", "HTML is great for declaring static documents, but it falters when we try to use it for declaring dynamic views in web-applications. AngularJS lets you extend HTML vocabulary for your application. The resulting environment is extraordinarily expressive, readable, and quick to develop. "),
                new Todo(2L, "Use Twitter Bootstrap", "Sleek, intuitive, and powerful mobile-first front-end framework for faster and easier web development."),
                new Todo(3L, "Integrate with Restlet", "The leading web API framework for Java")).forEach(this::create);
    }

    @Override
    public List<Todo> list() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Todo get(Long id) {
        return repository.get(id);
    }

    @Override
    public void create(Todo todo) {
        long id = IDS.getAndIncrement();
        todo.setId(id);
        repository.put(id, todo);
    }

    @Override
    public void update(Todo todo) {
        repository.put(todo.getId(), todo);
    }

    @Override
    public boolean delete(Long id) {
        return repository.remove(id) != null;
    }
}
