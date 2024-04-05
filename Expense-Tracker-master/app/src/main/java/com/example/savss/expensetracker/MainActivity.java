// Import required libraries

// Define MainActivity class
public class MainActivity extends AppCompatActivity {

    // Override onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set content view to activity_main layout
        setContentView(R.layout.activity_main);
    }

    // Method to handle click event on login button
    public void loginButton_onClick(View v){
        // Create intent to navigate to LoginActivity
        Intent toLoginPage = new Intent(this, LoginActivity.class);
        
        // Start LoginActivity
        startActivity(toLoginPage);
    }

    // Method to handle click event on sign up button
    public void signUpButton_onClick(View v){
        // Create intent to navigate to SignUpActivity
        Intent toSignUpPage = new Intent(this, SignUpActivity.class);
        
        // Start SignUpActivity
        startActivity(toSignUpPage);
    }
}
