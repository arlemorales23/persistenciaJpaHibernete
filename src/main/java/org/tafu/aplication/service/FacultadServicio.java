package org.tafu.aplication.service;

import org.tafu.domain.model.Facultad;
import org.tafu.domain.port.FacultadRepositorio;

import java.util.List;
import java.util.Optional;

public class FacultadServicio {
    private final FacultadRepositorio facultadRepositorio;

    public FacultadServicio(FacultadRepositorio facultadRepositorio) {
        this.facultadRepositorio = facultadRepositorio;
    }

    public Facultad crearFacultad(String nombre) {
        Facultad facultad = new Facultad(nombre);
        return facultadRepositorio.guardar(facultad);
    }

    public Optional<Facultad> buscarFacultadPorId(Long id) {
        return facultadRepositorio.buscarPorId(id);
    }

    public List<Facultad> listarFacultades() {
        return facultadRepositorio.buscarTodas();
    }

    public Facultad actualizarFacultad(Long id, String nuevoNombre) {
        Optional<Facultad> facultadOpt = facultadRepositorio.buscarPorId(id);
        if (facultadOpt.isPresent()) {
            Facultad facultad = facultadOpt.get();
            facultad.setNombre(nuevoNombre);
            return facultadRepositorio.guardar(facultad);
        }
        throw new RuntimeException("Facultad no encontrada");
    }

    public void eliminarFacultad(Long id) {
        Optional<Facultad> facultadOpt = facultadRepositorio.buscarPorId(id);
        facultadOpt.ifPresent(facultadRepositorio::eliminar);
    }
}

