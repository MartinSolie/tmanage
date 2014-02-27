/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.interfaces;

import java.util.List;

/**
 *
 * @author martin
 */
public interface ITasksList {
    public ITask getTaskById(int id);
    public ITask getTaskByName(String name);
    public List<ITask> getAllTasks();
    public List<String> getNames();
    public List<ITask> getTasksByProject(IProject project);
    public void setTaskList(List<ITask> list);
    public boolean addTask(ITask t);
    public boolean deleteTask(ITask t);
    public boolean updateTask(ITask t, String newName, String newDescription, boolean completed);
}
