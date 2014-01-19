/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tmanage.DBConnector;
import tmanage.interfaces.*;

/**
 *
 * @author martin
 */
public class Task implements ITask{
    
    private int id;
    private int projectId;
    private String name;
    private String descritption;
    private int targetTime;
    private int spentTime;
    private boolean completed;
    
    public Task (int id, int projectId, String name, String descritption, int targetTime, int spentTime, boolean completed){
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.descritption = descritption;
        this.targetTime = targetTime;
        this.spentTime = spentTime;
        this.completed = completed;
    }
    
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritption() {
        return descritption;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    public int getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }
    
    @Override
    public int getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(int spentTime) {
        this.spentTime = spentTime;
        Connection connection = DBConnector.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE time_management.Tasks SET spent_time = "+spentTime+" where id = "+this.id);
        } catch (SQLException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }    
}
