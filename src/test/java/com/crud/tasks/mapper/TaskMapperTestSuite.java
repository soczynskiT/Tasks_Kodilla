package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {
    @Autowired
    TaskMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloMapperTestSuite.class);


    @Test
    public void mapToTask() {
        //Given
        final TaskDto taskDto = new TaskDto(1L, "title", "content");
        //When
        final Task task = mapper.mapToTask(taskDto);
        //Then
        assertEquals(new Long(1), task.getId());
        assertEquals("title", task.getTitle());
        assertEquals("content", task.getContent());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        final Task task = new Task(1L, "title", "content");
        //When
        final TaskDto taskDto = mapper.mapToTaskDto(task);
        //Then
        assertEquals(new Long(1), taskDto.getId());
        assertEquals("title", taskDto.getTitle());
        assertEquals("content", taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        final Task task = new Task(1L, "title", "content");
        final Task task2 = new Task(2L, "title2", "content2");
        final List<Task> tasks = new ArrayList<>(Arrays.asList(task, task2));
        //When
        final List<TaskDto> taskDtos = mapper.mapToTaskDtoList(tasks);
        //Then
        assertEquals(2, taskDtos.size());

        LOGGER.info("Checking content of mapped task list...");
        for (TaskDto taskDto : taskDtos) {
            System.out.println("Task ID: " + taskDto.getId() +
                    "\nTask title: " + taskDto.getTitle() +
                    "\nTask content: " + taskDto.getContent());
        }
    }
}