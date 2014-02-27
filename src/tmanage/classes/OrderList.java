/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tmanage.DBConnector;
import tmanage.DBLoader;
import tmanage.Storage;
import tmanage.interfaces.IOrder;
import tmanage.interfaces.IOrderList;
import tmanage.interfaces.IProject;
import tmanage.interfaces.ITask;
/**
 *
 * @author martin
 */
public class OrderList implements IOrderList{

    private List<IOrder> personOrders;
    private List<IOrder> companyOrders;
    private List<String> projectNames;
    
    public OrderList ()
    {
        personOrders = new ArrayList<IOrder>();
        companyOrders = new ArrayList<IOrder>();
    }
    
    public OrderList (List<IOrder> personOrders, List<IOrder> companyOrders)
    {
        this.personOrders = personOrders;
        this.companyOrders = companyOrders;
    }
    
    public void setPersonOrders(List<Order> list)
    {
        personOrders.addAll(list);
    }
    
    public void setCompanyOrders(List<Order> list)
    {
        companyOrders.addAll(list);
    }
    
    public List<String> getProjectNames(){
        if (projectNames != null){
            return projectNames;
        }
        List<String> names = new ArrayList<String>();
        for (IOrder order : this.companyOrders){
            names.add(order.getName());
        }
        for (IOrder order : this.personOrders){
            names.add(order.getName());
        }        
        projectNames = names;
        return names;            
    }
    
    public int getLastPersonOrderId(){
        return DBLoader.getLastID("CustomerOrders");
    }
    
    public int getLastCompanyOrderId(){
        return DBLoader.getLastID("CompanyOrders");
    }
    
    @Override
    public List<IOrder> getAllOrders() {
        List<IOrder> result;
        result = new ArrayList<IOrder>();
        result.addAll(personOrders);
        result.addAll(companyOrders);
        return result;
    }

    @Override
    public List<IOrder> getCompanyOrders() {
        return companyOrders;
    }

    @Override
    public List<IOrder> getPersonOrders() {
        return personOrders;
    }

    @Override
    public IOrder getOrderByName(String name) {
        for (IOrder order : personOrders){
            if (name.equalsIgnoreCase(order.getName())){
                return order;
            }
        }
        for (IOrder order : companyOrders){
            if (name.equalsIgnoreCase(order.getName())){
                return order;
            }
        }
        return null;        
    }
    
    public ArrayList<IOrder> getOrdersByCustomer (Customer customer){
        ArrayList<IOrder> result;
        result = new ArrayList<IOrder>();
        if (customer instanceof PrivateCustomer){
            for (IOrder order : personOrders){
                if (order.getCustomer().getId() == customer.getId()){
                    result.add(order);
                }
            }
        } else {
            for (IOrder order : companyOrders){
                if (order.getCustomer().getId() == customer.getId()){
                    result.add(order);
                }
            }
        }
        return result;
    }    

    @Override
    public void addProject(IProject project) throws SQLException {
        Connection connection = DBConnector.getConnection();
        PreparedStatement addPrj;
        //add project to DB
        addPrj = connection.prepareStatement("insert into time_management.Projects(name, description) values (?,?);");
        addPrj.setString(1, project.getName());
        addPrj.setString(2, project.getDescription());

        //debug information
        if (addPrj.executeUpdate() == 0) {

            System.out.println("Error adding project!");
        } else {
            System.out.println("Project added succesfully");
        }
        
        addPrj.close();
        
        projectNames.add(project.getName());
    }
    
    @Override
    public void addCompanyOrder(IOrder order) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement addOrder;
        
        try {
            
            addProject(order.getProject());
            
            //add order to DB
            addOrder = connection.prepareStatement("insert into time_management.CompanyOrders (company_id, project_id, dead_line, salary) values (?,?,?,?);");
            addOrder.setInt(1,order.getCustomer().getId());
            addOrder.setInt(2,order.getProject().getId());
            addOrder.setString(3,order.getDeadLine().toString());
            addOrder.setDouble(4, order.getSalary());
            
            //debug information
            if (addOrder.executeUpdate() == 0){
                System.out.println("Error adding order");
            } else {
                System.out.println("Order added succesfully");
            }            
            
            addOrder.close(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (companyOrders.add(order)){
            System.out.println("Order added to the list and to the DB");            
        }
        
    }    
    
    @Override
    public void addPersonOrder(IOrder order){
        
        Connection connection = DBConnector.getConnection();        
        PreparedStatement addOrder;
        try {
            
            addProject(order.getProject());
            
            //add order to DB
            addOrder = connection.prepareStatement("insert into time_management.CustomerOrders (customer_id, project_id, dead_line, salary) values (?,?,?,?);");
            addOrder.setInt(1,order.getCustomer().getId());
            addOrder.setInt(2,order.getProject().getId());
            addOrder.setString(3,order.getDeadLine().toString());
            addOrder.setDouble(4, order.getSalary());
            
            //debug information
            if (addOrder.executeUpdate() == 0){
                System.out.println("Error adding order");
            } else {
                System.out.println("Order added succesfully");
            }            
            
            addOrder.close(); 
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (personOrders.add(order)){
            System.out.println("Order added to the list and to the DB");
            
        }
    }
    
    @Override
    public boolean deleteOrder(IOrder o, List<ITask> tasks){
        for (ITask task : tasks){
            Storage.tasks.deleteTask(task);
        }        
        
        Order order = (Order)o;
        
        Connection connection = DBConnector.getConnection();
        PreparedStatement delOrder;
        PreparedStatement delProject;
        String table;
        if (companyOrders.contains(o)){
            table = "time_management.companyorders";
        } else {
            table = "time_management.customerorders";
        }
        
        try{
            delOrder = connection.prepareStatement("delete from "+table+" where id = ?;");
            delProject = connection.prepareStatement("delete from time_management.projects where id = ?;");
            delOrder.setInt(1, order.getId());
            delProject.setInt(1, order.getProject().getId());
            
            if (delOrder.executeUpdate() == 0 || delProject.executeUpdate() == 0){
                System.out.println("Error deleting order");
                return false;
            } else {
                System.out.println("Order delete succesfully");
            }
            
            delProject.close();
            delOrder.close();
            
            projectNames.remove(order.getProject().getName());
            if (companyOrders.contains(o)) {
                companyOrders.remove(o);
            } else {
                personOrders.remove(o);
            }
            
            return true;
        } catch (SQLException e) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    } 
    
    public boolean updateOrder(IOrder o, String newName, String newDescription, double newSalary, Date newDeadLine){
        Connection connection = DBConnector.getConnection();
        PreparedStatement updateOrder;
        PreparedStatement updateProject;
        Order order = (Order)o;
        String table;
        boolean isCompany;        
        
        if (companyOrders.contains(order)){
            isCompany = true;
            table = "time_management.companyorders";
        } else {
            isCompany = false;
            table = "time_management.customerorders";
        }
        
        try {
            updateOrder = connection.prepareStatement("update "+table+" "
                                                        + "set dead_line = ?, "
                                                        + "salary = ? "
                                                        + "where id = ?;");
            
            java.sql.Date tDate = new java.sql.Date(newDeadLine.getTime());
            updateOrder.setDate(1, tDate);
            updateOrder.setDouble(2, newSalary);
            updateOrder.setInt(3, order.getId());
            
            updateProject = connection.prepareStatement ("update time_management.projects "
                                                            + "set name = ?, "
                                                            + "description = ? "
                                                            + "where id = ?;");
            updateProject.setString(1, newName);
            updateProject.setString(2, newDescription);
            updateProject.setInt(3, order.getProject().getId());
            
            updateOrder.executeUpdate();
            updateProject.executeUpdate();
            
            updateOrder.close();
            updateProject.close();
            
            int index = 0;
            if (isCompany){
                index = companyOrders.indexOf(order);
            } else {
                index = personOrders.indexOf(order);
            }
            
            order.setName(newName);
            order.setDescritption(newDescription);
            order.setDeadLine(newDeadLine);
            order.setSalary(newSalary);
            
            if (isCompany){
                companyOrders.set(index, order);
            } else {
                personOrders.set(index, order);
            }
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
        
}
