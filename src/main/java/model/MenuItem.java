package model;

public class MenuItem {
    private int id;

    private int restaurantID;
    private String name;
    private String description;
    private int price;
    private int pax;

    public MenuItem(int restaurantID) {
        this(restaurantID, "", "", 0, 0);
    }

    public MenuItem(int restaurantID, String name, String description, int price, int pax) {
       this(0, restaurantID, name, description, price, pax);
    }

    public MenuItem(int id, int restaurantID, String name, String description, int price, int pax) {
        this.id = id;
        this.restaurantID = restaurantID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pax = pax;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }
}
