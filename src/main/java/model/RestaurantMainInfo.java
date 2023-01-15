package model;

public class RestaurantMainInfo {
    String restaurantID;

    String name;

    String country;

    String region;

    String city;


    public RestaurantMainInfo(String restaurantID, String name, String country, String region, String city) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.country = country;
        this.region = region;
        this.city = city;

    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
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
