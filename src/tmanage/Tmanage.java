/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        
        //initializing main window
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = (Parent)loader.load();
            Scene scene = new Scene(root);
            
            MainController controller = loader.<MainController>getController();            
            controller.initUserData(stage);
            stage.setScene(scene);
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