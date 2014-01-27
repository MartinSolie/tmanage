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
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;
import tmanage.interfaces.IOrder;
import tmanage.interfaces.ITask;

/**
 * FXML Controller class
 *
 * @author martin
 */
public class StatisticWindowController implements Initializable {
    @FXML
    PieChart projectsChart;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList <PieChart.Data> pieChartData = new ArrayList<PieChart.Data>();
        
        for (IOrder order : Storage.orders.getAllOrders()){
            long spentTime = 0;
            for (ITask task : Storage.tasks.getTasksByProject(order.getProject())){
                spentTime+=task.getSpentTime();
            }
            pieChartData.add(new PieChart.Data(order.getName(), spentTime));
        }            
        
        projectsChart.setData(FXCollections.observableArrayList(pieChartData));
        for (Node node : projectsChart.lookupAll("Text.chart-pie-label")) {
            if (node instanceof Text) {
                for (PieChart.Data data : pieChartData) {
                    if (data.getName().equals(((Text) node).getText())) {
                        ((Text) node).setText(TimeConverter.getFormattedTime((long)data.getPieValue()));
                    }
                }
            }
        }
    }    
}
