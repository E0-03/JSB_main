package com.jsbserver.jsbAPI.service;


import com.jsbserver.jsbAPI.entity.Account;
import com.jsbserver.jsbAPI.entity.Task;
import com.jsbserver.jsbAPI.repository.TaskDAO;
 
import java.util.List; 
import java.util.stream.Collectors; 


import org.springframework.beans.factory.annotation.Autowired; 

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service; 
import org.springframework.web.bind.annotation.RequestBody; 

@Service
public class TaskService {

    @Autowired
    TaskDAO taskDAO;

    public List<Task> getAllTasks(String app) {
        return taskDAO.getAllTasks(app);
    }

    public Task getOneTask(String taskId) {
        return taskDAO.getOneTask(taskId);
    }

    public String createTask(Task newTask) {
        return taskDAO.createTask(newTask);
    }
}