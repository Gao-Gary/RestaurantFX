package main.home;

import javafx.stage.Stage;
import main.Controller;
import main.WindowManager;

public class HistoryController implements Controller {

    private Stage stage;

    @Override
    public void initialize(WindowManager windowManager, Stage stage) {
        this.stage = stage;
    }
}
