package core;

import java.util.List;

public class Dish {
    private final String name;
    private final List<Ingredient> ingredients;
    private int price; // base price of dish

    public Dish(String name, List<Ingredient> ingredients, int price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }
}
