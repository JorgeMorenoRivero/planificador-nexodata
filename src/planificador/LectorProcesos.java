package planificador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Lee el fichero nombre;llegada;ráfaga y valida cada línea. */
public class LectorProcesos {

    public static List<Proceso> leer(String ruta) throws IOException {
        List<String> lineas = Files.readAllLines(Path.of(ruta));
        List<Proceso> procesos = new ArrayList<>();

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i).trim();
            int numLinea = i + 1;
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            String[] campos = linea.split(";");
            if (campos.length != 3) {
                throw new IllegalArgumentException("Línea " + numLinea
                        + ": se esperaban 3 campos (nombre;llegada;ráfaga) y hay " + campos.length);
            }

            String nombre = campos[0].trim();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("Línea " + numLinea + ": el nombre está vacío");
            }
            int llegada = entero(campos[1], "llegada", numLinea);
            int rafaga = entero(campos[2], "ráfaga", numLinea);

            if (llegada < 0) {
                throw new IllegalArgumentException("Línea " + numLinea
                        + ": la llegada no puede ser negativa (" + llegada + ")");
            }
            if (rafaga <= 0) {
                throw new IllegalArgumentException("Línea " + numLinea
                        + ": la ráfaga debe ser mayor que 0 (" + rafaga + ")");
            }
            procesos.add(new Proceso(nombre, llegada, rafaga, procesos.size()));
        }

        if (procesos.isEmpty()) {
            throw new IllegalArgumentException("El fichero no contiene ningún proceso");
        }
        return procesos;
    }

    private static int entero(String texto, String campo, int numLinea) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Línea " + numLinea + ": la " + campo
                    + " no es un número entero ('" + texto.trim() + "')");
        }
    }
}