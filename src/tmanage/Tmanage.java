/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author martin
 */
public class Tmanage extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        /*
         * Connect to the DB +
         * Get information about
         * Orders from Companies+
         * Orders from Privates +      
         * Tasks+
         * Settings
         */
        DBLoader.loadOrders();
        DBLoader.loadPrivateCustomers();
        DBLoader.loadCompanyCustomers();
        DBLoader.loadTasks();
        Font.loadFont(Tmanage.class.getResource("Buff.ttf").toExternalForm(), 48);
        
        //initializing main window
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = (Parent)loader.load();
            Scene scene = new Scene(root);
            
            MainController controller = loader.<MainController>getController();            
            controller.initUserData(stage);
            stage.setScene(scene);
            
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                Platform.exit();
                            }
                        });          
            
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        System.out.println("main finished");
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}