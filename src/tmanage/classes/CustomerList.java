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
import tmanage.interfaces.ICustomer;
import tmanage.interfaces.ICustomersList;
/**
 *
 * @author martin
 */
public class CustomerList implements ICustomersList {
    
    private List<ICustomer> customers;
    private List<String> names;
    
    public List<String> getNames(){
        
        if (names != null){
            return names;
        }
        
        names = new ArrayList<String>();
        
        for (ICustomer cust : customers){
            names.add(cust.getFullName());
        }            
        
        return names;
    }
    
    @Override
    public ICustomer getCustomerById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ICustomer> getAllCustomers() {
        return customers;
    }

    @Override
    public boolean addCustomer(ICustomer customer) {
        Connection connection = DBConnector.getConnection();
        
        if (customer instanceof PrivateCustomer){
            PrivateCustomer cust = (PrivateCustomer)customer;
            try {
                PreparedStatement addCustomer = connection.prepareStatement("insert into Customers(surname, name, patronymic, email, other) values(?,?,?,?,?);");
                
                addCustomer.setString(1, cust.getSurname());
                addCustomer.setString(2, cust.getName());
                addCustomer.setString(3, cust.getPatronymic());
                addCustomer.setString(4, cust.getEmail());
                addCustomer.setString(5, cust.getOther());
                
                if (addCustomer.executeUpdate()==0){
                    System.out.println("Error adding privateCustomer");
                    addCustomer.close();
                    return false;
                } else {
                    System.out.println("Private customer added succesfully");
                    addCustomer.close();                    
                }                
            } catch (SQLException ex) {
                Logger.getLogger(CustomerList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (customer instanceof CompanyCustomer){
            try {
                CompanyCustomer cust = (CompanyCustomer)customer;
                PreparedStatement addCustomer = connection.prepareStatement("insert into Companies(name, email, other) values (?,?,?);");
                
                addCustomer.setString(1, cust.getName());
                addCustomer.setString(2, cust.getEmail());
                addCustomer.setString(3, cust.getOther());
                
                if (addCustomer.executeUpdate() == 0){
                    System.out.print("Error adding company customer");
                    addCustomer.close();
                    return false;
                } else {
                    System.out.println("Company added succesfully");
                    addCustomer.close();                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(CustomerList.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Error: Some issues with transfering customer to the function");
            return false;
        }
        
        customers.add(customer);
        names.add(customer.getFullName());
        
        return true;
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCustomerList(List<ICustomer> list) {
        this.customers = list;
    }
    
    public ICustomer getCustomerByName(String name){
        for (ICustomer customer : customers){
            if (name.equalsIgnoreCase(customer.getFullName())){
                return customer;
            }            
        }
        
        return null;
    }
    
}
