package com.example.nomo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomo.api.DebtApi;
import com.example.nomo.model.DebtItem;
import com.example.nomo.model.GetDebtsRequest;
import com.example.nomo.utils.SharedPrefManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class DebtViewModel extends AndroidViewModel {
    private final MutableLiveData<List<DebtItem>> debtList = new MutableLiveData<>();
    private final DebtApi debtApi;
    private final SharedPrefManager sharedPrefManager;

    @Inject
    public DebtViewModel(Application application,
                         DebtApi debtApi,
                         SharedPrefManager sharedPrefManager) {
        super(application);
        this.debtApi = debtApi;
        this.sharedPrefManager = sharedPrefManager;
    }

    public LiveData<List<DebtItem>> getDebtList() {
        return debtList;
    }

    public void loadDebts() {
        long userId = sharedPrefManager.getUserId();
        if (userId == -1) return;

        debtApi.getDebtsByUserId(new GetDebtsRequest(userId)).enqueue(new Callback<List<DebtItem>>() {
            @Override
            public void onResponse(Call<List<DebtItem>> call, Response<List<DebtItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    debtList.postValue(response.body());
                } else {
                    Log.e("DebtViewModel", "Error loading debts");
                }
            }

            @Override
            public void onFailure(Call<List<DebtItem>> call, Throwable t) {
                Log.e("DebtViewModel", "Network error: " + t.getMessage());
            }
        });
    }
}
