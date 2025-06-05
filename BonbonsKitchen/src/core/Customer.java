package core;

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
}
