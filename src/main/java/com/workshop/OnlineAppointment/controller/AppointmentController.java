package com.workshop.OnlineAppointment.controller;

import com.workshop.OnlineAppointment.entity.Appointment;
import com.workshop.OnlineAppointment.entity.Status;
import com.workshop.OnlineAppointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment){
        try {
            if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
                return ResponseEntity.badRequest().body(null);
            }
            if (!appointmentService.isDoctorAvailable(appointment.getDoctorName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            Appointment bookedAppointment = appointmentService.bookAppointment(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookedAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/by-date")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam("date") LocalDate date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/by-doctor")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@RequestParam("doctorName") String doctorName) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorName);
        return ResponseEntity.ok(appointments);
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam("newStatus") Status newStatus) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, newStatus);
            return ResponseEntity.ok(updatedAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
