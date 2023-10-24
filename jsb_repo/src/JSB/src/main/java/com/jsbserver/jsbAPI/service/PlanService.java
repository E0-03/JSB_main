package com.jsbserver.jsbAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.jsbserver.jsbAPI.entity.Plan;
import com.jsbserver.jsbAPI.repository.PlanDAO;

@Service
public class PlanService {
  @Autowired
  PlanDAO planDAO;

  public void createPlan(@RequestBody Plan plan){
    planDAO.createPlan(plan);
  }
}
