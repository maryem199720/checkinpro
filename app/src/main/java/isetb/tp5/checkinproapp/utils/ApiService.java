package isetb.tp5.checkinproapp.utils;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/Employee/register")
    Call<Employee> registerEmployee(@Body Employee employee);

    @POST("Employee/login") // Ajouter un endpoint de connexion sur votre backend si nécessaire
    Call<Employee> loginEmployee(@Body LoginRequest loginRequest);

    @GET("employees/{id}")
        Call<Employee> getEmployeeById(@Path("id") Long id);

    @DELETE("employees/{id}")
        Call<Void> deleteEmployee(@Path("id") Long id);
    }



