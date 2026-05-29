package simulador_ecosistema;
/**
 * Clase Depredador: representa animales que cazan a otros para sobrevivir.
 * Hereda de Animal y agrega comportamiento propio de un depredador marino.
 * 
 * Ejemplos: Tiburón, Orca, Barracuda.
 *
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public class Depredador extends Animal {

    // ── Atributos propios del depredador ─────────────────────────────────────
    private int    fuerzaAtaque;    // Qué tan fuerte es al cazar (1-100)
    private int    cazasExitosas;   // Contador de cacerías exitosas
    private String zonaHabitat;     // Zona del mar donde vive (ej. "Aguas profundas")

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * Crea un nuevo depredador marino.
     *
     * @param nombre        Nombre del depredador
     * @param especie       Especie del depredador
     * @param edad          Edad inicial
     * @param peso          Peso inicial en kg
     * @param energia       Energía inicial
     * @param fuerzaAtaque  Fuerza de ataque (1-100)
     * @param zonaHabitat   Zona del mar donde habita
     */
    public Depredador(String nombre, String especie, int edad, double peso,
                      int energia, int fuerzaAtaque, String zonaHabitat) {
        // Llama al constructor del padre (Animal)
        super(nombre, especie, edad, peso, energia);
        this.fuerzaAtaque  = fuerzaAtaque;
        this.cazasExitosas = 0;
        this.zonaHabitat   = zonaHabitat;
    }

    // ── Implementación de métodos abstractos ─────────────────────────────────

    /**
     * El depredador intenta cazar.
     * Tiene un 70% de probabilidad de éxito si tiene suficiente energía.
     */
    @Override
    public void cazar() {
        if (!estaVivo) {
            System.out.println("  [!] " + nombre + " no puede cazar, está muerto.");
            return;
        }

        // Probabilidad de éxito basada en fuerza de ataque
        double probabilidad = (fuerzaAtaque / 100.0) * 0.9; // máx 90%
        double resultado    = Math.random();

        System.out.println("  " + nombre + " sale a cazar en " + zonaHabitat + "...");

        if (resultado <= probabilidad) {
            // Caza exitosa
            int energiaGanada = 30 + (int)(Math.random() * 20); // 30-50 puntos
            this.alimentarse(energiaGanada);
            this.cazasExitosas++;
            System.out.println("  ✔  Caza exitosa! Cazas totales: " + cazasExitosas);
        } else {
            // Caza fallida → pierde energía de todos modos
            System.out.println("  ✘  " + nombre + " no atrapó nada esta vez.");
            gastarEnergia();
        }
    }

    /**
     * Retorna el tipo de este animal.
     */
    @Override
    public String getTipo() {
        return "Depredador";
    }

    // ── Sobrescritura de mostrarInfo para agregar datos propios ──────────────

    /**
     * Muestra información completa del depredador.
     */
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();  // Imprime los datos de Animal primero
        System.out.println("  Tipo    : " + getTipo());
        System.out.println("  Fuerza  : " + fuerzaAtaque + "/100");
        System.out.println("  Hábitat : " + zonaHabitat);
        System.out.println("  Cacerías exitosas: " + cazasExitosas);
    }

    // ── Getters y Setters propios ────────────────────────────────────────────

    public int    getFuerzaAtaque()             { return fuerzaAtaque; }
    public int    getCazasExitosas()            { return cazasExitosas; }
    public String getZonaHabitat()              { return zonaHabitat; }

    public void setFuerzaAtaque(int fuerzaAtaque) { this.fuerzaAtaque = fuerzaAtaque; }
    public void setZonaHabitat(String zona)        { this.zonaHabitat  = zona; }
}
