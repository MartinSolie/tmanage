/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.interfaces;

import java.util.Date;

/**
 *
 * @author martin
 */
public interface IOrder {
    public void print();
    public String getName();
    public Date getDeadLine();
    public IProject getProject();
    public ICustomer getCustomer();
    public double getSalary();
}
