package model;

public class RestaurantMainInfo {
    int restaurantID;

    String name;

    String country;

    String region;

    String city;

    public RestaurantMainInfo() {

    }

    public RestaurantMainInfo(int restaurantID, String name, String country, String region, String city) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.country = country;
        this.region = region;
        this.city = city;

    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
