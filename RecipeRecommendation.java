import java.util.*;
class Recipe {
    String name;
    String[] ingredients;
    boolean isVeg;
    String[] category; // Changed to String[] for multiple categories
    int prepTime;
    static Map<String, String[]> ingredientSubstitutions = new HashMap<>();

    static {
        ingredientSubstitutions.put("tomato sauce", new String[]{"tomato", "ketchup", "pureed tomato"});
        ingredientSubstitutions.put("butter", new String[]{"ghee"});
        ingredientSubstitutions.put("milk", new String[]{"almond milk", "soy milk", "coconut milk"});
        ingredientSubstitutions.put("sugar", new String[]{"honey", "maple syrup", "jaggery"});
        ingredientSubstitutions.put("olive oil", new String[]{"groundnut oil", "mustard oil"});
        ingredientSubstitutions.put("vegetable oil", new String[]{"sunflower oil", "coconut oil"});
        ingredientSubstitutions.put("cheese", new String[]{"cream cheese", "mozzarella"});
        ingredientSubstitutions.put("bread", new String[]{"bun", "tortilla"});
        ingredientSubstitutions.put("vinegar", new String[]{"lemon juice"});
        ingredientSubstitutions.put("chilli powder", new String[]{"pepper"});
        ingredientSubstitutions.put("chicken masala", new String[]{"garam masala"});
        ingredientSubstitutions.put("green peas", new String[]{"chick peas"});
        ingredientSubstitutions.put("bread crumbs", new String[]{"cornflakes powder", "roasted suji"});
        ingredientSubstitutions.put("yogurt", new String[]{"curd", "buttermilk"});
        ingredientSubstitutions.put("corn flour", new String[]{"rice flour"});
        ingredientSubstitutions.put("maida", new String[]{"wheat flour", "besan", "rice flour"});
        ingredientSubstitutions.put("tamarind", new String[]{"amchur", "lemon"});
        ingredientSubstitutions.put("paneer", new String[]{"tofu", "chenna"});
        ingredientSubstitutions.put("mustard seeds", new String[]{"cumin seeds", "fenugreek seeds"});
        ingredientSubstitutions.put("curry masala", new String[]{"sambar podi", "rasam podi"});
        ingredientSubstitutions.put("chutney powder", new String[]{"idli podi", "coconut chutney powder"});
        ingredientSubstitutions.put("hazelnut", new String[]{"almond"});
    }

    public Recipe(String name, String[] ingredients, boolean isVeg, String[] category, int prepTime) {
        this.name = name;
        this.ingredients = ingredients;
        this.isVeg = isVeg;
        this.category = category;
        this.prepTime = prepTime;
    }

    public int countMissingIngredients(String[] availableIngredients) {
        int missingCount = 0;
        for (String recipeIngredient : ingredients) {
            if (!isIngredientAvailable(recipeIngredient, availableIngredients)) {
                missingCount++;
            }
        }
        return missingCount;
    }

    public String getMissingIngredients(String[] availableIngredients) {
        StringBuilder missingIngredients = new StringBuilder();
        for (String recipeIngredient : ingredients) {
            if (!isIngredientAvailable(recipeIngredient, availableIngredients)) {
                missingIngredients.append(recipeIngredient);
                String[] substitutes = ingredientSubstitutions.get(recipeIngredient);
                if (substitutes != null && substitutes.length > 0) {
                    missingIngredients.append(" (Try: ").append(String.join(", ", substitutes)).append(")");
                }
                missingIngredients.append(", ");
            }
        }
        return missingIngredients.length() > 0 ? missingIngredients.substring(0, missingIngredients.length() - 2) : "None";
    }

    private boolean isIngredientAvailable(String recipeIngredient, String[] availableIngredients) {
        for (String available : availableIngredients) {
            if (recipeIngredient.equalsIgnoreCase(available.trim()) || isTasteSubstitute(recipeIngredient, available.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean isTasteSubstitute(String recipeIngredient, String availableIngredient) {
        if (ingredientSubstitutions.containsKey(recipeIngredient)) {
            for (String substitute : ingredientSubstitutions.get(recipeIngredient)) {
                if (substitute.equalsIgnoreCase(availableIngredient)) {
                    return true;
                }
            }
        }
        return false;
    }
}

public class RecipeRecommendation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Recipe[] recipes = {
            new Recipe("Pasta", new String[]{"pasta", "tomato sauce", "cheese"}, true, new String[]{"Dinner"}, 20),
            new Recipe("Omelette", new String[]{"egg", "salt", "butter"}, false, new String[]{"Breakfast"}, 10),
            new Recipe("Salad", new String[]{"lettuce", "tomato", "cucumber", "olive oil"}, true, new String[]{"Lunch"}, 15),
            new Recipe("Grilled Cheese", new String[]{"bread", "cheese", "butter"}, true, new String[]{"Snack"}, 15),
            new Recipe("Fruit Smoothie", new String[]{"banana", "milk", "honey"}, true, new String[]{"Breakfast"}, 10),
            new Recipe("Tomato Curry", new String[]{"tomato", "onions", "chilli powder", "oil", "salt"}, true, 
            new String[]{"Lunch", "Dinner"}, 30),
            new Recipe("Paneer Butter Masala", new String[]{"paneer", "tomato puree", "onion", "butter", "spices", "cream"},
             true, new String[]{"Lunch", "Dinner"}, 40),
            new Recipe("Curd Rice", new String[]{"rice", "curd", "salt", "mustard seeds", "green chilli"}, true,
             new String[]{"Lunch"}, 20),
            new Recipe("Cutlet", new String[]{"potato", "garam masala", "bread crumbs", "chilli powder", "rice flour"},
             true, new String[]{"Snack"}, 35),
            new Recipe("Paratha", new String[]{"wheat flour", "chickpeas", "ghee"}, true, new String[]{"Breakfast"}, 25),
            new Recipe("Paneer Wrap", new String[]{"bread", "paneer", "onion", "tomato sauce", "cheese"}, true, 
            new String[]{"Snack"}, 20),
            new Recipe("Butter Chicken", new String[]{"chicken", "butter", "tomato", "curd", "garam masala", "spices"}, 
            false, new String[]{"Lunch", "Dinner"}, 45),
            new Recipe("Fish Fry", new String[]{"fish", "ginger garlic paste", "chilli powder", "salt"}, false, 
            new String[]{"Dinner"}, 25),
            new Recipe("Prawns Curry", new String[]{"prawns", "tomato", "oil", "mustard seeds", "onions", "spices"},
             false, new String[]{"Lunch", "Dinner"}, 35),
            new Recipe("Eggplant Curry", new String[]{"egg plant", "spices", "onion", "curd", "tomato"}, true,
             new String[]{"Lunch", "Dinner"}, 30),
            new Recipe("Sandwich", new String[]{"bread", "butter", "tomato", "onion", "capsicum", "pepper", "salt"}, true,
             new String[]{"Snack"}, 15),
            new Recipe("Palak Paneer", new String[]{"paneer", "spinach", "tomato", "garam masala", "chilli powder", "kasuri methi"}, 
            true, new String[]{"Lunch", "Dinner"}, 40),
            new Recipe("Kadai Chicken", new String[]{"chicken", "chicken masala", "onion", "tomato", "capsicum", "red chilli", 
            "ginger garlic paste"}, false, new String[]{"Lunch", "Dinner"}, 40),
            new Recipe("Chicken Lollipop", new String[]{"chicken", "tomato sauce", "ginger garlic paste", "garam masala"}, 
            false, new String[]{"Snack"}, 30),
            new Recipe("Sambar", new String[]{"sambar powder", "onion", "dal", "tamarind paste"}, true, new String[]{"Lunch"}, 
            35),
            new Recipe("Rasam", new String[]{"rasam powder", "pepper", "tamarind"}, true, new String[]{"Lunch"}, 20)
        };

        List<String> searchHistory = new ArrayList<>();

        while (true) {
            System.out.println("\nEnter category (breakfast, lunch, dinner, snack, all) or type 'history', 'clear', or 'exit':");
            String category = scanner.nextLine().trim().toLowerCase();

            if (category.equals("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (category.equals("history")) {
                System.out.println("\n--- Search History ---");
                if (searchHistory.isEmpty()) {
                    System.out.println("No previous searches.");
                } else {
                    for (String record : searchHistory) {
                        System.out.println(record);
                    }
                }
                continue;
            }

            if (category.equals("clear")) {
                searchHistory.clear();
                System.out.println("\nSearch history cleared.");
                continue;
            }

            if (!category.equals("breakfast") && !category.equals("lunch") && !category.equals("dinner") && 
                !category.equals("snack") && !category.equals("all")) {
                System.out.println("\nInvalid category. Please enter 'breakfast', 'lunch', 'dinner', 'snack', 'all', 'history', 'clear', or 'exit'.");
                continue;
            }

            System.out.println("\nEnter preference (veg, non-veg, all):");
            String preference = scanner.nextLine().trim().toLowerCase();

            if (!preference.equals("veg") && !preference.equals("non-veg") && !preference.equals("all")) {
                System.out.println("\nInvalid preference. Please enter 'veg', 'non-veg', or 'all'.");
                continue;
            }

            System.out.println("\nEnter available ingredients (comma separated):");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("\nPlease enter at least one ingredient.");
                continue;
            }

            String[] availableIngredients = input.split(",\\s*");

            // Filter recipes based on category and preference
            List<Recipe> filteredRecipes = new ArrayList<>();
            for (Recipe recipe : recipes) {
                boolean categoryMatch = category.equals("all");
                for (String cat : recipe.category) {
                    if (cat.equalsIgnoreCase(category)) {
                        categoryMatch = true;
                        break;
                    }
                }
                if (categoryMatch && 
                    (preference.equals("all") || 
                     (preference.equals("veg") && recipe.isVeg) || 
                     (preference.equals("non-veg") && !recipe.isVeg))) {
                    filteredRecipes.add(recipe);
                }
            }

            // Find the recipe with the least missing ingredients
            Recipe bestMatchRecipe = null;
            int leastMissingIngredients = Integer.MAX_VALUE;

            for (Recipe recipe : filteredRecipes) {
                int missingCount = recipe.countMissingIngredients(availableIngredients);
                if (missingCount < leastMissingIngredients) {
                    leastMissingIngredients = missingCount;
                    bestMatchRecipe = recipe;
                }
            }

            // Display the best matching recipe (if any)
            if (bestMatchRecipe != null) {
                String result = bestMatchRecipe.name + " | Type: " + (bestMatchRecipe.isVeg ? "Veg" : "Non-Veg") +
                        " | Category: " + String.join(", ", bestMatchRecipe.category) +
                        " | Prep Time: " + bestMatchRecipe.prepTime + " minutes" +
                        " | Missing Ingredients: " + bestMatchRecipe.getMissingIngredients(availableIngredients);
                System.out.println("\n--- Best Matching Recipe ---");
                System.out.println(result);
                searchHistory.add(result);
            } else {
                System.out.println("\nNo matching recipes found.");
            }
        }

        scanner.close();
    }
}
