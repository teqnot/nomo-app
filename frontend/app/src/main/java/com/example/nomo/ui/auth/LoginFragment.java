package com.example.nomo.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.nomo.MainActivity;
import com.example.nomo.R;
import com.example.nomo.viewmodel.AuthViewModel;
import com.example.nomo.utils.Validators;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private EditText editTextNickname;
    private EditText editTextPassword;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextNickname = view.findViewById(R.id.editTextNickname);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        authViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                startActivity(new Intent(requireContext(), MainActivity.class));
                requireActivity().finish();
            } else {
                Toast.makeText(requireContext(), "Ошибка входа!", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonBack).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        buttonLogin.setOnClickListener(v -> {
            String nickname = editTextNickname.getText().toString();
            String password = editTextPassword.getText().toString();

            if (!Validators.isValidNickname(nickname)) {
                Toast.makeText(requireContext(), "Некорректный формат nickname", Toast.LENGTH_LONG).show();
            } else if (!Validators.isValidPassword(password)) {
                Toast.makeText(requireContext(), "Пароль должен быть не менее 6 символов", Toast.LENGTH_LONG).show();
            } else {
                authViewModel.loginUser(nickname, password, requireContext());
            }
        });

        // Переход на регистрацию
        view.findViewById(R.id.buttonRegister).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).navigateToFragment(new RegistrationFragment(), "login_to_register");
        });
    }
}
