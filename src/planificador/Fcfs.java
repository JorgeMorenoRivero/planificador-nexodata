package planificador;

import java.util.List;

/** Primero en llegar, primero en ser atendido. La cola ya está en orden de llegada. */
public class Fcfs extends Planificador {
    @Override
    public String titulo() { return "FCFS"; }

    @Override
    protected Proceso elegir(List<Proceso> listos) {
        return listos.get(0);
    }
}