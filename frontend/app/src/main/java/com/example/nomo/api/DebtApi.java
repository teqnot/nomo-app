package com.example.nomo.api;

import com.example.nomo.model.DebtItem;
import com.example.nomo.model.GetDebtsRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DebtApi {
    @POST("debts/by-user")
    Call<List<DebtItem>> getDebtsByUserId(@Body GetDebtsRequest request);
}
