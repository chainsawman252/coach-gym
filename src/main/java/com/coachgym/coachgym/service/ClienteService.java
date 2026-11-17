package com.coachgym.coachgym.service;

import com.coachgym.coachgym.model.Cliente;
import com.coachgym.coachgym.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    public List<Cliente> listar(String filtro) {
        if (filtro != null && !filtro.isBlank()) {
            return clienteRepo.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(filtro, filtro);
        }
        return clienteRepo.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setCodigo("TMP-" + java.util.UUID.randomUUID().toString().substring(0, 12));
            Cliente guardado = clienteRepo.save(cliente);
            guardado.setCodigo(String.format("CG-%04d", guardado.getId()));
            return clienteRepo.save(guardado);
        }
        Cliente existente = clienteRepo.findById(cliente.getId()).orElseThrow();
        existente.setNombre(cliente.getNombre());
        existente.setTelefono(cliente.getTelefono());
        if (cliente.getPlan() != null) {
            existente.setPlan(cliente.getPlan());
        }
        return clienteRepo.save(existente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepo.findById(id).orElseThrow();
    }

    public void eliminar(Long id) {
        clienteRepo.deleteById(id);
    }
}