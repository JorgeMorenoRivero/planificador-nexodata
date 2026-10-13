package planificador;

import java.util.List;

/** SJF sin desalojo: de los que ya han llegado, el de ráfaga más corta. */
public class Sjf extends Planificador {
    @Override
    public String titulo() { return "SJF (sin desalojo)"; }

    @Override
    protected Proceso elegir(List<Proceso> listos) {
        Proceso mejor = listos.get(0);
        for (Proceso p : listos) {
            if (esMejor(p, mejor)) {
                mejor = p;
            }
        }
        return mejor;
    }

    // ráfaga menor; si empatan, llegó antes; si también, antes en el fichero
    private boolean esMejor(Proceso a, Proceso b) {
        if (a.getRafaga() != b.getRafaga()) return a.getRafaga() < b.getRafaga();
        if (a.getLlegada() != b.getLlegada()) return a.getLlegada() < b.getLlegada();
        return a.getOrden() < b.getOrden();
    }
}