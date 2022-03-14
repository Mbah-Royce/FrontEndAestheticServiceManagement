package com.se3.ase.ui.Registration;

import androidx.annotation.Nullable;

public class RegistrationFormState {

    @Nullable
    private Integer firstnameError;
    @Nullable
    private Integer lastnameError;
    @Nullable
    private Integer emialError;
    @Nullable
    private Integer phonenumberError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer passwordConfirmationError;
    private boolean isDataValid;

    public RegistrationFormState(@Nullable Integer firstnameError, @Nullable Integer lastnameError, @Nullable Integer emialError, @Nullable Integer phonenumberError, @Nullable Integer passwordError, @Nullable Integer passwordConfirmationError) {
        this.firstnameError = firstnameError;
        this.lastnameError = lastnameError;
        this.emialError = emialError;
        this.phonenumberError = phonenumberError;
        this.passwordError = passwordError;
        this.passwordConfirmationError = passwordConfirmationError;
        this.isDataValid = false;
    }

    public RegistrationFormState(boolean isDataValid) {
        this.firstnameError = null;
        this.lastnameError = null;
        this.emialError = null;
        this.phonenumberError = null;
        this.passwordError = null;
        this.passwordConfirmationError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getFirstnameError() {
        return firstnameError;
    }

    @Nullable
    public Integer getLastnameError() {
        return lastnameError;
    }

    @Nullable
    public Integer getEmialError() {
        return emialError;
    }

    @Nullable
    public Integer getPhonenumberError() {
        return phonenumberError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getPasswordConfirmationError() {
        return passwordConfirmationError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
