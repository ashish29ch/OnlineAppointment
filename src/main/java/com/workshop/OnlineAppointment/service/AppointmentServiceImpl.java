package com.workshop.OnlineAppointment.service;

import com.workshop.OnlineAppointment.entity.Appointment;
import com.workshop.OnlineAppointment.entity.Status;
import com.workshop.OnlineAppointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(String doctorName) {
        return appointmentRepository.findByDoctorName(doctorName);
    }

    @Override
    public Appointment updateAppointmentStatus(Long appointmentId, Status newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        appointment.setStatus(newStatus);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

}
