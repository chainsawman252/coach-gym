package com.coachgym.coachgym.service;

import com.coachgym.coachgym.model.Clase;
import com.coachgym.coachgym.model.Cliente;
import com.coachgym.coachgym.model.Reserva;
import com.coachgym.coachgym.repository.ClaseRepository;
import com.coachgym.coachgym.repository.ClienteRepository;
import com.coachgym.coachgym.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepo;
    private final ClienteRepository clienteRepo;
    private final ClaseRepository claseRepo;

    public ReservaService(ReservaRepository reservaRepo, ClienteRepository clienteRepo, ClaseRepository claseRepo) {
        this.reservaRepo = reservaRepo;
        this.clienteRepo = clienteRepo;
        this.claseRepo = claseRepo;
    }

    public String crearReserva(Long clienteId, Long claseId, LocalDate fecha) {
        Cliente cliente = clienteRepo.findById(clienteId).orElseThrow();
        Clase clase = claseRepo.findById(claseId).orElseThrow();

        // 1. Validar membresía vigente
        if (cliente.getFechaVencimiento() == null || cliente.getFechaVencimiento().isBefore(fecha)) {
            return "ERROR:El cliente no tiene membresía vigente para esa fecha.";
        }

        // 2. Validar que no reserve dos veces la misma clase ese día
        if (reservaRepo.existsByClienteIdAndClaseIdAndFechaAndEstado(clienteId, claseId, fecha, "CONFIRMADA")) {
            return "ERROR:El cliente ya tiene reservada esta clase para el " + fecha + ".";
        }

        // 3. Validar cupo disponible
        long ocupados = reservaRepo.countByClaseIdAndFechaAndEstado(claseId, fecha, "CONFIRMADA");
        if (ocupados >= clase.getCupo()) {
            return "ERROR:Cupo lleno para " + clase.getNombre() + " (" + clase.getCupo() + "/" + clase.getCupo() + ").";
        }

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setClase(clase);
        reserva.setFecha(fecha);
        reserva.setEstado("CONFIRMADA");
        reservaRepo.save(reserva);

        return "OK:Reserva confirmada para " + cliente.getNombre() + " en " + clase.getNombre();
    }
}