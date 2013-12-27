/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage.classes;

/**
 *
 * @author martin
 */
public class PrivateCustomer extends Customer{
    private String surname;
    private String name;
    private String patronymic;
    
    public PrivateCustomer(){
        super();
        surname = null;
        name = null;
        patronymic = null;
    }
    
    public PrivateCustomer(int id, String surname, String name, String patronymic, String email, String other){
        super (id,email,other);
        this.surname = surname;
        this.name = name;        
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    
    @Override
    public String getFullName(){
        return this.surname+' '+this.name+' '+this.patronymic;
    }
    
}
