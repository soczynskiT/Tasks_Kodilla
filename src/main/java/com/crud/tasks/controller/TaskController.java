package com.crud.tasks.controller;


import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @Autowired
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        final List<Task> allTasksList = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(allTasksList);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable("id") final Long taskId) throws TaskNotFoundException {
        final Task task = service.getTask(taskId).orElseThrow(TaskNotFoundException::new);
        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") final Long taskId) {
        service.deleteTaskById(taskId);
    }

    @PutMapping
    public TaskDto updateTask(@RequestBody final TaskDto taskDto) {
        final Task updatedTask = service.saveTask(taskMapper.mapToTask(taskDto));
        return taskMapper.mapToTaskDto(updatedTask);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody final TaskDto taskDto) {
        final Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
    }
}