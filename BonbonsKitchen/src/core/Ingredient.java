package core;

import java.util.Objects;

public class Ingredient {
    private final String name;
    private final String imagePath; // e.g. "assets/tomato.png"
    private int price;  // new field for cost

    public Ingredient(String name, String imagePath, int price) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    // New getter for price
    public int getPrice() {
        return price;
    }

    // Optional: new setter for price if you want to change it later
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
