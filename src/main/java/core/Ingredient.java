package main.java.core;

public class Ingredient {
    private final String name;
    private final double price;
    private final String imagePath;

    public Ingredient(String name, double price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }

    @Override
    public String toString() {
        return name;
    }
}
