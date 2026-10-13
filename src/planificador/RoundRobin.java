package planificador;

import java.util.List;

/** Cola FIFO con un quantum máximo por turno. */
public class RoundRobin extends Planificador {
    private final int quantum;

    public RoundRobin(int quantum) {
        if (quantum <= 0) {
            throw new IllegalArgumentException("El quantum debe ser mayor que 0");
        }
        this.quantum = quantum;
    }

    @Override
    public String titulo() { return "Round Robin (q=" + quantum + ")"; }

    @Override
    protected Proceso elegir(List<Proceso> listos) {
        return listos.get(0);
    }

    @Override
    protected boolean agotaTurno(int unidadesUsadas) {
        return unidadesUsadas >= quantum;
    }
}