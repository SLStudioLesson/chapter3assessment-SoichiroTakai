package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {
    private String filePath;

    public CSVDataHandler() {
        this.filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getMode() {
        return "CSV";
    }

    // recipes.csvからレシピデータを読み込み、それをリスト形式で返します。
    @Override
    public ArrayList<Recipe> readData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            ArrayList<Recipe> recipes = new ArrayList<>();
            int a;
            String name;
            // ArrayList<Ingredient> ingredients = new ArrayList<>();
            List<String> strIngredients;

            while ((line = reader.readLine()) != null) {
                a = line.indexOf(',');
                name = line.substring(0, a);

                strIngredients = Arrays.asList(line.substring(a + 1).split(","));
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for (String string : strIngredients) {
                    ingredients.add(new Ingredient(string));
                }

                recipes.add(new Recipe(name, ingredients));

            }
            return recipes;
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return null;
    }

    @Override
    public void writeData(Recipe recipe) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            
            
            fileWriter.write(recipe.getName() + ",");

            for (Ingredient ingredient : recipe.getIngredients()) {
                fileWriter.write(ingredient.getName());
                
            }
            fileWriter.newLine();

        } catch (Exception e) {
        }

    }

    @Override
    public ArrayList<Recipe> searchData(String keyword) throws IOException {
        return null;
    }

}
