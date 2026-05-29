package simulador_ecosistema;
/**
 * Clase Presa: representa animales que pueden ser cazados por depredadores.
 * Hereda de Animal y agrega comportamiento de evasión y reproducción.
 *
 * Ejemplos: Pez payaso, Sardina, Calamar pequeño, Tortuga joven.
 *
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public class Presa extends Animal {

    // ── Atributos propios de la presa ────────────────────────────────────────
    private int    velocidadEvasion;   // Qué tan rápido huye (1-100)
    private int    vecesEscapada;      // Cuántas veces ha escapado de un depredador
    private boolean enPeligro;         // Si hay un depredador cerca

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * Crea una nueva presa marina.
     *
     * @param nombre            Nombre de la presa
     * @param especie           Especie
     * @param edad              Edad inicial
     * @param peso              Peso inicial en kg
     * @param energia           Energía inicial
     * @param velocidadEvasion  Velocidad para escapar (1-100)
     */
    public Presa(String nombre, String especie, int edad, double peso,
                 int energia, int velocidadEvasion) {
        super(nombre, especie, edad, peso, energia);
        this.velocidadEvasion = velocidadEvasion;
        this.vecesEscapada    = 0;
        this.enPeligro        = false;
    }

    // ── Implementación de métodos abstractos ─────────────────────────────────

    /**
     * Las presas no cazan; en cambio, buscan alimento vegetal (algas, plancton).
     * Al "cazar" simplemente se alimentan de lo que encuentran.
     */
    @Override
    public void cazar() {
        if (!estaVivo) {
            System.out.println("  [!] " + nombre + " no puede moverse, está muerto.");
            return;
        }
        // Las presas buscan plancton o algas
        System.out.println("  " + nombre + " busca plancton y algas...");
        int energiaGanada = 15 + (int)(Math.random() * 15); // 15-30 puntos
        this.alimentarse(energiaGanada);
    }

    /**
     * Retorna el tipo de este animal.
     */
    @Override
    public String getTipo() {
        return "Presa";
    }

    // ── Método exclusivo de la presa ─────────────────────────────────────────

    /**
     * La presa intenta escapar de un depredador.
     * La probabilidad de escapar depende de su velocidad de evasión.
     *
     * @return true si logró escapar, false si fue capturada
     */
    public boolean escapar() {
        if (!estaVivo) return false;

        double probabilidadEscape = velocidadEvasion / 100.0;
        double resultado          = Math.random();

        this.enPeligro = true;
        System.out.println("  ¡" + nombre + " detectó un depredador! Intentando escapar...");

        if (resultado <= probabilidadEscape) {
            vecesEscapada++;
            this.enPeligro = false;
            gastarEnergia(); // Escapar también cansa
            System.out.println("  ✔  " + nombre + " escapó! (escapadas: " + vecesEscapada + ")");
            return true;
        } else {
            this.estaVivo  = false;
            this.enPeligro = false;
            System.out.println("  ✘  " + nombre + " fue capturado por el depredador.");
            return false;
        }
    }

    /**
     * Muestra información completa de la presa.
     */
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("  Tipo      : " + getTipo());
        System.out.println("  Velocidad : " + velocidadEvasion + "/100");
        System.out.println("  Escapadas : " + vecesEscapada);
        System.out.println("  En peligro: " + (enPeligro ? "Sí" : "No"));
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public int     getVelocidadEvasion()                   { return velocidadEvasion; }
    public int     getVecesEscapada()                      { return vecesEscapada; }
    public boolean isEnPeligro()                           { return enPeligro; }

    public void setVelocidadEvasion(int velocidadEvasion)  { this.velocidadEvasion = velocidadEvasion; }
    public void setEnPeligro(boolean enPeligro)            { this.enPeligro = enPeligro; }
}
