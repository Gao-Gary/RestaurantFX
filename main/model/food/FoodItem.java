package main.model.food;

public class FoodItem {
    private Double quantity;
    private Double price;
    private String name;
    private FoodCategory type;

    public FoodItem(Double price, String name, FoodCategory type) {
        this.type = type;
        this.price = price;
        this.name = name;
        this.quantity = 0.0;
    }

    public void addOne() {
        quantity++;
    }

    public void removeOne() {
        quantity--;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodCategory getType() {
        return type;
    }

    public void setType(FoodCategory type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodItem foodItem = (FoodItem) o;

        if (!getPrice().equals(foodItem.getPrice())) return false;
        if (!getName().equals(foodItem.getName())) return false;
        return getType() == foodItem.getType();
    }

    @Override
    public int hashCode() {
        int result = getPrice().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
}
