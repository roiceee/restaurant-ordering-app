package model;

public class AnalyticsModel {
    private final int orders;
    private final double grossRevenue;

    public AnalyticsModel(int orders, double grossRevenue) {
        this.orders = orders;
        this.grossRevenue = grossRevenue;
    }

    public double getGrossRevenue() {
        return grossRevenue;
    }

    public int getOrders() {
        return orders;
    }
}
