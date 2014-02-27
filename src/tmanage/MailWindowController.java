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
import org.apache.commons.mail.HtmlEmail;
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
        try {/*
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
            System.out.println("Mail sent!");*/
             HtmlEmail email = new HtmlEmail();
             email.setTLS(true);
             email.setSmtpPort(Settings.getPort());
             email.setAuthenticator(new DefaultAuthenticator(Settings.getLogin(),
                    Settings.getPassword()));
  email.setHostName(Settings.getHost());
  email.addTo("antonymartynov@gmail.com");
  email.setFrom(Settings.getLogin());
  email.setSubject("Test email with inline image"); 
  
  // set the html message
  email.setHtmlMsg("<html>\n" +
"  <head>\n" +
"    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\n" +
"    <script type=\"text/javascript\">\n" +
"      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});\n" +
"      google.setOnLoadCallback(drawChart);\n" +
"      function drawChart() {\n" +
"        var data = google.visualization.arrayToDataTable([\n" +
"          ['Task', 'Hours per Day'],\n" +
"          ['Work',     11],\n" +
"          ['Eat',      2],\n" +
"          ['Commute',  2],\n" +
"          ['Watch TV', 2],\n" +
"          ['Sleep',    7]\n" +
"        ]);\n" +
"\n" +
"        var options = {\n" +
"          title: 'My Daily Activities'\n" +
"        };\n" +
"\n" +
"        var chart = new google.visualization.PieChart(document.getElementById('piechart'));\n" +
"        chart.draw(data, options);\n" +
"      }\n" +
"    </script>\n" +
"  </head>\n" +
"  <body>\n" +
"    <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>\n" +
"  </body>\n" +
"</html>");

  // set the alternative message
  email.setTextMsg("Your email client does not support HTML messages");

  // send the email
  email.send();
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
