// Define a package
package com.example.savss.expensetracker;

// Import necessary libraries
import android.graphics.Color;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import java.util.ArrayList;

// Define the class BarChartExpenseData
public class BarChartExpenseData {
    // Declare instance variables
    private ArrayList<BarEntry> expenseAmounts = new ArrayList<>();
    private ArrayList<BarEntry> incomeAmounts = new ArrayList<>();
    private ArrayList<String> categories = new ArrayList<>();

    // Method to add data for a category
    public void add(String category, int expenseAmount, int incomeAmount) {
        expenseAmounts.add(new BarEntry(expenseAmounts.size(), expenseAmount));
        incomeAmounts.add(new BarEntry(incomeAmounts.size(), incomeAmount));
        categories.add(category);
    }

    // Method to get BarData for the chart
    public BarData getBarData() {
        // Create BarDataSet for expense data
        BarDataSet expenseBarDataSet = new BarDataSet(expenseAmounts, "Expense");
        expenseBarDataSet.setColors(Color.RED);
        expenseBarDataSet.setValueTextColor(Color.WHITE);
        
        // Create BarDataSet for income data
        BarDataSet incomeBarDataSet = new BarDataSet(incomeAmounts, "Income");
        incomeBarDataSet.setColors(Color.GREEN);
        incomeBarDataSet.setValueTextColor(Color.WHITE);

        // Create BarData object with both expense and income BarDataSets
        BarData barData = new BarData(expenseBarDataSet, incomeBarDataSet);
        barData.setValueTextSize(12f);
        barData.setValueFormatter(new LargeValueFormatter());
        return barData;
    }

    // Method to get the list of categories
    public ArrayList<String> getCategories() {
        return categories;
    }

    // Method to get the count of categories
    public int count() {
        return categories.size();
    }
}
