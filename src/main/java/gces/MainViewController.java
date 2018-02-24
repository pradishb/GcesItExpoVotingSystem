package gces;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

/**
 *
 * @author pradish
 */

public class MainViewController implements Initializable {
    @FXML
    PieChart piechart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        ObservableList<PieChart.Data> pieChartDat =
//                FXCollections.observableArrayList(
//                        new PieChart.Data("Grapefruit", 13),
//                        new PieChart.Data("Oranges", 25),
//                        new PieChart.Data("Plums", 10),
//                        new PieChart.Data("Pears", 22),
//                        new PieChart.Data("Apples", 30));
//        piechart.setData(pieChartDat);
//        piechart.setTitle("Imported Fruits");


        DatabaseReference ref = FirebaseEngine.database.getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ObservableList<PieChart.Data> pieChartData =  FXCollections.observableArrayList();
                for (DataSnapshot postSnapshot : dataSnapshot.child("projects").getChildren()) {
                    String projectTitle = postSnapshot.child("title").getValue(String.class);
                    String projectKey = postSnapshot.getKey();
                    long count = dataSnapshot.child("votes").child(projectKey).getChildrenCount();

                    pieChartData.add(new PieChart.Data(projectTitle, count));
                    System.out.println(projectTitle + count);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        piechart.setData(pieChartData);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addListenerForSingleValueEvent(postListener);

    }
}
