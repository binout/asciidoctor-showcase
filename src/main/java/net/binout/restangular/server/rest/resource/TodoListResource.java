/*
 * Copyright 2014 Benoit Prioux
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

package net.binout.restangular.server.rest.resource;

import net.binout.restangular.server.dao.TodoRepository;
import net.binout.restangular.server.model.Todo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/todos")
public class TodoListResource {

    @Inject
    private TodoRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Todo> list() {
        return repository.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Todo todo) throws IOException {
        repository.create(todo);
    }

}
