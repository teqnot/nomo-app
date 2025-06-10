package com.example.nomo.api;

import com.example.nomo.model.DebtItem;
import com.example.nomo.model.DebtRequest;
import com.example.nomo.model.GetDebtsRequest;
import com.example.nomo.model.PayDebtRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface DebtApi {
    @POST("debts/by-user")
    Call<List<DebtItem>> getDebtsByUserId(@Body GetDebtsRequest request);

    @POST("debts/create")
    Call<Void> createDebt(@Body DebtRequest debtRequest);

    @POST("debts/pay")
    Call<Void> payDebt(@Body PayDebtRequest request);
}
