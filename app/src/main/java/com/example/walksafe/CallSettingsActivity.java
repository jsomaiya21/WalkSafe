package com.example.walksafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Objects;

public class CallSettingsActivity extends AppCompatActivity {

    public static final String TAG = "CallSettingsActivity";
    private TextView tvEmCall1;
    private TextView tvEmCall1Description;
    private EditText etEmCall1;
    private Button btnCallSave1;
    private TextView tvEmWord1Description;
    private EditText etEmWord1;
    private Button btnWordSave1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_settings);

        tvEmCall1 = findViewById(R.id.tvEmCall1);
        tvEmCall1Description = findViewById(R.id.tvEmCall1Description);
        etEmCall1 = findViewById(R.id.etEmCall1);
        btnCallSave1 = findViewById(R.id.btnCallSave1);
        tvEmWord1Description = findViewById(R.id.tvEmWord1Description);
        etEmWord1 = findViewById(R.id.etEmWord1);
        btnWordSave1 = findViewById(R.id.btnWordSave1);

        ParseUser currentUser = ParseUser.getCurrentUser();

        String currentNumber1 = currentUser.get("firstCall").toString();
        etEmCall1.setText(currentNumber1);

        String currentWord1 = "";

        if (currentUser.get("setFirstCallWord").equals(true)) {
            currentWord1 = currentUser.get("firstCallWord").toString();
            etEmWord1.setText(currentWord1);
        }

        btnCallSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String callNumber1 = etEmCall1.getText().toString();
                if (!(callNumber1.matches("[0-9]+"))){
                    Toast.makeText(CallSettingsActivity.this, "Phone number must contain numbers only", Toast.LENGTH_SHORT).show();
                }
                else if (callNumber1.equals("911") || callNumber1.equals("988") || callNumber1.length() == 9) {
                    currentUser.put("firstCall", callNumber1);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(CallSettingsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(CallSettingsActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnWordSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String callWord1 = etEmWord1.getText().toString();
                if (!(callWord1.isEmpty())) {
                    currentUser.put("firstCallWord", callWord1);
                    if (currentUser.get("setFirstCallWord").equals(false)) {
                        currentUser.put("setFirstCallWord", true);
                    }
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(CallSettingsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "sdmdfkm" + callWord1);
                        }
                    });
                }
                else {
                    Toast.makeText(CallSettingsActivity.this, "Please enter a valid word!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
