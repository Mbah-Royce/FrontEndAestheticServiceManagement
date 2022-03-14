package com.se3.ase.ui.services;

import com.se3.ase.ServicesItem;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ServicesResult {

    @Nullable
    private Integer error;
    private List<ServicesItem> success;

    public ServicesResult(@Nullable Integer error) {
        this.error = error;
    }

    public ServicesResult(List<ServicesItem> success) {
        this.success = success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }

    public List<ServicesItem> getSuccess() {
        return success;
    }
}
