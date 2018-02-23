package gces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;

public class App extends Application{
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
    }
}