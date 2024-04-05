package com.example.savss.expensetracker;

// Import necessary libraries

// Define a package
package com.example.savss.expensetracker;

// Import necessary libraries for Android
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// Define the class AddTransactionFragment
public class AddTransactionFragment extends Fragment {
    // Declare variables
    private String transactionType = "+";
    private View addTransactionView;
    private LocalDatabaseHelper localDatabaseHelper;
    private Button income;
    private Button expense;
    private TextView incomeOrExpense;
    private EditText value;
    private Button clear;
    private EditText description;
    private Button add;
    private Spinner categorySpinner;
    private TextView dateTextView;
    private float defaultTextSize = 80;
    private float changedTextSize = 80;
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    Toast toast;

    // Override onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Override onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addTransactionView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        localDatabaseHelper = new LocalDatabaseHelper(addTransactionView.getContext(), null, null, 1);

        // Initialize UI components
        initialise();
        // Set listeners
        setListener();

        return addTransactionView;
    }

    // Initialize UI components
    @SuppressLint("ShowToast")
    private void initialise() {
        // Find UI components by their IDs
        income = addTransactionView.findViewById(R.id.income_button);
        expense = addTransactionView.findViewById(R.id.expense_button);
        incomeOrExpense = addTransactionView.findViewById(R.id.income_or_expense);
        value = addTransactionView.findViewById(R.id.valueDisplay);
        clear = addTransactionView.findViewById(R.id.clearButton);
        add = addTransactionView.findViewById(R.id.addButton);
        toast = Toast.makeText(addTransactionView.getContext(), "", Toast.LENGTH_SHORT);
        description = addTransactionView.findViewById(R.id.descriptionView);
        categorySpinner = addTransactionView.findViewById(R.id.categorySpinner);
        dateTextView = addTransactionView.findViewById(R.id.dateTextView);

        // Set default date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateTextView.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

        // Populate category spinner
        ArrayList<String> categories = new ArrayList<>(UserData.categories);
        categories.add(0, "Choose a category");
        ArrayAdapter arrayAdapter = new ArrayAdapter(addTransactionView.getContext(), R.layout.category_spinner_layout, categories.toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);
    }

    // Set listeners for UI components
    private void setListener() {
        value.addTextChangedListener(valueTextViewWatcher);
        clear.setOnClickListener(clearOnClickListener);
        add.setOnClickListener(addOnClickListener);
        income.setOnClickListener(incomeOnClickListener);
        expense.setOnClickListener(expenseOnClickListener);
        dateTextView.setOnClickListener(dateOnClickListener);
    }

    // OnClickListener for income button
    private View.OnClickListener incomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transactionType = "+";
            incomeOrExpense.setTextSize(80);
            incomeOrExpense.setText(transactionType);
        }
    };

    // OnClickListener for expense button
    private View.OnClickListener expenseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transactionType = "-";
            incomeOrExpense.setTextSize(100);
            incomeOrExpense.setText(transactionType);
        }
    };

    // OnClickListener for clear button
    private View.OnClickListener clearOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            value.setText("0.0");
            transactionType = "+";
            incomeOrExpense.setText(transactionType);
            description.setText("");
        }
    };

    // OnClickListener for add button
    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String valueOfTransactionType = transactionType.equals("+") ? "income" : "expense";

            if (Float.parseFloat(value.getText().toString()) == 0.0) {
                displayToast("Transaction amount cannot be 0");
                return;
            }

            if (categorySpinner.getSelectedItem().toString().equals("Choose a category")) {
                displayToast("A category has to be chosen");
                return;
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date transactionDate = null;
            try {
                transactionDate = simpleDateFormat.parse(dateTextView.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String message = "";

            /*if (valueOfTransactionType.equals("expense")) {
                message = notifyIfExceededLimit(categorySpinner.getSelectedItem().toString(), Float.parseFloat(value.getText().toString()));
            }*/

            localDatabaseHelper.addTransaction(String.valueOf(UserData.userID), UserData.categories.indexOf(categorySpinner.getSelectedItem().toString()) + 1, valueOfTransactionType, value.getText().toString(), description.getText().toString(), transactionDate);

            clear.callOnClick();
            displayToast(message + "Transaction Added Successfully");
        }
    };

    // OnClickListener for date text view
    private View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(addTransactionView.getContext(), R.style.Theme_AppCompat_Light_Dialog, datePickerDateSetListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            datePickerDialog.show();
        }
    };

    // DatePickerDialog OnDateSetListener
    private DatePickerDialog.OnDateSetListener datePickerDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month++;
            String pickedDate = day + "/" + month + "/" + year;
            dateTextView.setText(pickedDate);
        }
    };

    // Display toast message
    private void displayToast(String message) {
        Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }

    // TextWatcher for value text view
    private TextWatcher valueTextViewWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isTooLarge()) {
                changedTextSize  = defaultTextSize - (value.getText().toString().trim().length() - 8) * 6;
                value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, changedTextSize);
            }
            else {
                value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, defaultTextSize);
                changedTextSize = defaultTextSize;
            }
        }
        private boolean isTooLarge() {
            return value.getText().toString().trim().length() > 7;
        }
    };
}
