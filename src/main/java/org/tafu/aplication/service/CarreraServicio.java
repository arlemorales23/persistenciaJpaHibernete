package org.tafu.aplication.service;

import org.tafu.domain.model.Carrera;
import org.tafu.domain.model.Facultad;
import org.tafu.domain.port.CarreraRepositorio;
import org.tafu.domain.port.FacultadRepositorio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class CarreraServicio {
    private final CarreraRepositorio carreraRepositorio;
    private final FacultadRepositorio facultadRepositorio;
    private final EntityManagerFactory emf;

    public CarreraServicio(CarreraRepositorio carreraRepositorio, FacultadRepositorio facultadRepositorio, EntityManagerFactory emf) {
        this.carreraRepositorio = carreraRepositorio;
        this.facultadRepositorio = facultadRepositorio;
        this.emf = emf;
    }

    public Carrera crearCarrera(String nombre, Long facultadId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Facultad facultad = em.find(Facultad.class, facultadId);
            if (facultad == null) {
                throw new RuntimeException("Facultad no encontrada");
            }

            Carrera carrera = new Carrera(nombre);
            carrera.setFacultad(facultad);
            facultad.getCarreras().add(carrera);

            em.persist(carrera);
            em.merge(facultad);

            em.getTransaction().commit();
            return carrera;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al crear la carrera: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Carrera> buscarCarreraPorId(Long id) {
        return carreraRepositorio.buscarPorId(id);
    }

    public List<Carrera> listarCarreras() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Carrera c JOIN FETCH c.facultad", Carrera.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Carrera actualizarCarrera(Long id, String nuevoNombre) {
        Optional<Carrera> carreraOpt = carreraRepositorio.buscarPorId(id);
        if (carreraOpt.isPresent()) {
            Carrera carrera = carreraOpt.get();
            carrera.setNombre(nuevoNombre);
            return carreraRepositorio.guardar(carrera);
        }
        throw new RuntimeException("Carrera no encontrada");
    }

    public void eliminarCarrera(Long id) {
        Optional<Carrera> carreraOpt = carreraRepositorio.buscarPorId(id);
        carreraOpt.ifPresent(carreraRepositorio::eliminar);
    }
}
