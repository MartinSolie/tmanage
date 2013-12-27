/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

/**
 *
 * @author martin
 */
public class CompanyCustomer extends Customer{
    private String name;

    public CompanyCustomer(){
        super();
        name = null;
    }
    
    public CompanyCustomer (int id, String name, String email, String other){
        super (id,email,other);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getFullName(){
        return this.name;
    }
}
