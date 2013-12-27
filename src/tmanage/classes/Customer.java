/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

import tmanage.interfaces.ICustomer;
/**
 *
 * @author martin
 */
public abstract class Customer implements ICustomer{    
    protected int id;
    protected String email;
    protected String other;
    
    public Customer () {
        id = 0;
        email = null;
        other = null;
    }
    
    public Customer(int id, String email, String other){
        this.id = id;
        this.email = email;
        this.other = other;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }        
}
