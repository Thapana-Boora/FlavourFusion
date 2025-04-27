import java.util.*;

class Recipe {
    String name;
    String[] ingredients;
    boolean isVeg; // Add a flag for Veg or Non-Veg
    static Map<String, String[]> ingredientSubstitutions = new HashMap<>();

    static {
        // Substitution list
        ingredientSubstitutions.put("tomato sauce", new String[]{"tomato", "ketchup", "pureed tomato"});
        ingredientSubstitutions.put("butter", new String[]{"ghee"});
        ingredientSubstitutions.put("milk", new String[]{"almond milk", "soy milk", "coconut milk"});
        ingredientSubstitutions.put("sugar", new String[]{"honey", "maple syrup","jaggery"});
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

    public Recipe(String name, String[] ingredients, boolean isVeg) {
        this.name = name;
        this.ingredients = ingredients;
        this.isVeg = isVeg;
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
                missingIngredients.append(recipeIngredient).append(", ");
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
            new Recipe("Pasta", new String[]{"pasta", "tomato sauce", "cheese"}, true),
            new Recipe("Omelette", new String[]{"egg", "salt", "butter"}, false),
            new Recipe("Salad", new String[]{"lettuce", "tomato", "cucumber", "olive oil"}, true),
            new Recipe("Grilled Cheese", new String[]{"bread", "cheese", "butter"}, true),
            new Recipe("Fruit Smoothie", new String[]{"banana", "milk", "honey"}, true),
            new Recipe("Tomato Curry", new String[]{"tomato", "onions", "chilli powder", "oil", "salt"}, true),
            new Recipe("Paneer Butter Masala", new String[]{"paneer", "tomato puree", "onion", "butter", "spices", "cream"}, true),
            new Recipe("Curd Rice", new String[]{"rice", "curd", "salt", "mustard seeds", "green chilli"}, true),
            new Recipe("Cutlet", new String[]{"potato", "garam masala", "bread crumbs", "chilli powder", "rice flour"}, true),
            new Recipe("Paratha", new String[]{"wheat flour", "chickpeas", "ghee"}, true),
            new Recipe("Paneer Wrap", new String[]{"bread", "paneer", "onion", "tomato sauce", "cheese"}, true),
            new Recipe("Butter Chicken", new String[]{"chicken", "butter", "tomato", "curd", "garam masala", "spices"}, false),
            new Recipe("Fish Fry", new String[]{"fish", "ginger garlic paste", "chilli powder", "salt"}, false),
            new Recipe("Prawns Curry", new String[]{"prawns", "tomato", "oil", "mustard seeds", "onions", "spices"}, false),
            new Recipe("Eggplant Curry", new String[]{"egg plant", "spices", "onion", "curd", "tomato"}, true),
            new Recipe("Sandwich", new String[]{"bread", "butter", "tomato", "onion", "capsicum", "pepper", "salt"}, true),
            new Recipe("Palak Paneer", new String[]{"paneer", "spinach", "tomato", "garam masala", "chilli powder", "kasuri methi"}, true),
            new Recipe("Kadai Chicken", new String[]{"chicken", "chicken masala", "onion", "tomato", "capsicum", "red chilli", "ginger garlic paste"}, false),
            new Recipe("Chicken Lollipop", new String[]{"chicken", "tomato sauce", "ginger garlic paste", "garam masala"}, false),
            new Recipe("Sambar", new String[]{"sambar powder", "onion", "dal", "tamarind paste"}, true),
            new Recipe("Rasam", new String[]{"rasam powder", "pepper", "tamarind"}, true)
        };

        List<String> searchHistory = new ArrayList<>();

        while (true) {
            System.out.println("\nEnter available ingredients (comma separated) or type 'history' to view previous searches or 'exit' to quit:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.equalsIgnoreCase("history")) {
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

            String[] availableIngredients = input.split(",\\s*");

            // Map to store recipe name and missing count
            Map<Recipe, Integer> recipeMissingMap = new HashMap<>();
            for (Recipe recipe : recipes) {
                int missingCount = recipe.countMissingIngredients(availableIngredients);
                recipeMissingMap.put(recipe, missingCount);
            }

            // Find the first recipe with the least missing ingredients
            Recipe bestMatchRecipe = null;
            int leastMissingIngredients = Integer.MAX_VALUE;

            for (Map.Entry<Recipe, Integer> entry : recipeMissingMap.entrySet()) {
                if (entry.getValue() < leastMissingIngredients) {
                    leastMissingIngredients = entry.getValue();
                    bestMatchRecipe = entry.getKey();
                }
            }

            // Display the first matching recipe (if any)
            if (bestMatchRecipe != null) {
                String result = bestMatchRecipe.name + " | Type: " + (bestMatchRecipe.isVeg ? "Veg" : "Non-Veg") +
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
