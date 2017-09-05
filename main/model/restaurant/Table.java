package main.model.restaurant;


public class Table {
    private int numCustomers;
    private int tableID;
    private int maxCustomers;
    private Bill bill;
    private TableStatus status;

    public Table(int maxCapacity, int tableID) {
        numCustomers = 0;
        this.tableID = tableID;
        bill = new Bill(0);
        status = TableStatus.available;
        maxCustomers = maxCapacity;
    }

    public boolean dine(Integer customers) {
        if(customers > this.maxCustomers)
            return false;

        assert status.equals(TableStatus.available);
        bill = new Bill(customers);
        status = TableStatus.inProgress;
        numCustomers = customers;
        return true;

    }

    public void checkPlease() {
        assert status.equals(TableStatus.inProgress);


    }

    public void finishDine() {
        assert status.equals(TableStatus.paidFor);


    }

    public void makeOrder() {

    }

    public Bill getBill() {
        return bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table = (Table) o;

        return tableID == table.tableID;
    }

    @Override
    public int hashCode() {
        return tableID;
    }

}
