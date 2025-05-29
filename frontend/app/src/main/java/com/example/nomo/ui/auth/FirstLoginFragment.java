package com.example.nomo.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nomo.R;

public class FirstLoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.buttonLogin).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).navigateToFragment(new LoginFragment(), "firstlogin_to_login");
        });

        view.findViewById(R.id.buttonRegister).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).navigateToFragment(new RegistrationFragment(), "firstlogin_to_registration");
        });
    }
}
