/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tmanage.DBConnector;
import tmanage.interfaces.IProject;
import tmanage.interfaces.ITask;
import tmanage.interfaces.ITasksList;

/**
 *
 * @author martin
 */
public class TaskList implements ITasksList{
    private List<ITask> tasks;
    private List<String> names;

    @Override
    public ITask getTaskById(int id) {
        for (ITask task : tasks){
            if(task.getId()==id)
                return task;
        }
        return null;
    }

    @Override
    public ITask getTaskByName(String name) {
        for (ITask task : tasks){
            if (task.getName().equalsIgnoreCase(name))
                return task;
        }
        return null;
    }

    @Override
    public List<ITask> getAllTasks() {
        return tasks;
    }

    @Override
    public List<ITask> getTasksByProject(IProject project) {
        List<ITask> result = new ArrayList<ITask>();        
        for (ITask t : tasks){
            if (t.getProjectId() == project.getId()){
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public boolean addTask(ITask task) {
        Task src = (Task)task;
        
        Connection connection = DBConnector.getConnection();
        PreparedStatement addTask;
        try {
            addTask = connection.prepareStatement("insert into time_management.Tasks(project_id, name, description, targ_time, spent_time, completed)"+ 
                    " values (?,?,?,?,?,?);");
            addTask.setInt(1,src.getProjectId());
            addTask.setString(2, src.getName());
            addTask.setString(3, src.getDescritption());
            addTask.setInt(4, src.getTargetTime());
            addTask.setInt(5, src.getSpentTime());
            addTask.setBoolean(6, src.isCompleted());
            
            if(addTask.executeUpdate() == 0){
                System.out.println("Error adding task!"); 
                return false;
            } else {
                System.out.println("Task added succesfully");
            }                
            
            addTask.close();
            
            tasks.add(task);
            names.add(task.getName());
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteTask(int taskId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTaskList(List<ITask> list) {
        this.tasks = list;
    }
    
    @Override
    public List<String> getNames(){
        if (names!=null)
            return names;
        
        names = new ArrayList<String>();
        
        for (ITask task : tasks){
            names.add(task.getName());
        }
        
        return names;
    }
    
}
