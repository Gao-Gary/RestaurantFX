package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // initialize WindowManager
        WindowManager manager = new WindowManager(primaryStage);
        manager.initialize();


    }


    public static void main(String[] args) {
        launch(args);
    }


}
