package com.example.ToDoist.Tasks;


import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class TaskRepository
{
    private static JdbcClient jdbcClient;

    TaskRepository(JdbcClient jdbcClient)
    {
        TaskRepository.jdbcClient = jdbcClient;
    }

    List<Task> findAll()
    {
        return jdbcClient.sql("SELECT * FROM TASKS;")
                .query(Task.class)
                .list();
    }

    public Optional<Task> getTask(Integer id) {
        return jdbcClient.sql("SELECT * FROM TASKS WHERE id = :id")
                .param("id", id)
                .query(Task.class)
                .optional();
    }

    public void create(Task task) {
        int affectedRows = jdbcClient.sql("INSERT INTO tasks(name, status) values(:name, :status);")
                .params(Map.of(
                        "name", task.name(),
                        "status", task.status().getCode()
                ))
                .update();

    }

    public void update(Integer id, Task task) throws Exception{
        int affectedRows = jdbcClient.sql("UPDATE tasks SET name = :name, status = :status WHERE id = :id")
                .param("name", task.name())
                .param("status", task.status().getCode())
                .param("id", id)
                .update();

        if(affectedRows == 0)
        {
            throw new Exception("No rows updated, since Resource not exists");
        }
    }

    public void remove(Integer id) {
        jdbcClient.sql("DELETE FROM tasks WHERE id = :id")
                .param("id", id)
                .update();
    }
}
