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

import java.util.HashMap;

public class BillingController implements Controller{
    @FXML private JFXButton close;
    @FXML private JFXButton confirm;
    @FXML private ComboBox<Integer> move;
    @FXML private JFXButton moveButton;
    @FXML private JFXButton split;
    @FXML private JFXButton extra;
    @FXML private Label reminder;
    @FXML private TableView<FoodItem> mainTable;
    @FXML private TabPane tabs;
    @FXML private TableColumn<FoodItem, Double> quantity1;
    @FXML private TableColumn<FoodItem, String> name1;
    @FXML private TableColumn<FoodItem, Double> price1;

    private HashMap<Integer, TableView<FoodItem>> bills;


    private Stage stage;

    @Override
    public void initialize(WindowManager windowManager, Stage stage) {
        bills = new HashMap<>();

        int selectedTable = DataManager.getInstance().getSelectedTable();
        name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price1.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainTable.setItems(DataManager.getInstance().getTable(selectedTable).getBill().getItems());
        mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bills.put(1, mainTable);
        renderComboBox();
        this.stage = stage;
        addListeners();
    }

    private void addListeners() {
        close.setOnAction(e -> close());
        extra.setOnAction(e -> extra());
        split.setOnAction(e -> split());
        moveButton.setOnAction(e -> move());
        confirm.setOnAction(e -> confirm());
    }

    private void close() {
        reminder.setText("");
        stage.close();
    }

    private void split() {
        int numTabs = tabs.getTabs().size();
        if(numTabs == 1) {
            reminder.setText("No bill to split with!");
            return;
        }

        int selectedIndex = tabs.getSelectionModel().getSelectedIndex()+1;
        if(bills.get(selectedIndex).getSelectionModel().getSelectedItem() == null)
            return;

        ObservableList<FoodItem> selectedItems = bills.get(selectedIndex).getSelectionModel().getSelectedItems();

        for(FoodItem item : selectedItems) {
            double quantity = item.getQuantity();

             for(int i = 1; i <= numTabs; i++) {
                 if(i == selectedIndex) {
                     item.setQuantity(quantity/numTabs);
                 } else {
                     FoodItem splitItem = new FoodItem(item.getPrice(), item.getName(), item.getType());
                     splitItem.setQuantity(quantity/numTabs);
                     bills.get(i).getItems().add(splitItem);
                 }
             }
        }
        bills.get(selectedIndex).refresh();
    }

    private void move() {
        int selectedIndex = tabs.getSelectionModel().getSelectedIndex()+1;
        if(bills.get(selectedIndex).getSelectionModel().getSelectedItem() == null
                || move.getSelectionModel().getSelectedItem() == null
                || move.getSelectionModel().getSelectedItem() == selectedIndex)
            return;

        ObservableList<FoodItem> selectedItems = bills.get(selectedIndex).getSelectionModel().getSelectedItems();

        TableView<FoodItem> removeFrom = bills.get(selectedIndex);

        for(FoodItem item : selectedItems) {
            removeFrom.getItems().remove(item);
            removeFrom.refresh();
            bills.get(move.getSelectionModel().getSelectedItem()).getItems().add(item);
        }
    }

    private void renderComboBox() {
        move.setItems(null);
        ObservableList<Integer> toAdd = FXCollections.observableArrayList();
        for(int i = 1; i <= tabs.getTabs().size(); i++) {
            toAdd.add(i);
        }

        move.setItems(toAdd);
    }

    private void confirm() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        Label statusColour = DataManager.getInstance().getStatusColour(selectedTable);
        statusColour.setStyle("-fx-background-color: yellow;");

        Label statusText = DataManager.getInstance().getStatus(selectedTable);
        statusText.setText("Billing");

        DataManager.getInstance().getTable(selectedTable).checkPlease();
        close();
    }

    private void extra() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        int numTabs = tabs.getTabs().size();
        if(numTabs == DataManager.getInstance().getTable(selectedTable).getCustomers()) {
            reminder.setText("Number bills would exceed # customers");
            return;
        }

        Tab nextTab = new Tab("Bill " + ++numTabs);
        TableView<FoodItem> nextTable = new TableView<>();
        nextTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bills.put(numTabs, nextTable);

        TableColumn<FoodItem, Double> quantity = new TableColumn<>();
        quantity.setText("#");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantity.setPrefWidth(75.0);

        TableColumn<FoodItem, String> name = new TableColumn<>();
        name.setText("Item Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(433.0);

        TableColumn<FoodItem, Double> price = new TableColumn<>();
        price.setText("Unit Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setPrefWidth(92.0);

        nextTable.getColumns().addAll(quantity, name, price);
        nextTab.setContent(nextTable);
        tabs.getTabs().add(nextTab);
        tabs.getSelectionModel().select(nextTab);
        reminder.setText("");
        renderComboBox();
    }
}
