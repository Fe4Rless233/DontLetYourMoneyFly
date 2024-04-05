package com.example.savss.expensetracker;

// Import necessary libraries

// Define a package
package com.example.savss.expensetracker;

// Import necessary libraries for Android testing
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

// Import necessary libraries for JUnit testing
import org.junit.Test;
import org.junit.runner.RunWith;

// Import assertion library for test assertions
import static org.junit.Assert.*;

// Define a class for instrumented testing
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    // Define a test method
    @Test
    public void useAppContext() throws Exception {
        // Get the context of the app under test
        Context appContext = InstrumentationRegistry.getTargetContext();

        // Assert that the package name of the context matches the expected package name
        assertEquals("com.example.savss.expensetracker", appContext.getPackageName());
    }
}
