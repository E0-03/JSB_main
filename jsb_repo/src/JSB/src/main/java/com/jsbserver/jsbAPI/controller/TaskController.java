package com.jsbserver.jsbAPI.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.PostMapping; 
// import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.jsbserver.jsbAPI.entity.Account;
import com.jsbserver.jsbAPI.entity.Task;
import com.jsbserver.jsbAPI.entity.Group;

import com.jsbserver.jsbAPI.security.SecurityConstants;
import com.jsbserver.jsbAPI.service.AccountService;
import com.jsbserver.jsbAPI.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor; 

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/getAllTasks")
    public ResponseEntity<Object> getAllTasks(@RequestBody Map<String, String> requestBody) {
    try {
        String app = requestBody.get("app");
        List<Task> task = taskService.getAllTasks(app);

    if (task != null) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", null);
        List<Task> taskList = taskService.getAllTasks(app);
        data.put("message", taskList);
        return new ResponseEntity<>(data, HttpStatus.OK); // Task retrieval successful
    } else {
        Map<String, Object> data= new HashMap<>();
        data.put("error", "No tasks found");
        data.put("message", null);
        return new ResponseEntity<>(data, HttpStatus.OK); // Task retrieval failed
    }
    } catch (Exception e) {
        System.out.println(e.getMessage());
        Map<String, Object> datafail = new HashMap<>();
        datafail.put("error", e.toString());
        datafail.put("message", "check input criteria and try again!");
        return new ResponseEntity<>(datafail, HttpStatus.OK); // Internal server error
        }
    }

    @PostMapping("/getOneTask")
    public ResponseEntity<Object> getOneTask(@RequestBody Map<String, String> requestBody) {
    try {
        String taskId = requestBody.get("Task_id");
        Task task = taskService.getOneTask(taskId);

        if (task != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("err", null);

            Map<String, Object> taskDetails = new HashMap<>();
            taskDetails.put("taskName", task.getTask_name());
            taskDetails.put("taskDescription", task.getTask_description());
            taskDetails.put("taskNotes", task.getTask_notes());
            taskDetails.put("taskPlan", task.getTask_plan());
            taskDetails.put("taskAppAcronym", task.getTask_app_Acronym());
            taskDetails.put("taskState", task.getTask_state());
            taskDetails.put("taskCreator", task.getTask_creator());
            taskDetails.put("taskOwner", task.getTask_owner());
            taskDetails.put("taskCreateDate", task.getTask_create_date());
            taskDetails.put("taskId", task.getTask_id()); // Assuming you want to include taskId

            data.put("message", taskDetails);

            return new ResponseEntity<>(data, HttpStatus.OK); // Task retrieval successful
        } else {
            Map<String, Object> datafail = new HashMap<>();
            datafail.put("err", "Task not found");
            datafail.put("message", null);
            return new ResponseEntity<>(datafail, HttpStatus.OK); // Task retrieval failed
        }
    } catch (Exception e) {
        Map<String, Object> datafail = new HashMap<>();
        datafail.put("err", e.toString());
        datafail.put("message", "Check input criteria and try again!");
        return new ResponseEntity<>(datafail, HttpStatus.OK); // Internal server error
    }}

    @PostMapping("/createTask")
    public ResponseEntity<Object> createTask(@RequestBody Task newTask) {
        Map<String, Object> data = new HashMap<>();

        try {
            String taskId = taskService.createTask(newTask);
            data.put("error", null);
            data.put("message", taskId + " successfully created!");
            return new ResponseEntity<>(data, HttpStatus.OK); // Task creation successful
        } catch (Exception e) {
            data.put("error", e);
            data.put("response", "Failure, try again!");
            data.put("message", "check input criteria and try again!");
            return new ResponseEntity<>(data, HttpStatus.OK); // Internal server error
        }
}

    }