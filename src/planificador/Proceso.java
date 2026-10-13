package planificador;

/** Un proceso con lo que necesita el planificador y lo necesario para las métricas. */
public class Proceso {
    private final String nombre;
    private final int llegada;
    private final int rafaga;
    private final int orden;      // posición en el fichero, sirve para desempatar
    private int restante;
    private EstadoProceso estado;
    private int inicio = -1;      // primer instante en CPU
    private int fin = -1;

    public Proceso(String nombre, int llegada, int rafaga, int orden) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.orden = orden;
        this.restante = rafaga;
        this.estado = EstadoProceso.NUEVO;
    }

    /** Copia limpia (sin simular) para poder lanzar varios algoritmos con los mismos datos. */
    public Proceso copia() {
        return new Proceso(nombre, llegada, rafaga, orden);
    }

    public String getNombre() { return nombre; }
    public int getLlegada() { return llegada; }
    public int getRafaga() { return rafaga; }
    public int getOrden() { return orden; }
    public int getRestante() { return restante; }
    public EstadoProceso getEstado() { return estado; }
    public int getInicio() { return inicio; }
    public int getFin() { return fin; }

    public void setEstado(EstadoProceso estado) { this.estado = estado; }
    public void setInicio(int inicio) { this.inicio = inicio; }
    public void setFin(int fin) { this.fin = fin; }

    public void ejecutarUnidad() { restante--; }

    public int retorno() { return fin - llegada; }
    public int espera() { return retorno() - rafaga; }
    public int respuesta() { return inicio - llegada; }
}