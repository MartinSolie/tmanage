/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tmanage.classes.CompanyCustomer;
import tmanage.classes.Customer;
import tmanage.classes.Order;
import tmanage.classes.OrderList;
import tmanage.classes.PrivateCustomer;
import tmanage.classes.Project;
import tmanage.classes.Task;
import tmanage.interfaces.ICustomer;
import tmanage.interfaces.ITask;

/**
 *
 * @author martin
 */
public class DBLoader {
    public static void loadOrders() throws SQLException{
        List <Order> list = new ArrayList<Order>();
        
        Connection connection = DBConnector.Connect();
        
        Statement selectOrdersST = null;
        PreparedStatement selectProjectsST = null;
        PreparedStatement selectCustomersST = null;
        
        ResultSet rsOrders;
        ResultSet rsProjects;
        ResultSet rsCustomers;
        
        Customer cust;
        Project prj;
        try{
            selectOrdersST = connection.createStatement();
            selectProjectsST = connection.prepareStatement("SELECT * FROM time_management.Projects where id = ?");
            selectCustomersST = connection.prepareStatement("SELECT * FROM time_management.Customers where id = ?");
            
            rsOrders = selectOrdersST.executeQuery("SELECT * FROM time_management.CustomerOrders;");
             
            while (rsOrders.next()){     
                selectProjectsST.setInt(1, rsOrders.getInt("project_id"));
                rsProjects = selectProjectsST.executeQuery();
                
                selectCustomersST.setInt(1, rsOrders.getInt("customer_id"));
                rsCustomers = selectCustomersST.executeQuery();
                
                cust = null;
                if (rsCustomers.next()!=false){                    
                    cust = new PrivateCustomer(rsCustomers.getInt("id"), rsCustomers.getString("surname"),
                            rsCustomers.getString("name"), rsCustomers.getString("patronymic"), rsCustomers.getString("email"),
                            rsCustomers.getString("other")); 
                } else {
                    System.out.println("There are some errors occur while reading information about Customer");
                }
                
                prj = null;
                if (rsProjects.next()!=false){
                    prj = new Project(rsProjects.getInt("id"), rsProjects.getString("name"), rsProjects.getString("description"));                                        
                } else {
                    System.out.println("There are some errors occur while reading information about Project");
                }
                                
                list.add(new Order (rsOrders.getInt("id"), rsOrders.getDouble("salary"), rsOrders.getDate("dead_line"), cust, prj));
            }
            
            Storage.orders.setPersonOrders(list);
            
            list.clear();            
            selectCustomersST = connection.prepareStatement("SELECT * FROM time_management.Companies where id = ?");            
            rsOrders = selectOrdersST.executeQuery("SELECT * FROM time_management.CompanyOrders;");
            
            while (rsOrders.next()){                     
                selectProjectsST.setInt(1, rsOrders.getInt("project_id"));
                rsProjects = selectProjectsST.executeQuery();
                
                selectCustomersST.setInt(1, rsOrders.getInt("company_id"));
                rsCustomers = selectCustomersST.executeQuery();
                
                cust = null;
                if (rsCustomers.next()!=false){                    
                    cust = new CompanyCustomer(rsCustomers.getInt("id"), rsCustomers.getString("name"), rsCustomers.getString("email"),
                            rsCustomers.getString("other"));                    
                } else {
                    System.out.println("There are some errors occur while reading information about Customer");
                }
                
                prj = null;
                if (rsProjects.next()!=false){
                    prj = new Project(rsProjects.getInt("id"), rsProjects.getString("name"), rsProjects.getString("description"));                    
                } else {
                    System.out.println("There are some errors occur while reading information about Project");
                }
                                
                list.add(new Order (rsOrders.getInt("id"), rsOrders.getDouble("salary"), rsOrders.getDate("dead_line"), cust, prj));
            }
            
            Storage.orders.setCompanyOrders(list);
            
        }
        catch (Exception ex){
            
        }
        finally{
            if (selectOrdersST!=null){
                selectOrdersST.close();
            }
            if (selectProjectsST!=null){
                selectProjectsST.close();
            }
            if (selectCustomersST != null){
                selectCustomersST.close();
            }
        }
        
        if (list.isEmpty() == true)
        {
            System.out.println("List is empty man!");
        }
        else
        {
            System.out.println("Everything is okay!");
        }
    }
    
    public static void loadPrivateCustomers() throws SQLException{
        List<ICustomer> list = new ArrayList<ICustomer>();
        Connection connection = DBConnector.Connect();        
        Statement selectCustomers = null;        
        ResultSet rsCustomers;        
        PrivateCustomer customer;
        
        try{
            selectCustomers = connection.createStatement();
            rsCustomers = selectCustomers.executeQuery("SELECT * FROM time_management.Customers");
            
            while (rsCustomers.next()){                    
                    customer = new PrivateCustomer(rsCustomers.getInt("id"), rsCustomers.getString("surname"),
                            rsCustomers.getString("name"), rsCustomers.getString("patronymic"), rsCustomers.getString("email"),
                            rsCustomers.getString("other")); 
                    list.add(customer);
                }
            Storage.persons.setCustomerList(list);
        }
        catch (Exception e){
        
        }
        finally{
            if (selectCustomers != null)
                selectCustomers.close();
        }        
    }
    
    public static void loadCompanyCustomers() throws SQLException{
        List<ICustomer> list = new ArrayList<ICustomer>();
        Connection connection = DBConnector.Connect();        
        Statement selectCustomers = null;        
        ResultSet rsCustomers;        
        CompanyCustomer customer;
        
        try{
            selectCustomers = connection.createStatement();
            rsCustomers = selectCustomers.executeQuery("SELECT * FROM time_management.Companies");
            
            while (rsCustomers.next()){                    
                    customer = new CompanyCustomer(rsCustomers.getInt("id"), rsCustomers.getString("name"), rsCustomers.getString("email"),
                            rsCustomers.getString("other"));                    
                    list.add(customer);
                } 
            Storage.companies.setCustomerList(list);
        }
        catch (Exception e){
        
        }
        finally{
            if (selectCustomers != null)
                selectCustomers.close();
        }
        
    }
    
    public static void loadTasks() throws SQLException{
        List<ITask> list = new ArrayList<ITask>();
        Connection connection = DBConnector.Connect();        
        Statement selectTasks = null;        
        ResultSet rsTasks;        
        Task task;
        
        try{
            selectTasks = connection.createStatement();
            rsTasks = selectTasks.executeQuery("SELECT * FROM time_management.Tasks");
            
            while (rsTasks.next()!=false){                    
                    //Task (int id, int projectId, String name, String descritption, Date targetTime, double spentTime, boolean completed){
                    task = new Task(rsTasks.getInt("id"), rsTasks.getInt("project_id"), rsTasks.getString("name"),
                            rsTasks.getString("description"), rsTasks.getInt("targ_time"),
                            rsTasks.getInt("spent_time"), rsTasks.getBoolean("completed") );
                    list.add(task);
                }
            Storage.tasks.setTaskList(list);
        }
        catch (Exception e){
        
        }
        finally{
            if (selectTasks != null)
                selectTasks.close();
        }
        
        
    }    
    
    public static void loadSettings(){
        
    }
    
    public static int getLastID(String table){
        int result = -1;
        Connection connection = DBConnector.Connect();
        Statement statement;        
        ResultSet rs;
        try {
            //statement = connection.prepareStatement("SELECT MAX(id) FROM time_management."+table);
            statement = connection.createStatement();
            rs = statement.executeQuery("SHOW TABLE STATUS LIKE \'"+table+"\'");
            if (rs.next()){
                System.out.println("There is information in rs");
                result = rs.getInt("Auto_increment");
            } else {
                System.out.println("No there is no information in rs");
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(OrderList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
}
