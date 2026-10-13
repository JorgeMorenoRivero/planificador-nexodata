package planificador;

import java.util.List;

/** Todo lo que sale de una simulación. */
public class Resultado {
    public final String titulo;
    public final List<Proceso> procesos;
    public final List<String> gantt;
    public final List<String> traza;
    public final int cambiosContexto;

    public Resultado(String titulo, List<Proceso> procesos, List<String> gantt,
                     List<String> traza, int cambiosContexto) {
        this.titulo = titulo;
        this.procesos = procesos;
        this.gantt = gantt;
        this.traza = traza;
        this.cambiosContexto = cambiosContexto;
    }
}