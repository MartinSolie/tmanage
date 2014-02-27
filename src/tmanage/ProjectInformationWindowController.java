/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
    private Button exportButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField nameField;
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
    
    @FXML
    private void handleExportButtonPressed(MouseEvent event){
        /*try {
            File file = new File (order.getProject().getName());
            WorkbookSettings wbSettings = new WorkbookSettings();
            
            wbSettings.setLocale(new Locale ("en", "EN"));
            
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            workbook.createSheet(order.getProject().getName()+"Report", 0);
            WritableSheet excelSheet = workbook.getSheet(0);
            createLabel(excelSheet);
            createContent(excelSheet);
            
            workbook.write();
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(ProjectInformationWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(ProjectInformationWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }*/      
    }
    
    @FXML
    private void handleDeleteButtonPressed(MouseEvent event){
        if(Storage.orders.deleteOrder(order, Storage.tasks.getTasksByProject(order.getProject()))){
            System.out.println("Succesfully deleted");
        } else {
            System.out.println("Man you are in trouble");
        }
        
        Stage stage = (Stage)deleteButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleSaveButtonPressed (MouseEvent event){
        String name = nameField.getText();
        String description = descriptionField.getText();
        double salary = order.getSalary();
        Date deadLine = new Date (order.getDeadLine().getTime());
        
        try{
            salary = Double.valueOf(salaryField.getText());
            deadLine = Date.valueOf(deadLineField.getText());            
        } catch (Exception e) {
            System.out.println("Cannot parse one of the fields because:\n"+e.getMessage());
        }
        
        if (!order.getProject().getName().equalsIgnoreCase(name)|| 
                !order.getProject().getDescription().equalsIgnoreCase(description)||
                order.getSalary()!=salary || order.getDeadLine() != deadLine ){
            Storage.orders.updateOrder(order, name, description, salary, deadLine);
        } else {
            System.out.println("Nothing changed");
        }
        
        Stage stage = (Stage)saveButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleCancelButtonPressed(MouseEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleTasksTablePressed (MouseEvent event){
        System.out.println("I am in");
        if (event.getClickCount()>1){
            Task task = (Task)tasksTable.getSelectionModel().getSelectedItem();
            if (task != null){
                try {
                    Stage stage = new Stage();
                    Parent root;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskInformationWindow.fxml"));
                    root = (Parent)loader.load();
                    Scene scene = new Scene(root);
                    
                    TaskInformationWindowController controller = loader.<TaskInformationWindowController>getController();
                    controller.initTask(task);
                    
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ProjectInformationWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
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
        spentTimeField.setEditable(false);
        
        //setting up table view with tasks from the project
        nameColumn.setCellValueFactory(
            new PropertyValueFactory<Task,String>("name")
        );        
        doneColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, String>, ObservableValue<String>>() {            
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Task, String> p) {
                if (p.getValue()!=null){
                    if (p.getValue().isCompleted()){
                        return new SimpleStringProperty("Yes");
                    } else {
                        return new SimpleStringProperty("No");
                    }                        
                } else {
                    return new SimpleStringProperty("NULL");
                }
            }
        });        
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
        nameField.setText(order.getProject().getName());
        customerField.setText(order.getCustomer().getFullName());
        descriptionField.setText(order.getProject().getDescription());        
        salaryField.setText(String.valueOf(order.getSalary()));
        deadLineField.setText(order.getDeadLine().toString());
        long spentTime = 0;
        for (ITask task : Storage.tasks.getTasksByProject(order.getProject())){
            spentTime+=task.getSpentTime();
        }
        spentTimeField.setText(TimeConverter.getFormattedTime(spentTime));
        //projectsList.getSelectionModel().select(order.getName());        
        tasks = FXCollections.observableArrayList(Storage.tasks.getTasksByProject(order.getProject()));        
        tasksTable.setItems(tasks);        
    }
}
