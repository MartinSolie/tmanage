/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tmanage.classes.Task;

/**
 *
 * @author martin
 */
public class MainController implements Initializable {

    //GUI fields
    @FXML
    private Text timeText;
    @FXML
    private ChoiceBox tasksList;
    private Stage stage;
    //timer fields
    private long timeSpent;
    private SimpleStringProperty stime = new SimpleStringProperty();
    private PausableTimer timer = new PausableTimer();        

    @FXML
    private void handleStartButtonPressed(MouseEvent event) {
        timer.start();        
    }

    @FXML
    private void handlePauseButtonPressed(MouseEvent event) {
        timer.stop();        
    }

    @FXML
    private void handleListButtonPressed(MouseEvent event) {
        Stage listStage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListWindow.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root);

            ListWindowController controller = loader.<ListWindowController>getController();

            listStage.setScene(scene);
            listStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setting up timer
        timeText.textProperty().bindBidirectional(stime);        
        stime.set("00:00:00");

        //settin up choice box with tasks
        final List<String> tasks = new ArrayList<String>();
        tasks.add("Add new task");
        tasks.addAll(Storage.tasks.getNames());
        tasksList.setItems(FXCollections.observableArrayList(tasks));
        tasksList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                if (t1.toString().equalsIgnoreCase("Add new task")) {
                    timer.stop();
                    Stage newTaskStage = new Stage();
                    Parent root;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewTaskWindow.fxml"));
                        root = (Parent) loader.load();
                        Scene scene = new Scene(root);

                        NewTaskWindowController controller = loader.<NewTaskWindowController>getController();

                        newTaskStage.setScene(scene);
                        newTaskStage.show();

                        /*newTaskStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
                         @Override
                         public void handle(WindowEvent t) {
                         tasks.clear();
                         tasks.add("Add new task");
                         tasks.addAll(Storage.tasks.getNames());
                         for (String name : tasks){
                         System.out.println(name);
                         }
                         tasksList.setItems(FXCollections.observableArrayList(tasks));        
                         }                            
                         });*/


                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    timer.stop();
                    if (t != null && !t.toString().equalsIgnoreCase("Add new task")) {
                        Task previousTask = (Task) Storage.tasks.getTaskByName(t.toString());
                        previousTask.setSpentTime((int) timeSpent);
                    }
                    Task nextTask = (Task) Storage.tasks.getTaskByName(t1.toString());
                    timeSpent = nextTask.getSpentTime();
                    timer.start();                    
                }
            }
        });



    }

    //we should stop timer and save time on closing, if it was working    
    public void initUserData(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timer.stop();
                if (!"Add new task".equalsIgnoreCase(tasksList.getSelectionModel().getSelectedItem().toString())){
                    Task task = (Task) Storage.tasks.getTaskByName((String)tasksList.getSelectionModel().getSelectedItem());
                    task.setSpentTime((int) timeSpent);               
                }
            }
        });
    }

    

    private class PausableTimer {

        private Timer timer;
        private TimerTask tTask;
        public boolean works = false;
        
        public void start() {
            if (!works) {
                timer = new Timer();
                tTask = new TimerTask() {
                    @Override
                    public void run() {
                        stime.set(TimeConverter.getFormattedTime(++timeSpent));
                    }
                };                
                timer.scheduleAtFixedRate(tTask, 0, 1000);
                works = true;
            }
        }

        public void stop() {
            if (works) {
                timer.cancel();
                works = false;
            }
        }
    }
}
