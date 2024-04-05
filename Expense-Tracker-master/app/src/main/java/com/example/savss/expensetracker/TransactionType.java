// Define TransactionType enum
public enum TransactionType {
    // Define enum constants for income and expense
    Income("income"),
    Expense("expense");

    // Declare a private member variable to hold the string value of the enum
    private final String stringValue;

    // Constructor to initialize enum constants with string values
    TransactionType(String stringValue) {
        this.stringValue = stringValue;
    }

    // Override toString() method to return the string value of the enum
    @Override
    public String toString() {
        return this.stringValue;
    }
}
