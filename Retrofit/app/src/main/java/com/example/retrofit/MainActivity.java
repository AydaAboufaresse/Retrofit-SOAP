package com.example.retrofit;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.beans.TypeCompte;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText soldeInput;
    private Spinner typeSpinner;
    private LinearLayout compteContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soldeInput = findViewById(R.id.soldeInput);
        typeSpinner = findViewById(R.id.typeSpinner);
        Button createButton = findViewById(R.id.createButton);
        compteContainer = findViewById(R.id.compteContainer);

        initializeTypeSpinner();

        createButton.setOnClickListener(v -> {
            String soldeText = soldeInput.getText().toString();
            String selectedType = typeSpinner.getSelectedItem().toString();

            if (soldeText.isEmpty()) {
                Toast.makeText(this, "Please enter solde", Toast.LENGTH_SHORT).show();
            } else {
                double solde = Double.parseDouble(soldeText);
                addCompteView(selectedType, solde);
                soldeInput.setText("");
            }
        });
    }

    private void initializeTypeSpinner() {
        ArrayList<String> types = new ArrayList<>();
        for (TypeCompte type : TypeCompte.values()) {
            types.add(type.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }
    private int convertDpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
    private void addCompteView(String type, double solde) {
        // Create the main LinearLayout for the account view
        LinearLayout compteLayout = new LinearLayout(this);
        compteLayout.setOrientation(LinearLayout.HORIZONTAL);
        compteLayout.setPadding(15, 15, 15, 15);
        compteLayout.setBackgroundColor(Color.parseColor("#6A458C"));

        // Set layout params for the compte layout
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 15, 0, 15);
        compteLayout.setLayoutParams(layoutParams);

        // Create the TextView that will display the account info
        TextView compteInfo = new TextView(this);
        compteInfo.setText(type + ": " + solde);
        compteInfo.setTextSize(16); // Set text size
        compteInfo.setTextColor(Color.BLACK); // Set text color
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        compteInfo.setLayoutParams(infoParams);

        // Set icon size in dp (adjust the value if needed)
        int iconSize = convertDpToPx(25); // Set icon size to 25dp

        // Create ImageView for "Edit" action
        ImageView editIcon = new ImageView(this);
        editIcon.setImageResource(R.drawable.ic_edit);  // Your edit icon
        editIcon.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize)); // Set size
        editIcon.setContentDescription("Edit Account");
        editIcon.setOnClickListener(v -> {
            // Show confirmation dialog for Edit
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Voulez vous modifier les infos du Compte?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        soldeInput.setText(String.valueOf(solde));
                        typeSpinner.setSelection(((ArrayAdapter<String>) typeSpinner.getAdapter()).getPosition(type));
                        compteContainer.removeView(compteLayout);
                    })
                    .setNegativeButton("Non", null)
                    .show();
        });

        // Create ImageView for "Delete" action
        ImageView deleteIcon = new ImageView(this);
        deleteIcon.setImageResource(R.drawable.ic_delete);  // Your delete icon
        deleteIcon.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize)); // Set size
        deleteIcon.setContentDescription("Delete Account");
        deleteIcon.setOnClickListener(v -> {
            // Show confirmation dialog for Delete
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Voulez vous supprimer le compte ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        compteContainer.removeView(compteLayout);
                        Toast.makeText(this, "Compte deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Non", null)
                    .show();
        });

        // Add views to the LinearLayout
        compteLayout.addView(compteInfo); // Account information text
        compteLayout.addView(editIcon); // Edit icon
        compteLayout.addView(deleteIcon); // Delete icon

        // Add the compte layout to the main container
        compteContainer.addView(compteLayout);
    }





}