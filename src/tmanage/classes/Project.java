package tmanage.classes;

import tmanage.Storage;
import tmanage.interfaces.IProject;
import tmanage.interfaces.ITask;
/**
 *
 * @author martin
 */
public class Project implements IProject {
    
    private int id;
    private String name;
    private String description;       

    public Project(){
        id = 0;
        name = "NULL";
        description = "NULL";
    }
    
    public Project (int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
    
    public void print(){
        System.out.println("ID: "+this.id+"\nname: "+this.name+"\ndescritption: "+this.description);
    }
            
}
