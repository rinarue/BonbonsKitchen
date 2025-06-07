package core;

import core.Ingredient;

import java.util.Objects;

public class Customer {
    private final String name;
    private final String bio;
    private final Ingredient favoriteIngredient;
    private final String imagePath;

    public Customer(String name, String bio, Ingredient favoriteIngredient, String imagePath) {
        this.name = name;
        this.bio = bio;
        this.favoriteIngredient = favoriteIngredient;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public String getBio() { return bio; }
    public Ingredient getFavoriteIngredient() { return favoriteIngredient; }
    public String getImagePath() { return imagePath; }
    // In core/Customer.java
    public String getHint() {
        return "I’m craving something with " + favoriteIngredient.getName().toLowerCase() + "!";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name); // or compare by unique ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // same field used in equals
    }
}
