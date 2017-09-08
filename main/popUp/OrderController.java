package main.popUp;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import main.Controller;
import main.DataManager;
import main.WindowManager;
import main.model.food.FoodItem;
import main.model.restaurant.Employee;


import java.util.Calendar;

public class OrderController implements Controller {
    @FXML private JFXButton makeOrder;
    @FXML private JFXButton removeItem;
    @FXML private JFXButton removeOne;
    @FXML private JFXButton cancel;
    @FXML private TableColumn<FoodItem, Integer> quantityColumn;
    @FXML private TableColumn<FoodItem, String> nameColumn;
    @FXML private TableColumn<FoodItem, Double> priceColumn;
    @FXML private TableView<FoodItem> menu;
    @FXML private ComboBox<String> waiterChoice;
    @FXML private ComboBox<Integer> numCustomers;
    @FXML private Label reminderLabel;

    private Stage stage;

    @Override
    public void initialize(WindowManager windowManager, Stage stage) {
        this.stage = stage;
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        menu.setItems(DataManager.getInstance().getMenu());
        menu.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addListeners();
        renderCustomerBox();

        waiterChoice.setItems(DataManager.getInstance().getWaiterNames());
    }

    private void renderCustomerBox() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        ObservableList<Integer> choices = FXCollections.observableArrayList();

        for(int i = 1; i <= DataManager.getInstance().getTable(selectedTable).getCapacity(); i++) {
            choices.add(i);
        }

        numCustomers.setItems(choices);
        numCustomers.getSelectionModel().select(0);
    }

    private void addListeners() {
        cancel.setOnAction(e -> closePopUp());
        removeOne.setOnAction(e -> removeOne());
        menu.setOnMouseClicked(e ->  {
            if(e.getClickCount() == 2)
                 addOne();
        });
        removeItem.setOnAction(e -> removeAll());
        makeOrder.setOnAction(e -> makeOrder());
    }

    private void addOne() {
        if(menu.getSelectionModel().getSelectedItem() != null) {
            double quantity = menu.getSelectionModel().getSelectedItem().getQuantity();
            menu.getSelectionModel().getSelectedItem().setQuantity(++quantity);
            menu.refresh();
        }
    }

    private void removeOne() {
        if(menu.getSelectionModel().getSelectedItem() != null) {
            double quantity = menu.getSelectionModel().getSelectedItem().getQuantity();
            if(quantity >= 1) {
                menu.getSelectionModel().getSelectedItem().setQuantity(--quantity);
                menu.refresh();
            }
        }
    }

    private void removeAll() {
        if(menu.getSelectionModel().getSelectedItem() != null) {
            menu.getSelectionModel().getSelectedItem().setQuantity(0.0);
            menu.refresh();
        }
    }

    private void closePopUp() {
        reminderLabel.setText("");
        stage.close();
    }

    private void makeOrder() {
        if(waiterChoice.getSelectionModel().getSelectedItem() == null) {
            reminderLabel.setText("Choose a waiter!");
            return;
        }

        ObservableList<FoodItem> toAdd = FXCollections.observableArrayList();
        ObservableList<FoodItem> order = menu.getItems();

        for(FoodItem item : order) {
            if(item.getQuantity() > 0) {
                toAdd.add(item);
            }
        }

        if(toAdd.isEmpty()) {
            reminderLabel.setText("Order is empty!");
        } else {
            int selectedTable = DataManager.getInstance().getSelectedTable();
            DataManager.getInstance().getTable(selectedTable).dine(numCustomers.getSelectionModel().getSelectedItem(),
                    new Employee(waiterChoice.getSelectionModel().getSelectedItem()));
            DataManager.getInstance().getTable(selectedTable).getBill().addAllToBill(toAdd);

            Label statusColour = DataManager.getInstance().getStatusColour(selectedTable);
            statusColour.setStyle("-fx-background-color: red;");

            Label statusText = DataManager.getInstance().getStatus(selectedTable);
            Label activeLabel = DataManager.getInstance().getActiveLabel();

            if(!statusText.getText().equals("Occupied")) {
                int activeTables = DataManager.getInstance().getActiveTables();
                DataManager.getInstance().setActiveTables(++activeTables);
                activeLabel.setText("Tables Active: " + activeTables);
            }

            statusText.setText("Occupied");

            Calendar calendar = Calendar.getInstance();
            Label time = DataManager.getInstance().getTime(selectedTable);
            time.setText("Start: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

            Label waiterLabel = DataManager.getInstance().getWaiter(selectedTable);
            waiterLabel.setText("Waiter: " + waiterChoice.getSelectionModel().getSelectedItem());

            Label numCust = DataManager.getInstance().getNumCustLabel(selectedTable);
            numCust.setText("Customers: " + numCustomers.getSelectionModel().getSelectedItem());
            reminderLabel.setText("");
            closePopUp();
        }
    }
}
