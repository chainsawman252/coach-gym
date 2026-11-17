package com.coachgym.coachgym.repository;

import com.coachgym.coachgym.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsByClienteIdAndClaseIdAndFechaAndEstado(Long clienteId, Long claseId, LocalDate fecha, String estado);
    long countByClaseIdAndFechaAndEstado(Long claseId, LocalDate fecha, String estado);
    List<Reserva> findByFechaOrderByIdDesc(LocalDate fecha);
}