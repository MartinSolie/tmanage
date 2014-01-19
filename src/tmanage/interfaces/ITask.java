/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.interfaces;

/**
 *
 * @author martin
 */
public interface ITask {
    public int getId();
    public int getProjectId();
    public String getName();
    public int getSpentTime();
    public void setCompleted(boolean completed);
}
