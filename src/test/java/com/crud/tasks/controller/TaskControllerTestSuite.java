package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskMapper taskMapper;

    @MockBean
    DbService dbService;

    @Test
    public void shouldReturnEmptyTasksList() throws Exception {
        //Given
        final List<Task> tasks = new ArrayList<>();
        final List<TaskDto> taskDtos = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnAllTasks() throws Exception {
        //Given
        final Task task1 = new Task(1L, "title1", "content1");
        final Task task2 = new Task(2L, "title2", "content2");
        final List<Task> tasks = new ArrayList<>(Arrays.asList(task1, task2));

        final TaskDto taskDto1 = new TaskDto(1L, "title1", "content1");
        final TaskDto taskDto2 = new TaskDto(2L, "title2", "content2");
        final List<TaskDto> taskDtos = new ArrayList<>(Arrays.asList(taskDto1, taskDto2));

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnRequestedTask() throws Exception {
        //Given
        final Task task = new Task(1L, "title1", "content1");
        final TaskDto taskDto = new TaskDto(1L, "title1", "content1");

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.content", is("content1")));
    }

    @Test(expected = TaskNotFoundException.class)
    public void shouldThrowTaskNotFoundException() throws TaskNotFoundException {
        //Given
        when(dbService.getTask(1L)).thenThrow(TaskNotFoundException.class);

        //When & Then
        try {
            mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new TaskNotFoundException();
        }
    }

    @Test
    public void shouldUpdateTask() throws Exception{
        //Given
        final Task task = new Task(1L, "title", "content");
        final TaskDto taskDto = new TaskDto(1L, "title", "content");
        final Task updatedTask = new Task(1L, "title", "updated_content");
        final TaskDto updatedTaskDto = new TaskDto(1L, "title", "updated_content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(updatedTaskDto);
        when(taskMapper.mapToTaskDto(updatedTask)).thenReturn(updatedTaskDto);
        when(dbService.saveTask(any(Task.class))).thenReturn(updatedTask);

        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(updatedTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("updated_content")));

    }

    @Test
    public void createTask() {
    }


    @Test
    public void deleteTask() {
    }




}