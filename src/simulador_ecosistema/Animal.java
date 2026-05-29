package simulador_ecosistema;
/**
 * Clase base (abstracta) para todos los animales del ecosistema marino.
 * Contiene los atributos y métodos comunes a cualquier animal.
 * 
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public abstract class Animal {

    // ── Atributos comunes ────────────────────────────────────────────────────
    protected String nombre;       // Nombre del animal (ej. "Tiburón Blanco")
    protected String especie;      // Especie (ej. "Carcharodon carcharias")
    protected int    edad;         // Edad en años
    protected double peso;         // Peso en kilogramos
    protected int    energia;      // Energía actual (0 = muere, 100 = máx)
    protected boolean estaVivo;    // Estado de vida del animal

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * Constructor que inicializa un animal con sus datos básicos.
     *
     * @param nombre   Nombre del animal
     * @param especie  Especie del animal
     * @param edad     Edad inicial en años
     * @param peso     Peso inicial en kg
     * @param energia  Energía inicial (0-100)
     */
    public Animal(String nombre, String especie, int edad, double peso, int energia) {
        this.nombre   = nombre;
        this.especie  = especie;
        this.edad     = edad;
        this.peso     = peso;
        this.energia  = energia;
        this.estaVivo = true;  // Todo animal nace vivo
    }

    // ── Métodos comunes ──────────────────────────────────────────────────────

    /**
     * Reduce la energía del animal en cada ciclo de simulación.
     * Si la energía llega a 0, el animal muere.
     */
    public void gastarEnergia() {
        this.energia -= 10;
        if (this.energia <= 0) {
            this.energia  = 0;
            this.estaVivo = false;
            System.out.println("  ⚠  " + nombre + " ha muerto por falta de energía.");
        }
    }

    /**
     * Aumenta la energía del animal al alimentarse.
     * No puede superar el máximo de 100.
     *
     * @param cantidad Cantidad de energía que recupera
     */
    public void alimentarse(int cantidad) {
        this.energia += cantidad;
        if (this.energia > 100) {
            this.energia = 100;  // No puede superar el máximo
        }
        System.out.println("  ✔  " + nombre + " se alimentó. Energía: " + this.energia);
    }

    /**
     * Envejece al animal un año.
     * Si supera cierta edad límite, muere de vejez.
     *
     * @param edadMaxima Edad máxima que puede vivir esta especie
     */
    public void envejecer(int edadMaxima) {
        this.edad++;
        if (this.edad >= edadMaxima) {
            this.estaVivo = false;
            System.out.println("  ⚠  " + nombre + " murió de vejez (" + edad + " años).");
        }
    }

    /**
     * Muestra la información básica del animal en consola.
     */
    public void mostrarInfo() {
        System.out.println("  Nombre  : " + nombre);
        System.out.println("  Especie : " + especie);
        System.out.println("  Edad    : " + edad + " años");
        System.out.println("  Peso    : " + peso + " kg");
        System.out.println("  Energía : " + energia + "/100");
        System.out.println("  Estado  : " + (estaVivo ? "Vivo" : "Muerto"));
    }

    // ── Métodos abstractos (cada subclase los implementa a su manera) ────────

    /**
     * Cada tipo de animal tiene su propio comportamiento de caza o alimentación.
     */
    public abstract void cazar();

    /**
     * Devuelve el tipo de animal como texto (ej. "Depredador" o "Presa").
     */
    public abstract String getTipo();

    // ── Getters y Setters ────────────────────────────────────────────────────

    public String getNombre()        { return nombre; }
    public String getEspecie()       { return especie; }
    public int    getEdad()          { return edad; }
    public double getPeso()          { return peso; }
    public int    getEnergia()       { return energia; }
    public boolean isEstaVivo()      { return estaVivo; }

    public void setNombre(String nombre)      { this.nombre  = nombre; }
    public void setEspecie(String especie)    { this.especie = especie; }
    public void setPeso(double peso)          { this.peso    = peso; }
    public void setEnergia(int energia)       { this.energia = energia; }
    public void setEstaVivo(boolean estaVivo) { this.estaVivo = estaVivo; }
}
