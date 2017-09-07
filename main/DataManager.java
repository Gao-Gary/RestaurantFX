package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import main.model.food.FoodCategory;
import main.model.food.FoodItem;
import main.model.restaurant.Bill;
import main.model.restaurant.Employee;
import main.model.restaurant.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private List<Employee> waiters;
    private HashMap<Integer, Table> tables;
    private HashMap<Integer, Label> statusList;
    private HashMap<Integer, Label> statusColour;
    private HashMap<Integer, Label> startTime;
    private HashMap<Integer, Label> waiterList;
    private HashMap<Integer, Label> numCusts;
    private List<Bill> history;
    private int selectedTable;
    private Label activeLabel;
    private int activeTables;

    private DataManager() {
        selectedTable = 1;
        activeTables = 0;
        waiters = new ArrayList<>();
        waiters.add(new Employee("Bob"));
        waiters.add(new Employee("Raina"));
        waiters.add(new Employee("Joy"));

        history = new ArrayList<>();
        tables = new HashMap<>();
        startTime = new HashMap<>();
        statusList = new HashMap<>();
        waiterList = new HashMap<>();
        statusColour = new HashMap<>();
        numCusts = new HashMap<>();
    }

    public ObservableList<FoodItem> getMenu() {
        ObservableList<FoodItem> menu = FXCollections.observableArrayList();
        menu.add(new FoodItem(3.5, "Coca-Cola", FoodCategory.drink));
        menu.add(new FoodItem(12.0, "Tuna Sashimi", FoodCategory.entree));
        menu.add(new FoodItem(12.5, "Salmon Sashimi", FoodCategory.entree));
        menu.add(new FoodItem(9.0, "Calamari", FoodCategory.appetizer));

        return menu;
    }

    public ObservableList<Employee> getWaiters() {
        ObservableList<Employee> waiters = FXCollections.observableArrayList();
        waiters.addAll(this.waiters);

        return waiters;
    }

    public ObservableList<String> getWaiterNames() {
        ObservableList<String> waiters = FXCollections.observableArrayList();
        for(Employee waiter : this.waiters) {
            waiters.add(waiter.getName());
        }

        return waiters;
    }

    public void putTable(int tableNum) {
        tables.put(tableNum, new Table(4, tableNum));
    }

    public Table getTable(int tableNum) {
        return tables.get(tableNum);
    }

    public int getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(int selectedTable) {
        this.selectedTable = selectedTable;
    }

    public Label getStatus(int forTable) {
        return statusList.get(forTable);
    }

    public void putStatus(int forTable, Label label) {
        statusList.put(forTable, label);
    }

    public Label getTime(int forTable) {
        return startTime.get(forTable);
    }

    public void putTime(int forTable, Label label) {
        startTime.put(forTable, label);
    }

    public Label getWaiter(int forTable) {
        return waiterList.get(forTable);
    }

    public void putWaiter(int forTable, Label label) {
        waiterList.put(forTable, label);
    }

    public Label getStatusColour(int forTable) {
        return statusColour.get(forTable);
    }

    public void putStatusColour(int forTable, Label label) {
        statusColour.put(forTable, label);
    }

    public Label getActiveLabel() {
        return activeLabel;
    }

    public void setActiveLabel(Label activeLabel) {
        this.activeLabel = activeLabel;
    }

    public int getActiveTables() {
        return activeTables;
    }

    public void setActiveTables(int activeTables) {
        this.activeTables = activeTables;
    }

    public Label getNumCustLabel(int forTable) { return numCusts.get(forTable);}

    public void putNumCustLabel(int forTable, Label numCustLabel) { numCusts.put(forTable, numCustLabel);}

    public void putHistory(Bill bill) {
        history.add(bill);
    }

    public ObservableList<FoodItem> getHistory() {
        ObservableList<FoodItem> result = FXCollections.observableArrayList();

        for(Bill bill : history) {
            result.addAll(bill.getItems());
        }

        return result;
    }
}
