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


public class App extends Application implements BarcodeListener{
    private Text text;
    private Button btn;
    private ListView<String> list = new ListView<>();

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
            Scene scene = new Scene(root);
            ScreenController screenController = new ScreenController(scene);
            screenController.addScreen("voting_view", FXMLLoader.load(new File("voting_view.fxml").toURI().toURL()));
            screenController.addScreen("insert_barcode_view", FXMLLoader.load(new File("insert_barcode_view.fxml").toURI().toURL()));
            screenController.activate("insert_barcode_view");

            primaryStage.setScene(scene);
            primaryStage.show();

            BarcodeReader.addReader(this, scene);
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

    @Override
    public void onBarcodeRead(String barcode) {
        System.out.println("hello barcode is read : " + barcode);
        text.setText("Your barcode is : " + barcode + "\nConnecting to database...");

        DatabaseReference ref = FirebaseEngine.database.getReference("users");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                String key = "";
                String name = "";
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Long barCode = postSnapshot.child("barCode").getValue(Long.class);
                    if(barCode.toString().equals(barcode)){
                        key = postSnapshot.getKey();
                        name = postSnapshot.child("name").getValue(String.class);
                        found = true;
                        break;
                    }
                }
                if(found){
                    text.setText("Barcode found.\nKey : " + key + "\nName : " + name);
                    showProjectSelectScene();
                }
                else {
                    text.setText("Barcode not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                text.setText("Read failed.");
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        ref.addListenerForSingleValueEvent(postListener);
    }

    public void showProjectSelectScene(){
        text.setVisible(false);
        btn.setVisible(true);
        list.setVisible(true);
    }
}