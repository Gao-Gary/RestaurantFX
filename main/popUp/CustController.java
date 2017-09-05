package main.popUp;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Controller;
import main.DataManager;
import main.WindowManager;

public class CustController implements Controller {
    @FXML private JFXButton confirm;
    @FXML private JFXButton cancel;
    @FXML private ComboBox<Integer> selectionBox;

    private Stage stage;

    @Override
    public void initialize(WindowManager windowManager, Stage stage) {
        this.stage = stage;
        renderCustomerBox();
        addListeners();
    }

    private void addListeners() {
        cancel.setOnAction(e -> cancel());
        confirm.setOnAction(e -> confirm());
    }

    private void confirm() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        Label numCust = DataManager.getInstance().getNumCustLabel(selectedTable);
        DataManager.getInstance().getTable(selectedTable).setcustomers(selectionBox.getSelectionModel().getSelectedItem());
        numCust.setText("Customers: " + selectionBox.getSelectionModel().getSelectedItem());
        cancel();
    }

    private void cancel() {
        stage.close();
    }

    private void renderCustomerBox() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        ObservableList<Integer> choices = FXCollections.observableArrayList();

        for(int i = 1; i <= DataManager.getInstance().getTable(selectedTable).getCapacity(); i++) {
            choices.add(i);
        }

        selectionBox.setItems(choices);
        selectionBox.getSelectionModel().select(DataManager.getInstance().getTable(selectedTable).getCustomers()-1);
    }
}
