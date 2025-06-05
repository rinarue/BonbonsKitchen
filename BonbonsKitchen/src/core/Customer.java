package core;

public class Customer {
    private final String name;
    private final String bio;
    private final Ingredient favoriteIngredient;
    //private final int basePayment; // money paid if dish has favorite ingredient
    private final String imageFileName; // NEW FIELD for image

    public Customer(String name, String bio, Ingredient favoriteIngredient, String imageFileName) {
        this.name = name;
        this.bio = bio;
        this.favoriteIngredient = favoriteIngredient;
        //this.basePayment = basePayment;
        this.imageFileName = imageFileName;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public Ingredient getFavoriteIngredient() {
        return favoriteIngredient;
    }

    //public int getBasePayment() {
    //    return basePayment;
    //}

    public String getImageFileName() {
        return imageFileName;
    }
}
