package org.tafu.domain.port;

import org.tafu.domain.model.Carrera;

import java.util.List;
import java.util.Optional;

public interface CarreraRepositorio {
    Carrera guardar(Carrera carrera);
    Optional<Carrera> buscarPorId(Long id);
    List<Carrera> buscarTodas();
    void eliminar(Carrera carrera);
}