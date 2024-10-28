package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.recipeapp.datahandler.CSVDataHandler;
import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        displayRecipes();
                        break;
                    case "2":
                        addNewRecipe();
                        break;
                    case "3":
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /*
     * - DataHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     * - IOExceptionを受け取った場合はError reading file: 例外のメッセージとコンソールに表示します
     */
    public void displayRecipes() {
        try {
            if (dataHandler.readData().isEmpty()) {
                System.out.println("No recipes available.");
                return;
            }

            ArrayList<Recipe> recipes = dataHandler.readData();
            ;
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            System.out.println("\n" + "Recipes:");
            System.out.println("-----------------------------------");
            for (int i = 0; i < recipes.size(); i++) {
                System.out.println("Recipe Name: " + recipes.get(i).getName());
                System.out.print("Main Ingredients: ");
                ingredients = recipes.get(i).getIngredients();
                for (int j = 0; j < ingredients.size(); j++) {
                    if (j < ingredients.size() - 1) {
                        System.out.print(ingredients.get(j).getName() + ",");
                    } else {
                        System.out.print(ingredients.get(j).getName());
                    }
                }
                System.out.println();
                System.out.println("-----------------------------------");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void addNewRecipe() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Recipe recipe;
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            System.out.println("Adding a new recipe.");

            System.out.print("Enter recipe name: ");
            String name = br.readLine();

            System.out.println("Enter ingredients (type 'done' when finished):");
            Boolean done = true;
            while (done) {
                System.out.print("Ingredient: ");
                String ing = br.readLine();
                if (!(ing.equals("done"))) {
                    ingredients.add(new Ingredient(ing));
                } else {
                    done = false;
                }
            }

            Recipe addRecipe = new Recipe(name, ingredients);

            dataHandler.writeData(addRecipe);
            System.out.println("Recipe added successfully.");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
