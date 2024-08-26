package com.workshop.OnlineAppointment.repository;

import com.workshop.OnlineAppointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByAppointmentDate(LocalDate date);

    List<Appointment> findByDoctorName(String doctorName);
}
