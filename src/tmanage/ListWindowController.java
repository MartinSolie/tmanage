/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tmanage.classes.Order;
import tmanage.classes.Project;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class ListWindowController implements Initializable {
    
    public List<Order> orders;
    
    @FXML
    private ListView customersListView;    
    @FXML
    private ListView companiesListView;
    @FXML
    private ListView projectsListView;
    @FXML
    private Label customerLabel;
    
    @FXML
    private Label companyLabel;
    
    @FXML
    private Label projectLabel;

    @FXML
    private void handleProjectAddButtonPressed(MouseEvent event)
    {
        
        //debugging information        
        System.out.println(DBLoader.getLastID("CustomerOrders"));
        System.out.println(DBLoader.getLastID("CompanyOrders"));
        System.out.println(DBLoader.getLastID("Customers"));
        System.out.println("Projects: "+DBLoader.getLastID("Projects"));
                
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewProjectWindow.fxml"));
            root = (Parent)loader.load();
            Scene scene = new Scene(root);
            
            NewProjectWindowController controller = loader.<NewProjectWindowController>getController();            
                    
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    private void handleCustomerAddButtonPressed(MouseEvent event)
    {
        Stage stage = new Stage();
        Parent root;
        
        try {          
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCustomerWindow.fxml"));
            root = (Parent)loader.load();
            Scene scene  = new Scene (root);
            
            NewCustomerWindowController controller = loader.<NewCustomerWindowController>getController();
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ListWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @FXML
    private void handleCompanyAddButtonPressed(MouseEvent event)
    {
        Stage stage = new Stage();
        Parent root;
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCompanyWindow.fxml"));
            root = (Parent)loader.load();
            Scene scene = new Scene (root);
            
            NewCompanyWindowController controller = loader.<NewCompanyWindowController>getController();
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ListWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleProjectListPressed(MouseEvent event) {
        //System.out.println("ProjectListPressed");
        if (event.getClickCount()==2){
            try {
                Stage stage = new Stage();
                Parent root;
                FXMLLoader loader = new FXMLLoader (getClass().getResource("ProjectInformationWindow.fxml"));
                root=(Parent)loader.load();
                Scene scene = new Scene(root);
                
                ProjectInformationWindowController controller = loader.<ProjectInformationWindowController>getController();
                controller.initOrder(Storage.orders.getOrderByName((String)projectsListView.getSelectionModel().getSelectedItem()));
                
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ListWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {            
            projectLabel.setText(Storage.orders.getOrderByName((String)projectsListView.getSelectionModel().getSelectedItem()).getDeadLine().toString());
        }
    }
    
    public void initUserData(List<Order> orders){
        this.orders = orders;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //populating lists with the data
        projectsListView.setItems(FXCollections.observableList(Storage.orders.getProjectNames()));
        customersListView.setItems(FXCollections.observableList(Storage.persons.getNames()));
        companiesListView.setItems(FXCollections.observableList(Storage.companies.getNames()));
        
        customersListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>(){
                    @Override
                    public void changed (ObservableValue<? extends String> ov, String old_val, String new_val){                        
                        customerLabel.setText(Storage.persons.getCustomerByName(new_val).getEmail());                        
                    }
                }
                );
        
        companiesListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>(){
                    @Override
                    public void changed (ObservableValue<? extends String> ov, String old_val, String new_val){
                        companyLabel.setText(Storage.companies.getCustomerByName(new_val).getEmail());
                    }
                }
                );
        
        /*projectsListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>(){
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
                        projectLabel.setText(Storage.orders.getOrderByName(new_val).getDeadLine().toString());
                    }
                }
                );*/
    }    
}
