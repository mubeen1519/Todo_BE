package com.stellerByte.todo.repository;

import com.stellerByte.todo.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks,Long> {
    List<Tasks> findByUserUsernameAndCompletedTrue(String username);
    List<Tasks> findByUserUsernameAndCompletedFalse(String username);
    List<Tasks> findAll();
    List<Tasks> findByUserUsername(String username);
    Tasks getById(Long id);
}
