/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import tmanage.classes.Order;
import tmanage.classes.Task;
import tmanage.interfaces.IOrder;
import tmanage.interfaces.ITask;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class ProjectInformationWindowController implements Initializable {

    @FXML
    private Button sendButton;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField customerField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField deadLineField;
    @FXML
    private TextField spentTimeField;
    @FXML
    private ChoiceBox projectsList;
    @FXML
    private TableView tasksTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn doneColumn;
    @FXML
    private TableColumn spentTimeColumn;
    
    private Order order;
    
    private ObservableList<ITask> tasks;
    
    @FXML
    private void handleSendButtonPressed(MouseEvent event) {
        
        Stage mailStage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MailWindow.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root);

            MailWindowController controller = loader.<MailWindowController>getController();
            controller.initOrder(order);

            mailStage.setScene(scene);
            mailStage.show();           
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void initOrder(IOrder order){
        this.order = (Order) order;        
        setOrderInformation();        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerField.setEditable(false);
        descriptionField.setEditable(false);
        salaryField.setEditable(false);
        deadLineField.setEditable(false);
        spentTimeField.setEditable(false);        
        
        //setting up choice box with projects
        projectsList.setItems(FXCollections.observableArrayList(Storage.orders.getProjectNames()));        
        projectsList.getSelectionModel().selectedItemProperty().addListener(
                 new ChangeListener<String>(){
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val){
                        order = (Order) Storage.orders.getOrderByName(new_val);
                        setOrderInformation();
                    }
                }
        );
        
        //setting up table view with tasks from the project
        nameColumn.setCellValueFactory(
            new PropertyValueFactory<Task,String>("name")
        );        
        doneColumn.setCellValueFactory(
            new PropertyValueFactory<Task,Boolean>("completed")
        );        
        spentTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {            
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> p) {
                if (p.getValue()!=null){
                    return new SimpleStringProperty(TimeConverter.getFormattedTime(p.getValue().getSpentTime()));
                } else {
                    return new SimpleStringProperty("NULL");
                }
            }
        });
        //tasksTable.setEditable(true);
    }
    public void setOrderInformation(){
        customerField.setText(order.getCustomer().getFullName());
        descriptionField.setText(order.getProject().getDescription());        
        salaryField.setText(String.valueOf(order.getSalary()));
        deadLineField.setText(order.getDeadLine().toString());
        long spentTime = 0;
        for (ITask task : Storage.tasks.getTasksByProject(order.getProject())){
            spentTime+=task.getSpentTime();
        }
        spentTimeField.setText(TimeConverter.getFormattedTime(spentTime));
        projectsList.getSelectionModel().select(order.getName());        
        tasks = FXCollections.observableArrayList(Storage.tasks.getTasksByProject(order.getProject()));        
        tasksTable.setItems(tasks);        
    }
}
