package com.jsbserver.jsbAPI.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import com.jsbserver.jsbAPI.entity.Plan;
import com.jsbserver.jsbAPI.util.HibernateUtil;

@Repository
public class PlanDAO {
  public void createPlan(Plan plan){
    Transaction transaction = null;
    try(Session session = HibernateUtil.getSessionFactory().openSession()){
      //start a transaction
      transaction = session.beginTransaction();

      String sql_statement = "INSERT INTO plan(plan_MVP_Name, plan_startDate, plan_endDate, plan_app_Acronym) VALUE(:Plan_MVP_Name, :Plan_startDate, :Plan_endDate, :Plan_App_Acronym)";

      NativeQuery<Plan> query = session.createNativeQuery(sql_statement, Plan.class)
      .setParameter("Plan_MVP_Name", plan.getPlan_MVP_Name())
      .setParameter("Plan_startDate", plan.getPlan_startDate())
      .setParameter("Plan_endDate", plan.getPlan_endDate())
      .setParameter("Plan_App_Acronym", plan.getPlan_app_Acronym());

      //Executing the query
      query.executeUpdate();

      //commit transaction
      transaction.commit();
    }catch(Exception e){
      if (transaction != null){
        transaction.rollback();
      }
      e.printStackTrace();
      throw e;
    }
  }
}
