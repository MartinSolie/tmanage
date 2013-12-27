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
public interface ICustomersList {
    public ICustomer getCustomerById(int id);
    public List<ICustomer> getAllCustomers();
    public boolean addCustomer(ICustomer customer);
    public boolean deleteCustomer(int customerId);
    public void setCustomerList(List<ICustomer> list);
}
