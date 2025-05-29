package com.example.nomo.ui.auth;

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

import com.example.nomo.R;
import com.example.nomo.viewmodel.AuthViewModel;
import com.example.nomo.utils.Validators;

public class RegistrationFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUsername = view.findViewById(R.id.editTextNickname);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonRegister = view.findViewById(R.id.buttonRegister);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);

        // Подключение ViewModel
        authViewModel = new AuthViewModel(); // Заменить на Hilt позже

        view.findViewById(R.id.buttonBack).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        buttonRegister.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (!Validators.isValidEmail(email)) {
                Toast.makeText(requireContext(), "Некорректный email", Toast.LENGTH_SHORT).show();
            } else if (!Validators.isValidPassword(password)) {
                Toast.makeText(requireContext(), "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Вызов API регистрации
                Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();

                // Перейти на login
                ((AuthActivity) requireActivity()).navigateToFragment(new LoginFragment(), "registration_to_login");
            }
        });

        // Переход на login
        view.findViewById(R.id.buttonLogin).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .addToBackStack("registration_to_login")
                    .commit();
        });
    }
}