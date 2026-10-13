package planificador;

import java.util.Locale;

/** Saca por consola el Gantt, la tabla de métricas, los cambios de contexto y la traza. */
public class Informe {
    private static final Locale ES = Locale.forLanguageTag("es-ES");

    public static void imprimir(Resultado r, boolean traza) {
        System.out.println("=== " + r.titulo + " ===");
        imprimirGantt(r);
        System.out.println();
        imprimirTabla(r);
        System.out.println("Cambios de contexto: " + r.cambiosContexto);
        if (traza) {
            System.out.println();
            System.out.println("Traza de estados:");
            for (String linea : r.traza) {
                System.out.println(linea);
            }
        }
        System.out.println();
    }

    private static void imprimirGantt(Resultado r) {
        int ancho = String.valueOf(r.gantt.size()).length();
        for (String c : r.gantt) {
            ancho = Math.max(ancho, c.length());
        }
        ancho++;

        StringBuilder tiempos = new StringBuilder("t   ");
        StringBuilder cpu = new StringBuilder("CPU ");
        for (int i = 0; i < r.gantt.size(); i++) {
            tiempos.append(String.format("%" + ancho + "d", i));
            cpu.append(String.format("%" + ancho + "s", r.gantt.get(i)));
        }
        System.out.println(tiempos);
        System.out.println(cpu);
    }

    private static void imprimirTabla(Resultado r) {
        int ancho = "Proceso".length();
        for (Proceso p : r.procesos) {
            ancho = Math.max(ancho, p.getNombre().length());
        }
        String fmt = "%-" + ancho + "s %8s %7s %5s %8s %7s %9s%n";
        System.out.printf(fmt, "Proceso", "Llegada", "Ráfaga", "Fin", "Retorno", "Espera", "Respuesta");

        double sumaRet = 0, sumaEsp = 0, sumaResp = 0;
        for (Proceso p : r.procesos) {
            System.out.printf(fmt, p.getNombre(), p.getLlegada(), p.getRafaga(), p.getFin(),
                    p.retorno(), p.espera(), p.respuesta());
            sumaRet += p.retorno();
            sumaEsp += p.espera();
            sumaResp += p.respuesta();
        }
        int n = r.procesos.size();
        System.out.println(String.format(ES, "Medias: retorno %.2f | espera %.2f | respuesta %.2f",
                sumaRet / n, sumaEsp / n, sumaResp / n));
    }
}