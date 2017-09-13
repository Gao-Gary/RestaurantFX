package main.home;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Controller;
import main.WindowManager;

public class HistoryController implements Controller {
    @FXML private JFXButton dining;
    @FXML private JFXButton manage;
    @FXML private JFXButton settings;
    @FXML private JFXButton logout;
    @FXML private JFXButton history;
    @FXML private Label tablesActive;
    @FXML private BorderPane window;

    private Stage stage;

    @Override
    public void initialize(WindowManager windowManager, Stage stage) {
        this.stage = stage;
    }
}
