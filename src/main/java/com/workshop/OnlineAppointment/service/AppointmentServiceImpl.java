package com.workshop.OnlineAppointment.service;

import com.workshop.OnlineAppointment.entity.Appointment;
import com.workshop.OnlineAppointment.entity.Status;
import com.workshop.OnlineAppointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Appointment bookAppointment(Appointment appointment) {
        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past.");
        }
        // Save the appointment details to the database
        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean isDoctorAvailable(String doctorName) {
        List<Appointment> appointments = getAppointmentsByDoctor(doctorName);
        appointments.stream().filter(appointment ->
                        appointment.getAppointmentDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        return appointments.size()>5;
    }

}
