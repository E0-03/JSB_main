package com.jsbserver.jsbAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.jsbserver.jsbAPI.entity.Group;
import com.jsbserver.jsbAPI.repository.GroupDAO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupService {

  @Autowired
    //GroupRepository groupRepository;
    GroupDAO groupDAO;

    //   public ResponseEntity<HttpStatus> createGroup(Group newGroup) {
    //     try {
    //         // Add validation or business logic here before saving
    //         groupRepository.save(newGroup);
    //         return new ResponseEntity<>(HttpStatus.CREATED); // User creation successful (HTTP 201 Created)
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // User creation failed (HTTP 500 Internal Server Error)
    //     }
    // }
    public String createGroup(@RequestBody Group newUser) { 
        return groupDAO.createGroup(newUser);
    } 

    public List<Group> getAllGroups() {
        return groupDAO.getAllGroup(); 
    } 
    
}
