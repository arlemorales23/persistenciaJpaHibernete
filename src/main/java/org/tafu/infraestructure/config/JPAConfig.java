package org.tafu.infraestructure.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConfig {
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("universidadPU");
        }
        return emf;
    }

    public static void cerrar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}