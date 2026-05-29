package simulador_ecosistema;

public class Consola {

    // ── Colores ANSI (funcionan en la mayoría de terminales modernas) ─────────
    public static final String RESET   = "\u001B[0m";
    public static final String NEGRO   = "\u001B[30m";
    public static final String ROJO    = "\u001B[31m";
    public static final String VERDE   = "\u001B[32m";
    public static final String AMARILLO= "\u001B[33m";
    public static final String AZUL    = "\u001B[34m";
    public static final String CYAN    = "\u001B[36m";
    public static final String BLANCO  = "\u001B[37m";
    public static final String BOLD    = "\u001B[1m";

    // ── Barra de energía/vida visual ─────────────────────────────────────────
    /**
     * Genera una barra de progreso visual con colores.
     * Ejemplo: [████████░░░░] 67%
     *
     * @param valor   Valor actual (0-100)
     * @param maximo  Valor máximo
     * @param largo   Cuántos bloques tiene la barra
     * @return String con la barra coloreada
     */
    public static String barra(int valor, int maximo, int largo) {
        int llenos  = (int)((valor / (double) maximo) * largo);
        int vacios  = largo - llenos;
        int porcentaje = (int)((valor / (double) maximo) * 100);

        // Color según nivel
        String color;
        if (porcentaje >= 60)      color = VERDE;
        else if (porcentaje >= 30) color = AMARILLO;
        else                       color = ROJO;

        StringBuilder sb = new StringBuilder();
        sb.append(color).append("[");
        for (int i = 0; i < llenos; i++) sb.append("█");
        for (int i = 0; i < vacios; i++) sb.append("░");
        sb.append("] ").append(porcentaje).append("%").append(RESET);
        return sb.toString();
    }

    // ── Efecto de texto que aparece letra por letra ───────────────────────────
    /**
     * Imprime texto letra por letra con una pausa entre cada carácter.
     * Da sensación de que "algo está pasando".
     *
     * @param texto     Texto a mostrar
     * @param delayMs   Milisegundos entre cada letra (ej: 30)
     */
    public static void typing(String texto, int delayMs) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(delayMs); } catch (InterruptedException e) {}
        }
        System.out.println();
    }

    // ── Barra de carga animada ────────────────────────────────────────────────
    /**
     * Muestra una barra de carga animada que avanza de 0 a 100%.
     *
     * @param mensaje  Texto que aparece antes de la barra
     * @param duracionMs Duración total de la animación en ms
     */
    public static void barraAnimada(String mensaje, int duracionMs) {
        int pasos = 20;
        int delayPorPaso = duracionMs / pasos;

        System.out.print("  " + mensaje + " [");
        for (int i = 0; i <= pasos; i++) {
            // Borrar la línea y redibujar
            System.out.print("\r  " + mensaje + " [");
            for (int j = 0; j < i;     j++) System.out.print("█");
            for (int j = i; j < pasos; j++) System.out.print("░");
            System.out.print("] " + (i * 5) + "%");
            try { Thread.sleep(delayPorPaso); } catch (InterruptedException e) {}
        }
        System.out.println(" ✔");
    }

    // ── Pausa con puntos animados ─────────────────────────────────────────────
    /**
     * Muestra un mensaje con puntos que aparecen uno a uno.
     * Ej: "Procesando..." → "Procesando....." 
     *
     * @param mensaje  Texto base
     * @param puntos   Cuántos puntos mostrar
     * @param delayMs  Delay entre cada punto
     */
    public static void puntosAnimados(String mensaje, int puntos, int delayMs) {
        System.out.print("  " + mensaje);
        for (int i = 0; i < puntos; i++) {
            System.out.print(".");
            try { Thread.sleep(delayMs); } catch (InterruptedException e) {}
        }
        System.out.println();
    }

    // ── Arte ASCII del clima ──────────────────────────────────────────────────
    /**
     * Imprime un arte ASCII según el clima actual.
     *
     * @param condicion Condición del clima (usar constantes de Clima)
     */
    public static void imprimirClimaASCII(String condicion) {
        System.out.println();
        switch (condicion) {
            case Clima.SOLEADO:
                System.out.println(AMARILLO +
                "        \\   |   /        \n" +
                "     \\   \\ | /   /       \n" +
                "      '.       .'        \n" +
                "  ---    (   )    ---    \n" +
                "      .'       '.        \n" +
                "     /   / | \\   \\       \n" +
                "        /   |   \\        " + RESET);
                System.out.println(CYAN + "  ☀  CLIMA: SOLEADO - ¡Perfecto para la vida marina!" + RESET);
                break;

            case Clima.NUBLADO:
                System.out.println(BLANCO +
                "      .-~~~-.            \n" +
                "  .- (       ) -.        \n" +
                " (   '-------'   )       \n" +
                "  '-._       _.-'        \n" +
                "      '~~~~~'            " + RESET);
                System.out.println(AZUL + "  ☁  CLIMA: NUBLADO - Sin luz solar hoy." + RESET);
                break;

            case Clima.TORMENTA:
                System.out.println(AZUL +
                "    .~~~~~~~~~~.         \n" +
                "   (  ~~~~~~~~~~)        \n" +
                "    '----------'         \n" +
                "     / / | \\ \\           \n" +
                "    / / \\|/ \\ \\          \n" +
                "   ⚡    |    ⚡          " + RESET);
                System.out.println(ROJO + "  ⛈  CLIMA: TORMENTA - ¡Todos los animales en alerta!" + RESET);
                break;

            case Clima.FRIO:
                System.out.println(CYAN +
                "   *   .  *   .  *  .   \n" +
                " .  *    *  .   *    .  \n" +
                "  ❄   *   ❄   *   ❄   * \n" +
                " .  *    *  .   *    .  \n" +
                "   *   .  *   .  *  .   " + RESET);
                System.out.println(CYAN + "  ❄  CLIMA: FRÍO POLAR - ¡Temperaturas extremas!" + RESET);
                break;

            default:
                System.out.println(AZUL + "  🌊  Condición desconocida." + RESET);
        }
        System.out.println();
    }

    // ── Banner del título ─────────────────────────────────────────────────────
    /**
     * Imprime el banner principal del simulador en ASCII art grande.
     */
    public static void imprimirBanner() {
        System.out.println(CYAN + BOLD);
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║                                                      ║");
        System.out.println("  ║    ~~~~  SIMULADOR DE ECOSISTEMA MARINO  ~~~~        ║");
        System.out.println("  ║                                                      ║");
        System.out.println("  ║      🐋   🦈   🐠   🐙   🦑   🐡   🐢   🦐        ║");
        System.out.println("  ║                                                      ║");
        System.out.println("  ║   ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~  ~     ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    // ── Separador de sección ──────────────────────────────────────────────────
    /**
     * Imprime una línea separadora con un título centrado.
     *
     * @param titulo Texto del separador
     * @param color  Color ANSI a usar
     */
    public static void separador(String titulo, String color) {
        int largo    = 52;
        int espacios = (largo - titulo.length() - 2) / 2;
        System.out.print(color + "  ┌");
        for (int i = 0; i < largo; i++) System.out.print("─");
        System.out.println("┐");
        System.out.print("  │");
        for (int i = 0; i < espacios; i++) System.out.print(" ");
        System.out.print(" " + titulo + " ");
        for (int i = 0; i < espacios; i++) System.out.print(" ");
        if ((largo - titulo.length() - 2) % 2 != 0) System.out.print(" ");
        System.out.println("│");
        System.out.print("  └");
        for (int i = 0; i < largo; i++) System.out.print("─");
        System.out.println("┘" + RESET);
    }

    // ── Mostrar estado de un animal con barra visual ──────────────────────────
    /**
     * Imprime el estado de un animal con su barra de energía.
     *
     * @param nombre   Nombre del animal
     * @param energia  Energía actual
     * @param vivo     Si está vivo
     * @param tipo     "Depredador" o "Presa"
     */
    public static void mostrarEstadoAnimal(String nombre, int energia, boolean vivo, String tipo) {
        String icono = tipo.equals("Depredador") ? "🦈" : "🐠";
        String estado = vivo ? (VERDE + "VIVO" + RESET) : (ROJO + "MUERTO" + RESET);
        System.out.println("  " + icono + " " + BOLD + nombre + RESET
            + " [" + estado + "]");
        System.out.println("     Energía: " + barra(energia, 100, 15));
    }

    // ── Limpiar pantalla (funciona en la mayoría de terminales) ──────────────
    /**
     * Intenta limpiar la pantalla de la consola.
     */
    public static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si no puede limpiar, imprime líneas vacías
            for (int i = 0; i < 5; i++) System.out.println();
        }
    }

    // ── Pausa simple ─────────────────────────────────────────────────────────
    /**
     * Pausa el programa N milisegundos.
     *
     * @param ms Milisegundos a esperar
     */
    public static void pausa(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}
