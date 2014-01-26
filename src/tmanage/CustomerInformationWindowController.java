/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import tmanage.classes.Customer;
import tmanage.interfaces.IOrder;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class CustomerInformationWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea additionalInformationField;
    @FXML
    private ListView projectsList;
    
    private Customer customer;    
    private ArrayList <IOrder> orders;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameField.setEditable(false);
        emailField.setEditable(false);
        additionalInformationField.setEditable(false);
        
        projectsList.setCellFactory(new Callback<ListView<IOrder>, ListCell<IOrder>>(){
 
            @Override
            public ListCell<IOrder> call(ListView<IOrder> p) {
                 
                ListCell<IOrder> cell = new ListCell<IOrder>(){
 
                    @Override
                    protected void updateItem(IOrder t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getProject().getName());
                        }
                    }
 
                };
                 
                return cell;
            }
        });
        
    }
    
    public void initCustomer (Customer customer)
    {
        this.customer = customer;
        orders = Storage.orders.getOrdersByCustomer(customer);
        
        setCustomerInformation();
    }
    
    private void setCustomerInformation()
    {
        nameField.setText(customer.getFullName());
        emailField.setText(customer.getEmail());
        additionalInformationField.setText(customer.getOther());        
        projectsList.setItems(FXCollections.observableList(orders));
    }
    
    
}
