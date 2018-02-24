package gces;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.OnCompleteListener;
import com.google.firebase.tasks.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.FutureTask;

/**
 *
 * @author pradish
 */
public class VotingViewController implements Initializable, UserFoundListener{
    private String userKey;
    private List<String> projectKeyList;
    private boolean found = false;

    @FXML
    private Label barcode_label;

    @FXML
    private Label name_label;

    @FXML
    private Label error_label;

    @FXML
    private ListView<String> projects_list;

    @FXML
    private Button vote_btn;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(InternetChecker.internetAvailable()){
            error_label.setVisible(false);
            vote_btn.setDisable(true);

            if(projects_list.getSelectionModel().getSelectedIndex() != -1) {
                String projectKey = projectKeyList.get(projects_list.getSelectionModel().getSelectedIndex());

                System.out.println("Project Key : " + projectKey);
                System.out.println("User Key : " + userKey);

                DatabaseReference ref1 = FirebaseEngine.database.getReference("votes");
                Task<Void> task = ref1.child(projectKey).push().setValue(userKey);
                task.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        App.screenController.activate("text_view");
                        vote_btn.setDisable(false);
                    }
                });
            }
            else {
                vote_btn.setDisable(false);
                error_label.setVisible(true);
                error_label.setText("Please select a project.");
            }
        }
        else{
            error_label.setVisible(true);
            error_label.setText("Please check your internet connectivity");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextViewController.setUserFoundListener(this);
    }

    @Override
    public void onUserFound(User user, String userKey) {
        this.userKey = userKey;
        barcode_label.setText(Integer.toString(user.getBarCode()));
        name_label.setText(user.getName());


        if(InternetChecker.internetAvailable()) {
            error_label.setVisible(false);
            ObservableList<String> items = FXCollections.observableArrayList();

            DatabaseReference ref = FirebaseEngine.database.getReference("projects");
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> myKeyList = new ArrayList<>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String projectTitle = postSnapshot.child("title").getValue(String.class);
                        String projectKey = postSnapshot.getKey();
                        myKeyList.add(projectKey);
                        items.add(projectTitle);
                    }
                    projectKeyList = myKeyList;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    error_label.setText("Read failed.");
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            };
            ref.addListenerForSingleValueEvent(postListener);

            projects_list.setItems(items);
        }
        else{
            error_label.setVisible(true);
            error_label.setText("Please check your internet connectivity");
        }
    }
}
