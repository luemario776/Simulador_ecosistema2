package simulador_ecosistema;
/**
 * Clase Clima: simula las condiciones del entorno marino.
 * El clima afecta a todos los seres vivos del ecosistema:
 *  - La temperatura afecta la energía de los animales.
 *  - La claridad del agua afecta la fotosíntesis de las plantas.
 *  - Las tormentas reducen la actividad general.
 *
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public class Clima {

    // ── Constantes de condición climática ────────────────────────────────────
    public static final String SOLEADO   = "Soleado";
    public static final String NUBLADO   = "Nublado";
    public static final String TORMENTA  = "Tormenta";
    public static final String FRIO      = "Frío Polar";

    // ── Atributos ────────────────────────────────────────────────────────────
    private String condicion;        // Condición actual del clima
    private double temperatura;      // Temperatura del agua en °C
    private int    nivelContaminacion; // Contaminación del agua (0-100)
    private boolean hayLuzSolar;     // Si las plantas pueden hacer fotosíntesis

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * Inicializa el clima con condiciones normales.
     */
    public Clima() {
        this.condicion          = SOLEADO;
        this.temperatura        = 22.0;   // Temperatura normal del mar
        this.nivelContaminacion = 0;
        this.hayLuzSolar        = true;
    }

    // ── Métodos ──────────────────────────────────────────────────────────────

    /**
     * Cambia el clima aleatoriamente (simula el paso del tiempo).
     * Se llama en cada ciclo de simulación.
     */
    public void cambiarClima() {
        double azar = Math.random();

        if (azar < 0.40) {
            condicion    = SOLEADO;
            temperatura  = 20 + Math.random() * 8;  // 20-28 °C
            hayLuzSolar  = true;
            System.out.println("  ☀  Clima: " + condicion + " | Temp: " + String.format("%.1f", temperatura) + "°C");

        } else if (azar < 0.65) {
            condicion    = NUBLADO;
            temperatura  = 15 + Math.random() * 7;  // 15-22 °C
            hayLuzSolar  = false;
            System.out.println("  ☁  Clima: " + condicion + " | Temp: " + String.format("%.1f", temperatura) + "°C");

        } else if (azar < 0.85) {
            condicion    = TORMENTA;
            temperatura  = 12 + Math.random() * 5;  // 12-17 °C
            hayLuzSolar  = false;
            System.out.println("  ⛈  Clima: " + condicion + " | Temp: " + String.format("%.1f", temperatura) + "°C");
            System.out.println("      ¡Todos los animales pierden 5 pts de energía extra!");

        } else {
            condicion    = FRIO;
            temperatura  = 2 + Math.random() * 6;   // 2-8 °C
            hayLuzSolar  = false;
            System.out.println("  ❄  Clima: " + condicion + " | Temp: " + String.format("%.1f", temperatura) + "°C");
            System.out.println("      ¡El frío extremo afecta a todos los animales!");
        }
    }

    /**
     * Sube la contaminación del agua (puede ocurrir aleatoriamente).
     *
     * @param cantidad Cuánto sube la contaminación
     */
    public void contaminar(int cantidad) {
        nivelContaminacion += cantidad;
        if (nivelContaminacion > 100) nivelContaminacion = 100;
        System.out.println("  ☣  Contaminación del agua: " + nivelContaminacion + "/100");

        if (nivelContaminacion >= 80) {
            System.out.println("  ⚠  ¡Nivel crítico de contaminación! El ecosistema está en peligro.");
        }
    }

    /**
     * Calcula el penalizador de energía por condición climática.
     * Las tormentas y el frío cuestan energía extra a los animales.
     *
     * @return Puntos de energía que se restan por el clima actual
     */
    public int getPenalizacionEnergia() {
        switch (condicion) {
            case TORMENTA: return 5;
            case FRIO:     return 8;
            default:       return 0;
        }
    }

    /**
     * Muestra el estado actual del clima.
     */
    public void mostrarEstado() {
        System.out.println("  Condición     : " + condicion);
        System.out.println("  Temperatura   : " + String.format("%.1f", temperatura) + " °C");
        System.out.println("  Luz solar     : " + (hayLuzSolar ? "Sí" : "No"));
        System.out.println("  Contaminación : " + nivelContaminacion + "/100");
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public String  getCondicion()          { return condicion; }
    public double  getTemperatura()        { return temperatura; }
    public int     getNivelContaminacion() { return nivelContaminacion; }
    public boolean isHayLuzSolar()         { return hayLuzSolar; }

    public void setCondicion(String condicion)        { this.condicion = condicion; }
    public void setTemperatura(double temperatura)    { this.temperatura = temperatura; }
    public void setNivelContaminacion(int nivel)      { this.nivelContaminacion = nivel; }
}
