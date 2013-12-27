/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author martin
 */
public interface IOrderList {
    public List<IOrder> getAllOrders();
    public List<IOrder> getCompanyOrders();
    public List<IOrder> getPersonOrders();  
    public IOrder getOrderByName(String name);
    public void addCompanyOrder (IOrder order);
    public void addPersonOrder (IOrder order);
    public void addProject (IProject project) throws SQLException;
}
