package com.coachgym.coachgym.config;

import com.coachgym.coachgym.model.Clase;
import com.coachgym.coachgym.model.Plan;
import com.coachgym.coachgym.model.Usuario;
import com.coachgym.coachgym.repository.ClaseRepository;
import com.coachgym.coachgym.repository.PlanRepository;
import com.coachgym.coachgym.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final PlanRepository planRepo;
    private final ClaseRepository claseRepo;

    public DataInitializer(UsuarioRepository usuarioRepo, PlanRepository planRepo, ClaseRepository claseRepo) {
        this.usuarioRepo = usuarioRepo;
        this.planRepo = planRepo;
        this.claseRepo = claseRepo;
    }

    @Override
    public void run(String... args) {
        // 1. Usuarios por defecto para la demo
        if (usuarioRepo.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRol("ADMIN");
            usuarioRepo.save(admin);

            Usuario recepcion = new Usuario();
            recepcion.setUsername("recepcion");
            recepcion.setPassword("recep123");
            recepcion.setRol("RECEPCION");
            usuarioRepo.save(recepcion);
        }

        // 2. Planes base
        if (planRepo.count() == 0) {
            Plan mensual = new Plan();
            mensual.setNombre("Mensual");
            mensual.setPrecio(new BigDecimal("30.00"));
            mensual.setDuracionDias(30);
            planRepo.save(mensual);

            Plan trimestral = new Plan();
            trimestral.setNombre("Trimestral");
            trimestral.setPrecio(new BigDecimal("80.00"));
            trimestral.setDuracionDias(90);
            planRepo.save(trimestral);

            Plan anual = new Plan();
            anual.setNombre("Anual");
            anual.setPrecio(new BigDecimal("290.00"));
            anual.setDuracionDias(365);
            planRepo.save(anual);
        }

        // 3. Clases iniciales
        if (claseRepo.count() == 0) {
            Clase spinning = new Clase();
            spinning.setNombre("Spinning Intenso");
            spinning.setInstructor("Carlos Méndez");
            spinning.setDiaSemana("Lunes");
            spinning.setHora(LocalTime.of(7, 0));
            spinning.setCupo(15);
            claseRepo.save(spinning);

            Clase crossfit = new Clase();
            crossfit.setNombre("CrossFit Funcional");
            crossfit.setInstructor("Sofía Reyes");
            crossfit.setDiaSemana("Miércoles");
            crossfit.setHora(LocalTime.of(18, 0));
            crossfit.setCupo(12);
            claseRepo.save(crossfit);
        }
    }
}