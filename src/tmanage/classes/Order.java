/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

import java.util.Date;
import tmanage.interfaces.*;
/**
 *
 * @author martin
 */
public class Order implements IOrder{        
    private int id;
    private double salary;
    private Date deadLine;
    private Project project;
    private Customer customer;

    public Order (){
        id = 0;
        salary = 0;
        deadLine = null;
        customer = null;
        project = null;
    }
    
    public Order(int id, double salary, Date deadLine, Customer customer,Project project){
        this.id = id;
        this.salary = salary;
        this.deadLine = deadLine;
        this.customer = customer;
        this.project = project;        
    }
    
    @Override
    public void print (){
        System.out.println(id+"  "+salary+"  "+deadLine.toString()+'\n'+customer.getFullName()+'\n'+project.getName()+' '+project.getDescription());
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
    
    @Override
    public String getName(){
        return this.project.getName();
    }
    
    public void setName(String name){
        this.project.setName(name);
    }
    
    public String getDescription(){
        return this.project.getDescription();
    }
    
    public void setDescritption(String descritption){
        this.project.setDescription(descritption);
    }    

    @Override
    public IProject getProject() {
        return this.project;
    }

    @Override
    public ICustomer getCustomer() {
        return this.customer;
    }
    
}
