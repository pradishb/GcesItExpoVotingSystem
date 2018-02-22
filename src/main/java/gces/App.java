package gces;

import com.google.firebase.database.DatabaseReference;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class App extends Application implements BarcodeListener{
    private Text text;
    private Button btn;

    public static void main(String[] args) {
        BarcodeReader x = new BarcodeReader();
        FirebaseEngine.initialize();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        text = new Text();

        //Setting font to the text
        text.setFont(new Font(45));

        //setting the position of the text
        text.setX(50);
        text.setY(150);

        //Setting the text to be added.
        text.setText("Scan the barcode...");


        btn = new Button();
        btn.setText("Vote");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btn.setVisible(false);
//                Vote myVote = new Vote("xx", "yy");
//                DatabaseReference ref = FirebaseEngine.database.getReference("votes");
//                ref.push().setValueAsync(myVote);
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(text);
        root.getChildren().add(btn);
        btn.setVisible(false);
        Scene scene = new Scene(root, 700, 600);
        BarcodeReader.addReader(this, scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void onBarcodeRead(String barcode) {
        System.out.println("hello barcode is read : " + barcode);
        text.setVisible(false);
        btn.setVisible(true);
    }
}