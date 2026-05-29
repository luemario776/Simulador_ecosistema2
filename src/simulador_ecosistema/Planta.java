package simulador_ecosistema;
/**
 * Clase Planta: representa la flora marina del ecosistema (algas, corales, etc.).
 * Las plantas producen oxígeno y sirven de alimento para las presas.
 *
 * Ejemplos: Alga kelp, Coral, Posidonia, Fitoplancton.
 *
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public class Planta {

    // ── Atributos ────────────────────────────────────────────────────────────
    private String nombre;          // Nombre de la planta (ej. "Alga Kelp")
    private String tipo;            // Tipo: "Alga", "Coral", "Plancton", etc.
    private double tamano;          // Tamaño en metros
    private int    nivelOxigeno;    // Oxígeno que produce por ciclo (1-50)
    private int    nivelNutrientes; // Nutrientes disponibles para las presas (0-100)
    private boolean estaViva;       // Estado de la planta

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * Crea una nueva planta marina.
     *
     * @param nombre          Nombre de la planta
     * @param tipo            Tipo de planta
     * @param tamano          Tamaño en metros
     * @param nivelOxigeno    Oxígeno que produce (1-50)
     * @param nivelNutrientes Nutrientes iniciales (0-100)
     */
    public Planta(String nombre, String tipo, double tamano,
                  int nivelOxigeno, int nivelNutrientes) {
        this.nombre          = nombre;
        this.tipo            = tipo;
        this.tamano          = tamano;
        this.nivelOxigeno    = nivelOxigeno;
        this.nivelNutrientes = nivelNutrientes;
        this.estaViva        = true;
    }

    // ── Métodos ──────────────────────────────────────────────────────────────

    /**
     * La planta realiza fotosíntesis: recupera nutrientes con luz solar.
     * Si el nivel de nutrientes baja de 10, la planta muere.
     *
     * @param hayLuzSolar Si hay luz solar disponible (depende del clima)
     */
    public void fotosintesis(boolean hayLuzSolar) {
        if (!estaViva) return;

        if (hayLuzSolar) {
            nivelNutrientes += 20;
            if (nivelNutrientes > 100) nivelNutrientes = 100;
            System.out.println("  " + nombre + " realizó fotosíntesis. Nutrientes: " + nivelNutrientes);
        } else {
            // Sin sol pierde nutrientes lentamente
            nivelNutrientes -= 5;
            System.out.println("  " + nombre + " sin luz solar. Nutrientes: " + nivelNutrientes);
            if (nivelNutrientes <= 0) {
                nivelNutrientes = 0;
                estaViva        = false;
                System.out.println("  ⚠  " + nombre + " murió por falta de nutrientes.");
            }
        }
    }

    /**
     * Una presa consume nutrientes de esta planta.
     *
     * @param cantidad Cantidad de nutrientes consumidos
     * @return true si había suficientes nutrientes, false si no
     */
    public boolean serConsumida(int cantidad) {
        if (!estaViva || nivelNutrientes < cantidad) {
            System.out.println("  " + nombre + " no tiene suficientes nutrientes.");
            return false;
        }
        nivelNutrientes -= cantidad;
        System.out.println("  " + nombre + " fue consumida. Nutrientes restantes: " + nivelNutrientes);
        return true;
    }

    /**
     * Muestra la información de la planta en consola.
     */
    public void mostrarInfo() {
        System.out.println("  Nombre     : " + nombre);
        System.out.println("  Tipo       : " + tipo);
        System.out.println("  Tamaño     : " + tamano + " m");
        System.out.println("  Oxígeno    : " + nivelOxigeno + " uds/ciclo");
        System.out.println("  Nutrientes : " + nivelNutrientes + "/100");
        System.out.println("  Estado     : " + (estaViva ? "Viva" : "Muerta"));
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public String  getNombre()           { return nombre; }
    public String  getTipo()             { return tipo; }
    public double  getTamano()           { return tamano; }
    public int     getNivelOxigeno()     { return nivelOxigeno; }
    public int     getNivelNutrientes()  { return nivelNutrientes; }
    public boolean isEstaViva()          { return estaViva; }

    public void setNivelNutrientes(int nivelNutrientes) {
        this.nivelNutrientes = nivelNutrientes;
    }
    public void setEstaViva(boolean estaViva) {
        this.estaViva = estaViva;
    }
}
