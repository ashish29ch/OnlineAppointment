package com.workshop.OnlineAppointment.service;

import com.workshop.OnlineAppointment.entity.Appointment;
import com.workshop.OnlineAppointment.entity.Status;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    public Appointment createAppointment(Appointment appointment);

    public List<Appointment> getAppointmentsByDate(LocalDate date);

    public List<Appointment> getAppointmentsByDoctor(String doctorName);

    public Appointment updateAppointmentStatus(Long appointmentId, Status newStatus);

    public void cancelAppointment(Long appointmentId);

    public Appointment bookAppointment(Appointment appointment);

    public boolean isDoctorAvailable(String doctorName);
}
