package org.tafu.infraestructure.adapter;

import org.tafu.domain.model.Carrera;
import org.tafu.domain.port.CarreraRepositorio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class CarreraRepositorioImpl implements CarreraRepositorio {
    private final EntityManagerFactory emf;

    public CarreraRepositorioImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Carrera guardar(Carrera carrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (carrera.getId() == null) {
                em.persist(carrera);
            } else {
                carrera = em.merge(carrera);
            }
            em.getTransaction().commit();
            return carrera;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Carrera> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Carrera.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Carrera> buscarTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Carrera carrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(carrera)) {
                carrera = em.merge(carrera);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}