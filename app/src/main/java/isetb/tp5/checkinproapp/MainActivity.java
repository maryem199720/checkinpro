package isetb.tp5.checkinproapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.utils.ApiClient;
import isetb.tp5.checkinproapp.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText employeeIdField;
    private TextView employeeDetailsText;
    private Button fetchButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vérification si l'utilisateur est connecté
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);

            // Initialisation des vues
            employeeIdField = findViewById(R.id.employeeIdField);
            employeeDetailsText = findViewById(R.id.employeeDetailsText);
            fetchButton = findViewById(R.id.fetchButton);
            deleteButton = findViewById(R.id.deleteButton);

            // Gestion des clics
            fetchButton.setOnClickListener(v -> {
                String idInput = employeeIdField.getText().toString();
                if (idInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez saisir un ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long employeeId = Long.parseLong(idInput);
                fetchEmployee(employeeId);
            });

            deleteButton.setOnClickListener(v -> {
                String idInput = employeeIdField.getText().toString();
                if (idInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez saisir un ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long employeeId = Long.parseLong(idInput);
                deleteEmployee(employeeId);
            });
        }
    }

    private void fetchEmployee(Long employeeId) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.getEmployeeById(employeeId).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Employee employee = response.body();
                    employeeDetailsText.setText(
                            "Nom: " + employee.getNom() + "\n" +
                                    "Prénom: " + employee.getPrenom() + "\n" +
                                    "Email: " + employee.getEmail() + "\n" +
                                    "Poste: " + employee.getPoste()
                    );
                } else {
                    Toast.makeText(MainActivity.this, "Employé introuvable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEmployee(Long employeeId) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.deleteEmployee(employeeId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Employé supprimé avec succès", Toast.LENGTH_SHORT).show();
                    employeeDetailsText.setText("");  // Clear displayed details
                } else {
                    Toast.makeText(MainActivity.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
