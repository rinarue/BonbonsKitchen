package core;

import java.util.List;

public class GameRegistry {
    private static List<Ingredient> allIngredients;

    public static void setAllIngredients(List<Ingredient> ingredients) {
        allIngredients = ingredients;
    }

    public static Ingredient getIngredientByName(String name) {
        return allIngredients.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private static List<Dish> allDishes;

    public static void setAllDishes(List<Dish> dishes) {
        allDishes = dishes;
    }

    public static Dish getDishByName(String name) {
        return allDishes.stream()
                .filter(d -> d.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
