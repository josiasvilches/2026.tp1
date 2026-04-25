package main.java.com.bibliotech.service;

import main.java.com.bibliotech.exception.SocioYaExisteException;
import main.java.com.bibliotech.model.Socio;
import main.java.com.bibliotech.repository.SocioRepository;

public class SocioService {

    private final SocioRepository socioRepo;

    public SocioService(SocioRepository socioRepo) {
        this.socioRepo = socioRepo;
    }

    public void registrarSocio(Socio socio) {
        // Validación de DNI único
        if (socioRepo.buscarPorId(socio.dni()).isPresent()) {
            throw new SocioYaExisteException(socio.dni());
        }
        socioRepo.guardar(socio);
    }
}