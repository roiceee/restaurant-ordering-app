package model;

public class MenuItem {
    private String id;
    private String name;
    private String description;
    private int price;
    private int pax;

    public MenuItem() {

    }

    public MenuItem(String name, String description, int price, int pax) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pax = pax;
    }

    public MenuItem(String id, String name, String description, int price, int pax) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pax = pax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
