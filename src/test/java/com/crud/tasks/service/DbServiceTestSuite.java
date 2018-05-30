package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTestSuite {
    @Autowired
    DbService dbService;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void shouldSaveTaskInDataBase() {
        //Given
        final Task task = new Task("test_name", "test_content");

        //When
        dbService.saveTask(task);

        //Then
        final Long id = task.getId();
        final Optional<Task> readTask = taskRepository.findById(id);
        assertEquals(id, readTask.get().getId());
        assertEquals("test_name", readTask.get().getTitle());
        assertEquals("test_content", readTask.get().getContent());

        //Clean
        taskRepository.delete(id);
    }

    @Test
    public void shouldReturnAnExistingTask() {
        //Given
        final Task task = new Task("test_name", "test_content");
        dbService.saveTask(task);
        final Long id = task.getId();

        //When
        final Optional<Task> readTask = dbService.getTask(id);

        //Then
        assertEquals(id, readTask.get().getId());
        assertEquals("test_name", readTask.get().getTitle());
        assertEquals("test_content", readTask.get().getContent());

        //Clean
        taskRepository.delete(id);
    }

    @Test
    public void shouldReturnEmptyOptionalForNotExistingTask() {
        //Given
        final Long notExistingId = 9999L;
        //When
        final Optional<Task> readTask = dbService.getTask(notExistingId);

        //Then
        assertEquals(Optional.empty(), readTask);
    }

    @Test
    public void shouldDeleteTaskById() {
        //Given
        final Task task = new Task("test_name", "test_content");
        dbService.saveTask(task);
        final Long id = task.getId();

        //When
        dbService.deleteTaskById(id);

        //Then
        final Optional<Task> readTask = taskRepository.findById(id);
        assertEquals(Optional.empty(), readTask);
    }

    @Test
    public void shouldReturnAllTasks() {
        //Given
        final Task task = new Task("test_name", "test_content");
        final Task task2 = new Task("test_name2", "test_content2");
        dbService.saveTask(task);
        dbService.saveTask(task2);
        final Long id1 = task.getId();
        final Long id2 = task2.getId();

        //When
        final List<Task> readTasks = dbService.getAllTasks();

        //Then
        assertEquals(2, readTasks.size());

        //Clean
        taskRepository.delete(id1);
        taskRepository.delete(id2);
    }
}