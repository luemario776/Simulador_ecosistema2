package simulador_ecosistema;
/**
 * Clase Ecosistema: núcleo del simulador.
 * Administra todos los arreglos de animales y plantas,
 * y coordina las interacciones entre ellos.
 * Integra sonidos y efectos visuales en cada evento.
 *
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public class Ecosistema {

    // ── Capacidades máximas ───────────────────────────────────────────────────
    public static final int MAX_DEPREDADORES = 5;
    public static final int MAX_PRESAS       = 10;
    public static final int MAX_PLANTAS      = 8;

    // ── Atributos ─────────────────────────────────────────────────────────────
    private String       nombre;
    private Depredador[] depredadores;
    private Presa[]      presas;
    private Planta[]     plantas;
    private Clima        clima;
    private int          cantDepredadores;
    private int          cantPresas;
    private int          cantPlantas;
    private int          cicloActual;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Ecosistema(String nombre) {
        this.nombre           = nombre;
        this.depredadores     = new Depredador[MAX_DEPREDADORES];
        this.presas           = new Presa[MAX_PRESAS];
        this.plantas          = new Planta[MAX_PLANTAS];
        this.clima            = new Clima();
        this.cantDepredadores = 0;
        this.cantPresas       = 0;
        this.cantPlantas      = 0;
        this.cicloActual      = 0;
    }

    // ── Agregar seres vivos ───────────────────────────────────────────────────
    public boolean agregarDepredador(Depredador d) {
        if (cantDepredadores >= MAX_DEPREDADORES) {
            System.out.println("  [!] Capacidad máxima de depredadores alcanzada.");
            return false;
        }
        depredadores[cantDepredadores++] = d;
        System.out.println(Consola.VERDE + "  ✔  " + d.getNombre() + " agregado como depredador." + Consola.RESET);
        return true;
    }

    public boolean agregarPresa(Presa p) {
        if (cantPresas >= MAX_PRESAS) {
            System.out.println("  [!] Capacidad máxima de presas alcanzada.");
            return false;
        }
        presas[cantPresas++] = p;
        System.out.println(Consola.VERDE + "  ✔  " + p.getNombre() + " agregado como presa." + Consola.RESET);
        return true;
    }

    public boolean agregarPlanta(Planta pl) {
        if (cantPlantas >= MAX_PLANTAS) {
            System.out.println("  [!] Capacidad máxima de plantas alcanzada.");
            return false;
        }
        plantas[cantPlantas++] = pl;
        System.out.println(Consola.VERDE + "  ✔  " + pl.getNombre() + " agregada al ecosistema." + Consola.RESET);
        return true;
    }

    // ── Paneles visuales con barras de energía ────────────────────────────────

    /** Imprime panel de depredadores con barras de vida */
    public void imprimirPanelDepredadores() {
        if (cantDepredadores == 0) {
            System.out.println("  Sin depredadores.");
            return;
        }
        for (int i = 0; i < cantDepredadores; i++) {
            Depredador d = depredadores[i];
            Consola.mostrarEstadoAnimal(d.getNombre(), d.getEnergia(), d.isEstaVivo(), d.getTipo());
        }
    }

    /** Imprime panel de presas con barras de vida */
    public void imprimirPanelPresas() {
        if (cantPresas == 0) {
            System.out.println("  Sin presas.");
            return;
        }
        for (int i = 0; i < cantPresas; i++) {
            Presa p = presas[i];
            Consola.mostrarEstadoAnimal(p.getNombre(), p.getEnergia(), p.isEstaVivo(), p.getTipo());
        }
    }

    /** Imprime panel de plantas con barra de nutrientes */
    public void imprimirPanelPlantas() {
        if (cantPlantas == 0) {
            System.out.println("  Sin plantas.");
            return;
        }
        for (int i = 0; i < cantPlantas; i++) {
            Planta pl = plantas[i];
            String estado = pl.isEstaViva()
                ? (Consola.VERDE + "VIVA" + Consola.RESET)
                : (Consola.ROJO  + "MUERTA" + Consola.RESET);
            System.out.println("  🌿 " + Consola.BOLD + pl.getNombre() + Consola.RESET
                + " [" + estado + "]");
            System.out.println("     Nutrientes: "
                + Consola.barra(pl.getNivelNutrientes(), 100, 15));
        }
    }

    // ── Mostrar información detallada ─────────────────────────────────────────
    public void mostrarDepredadores() {
        System.out.println(Consola.ROJO + "\n  ═══ DEPREDADORES (" + cantDepredadores + ") ═══" + Consola.RESET);
        if (cantDepredadores == 0) { System.out.println("  No hay depredadores registrados."); return; }
        for (int i = 0; i < cantDepredadores; i++) {
            System.out.println("\n  --- Depredador #" + (i + 1) + " ---");
            depredadores[i].mostrarInfo();
        }
    }

    public void mostrarPresas() {
        System.out.println(Consola.AMARILLO + "\n  ═══ PRESAS (" + cantPresas + ") ═══" + Consola.RESET);
        if (cantPresas == 0) { System.out.println("  No hay presas registradas."); return; }
        for (int i = 0; i < cantPresas; i++) {
            System.out.println("\n  --- Presa #" + (i + 1) + " ---");
            presas[i].mostrarInfo();
        }
    }

    public void mostrarPlantas() {
        System.out.println(Consola.VERDE + "\n  ═══ PLANTAS (" + cantPlantas + ") ═══" + Consola.RESET);
        if (cantPlantas == 0) { System.out.println("  No hay plantas registradas."); return; }
        for (int i = 0; i < cantPlantas; i++) {
            System.out.println("\n  --- Planta #" + (i + 1) + " ---");
            plantas[i].mostrarInfo();
        }
    }

    public void mostrarResumen() {
        int vivosD = 0, vivosP = 0, vivasV = 0;
        for (int i = 0; i < cantDepredadores; i++) if (depredadores[i].isEstaVivo()) vivosD++;
        for (int i = 0; i < cantPresas;       i++) if (presas[i].isEstaVivo())       vivosP++;
        for (int i = 0; i < cantPlantas;       i++) if (plantas[i].isEstaViva())      vivasV++;

        System.out.println(Consola.CYAN + Consola.BOLD);
        System.out.println("  ╔═══════════════════════════════════════╗");
        System.out.println("  ║      RESUMEN DEL ECOSISTEMA           ║");
        System.out.println("  ╠═══════════════════════════════════════╣");
        System.out.println("  ║  Nombre  : " + nombre);
        System.out.println("  ║  Ciclo   : " + cicloActual);
        System.out.println("  ║  🦈 Depredadores vivos : " + vivosD + "/" + cantDepredadores);
        System.out.println("  ║  🐠 Presas vivas       : " + vivosP + "/" + cantPresas);
        System.out.println("  ║  🌿 Plantas vivas      : " + vivasV + "/" + cantPlantas);
        System.out.println("  ╠═══════════════════════════════════════╣");
        System.out.println("  ║  Clima actual:");
        clima.mostrarEstado();
        System.out.println("  ╚═══════════════════════════════════════╝");
        System.out.println(Consola.RESET);
    }

    // ── Lógica de simulación ──────────────────────────────────────────────────
    public void ejecutarCiclo() {
        cicloActual++;
        System.out.println();
        Consola.separador("CICLO #" + cicloActual, Consola.AZUL);

        // 1. Cambio de clima
        System.out.println(Consola.BOLD + "\n  [CLIMA]" + Consola.RESET);
        clima.cambiarClima();
        // Sonido de tormenta si aplica
        if (clima.getCondicion().equals(Clima.TORMENTA)) Sonido.sfxTormenta();

        Consola.pausa(400);

        // 2. Fotosíntesis
        System.out.println(Consola.VERDE + Consola.BOLD + "\n  [PLANTAS - Fotosíntesis]" + Consola.RESET);
        for (int i = 0; i < cantPlantas; i++) {
            plantas[i].fotosintesis(clima.isHayLuzSolar());
            Consola.pausa(150);
        }

        // 3. Presas se alimentan
        System.out.println(Consola.AMARILLO + Consola.BOLD + "\n  [PRESAS - Alimentación]" + Consola.RESET);
        for (int i = 0; i < cantPresas; i++) {
            if (presas[i].isEstaVivo()) {
                presas[i].cazar();
                Consola.pausa(200);
            }
        }

        // 4. Depredadores cazan
        System.out.println(Consola.ROJO + Consola.BOLD + "\n  [DEPREDADORES - Cacería]" + Consola.RESET);
        for (int i = 0; i < cantDepredadores; i++) {
            if (depredadores[i].isEstaVivo()) {
                depredadores[i].cazar();
                Consola.pausa(250);
            }
        }

        // 5. Encuentros depredador vs presa
        System.out.println(Consola.BOLD + "\n  [ENCUENTROS]" + Consola.RESET);
        simularEncuentros();

        // 6. Gasto de energía
        System.out.println(Consola.BOLD + "\n  [GASTO DE ENERGÍA]" + Consola.RESET);
        int pen = clima.getPenalizacionEnergia();
        for (int i = 0; i < cantDepredadores; i++) {
            if (depredadores[i].isEstaVivo()) {
                depredadores[i].gastarEnergia();
                if (pen > 0) {
                    int e = depredadores[i].getEnergia() - pen;
                    depredadores[i].setEnergia(Math.max(e, 0));
                    if (depredadores[i].getEnergia() == 0) {
                        depredadores[i].setEstaVivo(false);
                        Sonido.sfxMuerte();
                    }
                }
                Consola.pausa(150);
            }
        }
        for (int i = 0; i < cantPresas; i++) {
            if (presas[i].isEstaVivo()) {
                presas[i].gastarEnergia();
                if (pen > 0) {
                    int e = presas[i].getEnergia() - pen;
                    presas[i].setEnergia(Math.max(e, 0));
                    if (presas[i].getEnergia() == 0) {
                        presas[i].setEstaVivo(false);
                        Sonido.sfxMuerte();
                    }
                }
                Consola.pausa(150);
            }
        }

        Sonido.sfxBurbuja();
        System.out.println(Consola.CYAN + "\n  ── Fin del ciclo #" + cicloActual + " ──" + Consola.RESET);
    }

    /** Simula encuentros aleatorios entre depredadores y presas */
    private void simularEncuentros() {
        if (cantDepredadores == 0 || cantPresas == 0) {
            System.out.println("  Sin encuentros posibles.");
            return;
        }
        for (int i = 0; i < cantDepredadores; i++) {
            if (!depredadores[i].isEstaVivo()) continue;
            if (Math.random() < 0.40) {
                int intentos = 0, idx = -1;
                while (intentos < 5) {
                    int r = (int)(Math.random() * cantPresas);
                    if (presas[r].isEstaVivo()) { idx = r; break; }
                    intentos++;
                }
                if (idx >= 0) {
                    System.out.println(Consola.ROJO + "  !! " + depredadores[i].getNombre()
                        + " encontró a " + presas[idx].getNombre() + "!" + Consola.RESET);
                    Consola.pausa(300);
                    boolean escapo = presas[idx].escapar();
                    if (!escapo) {
                        depredadores[i].alimentarse(40);
                        Sonido.sfxCaza();
                    }
                    Consola.pausa(300);
                }
            }
        }
    }

    /** Poblar con datos de ejemplo para demostración */
    public void poblarDefecto() {
        agregarDepredador(new Depredador("Gran Tiburón Blanco", "Carcharodon carcharias", 5, 1200.0, 90, 95, "Aguas profundas"));
        agregarDepredador(new Depredador("Orca Reina",          "Orcinus orca",           8, 4500.0, 85, 88, "Mar abierto"));
        agregarDepredador(new Depredador("Barracuda Feroz",     "Sphyraena barracuda",    3, 15.0,   80, 70, "Arrecife"));
        agregarPresa(new Presa("Nemo",         "Amphiprioninae",     1, 0.20, 75, 60));
        agregarPresa(new Presa("Tortuga Lila", "Chelonia mydas",     4, 85.0, 70, 50));
        agregarPresa(new Presa("Sardina #1",   "Sardina pilchardus", 1, 0.05, 80, 80));
        agregarPresa(new Presa("Sardina #2",   "Sardina pilchardus", 1, 0.05, 80, 80));
        agregarPresa(new Presa("Calamar Azul", "Dosidicus gigas",    2, 2.00, 65, 55));
        agregarPlanta(new Planta("Alga Kelp Gigante", "Alga",    8.0, 40, 80));
        agregarPlanta(new Planta("Coral Cerebro",     "Coral",   0.5, 15, 60));
        agregarPlanta(new Planta("Fitoplancton",      "Plancton",0.0, 50, 95));
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getNombre()           { return nombre; }
    public int    getCantDepredadores() { return cantDepredadores; }
    public int    getCantPresas()       { return cantPresas; }
    public int    getCantPlantas()      { return cantPlantas; }
    public int    getCicloActual()      { return cicloActual; }
    public Clima  getClima()            { return clima; }
}
