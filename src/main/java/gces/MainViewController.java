package gces;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javafx.scene.Node;

/**
 *
 * @author pradish
 */

public class MainViewController implements Initializable {
    ObservableList<PieChart.Data> pieChartData =  FXCollections.observableArrayList();
    HashMap<String, ProjectView> projectList = new HashMap<>();

    @FXML
    PieChart piechart;

    @FXML
    Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        piechart.setData(pieChartData);

        Timer t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(InternetChecker.internetAvailable())
                {
                    error_label.setVisible(false);
                }
                else{
                    error_label.setVisible(true);
                }
            }
        }, 0, 2000);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);

        //Project change listener
        DatabaseReference projectRef = FirebaseEngine.database.getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.child("projects").getChildren()) {
                    String projectTitle = postSnapshot.child("title").getValue(String.class);
                    String projectKey = postSnapshot.getKey();
                    long count = dataSnapshot.child("votes").child(projectKey).getChildrenCount();
                    if(!projectList.keySet().contains(projectKey)){

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                projectList.put(projectKey, new ProjectView(pieChartData.size(), projectTitle, count));
                                pieChartData.add(new PieChart.Data(projectTitle + " (" + count + " votes)", count));

                            }
                        });
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(projectList.get(projectKey).getIndex() + projectTitle + count);
                            pieChartData.get(projectList.get(projectKey).getIndex()).setPieValue(count);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        projectRef.addValueEventListener(postListener);

    }
}
