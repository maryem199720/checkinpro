package isetb.tp5.checkinproapp.utils;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    @GET("Employee/profile/44") // Utilisation de l'ID fixe 44
    Call<Employee> getProfile();

    @PUT("Employee/profile/44") // Utilisation de l'ID fixe 44
    Call<Employee> updateProfile(@Body Employee employee);

    @POST("/Employee/register")
    Call<Employee> registerEmployee(@Body Employee employee);

    @POST("Employee/login")
    Call<Employee> loginEmployee(@Body LoginRequest loginRequest);
}
