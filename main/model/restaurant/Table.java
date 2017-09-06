package main.model.restaurant;


public class Table {
    private int numCustomers;
    private int tableID;
    private int maxCustomers;
    private Bill bill;


    private Employee waiter;
    private TableStatus status;

    public Table(int maxCapacity, int tableID) {
        numCustomers = 0;
        this.tableID = tableID;
        bill = new Bill();
        status = TableStatus.available;
        maxCustomers = maxCapacity;
    }

    public void dine(Integer customers, Employee waiter) {
        if(customers > this.maxCustomers)
            return;

        assert status.equals(TableStatus.available);
        this.waiter = waiter;
        status = TableStatus.inProgress;
        numCustomers = customers;
    }

    public Employee getWaiter() {
        return waiter;
    }

    public void setWaiter(Employee waiter) {
        this.waiter = waiter;
    }

    public int getCapacity() {
        return maxCustomers;
    }

    public int getCustomers() {
        return numCustomers;
    }

    public void setCustomers(int forCusts) {numCustomers = forCusts;}

    public void checkPlease() {
        assert status.equals(TableStatus.inProgress);
        status = TableStatus.inBilling;
    }

    public void finishDine() {
        bill.clearBill();
        numCustomers = 0;
        status = TableStatus.available;
        waiter = null;
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
