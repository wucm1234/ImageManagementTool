package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("Image Converter");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

        String s = "update github";
    }


    public static void main(String[] args) {
        launch(args);
    }
}
