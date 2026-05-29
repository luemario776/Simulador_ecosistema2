package simulador_ecosistema;
import java.util.Scanner;

/**
 * Clase Simulacion: controla el flujo de la simulación completa.
 * Ahora con animaciones, barras de vida y efectos de sonido.
 *
 * @author Equipo - Simulador de Ecosistema Marino
 * @version 1.0
 */
public class Simulacion {

    private Ecosistema ecosistema;
    private Scanner    scanner;
    private boolean    enCurso;

    public Simulacion(Ecosistema ecosistema, Scanner scanner) {
        this.ecosistema = ecosistema;
        this.scanner    = scanner;
        this.enCurso    = false;
    }

    // ── Iniciar simulación de N ciclos ────────────────────────────────────────
    public void iniciar() {
        System.out.println();
        Consola.separador("INICIAR SIMULACIÓN", Consola.VERDE);

        if (ecosistema.getCantDepredadores() == 0 && ecosistema.getCantPresas() == 0) {
            System.out.println(Consola.AMARILLO +
                "\n  [!] El ecosistema está vacío. Agrega animales primero." + Consola.RESET);
            return;
        }

        int ciclos = 0;
        while (ciclos <= 0) {
            System.out.print("\n  ¿Cuántos ciclos deseas simular? (1-20): ");
            try {
                ciclos = Integer.parseInt(scanner.nextLine().trim());
                if (ciclos <= 0 || ciclos > 20) {
                    System.out.println("  [!] Ingresa un número entre 1 y 20.");
                    ciclos = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [!] Dato inválido.");
                ciclos = 0;
            }
        }

        enCurso = true;
        System.out.println();
        Consola.typing("  🌊 Iniciando simulación en el " + ecosistema.getNombre() + "...", 30);
        Consola.barraAnimada("Preparando ciclos", 800);
        Consola.pausa(300);

        for (int i = 0; i < ciclos; i++) {
            ecosistema.ejecutarCiclo();
            mostrarPanelEstado();

            if (i < ciclos - 1) {
                System.out.println(Consola.AMARILLO +
                    "\n  Presiona ENTER para el siguiente ciclo..." + Consola.RESET);
                scanner.nextLine();
            }
        }

        enCurso = false;
        System.out.println();
        Consola.typing("  ✅ Simulación de " + ciclos + " ciclo(s) completada.", 30);
        Consola.pausa(400);
        ecosistema.mostrarResumen();

        System.out.println("\n  Presiona ENTER para continuar...");
        scanner.nextLine();
    }

    // ── Ejecutar un solo ciclo ────────────────────────────────────────────────
    public void ejecutarUnCiclo() {
        if (ecosistema.getCantDepredadores() == 0 && ecosistema.getCantPresas() == 0) {
            System.out.println(Consola.AMARILLO +
                "\n  [!] El ecosistema está vacío." + Consola.RESET);
            return;
        }
        Consola.puntosAnimados("  🌊 Ejecutando ciclo", 4, 200);
        ecosistema.ejecutarCiclo();
        mostrarPanelEstado();

        System.out.println("\n  Presiona ENTER para continuar...");
        scanner.nextLine();
    }

    // ── Panel de estado visual con barras de vida ─────────────────────────────
    /**
     * Muestra un panel visual con el estado actual de todos los animales,
     * usando barras de energía coloreadas.
     */
    public void mostrarPanelEstado() {
        System.out.println();
        Consola.separador("ESTADO DEL ECOSISTEMA - Ciclo #" + ecosistema.getCicloActual(), Consola.CYAN);

        // Arte ASCII del clima
        Consola.imprimirClimaASCII(ecosistema.getClima().getCondicion());

        // Panel de depredadores
        System.out.println(Consola.ROJO + Consola.BOLD + "  🦈 DEPREDADORES:" + Consola.RESET);
        ecosistema.imprimirPanelDepredadores();

        // Panel de presas
        System.out.println(Consola.AMARILLO + Consola.BOLD + "\n  🐠 PRESAS:" + Consola.RESET);
        ecosistema.imprimirPanelPresas();

        // Panel de plantas
        System.out.println(Consola.VERDE + Consola.BOLD + "\n  🌿 PLANTAS:" + Consola.RESET);
        ecosistema.imprimirPanelPlantas();
    }

    // ── Ver estadísticas completas ────────────────────────────────────────────
    public void verEstadisticas() {
        System.out.println();
        Consola.barraAnimada("Cargando estadísticas", 600);
        Consola.separador("ESTADÍSTICAS COMPLETAS", Consola.AZUL);
        ecosistema.mostrarResumen();
        ecosistema.mostrarDepredadores();
        ecosistema.mostrarPresas();
        ecosistema.mostrarPlantas();

        System.out.println("\n  Presiona ENTER para continuar...");
        scanner.nextLine();
    }

    public boolean isEnCurso()        { return enCurso; }
    public Ecosistema getEcosistema() { return ecosistema; }
}
