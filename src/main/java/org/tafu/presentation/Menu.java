package org.tafu.presentation;

import org.tafu.aplication.service.CarreraServicio;
import org.tafu.aplication.service.FacultadServicio;
import org.tafu.domain.model.Carrera;
import org.tafu.domain.model.Facultad;
import org.tafu.infraestructure.adapter.CarreraRepositorioImpl;
import org.tafu.infraestructure.adapter.FacultadRepositorioImpl;
import org.tafu.infraestructure.config.JPAConfig;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final FacultadServicio facultadServicio;
    private final CarreraServicio carreraServicio;

    public Menu() {
        this.scanner = new Scanner(System.in);
        EntityManagerFactory emf = JPAConfig.getEntityManagerFactory();
        this.facultadServicio = new FacultadServicio(new FacultadRepositorioImpl(emf));
        this.carreraServicio = new CarreraServicio(new CarreraRepositorioImpl(emf), new FacultadRepositorioImpl(emf), emf);
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Gestionar Facultades");
            System.out.println("2. Gestionar Carreras");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> gestionarFacultades();
                case 2 -> gestionarCarreras();
                case 0 -> {
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    JPAConfig.cerrar();
                    return;
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
    private void gestionarFacultades() {
        while (true) {
            System.out.println("\n--- Gestión de Facultades ---");
            System.out.println("1. Crear Facultad");
            System.out.println("2. Listar Facultades");
            System.out.println("3. Actualizar Facultad");
            System.out.println("4. Eliminar Facultad");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> crearFacultad();
                case 2 -> listarFacultades();
                case 3 -> actualizarFacultad();
                case 4 -> eliminarFacultad();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void gestionarCarreras() {
        while (true) {
            System.out.println("\n--- Gestión de Carreras ---");
            System.out.println("1. Crear Carrera");
            System.out.println("2. Listar Carreras");
            System.out.println("3. Actualizar Carrera");
            System.out.println("4. Eliminar Carrera");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> crearCarrera();
                case 2 -> listarCarreras();
                case 3 -> actualizarCarrera();
                case 4 -> eliminarCarrera();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void crearFacultad() {
        System.out.print("Ingrese el nombre de la facultad: ");
        String nombre = scanner.nextLine();
        Facultad facultad = facultadServicio.crearFacultad(nombre);
        System.out.println("Facultad creada con ID: " + facultad.getId());
    }

    private void listarFacultades() {
        List<Facultad> facultades = facultadServicio.listarFacultades();
        if (facultades.isEmpty()) {
            System.out.println("No hay facultades registradas.");
        } else {
            for (Facultad facultad : facultades) {
                System.out.println("ID: " + facultad.getId() + ", Nombre: " + facultad.getNombre());
            }
        }
    }

    private void actualizarFacultad() {
        System.out.print("Ingrese el ID de la facultad a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese el nuevo nombre de la facultad: ");
        String nuevoNombre = scanner.nextLine();

        try {
            Facultad facultadActualizada = facultadServicio.actualizarFacultad(id, nuevoNombre);
            System.out.println("Facultad actualizada: " + facultadActualizada.getNombre());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarFacultad() {
        System.out.print("Ingrese el ID de la facultad a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        try {
            facultadServicio.eliminarFacultad(id);
            System.out.println("Facultad eliminada correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearCarrera() {
        System.out.print("Ingrese el nombre de la carrera: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el ID de la facultad a la que pertenece la carrera: ");
        Long facultadId = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        try {
            Carrera carrera = carreraServicio.crearCarrera(nombre, facultadId);
            System.out.println("Carrera creada con ID: " + carrera.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarCarreras() {
        List<Carrera> carreras = carreraServicio.listarCarreras();
        if (carreras.isEmpty()) {
            System.out.println("No hay carreras registradas.");
        } else {
            for (Carrera carrera : carreras) {
                System.out.println("ID: " + carrera.getId() + ", Nombre: " + carrera.getNombre() +
                        ", Facultad: " + carrera.getFacultad().getNombre());
            }
        }
    }

    private void actualizarCarrera() {
        System.out.print("Ingrese el ID de la carrera a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese el nuevo nombre de la carrera: ");
        String nuevoNombre = scanner.nextLine();

        try {
            Carrera carreraActualizada = carreraServicio.actualizarCarrera(id, nuevoNombre);
            System.out.println("Carrera actualizada: " + carreraActualizada.getNombre());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarCarrera() {
        System.out.print("Ingrese el ID de la carrera a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        try {
            carreraServicio.eliminarCarrera(id);
            System.out.println("Carrera eliminada correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Menu().mostrarMenu();
    }
}

