package com.stellerByte.todo.service.task;

import com.stellerByte.todo.entity.Tasks;
import com.stellerByte.todo.entity.Users;
import com.stellerByte.todo.repository.TaskRepository;
import com.stellerByte.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public Tasks createTask(Tasks tasks) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found in SecurityContext");
        }

        String username = authentication.getName();
        log.info("Authenticated username: {}", username);

        Users users = userRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("User not found for username: " + username);
        }

        tasks.setUser(users);
        return taskRepository.save(tasks);
    }

    public Tasks getTaskById(Long id) {
        return taskRepository.getById(id);
    }

    public void deleteById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByUsername(username);
        Tasks task = taskRepository.findById(id).orElseThrow();

        if (task.getUser() == user)
            taskRepository.deleteById(id);
    }

    public List<Tasks> getAllTask() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByUserUsername(username);
    }

    public List<Tasks> getCompletedTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByUserUsernameAndCompletedTrue(username);
    }

    public List<Tasks> getNotCompletedTasks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return taskRepository.findByUserUsernameAndCompletedFalse(username);
    }

    public Tasks updateTask(Tasks tasks, Long id) {
        Tasks existingTask = getTaskById(id);
        existingTask.setTitle(tasks.getTitle());
        existingTask.setDescription(tasks.getDescription());
        existingTask.setDateTime(tasks.getDateTime());
        existingTask.setCompleted(tasks.getCompleted());
        return taskRepository.save(existingTask);
    }

}
