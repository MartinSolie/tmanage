/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import tmanage.classes.Order;
import tmanage.classes.Task;
import tmanage.interfaces.IOrder;
import tmanage.interfaces.ITask;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class MailWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField recieverField;
    @FXML
    private TextField topicField;
    @FXML
    private TextArea messageField;
    @FXML
    private Button sendButton;
    @FXML
    private Button cancelButton;
    
    private Order order;
    
    private String receiver;
    private String topic;
    private String message;
    
    @FXML
    private void handleSendButtonPressed (MouseEvent event){
        try {
            Email email = new SimpleEmail();
            email.setSmtpPort(Settings.getPort());
            email.setAuthenticator(new DefaultAuthenticator(Settings.getLogin(),
                    Settings.getPassword()));
            //email.setDebug(true);
            email.setHostName(Settings.getHost());
            email.setFrom(Settings.getLogin());
            email.setSubject(topicField.getText());
            email.setMsg(messageField.getText());
            email.addTo(recieverField.getText());
            email.setTLS(true);
            email.send();
            System.out.println("Mail sent!");
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
        Stage stage = (Stage)sendButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleCancelButtonPressed (MouseEvent event){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void initOrder(IOrder order){
        this.order = (Order)order;
        long totalSpentTime = 0;
        
        receiver = order.getCustomer().getEmail();
        recieverField.setText(receiver);
        
        topic = order.getProject().getName()+" project report";
        topicField.setText(topic);
        
        message = new String();
        
        message = "Project name: "+order.getProject().getName()+"\n";
        message += "Project description:\n"+order.getProject().getDescription()+"\n";
        message += "=======================\n";
        message += "Tasks:\n";
        for (Iterator<ITask> it = Storage.tasks.getTasksByProject(order.getProject()).iterator(); it.hasNext();) {
            Task task = (Task)it.next();
            message += task.getName()+"\n";
            message += task.getDescritption()+"\n";
            message += "Spent time: "+TimeConverter.getFormattedTime(task.getSpentTime())+"\n";
            message += "-------------------\n";
            totalSpentTime+=task.getSpentTime();
        }
        message += "=======================\n";
        message += "Total spent time: "+TimeConverter.getFormattedTime(totalSpentTime);
        
        messageField.setText(message);
    }
}
