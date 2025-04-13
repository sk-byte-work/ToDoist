package com.example.ToDoist.Tasks;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController
{
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    @GetMapping("")
    List<Task> findAll(){
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    Task getTask(@PathVariable Integer id) throws Exception{
        Optional<Task> task = taskRepository.getTask(id);
        if(task.isEmpty())
        {
            throw new Exception("Resource not found");
        }

        return task.get();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    void createTask(@RequestBody Task task){
        taskRepository.create(task);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    void updateTask(@PathVariable Integer id, @RequestBody Task task) throws Exception {
        taskRepository.update(id, task);
    }

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Integer id){
        taskRepository.remove(id);
    }
}
