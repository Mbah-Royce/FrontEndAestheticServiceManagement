package com.se3.ase.data.model;

public class AppointmentModel {

    String appointmentId;
    String sessionId;
    String userId;
    String appointmentData;
    String appointmentStatus;
    String appointmentCreatedAt;
    String appointmentUpdataAt;
    String sessionName;
    String categoryName;
    String servieName;

    public AppointmentModel(String appointmentId, String sessionId, String userId, String appointmentData, String appointmentStatus, String appointmentCreatedAt, String appointmentUpdataAt, String sessionName, String categoryName, String servieName) {
        this.appointmentId = appointmentId;
        this.sessionId = sessionId;
        this.userId = userId;
        this.appointmentData = appointmentData;
        this.appointmentStatus = appointmentStatus;
        this.appointmentCreatedAt = appointmentCreatedAt;
        this.appointmentUpdataAt = appointmentUpdataAt;
        this.sessionName = sessionName;
        this.categoryName = categoryName;
        this.servieName = servieName;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAppointmentData() {
        return appointmentData;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public String getAppointmentCreatedAt() {
        return appointmentCreatedAt;
    }

    public String getAppointmentUpdataAt() {
        return appointmentUpdataAt;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getServieName() {
        return servieName;
    }
}
