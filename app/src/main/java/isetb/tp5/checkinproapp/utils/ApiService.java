package isetb.tp5.checkinproapp.utils;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/Employee/register")
    Call<Employee> registerEmployee(@Body Employee employee);

    @POST("Employee/login") // Ajouter un endpoint de connexion sur votre backend si nécessaire
    Call<Employee> loginEmployee(@Body LoginRequest loginRequest);
}

