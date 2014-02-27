/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import com.sun.mail.smtp.SMTPMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
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
    private TextField receiverField;
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
    private void handleSendButtonPressed(MouseEvent event) {
        
        createTasksPieChart();
        
        receiver = receiverField.getText();
        
        try {
            Session session = buildSession();
            Message message = buildMessage(session);
            addressAndSendMessage(message, receiver);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }  
        
        
        
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelButtonPressed(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void createTasksPieChart(){
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (ITask t : Storage.tasks.getTasksByProject(order.getProject())){
            pieDataset.setValue(t.getName(), new Integer(t.getSpentTime()));
        }
        JFreeChart chart = ChartFactory.createPieChart("Время, затраченное на задачи", pieDataset, true, true,false);
        
        try{
            ChartUtilities.saveChartAsJPEG(new File ("C:\\tmpchart.jpg"), chart, 500,300);
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }            
    
    public void initOrder(IOrder order) {
        this.order = (Order) order;
        long totalSpentTime = 0;

        receiver = order.getCustomer().getEmail();
        receiverField.setText(receiver);

        topic = order.getProject().getName() + " project report";
        topicField.setText(topic);

        message = new String();

        message = "Project name: " + order.getProject().getName() + "\n";
        message += "Project description:\n" + order.getProject().getDescription() + "\n";
        message += "=======================\n";
        message += "Tasks:\n";
        for (Iterator<ITask> it = Storage.tasks.getTasksByProject(order.getProject()).iterator(); it.hasNext();) {
            Task task = (Task) it.next();
            message += task.getName() + "\n";
            message += task.getDescritption() + "\n";
            message += "Spent time: " + TimeConverter.getFormattedTime(task.getSpentTime()) + "\n";
            message += "-------------------\n";
            totalSpentTime += task.getSpentTime();
        }
        message += "=======================\n";
        message += "Total spent time: " + TimeConverter.getFormattedTime(totalSpentTime);

        messageField.setText(message);
    }
    
    //emailing methods
    
    public Session buildSession() {
        Properties mailProps = new Properties();
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.host", Settings.getHost());
        mailProps.put("mail.from", Settings.getLogin());
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.smtp.port", Settings.getPort());
        mailProps.put("mail.smtp.auth", "true");

        final PasswordAuthentication usernamePassword = new PasswordAuthentication(
                Settings.getLogin(), Settings.getPassword());
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return usernamePassword;
            }
        };
        Session session = Session.getInstance(mailProps, auth);
        session.setDebug(true);
        return session;
    }
    
    public Message buildMessage(Session session)
            throws MessagingException, IOException {
        SMTPMessage m = new SMTPMessage(session);
        MimeMultipart content = new MimeMultipart("related");

        // ContentID is used by both parts
        String cid = ContentIdGenerator.getContentId();

        // HTML part
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("<html>"
                + "<body><div><b>Hi there!</b></div>"
                + "<div>Sending HTML in email is so <i>cool!</i> </div>\n"
                + "<div>And here's an image: <img src=\"cid:"
                + cid
                + "\" /></div>\n" + "<div>I hope you like it!</div></body></html>",
                "US-ASCII", "html");
        content.addBodyPart(textPart);

        // Image part
        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.attachFile("C:/tmpchart.jpg");
        imagePart.setContentID("<" + cid + ">");
        imagePart.setDisposition(MimeBodyPart.INLINE);
        content.addBodyPart(imagePart);

        m.setContent(content);
        m.setSubject(topicField.getText());
        return m;
    }
    
    public void addressAndSendMessage(Message message, String recipient)
            throws AddressException, MessagingException {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        Transport.send(message);
    }
}
