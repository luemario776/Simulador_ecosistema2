package simulador_ecosistema;

import java.util.Scanner;
public class Main {

    // ── Scanner global para toda la aplicación ───────────────────────────────
    static Scanner scanner = new Scanner(System.in);

    // ── Método principal ─────────────────────────────────────────────────────
    public static void main(String[] args) {

        mostrarBienvenida();

        // Crear el ecosistema principal
        Ecosistema mar        = new Ecosistema("Mar Caribe");
        Simulacion simulacion = new Simulacion(mar, scanner);

        // Iniciar música de fondo
        Sonido.iniciarMusicaFondo();

        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEnteroValidado("  Elige una opción: ", 1, 7);

            switch (opcion) {
                case 1: menuAgregarSeres(mar);        break;
                case 2: simulacion.iniciar();          break;
                case 3: simulacion.ejecutarUnCiclo();  break;
                case 4: menuVerInformacion(mar);       break;
                case 5: simulacion.verEstadisticas();  break;
                case 6: menuConfiguracion(mar);        break;
                case 7: salir = true;                  break;
                default: System.out.println("  [!] Opción inválida."); break;
            }
        }

        mostrarDespedida();
        Sonido.detenerMusicaFondo();
        scanner.close();
    }

    // ── Pantalla de bienvenida ────────────────────────────────────────────────
    static void mostrarBienvenida() {
        Consola.limpiarPantalla();
        Consola.imprimirBanner();

        Consola.typing("  Bienvenido al Simulador de Ecosistema Marino...", 35);
        Consola.pausa(300);
        System.out.println();

        Consola.barraAnimada("Cargando ecosistema", 1500);
        Consola.pausa(300);

        System.out.println();
        System.out.println(Consola.CYAN + "  🌊 El océano está listo." + Consola.RESET);
        System.out.println();
        System.out.println("  Presiona ENTER para continuar...");
        scanner.nextLine();
    }

    // ── Menú principal ────────────────────────────────────────────────────────
    static void mostrarMenuPrincipal() {
        Consola.limpiarPantalla();
        Consola.imprimirBanner();
        System.out.println(Consola.BOLD + Consola.AZUL);
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║         MENÚ PRINCIPAL               ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║  1. 🐠 Agregar seres vivos           ║");
        System.out.println("  ║  2. ▶  Iniciar simulación (N ciclos) ║");
        System.out.println("  ║  3. ⏭  Ejecutar un solo ciclo        ║");
        System.out.println("  ║  4. 📋 Ver información               ║");
        System.out.println("  ║  5. 📊 Estadísticas completas        ║");
        System.out.println("  ║  6. ⚙  Configuración                 ║");
        System.out.println("  ║  7. 🚪 Salir                         ║");
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println(Consola.RESET);
    }

    // ── Pantalla de despedida ─────────────────────────────────────────────────
    static void mostrarDespedida() {
        Consola.limpiarPantalla();
        System.out.println();
        Consola.typing("  Cerrando el ecosistema marino...", 40);
        Consola.puntosAnimados("  Liberando recursos", 6, 200);
        System.out.println();
        System.out.println(Consola.CYAN + Consola.BOLD);
        System.out.println("  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("   Gracias por usar el Simulador de Ecosistema Marino");
        System.out.println("   🐋  🦈  🐠  🐙  🦑  🐡  🐢  🦐  🌊  🌿  🪸");
        System.out.println("  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(Consola.RESET);
    }

    // ── Submenú 1: Agregar seres vivos ───────────────────────────────────────
    static void menuAgregarSeres(Ecosistema eco) {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            Consola.separador("AGREGAR SERES VIVOS", Consola.VERDE);
            System.out.println("  1. 🦈 Agregar depredador");
            System.out.println("  2. 🐠 Agregar presa");
            System.out.println("  3. 🌿 Agregar planta");
            System.out.println("  4. ⚡ Cargar datos de ejemplo (inicio rápido)");
            System.out.println("  5. ← Volver");
            System.out.println();

            int op = leerEnteroValidado("  Elige una opción: ", 1, 5);
            switch (op) {
                case 1: agregarDepredadorManual(eco); break;
                case 2: agregarPresaManual(eco);      break;
                case 3: agregarPlantaManual(eco);     break;
                case 4:
                    Consola.barraAnimada("Poblando ecosistema", 1200);
                    eco.poblarDefecto();
                    Consola.pausa(400);
                    System.out.println(Consola.VERDE + "\n  ✔  ¡Ecosistema poblado con éxito!" + Consola.RESET);
                    Consola.pausa(800);
                    break;
                case 5: volver = true; break;
            }
        }
    }

    static void agregarDepredadorManual(Ecosistema eco) {
        System.out.println();
        Consola.separador("NUEVO DEPREDADOR", Consola.ROJO);

        System.out.print("  Nombre       : ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) { System.out.println("  [!] El nombre no puede estar vacío."); return; }

        System.out.print("  Especie      : ");
        String especie = scanner.nextLine().trim();
        if (especie.isEmpty()) especie = "Desconocida";

        int    edad   = leerEnteroValidado("  Edad (años)  : ", 1, 50);
        double peso   = leerDecimalValidado("  Peso (kg)    : ", 0.1, 10000.0);
        int    fuerza = leerEnteroValidado("  Fuerza (1-100): ", 1, 100);

        System.out.print("  Zona hábitat : ");
        String zona = scanner.nextLine().trim();
        if (zona.isEmpty()) zona = "Mar abierto";

        Depredador d = new Depredador(nombre, especie, edad, peso, 80, fuerza, zona);
        Consola.puntosAnimados("  Registrando depredador", 4, 150);
        eco.agregarDepredador(d);
        Consola.pausa(500);
    }

    static void agregarPresaManual(Ecosistema eco) {
        System.out.println();
        Consola.separador("NUEVA PRESA", Consola.AMARILLO);

        System.out.print("  Nombre       : ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) { System.out.println("  [!] El nombre no puede estar vacío."); return; }

        System.out.print("  Especie      : ");
        String especie = scanner.nextLine().trim();
        if (especie.isEmpty()) especie = "Desconocida";

        int    edad      = leerEnteroValidado("  Edad (años)  : ", 1, 30);
        double peso      = leerDecimalValidado("  Peso (kg)    : ", 0.01, 500.0);
        int    velocidad = leerEnteroValidado("  Velocidad (1-100): ", 1, 100);

        Presa p = new Presa(nombre, especie, edad, peso, 75, velocidad);
        Consola.puntosAnimados("  Registrando presa", 4, 150);
        eco.agregarPresa(p);
        Consola.pausa(500);
    }

    static void agregarPlantaManual(Ecosistema eco) {
        System.out.println();
        Consola.separador("NUEVA PLANTA", Consola.VERDE);

        System.out.print("  Nombre       : ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) { System.out.println("  [!] El nombre no puede estar vacío."); return; }

        System.out.print("  Tipo (Alga/Coral/Plancton): ");
        String tipo = scanner.nextLine().trim();
        if (tipo.isEmpty()) tipo = "Alga";

        double tamano  = leerDecimalValidado("  Tamaño (m)  : ", 0.0, 30.0);
        int    oxigeno = leerEnteroValidado("  Oxígeno (1-50): ", 1, 50);

        Planta pl = new Planta(nombre, tipo, tamano, oxigeno, 70);
        Consola.puntosAnimados("  Plantando", 4, 150);
        eco.agregarPlanta(pl);
        Consola.pausa(500);
    }

    // ── Submenú 4: Ver información ───────────────────────────────────────────
    static void menuVerInformacion(Ecosistema eco) {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            Consola.separador("VER INFORMACIÓN", Consola.AZUL);
            System.out.println("  1. 🦈 Ver depredadores");
            System.out.println("  2. 🐠 Ver presas");
            System.out.println("  3. 🌿 Ver plantas");
            System.out.println("  4. 🌤  Ver estado del clima");
            System.out.println("  5. 📋 Ver resumen general");
            System.out.println("  6. ← Volver");
            System.out.println();

            int op = leerEnteroValidado("  Elige una opción: ", 1, 6);
            switch (op) {
                case 1: eco.mostrarDepredadores();               break;
                case 2: eco.mostrarPresas();                     break;
                case 3: eco.mostrarPlantas();                    break;
                case 4:
                    Consola.imprimirClimaASCII(eco.getClima().getCondicion());
                    eco.getClima().mostrarEstado();
                    break;
                case 5: eco.mostrarResumen();                    break;
                case 6: volver = true;                           break;
            }
            if (op != 6) {
                System.out.println("\n  Presiona ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    // ── Submenú 6: Configuración ─────────────────────────────────────────────
    static void menuConfiguracion(Ecosistema eco) {
        boolean volver = false;
        while (!volver) {
            System.out.println();
            Consola.separador("CONFIGURACIÓN", Consola.AMARILLO);
            System.out.println("  1. ☣  Contaminar el mar");
            System.out.println("  2. 🔊 " + (Sonido.isSonidoActivado() ? "Desactivar" : "Activar") + " sonido");
            System.out.println("  3. 👥 Ver créditos");
            System.out.println("  4. ← Volver");
            System.out.println();

            int op = leerEnteroValidado("  Elige una opción: ", 1, 4);
            switch (op) {
                case 1:
                    int nivel = leerEnteroValidado("  ¿Cuánto contaminar? (1-30): ", 1, 30);
                    eco.getClima().contaminar(nivel);
                    Consola.pausa(500);
                    break;
                case 2:
                    Sonido.setSonidoActivado(!Sonido.isSonidoActivado());
                    System.out.println("  🔊 Sonido: " + (Sonido.isSonidoActivado() ? "ACTIVADO" : "DESACTIVADO"));
                    Consola.pausa(600);
                    break;
                case 3:
                    mostrarCreditos();
                    System.out.println("\n  Presiona ENTER para continuar...");
                    scanner.nextLine();
                    break;
                case 4: volver = true; break;
            }
        }
    }

    static void mostrarCreditos() {
        System.out.println();
        System.out.println(Consola.CYAN + Consola.BOLD);
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║         CRÉDITOS DEL PROYECTO        ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║  Simulador de Ecosistema Marino      ║");
        System.out.println("  ║  Instituto Nacional de Sonzacate     ║");
        System.out.println("  ║  Módulo: Resolución de Problemas     ║");
        System.out.println("  ║  ing : Kevin Antonio Valenzuela      ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║  Desarrollado por:                   ║");
        System.out.println("  ║    • Mario Lue                       ║");
        System.out.println("  ║                                      ║");
        System.out.println("  ║                                      ║");
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println(Consola.RESET);
    }

    // ── Validación de entradas ────────────────────────────────────────────────
    static int leerEnteroValidado(String mensaje, int min, int max) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) valido = true;
                else System.out.println("  [!] Ingresa un número entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [!] Dato inválido. Ingresa un número entero.");
            }
        }
        return valor;
    }

    static double leerDecimalValidado(String mensaje, double min, double max) {
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            try {
                valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                if (valor >= min && valor <= max) valido = true;
                else System.out.println("  [!] Ingresa un número entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("  [!] Dato inválido. Ingresa un número decimal.");
            }
        }
        return valor;
    }
}
