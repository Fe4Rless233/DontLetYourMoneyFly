package com.example.savss.expensetracker;

// Import necessary libraries

// Define a package
package com.example.savss.expensetracker;

// Import necessary libraries for Android
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

// Define the class AppSettings
public class AppSettings extends Fragment {
    // Declare variables
    private ViewSwitcher viewSwitcher;
    Button btnNext, btnPrev, btnCan;
    View viewapp;

    // Override onCreate method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Override onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewapp = inflater.inflate(R.layout.fragment_app_settings,container, false);
        // Create the table
        tableCreate();

        // View switcher code
        btnNext = (Button) viewapp.findViewById(R.id.addCategory);
        btnPrev = (Button) viewapp.findViewById(R.id.saveCategory);
        btnCan = (Button) viewapp.findViewById(R.id.cancel);
        viewSwitcher = (ViewSwitcher) viewapp.findViewById(R.id.simpleViewSwitcher);
        btnNext.setOnClickListener(setViewSwitcherNext);
        btnPrev.setOnClickListener(setViewSwitcherPrev);
        btnCan.setOnClickListener(setViewSwitcherCancel);
        return viewapp;
    }

    // OnClickListener for next button of view switcher
    private View.OnClickListener setViewSwitcherNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewSwitcher.setDisplayedChild(1);
        }
    };

    // OnClickListener for previous button of view switcher
    private View.OnClickListener setViewSwitcherPrev = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LocalDatabaseHelper DB = new LocalDatabaseHelper(getActivity(), null, null, 1);
            TextView cat= viewSwitcher.findViewById(R.id.category);
            TextView bud= viewSwitcher.findViewById(R.id.budget);
            int budget=Integer.parseInt(bud.getText().toString().trim());
            DB.makeNewCategory(cat.getText().toString(), budget);
            tableCreate();
            DB.initializeUserData(UserData.userID);
            viewSwitcher.setDisplayedChild(0);
        }
    };

    // OnClickListener for cancel button of view switcher
    private View.OnClickListener setViewSwitcherCancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewSwitcher.setDisplayedChild(0);
        }
    };

    // Method to create table dynamically
    public void tableCreate() {
        // Create database helper instance
        LocalDatabaseHelper DB = new LocalDatabaseHelper(getActivity(), null, null, 1);
        // Get category details from database
        ArrayList<String> categories = DB.getAllCategories();
        ArrayList<Integer> budgets = DB.getAllCategoryBudgets();
        ArrayList<Float> expenses = DB.getCategoryWiseExpenses();

        // Initialize variables
        int count=categories.size();
        String rv[]=new String[count];
        for(int i=0;i<count;i++) {
            rv[i]=""+(i+1);
        }
        String cv[]={"Category", "Budget", "%Spent"};
        int rowCount=count+1;
        int columnCount=cv.length;

        // Set table layout parameters
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = (TableLayout) viewapp.findViewById(R.id.cat_table);
        tableLayout.removeAllViewsInLayout();
        tableLayout.setBackgroundColor(Color.BLACK);

        // Set table row parameters
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < rowCount; i++) {
            // Create table row
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setBackgroundColor(Color.BLACK);

            for (int j= 0; j < columnCount+1; j++) {
                // Create text view
                TextView textView = new TextView(getActivity());
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                // Set text for the cells
                String rowentry[]=new String[3];
                if(i!=0) {
                    rowentry[0]=categories.get(i-1);
                    rowentry[1]=""+budgets.get(i-1);
                    float spent;
                    if(i-1<expenses.size())
                        spent = expenses.get(i-1)/budgets.get(i-1);
                    else
                        spent= (float) 0.0;
                    rowentry[2]=spent+"%";
                }

                if (i ==0 && j==0){
                    textView.setText("SNo");
                } else if(i==0){
                    textView.setText(cv[j-1]);
                } else if( j==0){
                    textView.setText(rv[i-1]);
                } else {
                    textView.setText(rowentry[j-1]);
                }

                // Add text view to table row
                tableRow.addView(textView, tableRowParams);
            }

            // Add table row to table layout
            tableLayout.addView(tableRow, tableLayoutParams);
        }
    }
}
