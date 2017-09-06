package main.model.restaurant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.model.food.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private ArrayList<FoodItem> order;

    public Bill() {
        order = new ArrayList<>();
    }

    public void addToBill(FoodItem item) {
        order.add(item);
    }

    public void addAllToBill(List<FoodItem> items) {
        for(FoodItem newItem : items) {
            if(order.contains(newItem)) {
                FoodItem existingItem = order.get(order.indexOf(newItem));
                existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
            } else {
                order.add(newItem);
            }
        }
    }

    public void removeFromBill(FoodItem item) { order.remove(item);}

    public void clearBill() { order.clear();}

    public ObservableList<FoodItem> getItems() {
        ObservableList<FoodItem> items = FXCollections.observableArrayList();

        items.addAll(order);
        return items;
    }


}
