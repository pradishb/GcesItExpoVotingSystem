package gces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import io.grpc.internal.IoUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class App extends Application{
    private Text text;
    private Button btn;
    private ListView<String> list = new ListView<>();
    static ScreenController screenController;
    static Scene scene;

    public static void main(String[] args) {
        BarcodeReader x = new BarcodeReader();
        FirebaseEngine.initialize();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");


        try {
            StackPane root = new StackPane();
            scene = new Scene(root);
            screenController = new ScreenController(scene);
            screenController.addScreen("voting_view", FXMLLoader.load(new File("voting_view.fxml").toURI().toURL()));
            screenController.addScreen("text_view", FXMLLoader.load(new File("text_view.fxml").toURI().toURL()));
            screenController.activate("text_view");

            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }




//        btn = new Button();
//        btn.setText("Vote");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                btn.setVisible(false);
//                DatabaseReference ref = FirebaseEngine.database.getReference("votes");
//                Vote myVote = new Vote("xx", "yy");
//                ref.push().setValueAsync(myVote);
//            }
//        });
//
//
//        //Scene1
//        ObservableList<String> items = FXCollections.observableArrayList ();
//
//        DatabaseReference ref = FirebaseEngine.database.getReference("projects");
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    String projectTitle = postSnapshot.child("title").getValue(String.class);
//                    items.add(projectTitle);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                text.setText("Read failed.");
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        };
//        ref.addListenerForSingleValueEvent(postListener);
//
//        list.setItems(items);
//        list.setVisible(false);
//
//        StackPane root = new StackPane();
//        text = new Text();
//        text.setFont(new Font(45));
//        text.setX(50);
//        text.setY(150);
//        text.setText("Scan the barcode...");
//        root.getChildren().add(text);
//        root.getChildren().add(list);
//        Scene scene1 = new Scene(root, 700, 600);
//




    }
}