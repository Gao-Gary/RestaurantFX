package main;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.model.food.FoodItem;


import java.io.IOException;
import java.util.HashMap;

public class WindowManager {
    private Stage stage;
    private HashMap<String, Scene> windows;

    WindowManager(Stage stage) {
        this.stage = stage;
        windows = new HashMap<>();
    }

    void initialize() {
        createView(stage,"Login");
    }

    public void createView(Stage stage, String type) {
        try {
            FXMLLoader loader = getLoader(type);
            Scene window = new Scene(new StackPane());
            window.setRoot(loader.load());

            Controller controller = loader.getController();
            controller.initialize(this, stage);
            stage.setScene(window);

            if(stage == this.stage) {
                addScene(type, window);
                stage.show();
            } else {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }

        } catch (IOException ex) {
            System.out.printf("Invalid file path specified");
        }
    }

    private FXMLLoader getLoader(String type) {
        FXMLLoader result;

        switch(type) {
            case "Home":
                result = new FXMLLoader(getClass().getResource("home/HomeController.fxml"));
                break;
            case "Login":
                result = new FXMLLoader(getClass().getResource("login/LoginController.fxml"));
                break;
            case "OrderPopup":
                result = new FXMLLoader(getClass().getResource("popUp/OrderController.fxml"));
                break;
            case "BillPopup":
                result = new FXMLLoader(getClass().getResource("popUp/BillController.fxml"));
                break;
            case "WaiterPopup":
                result = new FXMLLoader(getClass().getResource("popUp/WaiterController.fxml"));
                break;
            case "CustomerPopup":
                result = new FXMLLoader(getClass().getResource("popUp/CustController.fxml"));
                break;
            default:
                // TODO
                result = null;
        }

        return result;
    }

    private void switchScene(String type) {
        Scene toSwitch = getScene(type);

        if(toSwitch == null) {
            createView(stage, type);
            toSwitch = getScene(type);
        }

        stage.setScene(toSwitch);
    }

    private void addScene(String type, Scene scene) {
        windows.put(type, scene);
    }

    public Scene getScene(String type) {
        return windows.get(type);
    }

    public void login() {
        switchScene("Home");
    }

    public void logout() { switchScene("Login");}
}
