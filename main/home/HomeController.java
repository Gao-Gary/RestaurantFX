package main.home;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import main.Controller;
import main.DataManager;
import main.WindowManager;
import main.model.food.FoodItem;



public class HomeController implements Controller{
    @FXML private JFXButton logout;
    @FXML private JFXButton settings;
    @FXML private JFXButton history;
    @FXML private JFXButton manage;
    @FXML private JFXButton dining;
    @FXML private JFXButton order;
    @FXML private JFXButton remove;
    @FXML private JFXButton reset;
    @FXML private JFXButton billing;
    @FXML private JFXButton addCust;
    @FXML private JFXButton changeWaiter;
    @FXML private JFXButton endSession;
    @FXML private Label tablesActive;
    @FXML private GridPane tableGrid;
    @FXML private TableView<FoodItem> sidePanel;
    @FXML private TableColumn<FoodItem, Integer> quantityColumn;
    @FXML private TableColumn<FoodItem, String> nameColumn;
    @FXML private TableColumn<FoodItem, Double> priceColumn;


    private WindowManager windowManager;

    private static final int numColumns = 5;
    private static final int numRows = 4;

    public void initialize(WindowManager windowManager, Stage stage) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tablesActive.setText("Tables Active: " + 0);
        DataManager.getInstance().setActiveLabel(tablesActive);
        this.windowManager = windowManager;
        renderTables();
        renderSideView(1);
        addListeners();
    }

    private void renderTables() {
        for(int i = 0; i < numColumns; i++) {
            for(int j = 0; j < numRows; j++) {
                StackPane newTable = renderTable(i, j);
                GridPane.setConstraints(newTable, i, j);
                tableGrid.getChildren().add(newTable);
            }
        }
    }

    private StackPane renderTable(int index1, int index2) {
        int tableNum = index1 + (index2 * numColumns) + 1;
        DataManager.getInstance().putTable(tableNum);
        StackPane window = new StackPane();
        VBox content = new VBox(8);

        HBox header = new HBox(8);
        Label statusBox = new Label("      ");
        statusBox.setStyle("-fx-background-color: green;");
        Label tableID = new Label("Table " + tableNum);
        DataManager.getInstance().putStatusColour(tableNum, statusBox);
        header.getChildren().addAll(statusBox, tableID);

        VBox status = new VBox(8);
        Label tableStatus = new Label("Available");
        Label startTime = new Label("Start:");
        Label waiter = new Label("Waiter: ");
        Label numCust = new Label("Customers: ");
        status.getChildren().addAll(tableStatus, startTime, waiter, numCust);
        DataManager.getInstance().putStatus(tableNum, tableStatus);
        DataManager.getInstance().putTime(tableNum, startTime);
        DataManager.getInstance().putWaiter(tableNum, waiter);
        DataManager.getInstance().putNumCustLabel(tableNum, numCust);

        content.getChildren().addAll(header, status);
        window.getChildren().add(content);

        window.setOnMouseClicked(e -> renderSideView(tableNum));
        return window;
    }

    private void renderSideView(Integer forTable) {
        sidePanel.setItems(DataManager.getInstance().getTable(forTable).getBill().getItems());
        DataManager.getInstance().setSelectedTable(forTable);
    }

    private void addListeners() {
        logout.setOnAction(e -> windowManager.logout());
        remove.setOnAction(e -> removeEntry());
        reset.setOnAction(e -> removeAll());
        order.setOnAction(e -> addItems());
        changeWaiter.setOnAction(e -> changeWaiter());
        endSession.setOnAction(e -> endSession());
        addCust.setOnAction(e -> addCust());
        billing.setOnAction(e -> startBilling());
    }

    private void addItems() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        Stage stage = new Stage();
        windowManager.createView(stage, "OrderPopup");
        renderSideView(selectedTable);
        sidePanel.refresh();
    }

    private void removeEntry() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        if(sidePanel.getItems().size() == 0 || sidePanel.getSelectionModel().getSelectedItem() == null)
            return;

        if(sidePanel.getItems().size() == 1) {
            removeAll();
        } else {
            FoodItem toRemove = sidePanel.getSelectionModel().getSelectedItem();
            DataManager.getInstance().getTable(selectedTable).getBill().removeFromBill(toRemove);
            sidePanel.getItems().remove(toRemove);
        }
    }

    private void removeAll() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        DataManager.getInstance().getTable(selectedTable).finishDine();
        sidePanel.setItems(null);

        Label statusColour = DataManager.getInstance().getStatusColour(selectedTable);
        statusColour.setStyle("-fx-background-color: green;");

        Label statusText = DataManager.getInstance().getStatus(selectedTable);
        statusText.setText("Available");

        Label activeLabel = DataManager.getInstance().getActiveLabel();
        int activeTables = DataManager.getInstance().getActiveTables();
        if(activeTables > 0) {
            DataManager.getInstance().setActiveTables(--activeTables);
            activeLabel.setText("Tables Active: " + activeTables);
        }

        Label time = DataManager.getInstance().getTime(selectedTable);
        time.setText("Start:");

        Label waiterLabel = DataManager.getInstance().getWaiter(selectedTable);
        waiterLabel.setText("Waiter:");

        Label numCust = DataManager.getInstance().getNumCustLabel(selectedTable);
        numCust.setText("Customers: ");
    }

    private void changeWaiter() {
        if(sidePanel.getItems().size() == 0)
            return;

        Stage stage = new Stage();
        windowManager.createView(stage, "WaiterPopup");
    }

    private void endSession() {
        int selectedTable = DataManager.getInstance().getSelectedTable();
        Label statusText = DataManager.getInstance().getStatus(selectedTable);

        if(!statusText.getText().equals("Billing")) {
            return;
        }

        removeAll();
    }

    private void addCust() {
        if(sidePanel.getItems().size() == 0)
            return;

        Stage stage = new Stage();
        windowManager.createView(stage, "CustomerPopup");
    }

    private void startBilling() {
        if(sidePanel.getItems().size() == 0)
            return;

        Stage stage = new Stage();
        windowManager.createView(stage, "BillingPopup");

        int selectedTable = DataManager.getInstance().getSelectedTable();
        Label statusColour = DataManager.getInstance().getStatusColour(selectedTable);
        statusColour.setStyle("-fx-background-color: yellow;");

        DataManager.getInstance().getTable(selectedTable).checkPlease();
    }
}
