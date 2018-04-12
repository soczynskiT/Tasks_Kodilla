package com.crud.tasks.controller;


import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;


    @GetMapping(value = "getTasks")
    public List<TaskDto> getTasks() {
        System.out.println(service.getAllTasks());
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @GetMapping(value = "getTask")
    public TaskDto getTask(@RequestParam final Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @DeleteMapping(value = "deleteTask")
    public void deleteTask(@RequestParam final Long taskId) {
        service.deleteTaskById(taskId);
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(@RequestBody final TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @PostMapping(value = "createTask", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody final TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }
}