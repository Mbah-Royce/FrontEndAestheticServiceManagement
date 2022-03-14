package com.se3.ase.ui.Registration;

import org.jetbrains.annotations.Nullable;

public class RegistrationResult {

    @Nullable
    private RegisterUserView success;
    @Nullable
    private Integer error;

    public RegistrationResult(@Nullable RegisterUserView success) {
        this.success = success;
    }

    public RegistrationResult(@Nullable Integer error) {
        this.error = error;
    }

    @Nullable
    public RegisterUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
