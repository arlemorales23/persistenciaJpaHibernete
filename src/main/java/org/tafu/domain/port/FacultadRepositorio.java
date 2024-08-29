package org.tafu.domain.port;


import org.tafu.domain.model.Facultad;

import java.util.List;
import java.util.Optional;

public interface FacultadRepositorio {
    Facultad guardar(Facultad facultad);
    Optional<Facultad> buscarPorId(Long id);
    List<Facultad> buscarTodas();
    void eliminar(Facultad facultad);
}