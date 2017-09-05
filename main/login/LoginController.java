package main.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import main.Controller;
import main.WindowManager;

public class LoginController implements Controller{
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXButton login;
    @FXML private CheckBox rememberMe;

    public void initialize(WindowManager windowManager, Stage stage) {
        login.setOnAction(e -> {
            if(isAuthorized())
                windowManager.login();
        });
    }

    private boolean isAuthorized() {
        return true;
    }
}
