package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @GetMapping(value = "getTasks")
    public List<Task> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping(value = "getTask")
    public TaskDto getTask(String taskId) {
        return new TaskDto((long) 1, "test_title", "test_content");
    }

    @DeleteMapping(value = "deleteTask")
    public void deleteTask(String taskId) {
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto((long) 2, "test_title", "test_content");
    }

    @PostMapping(value = "createTask")
    public void createTask(TaskDto taskDto) {
    }
}