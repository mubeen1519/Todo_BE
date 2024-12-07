package com.stellerByte.todo.controller;

import com.stellerByte.todo.entity.Tasks;
import com.stellerByte.todo.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private TaskService taskService;


    @GetMapping("/all")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        try {
            List<Tasks> tasks = taskService.getAllTask();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable Long id) {
        try {
            Tasks taskById = taskService.getTaskById(id);
            return new ResponseEntity<>(taskById, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Tasks> createTask(@RequestBody Tasks tasks) {
        try {
            Tasks createdTask = taskService.createTask(tasks);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Tasks> deleteTaskById(@PathVariable Long id) {
        try {
            taskService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@RequestBody Tasks tasks,@PathVariable Long id) {
        try {
            taskService.updateTask(tasks,id);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Tasks>> getAllCompletedTasks() {
        List<Tasks> completedList = taskService.getCompletedTasks();
        if (!completedList.isEmpty()) {
            return new ResponseEntity<>(completedList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/incomplete")
    public ResponseEntity<List<Tasks>> getAllIncompleteTasks() {
        List<Tasks> completedList = taskService.getNotCompletedTasks();
        if (!completedList.isEmpty()) {
            return new ResponseEntity<>(completedList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
