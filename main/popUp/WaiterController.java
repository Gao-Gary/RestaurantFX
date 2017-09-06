package main.popUp;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Controller;
import main.DataManager;
import main.WindowManager;
import main.model.restaurant.Employee;

import javax.xml.crypto.Data;

public class WaiterController implements Controller {
    @FXML private JFXButton cancel;
    @FXML private JFXButton confirm;
    @FXML private ComboBox<String> waiterList;
    @FXML private Label warning;

    private Stage stage;

    @Override
    public void initialize(WindowManager windowManager, Stage stage) {
        waiterList.setItems(DataManager.getInstance().getWaiterNames());
        this.stage = stage;
        addListeners();
    }

    private void addListeners() {
        cancel.setOnAction(e -> cancel());
        confirm.setOnAction(e -> confirm());
    }

    private void confirm() {
        if(waiterList.getSelectionModel().getSelectedItem() == null) {
            warning.setText("Choose a waiter!");
            return;
        }
        int selectedTable = DataManager.getInstance().getSelectedTable();
        Label waiterLabel = DataManager.getInstance().getWaiter(selectedTable);
        waiterLabel.setText("Waiter: " + waiterList.getSelectionModel().getSelectedItem());
        DataManager.getInstance().getTable(selectedTable).setWaiter(new Employee(waiterList.getSelectionModel().getSelectedItem()));
        cancel();
    }

    private void cancel() {
        warning.setText("");
        stage.close();
    }
}
