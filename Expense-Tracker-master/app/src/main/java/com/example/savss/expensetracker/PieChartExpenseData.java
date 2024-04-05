// Import required libraries

// Define PieChartExpenseData class
public class PieChartExpenseData {
    // Define private variables to store expense amounts and total expense amount
    private ArrayList<PieEntry> expenseAmounts = new ArrayList<>();
    private int totalExpenseAmount = 0;

    // Method to add expense data for a category
    public void add(String category, int expenseAmount) {
        // Update total expense amount
        totalExpenseAmount += expenseAmount;
        
        // Add new PieEntry for the category with its expense amount
        expenseAmounts.add(new PieEntry(expenseAmount, category));
    }

    // Method to get PieDataSet for the pie chart
    public PieDataSet getPieDataSet() {
        // Create a new PieDataSet with expense data
        PieDataSet pieDataSet = new PieDataSet(expenseAmounts, "");
        
        // Set properties for the pie chart slices
        pieDataSet.setSliceSpace(10); // Set space between slices
        pieDataSet.setValueTextSize(20f); // Set text size for slice values
        pieDataSet.setFormSize(20f); // Set size for legend forms
        pieDataSet.setValueTextColor(Color.WHITE); // Set text color for slice values
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set colors for slices
        
        // Return the PieDataSet
        return pieDataSet;
    }

    // Method to get total expense amount
    public int getTotalExpenseAmount() {
        return totalExpenseAmount;
    }
}
