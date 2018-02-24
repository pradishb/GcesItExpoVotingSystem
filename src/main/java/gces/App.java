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
        FirebaseEngine.initialize();
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");


        try {
            StackPane root = new StackPane();
            scene = new Scene(root);
            screenController = new ScreenController(scene);
            screenController.addScreen("main_view", FXMLLoader.load(new File("main_view.fxml").toURI().toURL()));
            screenController.activate("main_view");

            primaryStage.setScene(scene);
            primaryStage.setAlwaysOnTop(true);
            primaryStage.setFullScreen(true);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}