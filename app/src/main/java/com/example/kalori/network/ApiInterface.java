package com.example.kalori.network;

import com.example.kalori.model.history.request.AddHistoryRequest;
import com.example.kalori.model.history.request.AddHistoryBreakfastRequest;
import com.example.kalori.model.history.request.AddHistoryDietRequest;
import com.example.kalori.model.history.request.AddHistoryDinnerRequest;
import com.example.kalori.model.history.request.AddHistoryLunchRequest;
import com.example.kalori.model.history.response.HistoryResponse;
import com.example.kalori.model.login.LoginResponse;
import com.example.kalori.model.nutrisi.NutrisiResponse;
import com.example.kalori.model.register.RegisterRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> doLogin(@Field("username") String username, @Field("password") String password);

    @POST("daftar")
    Call<ResponseBody> doRegister(@Body RegisterRequest request);

    @POST("historyDiet")
    Call<ResponseBody> addHistoryDiet(@Body AddHistoryDietRequest request);

    @POST("historyBreakfast")
    Call<ResponseBody> addHistoryBreakfast(@Body AddHistoryBreakfastRequest request);

    @POST("historyLunch")
    Call<ResponseBody> addHistoryLunch(@Body AddHistoryLunchRequest request);

    @POST("historyDinner")
    Call<ResponseBody> addHistoryDinner(@Body AddHistoryDinnerRequest request);

    @POST("history")
    Call<ResponseBody> addHistory(@Body AddHistoryRequest request);

    @GET("history")
    Call<HistoryResponse> getHistory(@Query("tanggal") String tanggal, @Query("username") String username);

    @GET("nutrisi")
    Call<NutrisiResponse> getNutrisi();


}
