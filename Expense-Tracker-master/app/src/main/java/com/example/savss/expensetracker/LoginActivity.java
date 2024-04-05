// Import required libraries
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// Define LoginActivity class
public class LoginActivity extends AppCompatActivity {

    // Declare variables
    Toast toast;
    private FirebaseAuth firebaseAuth;

    // Initialize activity
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        
        // Initialize toast object
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    // Method to validate login credentials
    public void loginValidation(View v) {
        // Get references to input fields
        EditText id = findViewById(R.id.emailAddress);
        EditText password = findViewById(R.id.password);

        // Check if email field is empty
        if (id.getText().toString().isEmpty()){
            displayError(R.string.emptyIDError, id);
            return;
        }

        // Check if password field is empty
        if (password.getText().toString().isEmpty()){
            displayError(R.string.emptyPasswordError, password);
            return;
        }
        else {
            // Attempt Firebase authentication
            firebaseAuth.signInWithEmailAndPassword(id.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // Check if authentication is successful
                    if (task.isSuccessful()) {
                        // If successful, navigate to HomeActivity
                        Intent inn = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(inn);
                        finish();
                    } else {
                        // If unsuccessful, display error message
                        Toast.makeText(LoginActivity.this, "No Record Has Found Please Signup ", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        // Instantiate LocalDatabaseHelper
        LocalDatabaseHelper localDatabaseHelper = new LocalDatabaseHelper(LoginActivity.this, null, null, 1);

        // Check credentials against local database (to be removed in final product)
        if (id.getText().toString().equals("a") && password.getText().toString().equals("a")) {
            Intent toDashboard = new Intent(this, HomeActivity.class);
            UserData.userID = 1;
            startActivity(toDashboard);
        }

        // Determine ID type (email or phone number)
        IDType idType;
        if (id.getText().toString().contains("@")) {
            idType = IDType.Email;
        }
        else {
            idType = IDType.PhoneNumber;
        }

        // Validate credentials against local database
        if (password.getText().toString().equals(localDatabaseHelper.getPassword(id.getText().toString(), idType))) {
            Intent toDashboard = new Intent(this, HomeActivity.class);
            UserData.userID = localDatabaseHelper.getUserID(id.getText().toString(), idType);
            startActivity(toDashboard);
        }
        else {
            displayError(R.string.loginErrorMessage);
        }
    }

    // Method to display error message
    private void displayError(int message) {
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }

    // Method to display error message and apply shake animation to view
    private void displayError(int message, View view) {
        Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        view.setAnimation(animShake);
        view.startAnimation(animShake);

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }
}
