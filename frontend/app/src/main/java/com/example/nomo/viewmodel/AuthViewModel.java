package com.example.nomo.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nomo.api.AuthApi;
import com.example.nomo.model.AuthRequest;
import com.example.nomo.model.AuthResponse;
import com.example.nomo.utils.SharedPrefManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class AuthViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();

    private final AuthApi authApi;
    private final SharedPrefManager sharedPrefManager;

    @Inject
    public AuthViewModel(Application application,
                         AuthApi authApi,
                         SharedPrefManager sharedPrefManager) {
        super(application);
        this.authApi = authApi;
        this.sharedPrefManager = sharedPrefManager;
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public void registerUser(String nickname, String email, String password, Context context) {
        AuthRequest request = new AuthRequest(nickname, email, password);

        authApi.registerUser(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sharedPrefManager.saveToken(response.body().getAccessToken());
                    sharedPrefManager.saveUserId(response.body().getUserId());
                    sharedPrefManager.saveUsername(nickname);
                    registerSuccess.postValue(true);
                } else {
                    registerSuccess.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                registerSuccess.postValue(false);
            }
        });
    }

    public void loginUser(String nickname, String password, Context context) {
        AuthRequest request = new AuthRequest(nickname, "", password);

        authApi.loginUser(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sharedPrefManager.saveToken(response.body().getAccessToken());
                    sharedPrefManager.saveUserId(response.body().getUserId());
                    sharedPrefManager.saveUsername(nickname);
                    loginSuccess.postValue(true);
                } else {
                    loginSuccess.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                loginSuccess.postValue(false);
            }
        });
    }

    public void showError(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
