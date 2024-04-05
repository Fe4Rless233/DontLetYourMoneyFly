// Define TransactionData class
public class TransactionData {
    // Declare private member variables
    private int id;
    private String amount;
    private Date dateTime;
    private String category;
    private String description;
    private String transactionType;

    // Constructor to initialize TransactionData object
    public TransactionData(int id, String amount, Date dateTime, String category, String description, String transactionType) {
        // Assign values to member variables
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.category = category;
        this.description = description;
        // Capitalize the first letter of transactionType and convert the rest to lowercase
        this.transactionType = transactionType.substring(0, 1).toUpperCase() + transactionType.substring(1).toLowerCase();
    }

    // Getter method for id
    public int getId() {
        return id;
    }

    // Getter method for amount
    public String getAmount() {
        return amount;
    }

    // Getter method for dateTime
    public Date getDateTime() {
        return dateTime;
    }

    // Getter method for category
    public String getCategory() {
        return category;
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // Getter method for transactionType
    public String getTransactionType() {
        return transactionType;
    }
}
