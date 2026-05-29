package simulador_ecosistema;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Sonido {

    // ── Atributos ────────────────────────────────────────────────────────────
    private static Clip   musicaFondo;        // Clip de música en loop
    private static boolean sonidoActivado = true; // On/Off global

    // ── Constantes: nombres de archivos ──────────────────────────────────────
    public static final String MUSICA_FONDO = "sounds/oceano.wav";
    public static final String SFX_CAZA     = "sounds/caza.wav";
    public static final String SFX_MUERTE   = "sounds/muerte.wav";
    public static final String SFX_TORMENTA = "sounds/tormenta.wav";
    public static final String SFX_BURBUJA  = "sounds/burbuja.wav";

    // ── Reproducir música de fondo en loop ───────────────────────────────────
    /**
     * Inicia la música de fondo en bucle continuo.
     * Si ya hay música sonando, no la reinicia.
     */
    public static void iniciarMusicaFondo() {
        if (!sonidoActivado) return;
        try {
            if (musicaFondo != null && musicaFondo.isRunning()) return;

            File archivoMusica = new File(MUSICA_FONDO);
            if (!archivoMusica.exists()) return; // Si no existe el archivo, silencio

            AudioInputStream audio = AudioSystem.getAudioInputStream(archivoMusica);
            musicaFondo = AudioSystem.getClip();
            musicaFondo.open(audio);
            musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Loop infinito
            musicaFondo.start();
        } catch (Exception e) {
            // Si hay error de audio, el programa sigue normalmente sin sonido
        }
    }

    /**
     * Detiene la música de fondo.
     */
    public static void detenerMusicaFondo() {
        if (musicaFondo != null && musicaFondo.isRunning()) {
            musicaFondo.stop();
            musicaFondo.close();
        }
    }

    // ── Reproducir efectos de sonido (SFX) ───────────────────────────────────
    /**
     * Reproduce un efecto de sonido una sola vez.
     * No bloquea el programa (se ejecuta en hilo aparte).
     *
     * @param rutaArchivo Ruta al archivo .wav del efecto
     */
    public static void reproducirEfecto(String rutaArchivo) {
        if (!sonidoActivado) return;

        // Se ejecuta en un hilo separado para no pausar el programa
        new Thread(() -> {
            try {
                File archivo = new File(rutaArchivo);
                if (!archivo.exists()) return;

                AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();

                // Espera a que termine y luego libera memoria
                Thread.sleep(clip.getMicrosecondLength() / 1000);
                clip.close();
            } catch (Exception e) {
                // Silencio si hay error
            }
        }).start();
    }

    // ── Métodos de acceso rápido para cada evento ────────────────────────────

    /** Sonido de caza exitosa */
    public static void sfxCaza()     { reproducirEfecto(SFX_CAZA);     }

    /** Sonido de muerte de animal */
    public static void sfxMuerte()   { reproducirEfecto(SFX_MUERTE);   }

    /** Sonido de tormenta */
    public static void sfxTormenta() { reproducirEfecto(SFX_TORMENTA); }

    /** Sonido de burbuja / ambiente */
    public static void sfxBurbuja()  { reproducirEfecto(SFX_BURBUJA);  }

    // ── Control de sonido ────────────────────────────────────────────────────

    /**
     * Activa o desactiva todo el sonido del juego.
     * @param activado true = sonido ON, false = sonido OFF
     */
    public static void setSonidoActivado(boolean activado) {
        sonidoActivado = activado;
        if (!activado) detenerMusicaFondo();
        else           iniciarMusicaFondo();
    }

    public static boolean isSonidoActivado() { return sonidoActivado; }
}
