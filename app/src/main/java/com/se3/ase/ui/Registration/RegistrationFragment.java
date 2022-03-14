package com.se3.ase.ui.Registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.se3.ase.AccountVerificationActivity;
import com.se3.ase.R;
import com.se3.ase.databinding.FragmentRegistrationBinding;


public class RegistrationFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    private RegistrationViewModel registrationViewModel;
    String gender = "male";
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        registrationViewModel.init();
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText emailEditText = binding.email;
        final EditText phoneNumberEditText = binding.phoneNumber;
        final EditText pwdEditText = binding.pwd;
        final EditText pwdConfirmEditText = binding.pwdConfirm;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button registerButton = binding.registerBtn;
        final RadioGroup genderRadioGroup = binding.radioSex;

        registrationViewModel.getRegisterFormState().observe(getViewLifecycleOwner(), new Observer<RegistrationFormState>() {
            @Override
            public void onChanged(RegistrationFormState registrationFormState) {
                if (registrationFormState == null) {
                    return;
                }
                registerButton.setEnabled(registrationFormState.isDataValid());
                if(registrationFormState.getFirstnameError() != null){
                    firstNameEditText.setError(getString(registrationFormState.getFirstnameError()));
                }
                if(registrationFormState.getLastnameError() != null){
                    lastNameEditText.setError(getString(registrationFormState.getLastnameError()));
                }
                if(registrationFormState.getEmialError() != null){
                    emailEditText.setError(getString(registrationFormState.getEmialError()));
                }
                if(registrationFormState.getPhonenumberError() != null){
                    phoneNumberEditText.setError(getString(registrationFormState.getPhonenumberError()));
                }
                if(registrationFormState.getPasswordError() != null){
                    pwdEditText.setError(getString(registrationFormState.getPasswordError()));
                }
                if(registrationFormState.getPasswordConfirmationError() != null){
                    pwdConfirmEditText.setError(getString(registrationFormState.getPasswordConfirmationError()));
                }
            }
        });

        registrationViewModel.getRegisterResult().observe(getViewLifecycleOwner(), new Observer<RegistrationResult>() {
            @Override
            public void onChanged(RegistrationResult registrationResult) {
                if(registrationResult == null){
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if(registrationResult.getError() != null){
                    showRegisterFailed(registrationResult.getError());
                }
                if(registrationResult.getSuccess() != null){
                    updateUiWithUser(registrationResult.getSuccess());
                    Intent intent = new Intent(getActivity(),AccountVerificationActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registrationViewModel.registrationDataChanged(
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        phoneNumberEditText.getText().toString(),
                        pwdEditText.getText().toString(),
                        pwdConfirmEditText.getText().toString()
                );
            }
        };
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        phoneNumberEditText.addTextChangedListener(afterTextChangedListener);
        pwdEditText.addTextChangedListener(afterTextChangedListener);
        pwdConfirmEditText.addTextChangedListener(afterTextChangedListener);
        pwdEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    registrationViewModel.register(
                            firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            phoneNumberEditText.getText().toString(),
                            pwdEditText.getText().toString(),
                            pwdConfirmEditText.getText().toString(),
                            getContext().getApplicationContext(),
                            pwdConfirmEditText.getText().toString()
                    );
                }
                return false;
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("here ins click listernerr");
                loadingProgressBar.setVisibility(View.VISIBLE);
                registrationViewModel.register(
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        phoneNumberEditText.getText().toString(),
                        pwdEditText.getText().toString(),
                        gender,
                        getContext().getApplicationContext(),
                        pwdConfirmEditText.getText().toString()
                );
            }
        });

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                gender = radioButton.getText().toString();
            }
        });
    }
    private void showRegisterFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void updateUiWithUser(RegisterUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

}