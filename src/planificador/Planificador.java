package planificador;

import java.util.ArrayList;
import java.util.List;

/**
 * Bucle de simulación común. Cada algoritmo solo decide dos cosas:
 * a quién elige (elegir) y cuándo le quita la CPU (agotaTurno).
 */
public abstract class Planificador {

    public abstract String titulo();

    /** Elige uno de los procesos listos. El método simular lo saca de la cola. */
    protected abstract Proceso elegir(List<Proceso> listos);

    /** Devuelve true si el proceso ya ha gastado su turno. Por defecto no hay desalojo. */
    protected boolean agotaTurno(int unidadesUsadas) {
        return false;
    }

    public Resultado simular(List<Proceso> originales) {
        List<Proceso> procesos = new ArrayList<>();
        for (Proceso p : originales) {
            procesos.add(p.copia());
        }

        List<Proceso> listos = new ArrayList<>();
        List<String> gantt = new ArrayList<>();
        List<String> traza = new ArrayList<>();

        Proceso enCpu = null;
        Proceso ultimo = null;
        int usadas = 0;
        int terminados = 0;
        int cambios = 0;
        int t = 0;

        while (terminados < procesos.size()) {
            // 1) llegadas en t (en el orden del fichero)
            for (Proceso p : procesos) {
                if (p.getLlegada() == t) {
                    pasar(traza, t, p, EstadoProceso.LISTO, "llega al sistema");
                    listos.add(p);
                }
            }

            // 2) ¿termina o agota el turno el que está en CPU?
            if (enCpu != null) {
                if (enCpu.getRestante() == 0) {
                    enCpu.setFin(t);
                    pasar(traza, t, enCpu, EstadoProceso.TERMINADO, "termina su ráfaga");
                    terminados++;
                    enCpu = null;
                } else if (agotaTurno(usadas)) {
                    if (listos.isEmpty()) {
                        usadas = 0; // nadie espera: sigue con quantum nuevo
                    } else {
                        pasar(traza, t, enCpu, EstadoProceso.LISTO, "agota el quantum");
                        listos.add(enCpu);
                        enCpu = null;
                    }
                }
            }

            if (terminados == procesos.size()) {
                break;
            }

            // 3) si la CPU está libre, elige el algoritmo
            if (enCpu == null && !listos.isEmpty()) {
                enCpu = elegir(listos);
                listos.remove(enCpu);
                pasar(traza, t, enCpu, EstadoProceso.EJECUCION, "el planificador lo elige");
                if (enCpu.getInicio() == -1) {
                    enCpu.setInicio(t);
                }
                if (ultimo != null && ultimo != enCpu) {
                    cambios++;
                }
                ultimo = enCpu;
                usadas = 0;
            }

            // 4) ejecuta una unidad
            if (enCpu == null) {
                gantt.add("-");
            } else {
                gantt.add(enCpu.getNombre());
                enCpu.ejecutarUnidad();
                usadas++;
            }
            t++;
        }

        return new Resultado(titulo(), procesos, gantt, traza, cambios);
    }

    private void pasar(List<String> traza, int t, Proceso p, EstadoProceso nuevo, String motivo) {
        traza.add("t=" + t + " " + p.getNombre() + " " + p.getEstado() + " -> " + nuevo
                + " (" + motivo + ")");
        p.setEstado(nuevo);
    }
}