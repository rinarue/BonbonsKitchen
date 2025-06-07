package core;

public class Ingredient {
    private final String name;
    private final int price;
    private final String imagePath;

    public Ingredient(String name, int price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getImagePath() { return imagePath; }

    @Override
    public String toString() {
        return name;
    }
}
