package planificador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Punto de entrada desde Main: carga los datos, lanza los algoritmos y muestra los informes. */
public class Simulador {

    public static void ejecutar(String fichero, String algoritmo, int quantum, boolean traza) {
        List<Proceso> procesos;
        try {
            procesos = LectorProcesos.leer(fichero);
        } catch (IOException e) {
            System.err.println("No encuentro el fichero '" + fichero + "'. Revisa la ruta y el Working directory.");
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Error en los datos: " + e.getMessage());
            return;
        }

        List<Planificador> algoritmos = new ArrayList<>();
        switch (algoritmo.toLowerCase()) {
            case "fcfs" -> algoritmos.add(new Fcfs());
            case "sjf" -> algoritmos.add(new Sjf());
            case "rr" -> algoritmos.add(new RoundRobin(quantum));
            case "todos" -> {
                algoritmos.add(new Fcfs());
                algoritmos.add(new Sjf());
                algoritmos.add(new RoundRobin(quantum));
            }
            default -> {
                System.err.println("Algoritmo desconocido: " + algoritmo + " (usa fcfs, sjf, rr o todos)");
                return;
            }
        }

        for (Planificador p : algoritmos) {
            Informe.imprimir(p.simular(procesos), traza);
        }
    }
}