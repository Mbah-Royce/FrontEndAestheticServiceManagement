package com.se3.ase.ui.serviceDetail;

public class ServiceDetailResult {

    private Integer error;
    private ServiceDetaileView success;

    public ServiceDetailResult(ServiceDetaileView success) {
        this.success = success;
    }
    public ServiceDetailResult(Integer error){
        this.error = error;
    }

    public Integer getError() {
        return error;
    }

    public ServiceDetaileView getSuccess() {
        return success;
    }
}
