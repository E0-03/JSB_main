package com.jsbserver.jsbAPI.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsbserver.jsbAPI.entity.Plan;
import com.jsbserver.jsbAPI.service.PlanService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PlanController {
  @Autowired
  PlanService planService;

  @PostMapping("/createPlan")
  public ResponseEntity<Map<String, Object>> createPlan(@RequestBody Plan plan){
    Map<String, Object> data = new HashMap<>();
    try{
      planService.createPlan(plan);
      data.put("error", null);
      data.put("response", null);
      data.put("message", "Plan successfully created");
      return new ResponseEntity<>(data, HttpStatus.OK);
    }catch(Exception e){
      data.put("error", e.getMessage());
      data.put("response", "There was a error");
      data.put("message", "Error");
      return new ResponseEntity<>(data, HttpStatus.OK);
    }
  }
}
