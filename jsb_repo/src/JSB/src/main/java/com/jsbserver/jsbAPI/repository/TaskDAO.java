package com.jsbserver.jsbAPI.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.management.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.jsbserver.jsbAPI.entity.Account;
import com.jsbserver.jsbAPI.entity.Application;
import com.jsbserver.jsbAPI.entity.Task; // Import Task entity
import com.jsbserver.jsbAPI.util.HibernateUtil;

import jakarta.persistence.EntityNotFoundException;

@Repository
public class TaskDAO {


    public List<Task> getAllTasks(String app) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Task> taskList = null;

            String sql_statement = "SELECT * from task WHERE Task_app_Acronym = :app";
    
            NativeQuery<Task> query = session.createNativeQuery(sql_statement, Task.class);
            query.setParameter("app", app); // Set the parameter
            taskList = query.list();
    
            if (!taskList.isEmpty()) {
                return taskList;
            } else {
                throw new EntityNotFoundException("No tasks found");
            }
    
        } catch (Exception e) {
            System.out.println(e.getMessage());

            System.out.println("Error occurred, failed to read:\n\t" + e);
        }
    
        return null;
      }

      public Task getOneTask (String taskId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql_statement = "SELECT * from task WHERE Task_id = :taskId";
    
            NativeQuery<Task> query = session.createNativeQuery(sql_statement, Task.class);
            query.setParameter("taskId", taskId); // Set the parameter
            List<Task> taskList = query.list();
    
            if (!taskList.isEmpty()) {
                return taskList.get(0);
            } else {
              return null;
            }
    
        } catch (Exception e) {
            System.out.println("Error occurred, failed to read:\n\t" + e);
        }
    
        return null;
      }

      public String createTask (Task newTask) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            NativeQuery<Integer> rNumberQuery = session.createNativeQuery("SELECT app_Rnumber FROM Application WHERE app_Acronym = :app_acronym", Integer.class);
            rNumberQuery.setParameter("app_acronym", newTask.getTask_app_Acronym());
            Integer appRNumber = rNumberQuery.uniqueResult();

            if (appRNumber == null){
                return null;
            }

            appRNumber += 1;

            String taskID = newTask.getTask_app_Acronym() + "_" + appRNumber;

            newTask.setTask_id(taskID);
            newTask.setTask_state("Open");
            newTask.setTask_create_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

            session.save(newTask);

            NativeQuery<Application> applicationQuery = session.createNativeQuery("FROM Application WHERE app_Acronym = :app_acronym", Application.class);
        applicationQuery.setParameter("app_acronym", newTask.getTask_app_Acronym());
        Application application = applicationQuery.uniqueResult();
        
        if (application != null) {
            application.setApp_Rnumber(appRNumber);
            session.update(application);
        }

        transaction.commit();

        return taskID;
        } catch (Exception e) {
                if (transaction != null){
                    transaction.rollback();
                }
                e.printStackTrace();
                return null;
            }

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
            String sql_statement = "INSERT INTO task VALUES(:task_name, :task_description, :task_notes, :task_plan, :task_app_Acronym, :task_state, :task_creator, :task_owner, :task_create_date, :task_id)";

            NativeQuery<Task> query = session.createNativeQuery(sql_statement, Task.class)
            .setParameter("task_name", newTask.getTask_name())
            .setParameter("task_description", newTask.getTask_description())
            .setParameter("task_notes", newTask.getTask_notes())
            .setParameter("task_plan", newTask.getTask_plan())
            .setParameter("task_app_Acronym", newTask.getTask_app_Acronym())
            .setParameter("task_state", newTask.getTask_state())
            .setParameter("task_creator", newTask.getTask_creator())
            .setParameter("task_owner", newTask.getTask_owner())
            .setParameter("task_create_date", newTask.getTask_create_date())
            .setParameter("task_id", newTask.getTask_id());

            query.executeUpdate();

            transaction.commit();

            return newTask.getTask_id();
        }catch(Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
      }
    }
