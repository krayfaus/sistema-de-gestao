package sga.util;

import sga.gui.JavaFxApp;

import java.io.IOException;

/**
 * Essa classe é o ponto de entrada do Aplicativo.
 *
 * Nela inicializamos o banco de dados usado no programa.
 * E em seguida acionamos a GUI JavaFX.
 **/
public class EntryPoint {

    static String mongodPath = ".\\deps\\bin\\mongo\\mongod.exe";
    static String dbDataPath = ".\\data\\db";
    static Process mongodProcess = null;

    public static void main(String[] args) {
        // Inicializa o banco de dados que será usado localmente no programa.
        try {
            mongodProcess = new ProcessBuilder(mongodPath,
                    "--nojournal", "--dbpath", dbDataPath).start();
            mongodProcess.onExit().thenAccept(e ->
                    System.out.println("LOG: processo mongodb encerrado com sucesso."));

            // Redireciona a saída do processo para nossa saída.
            StreamReader output = new StreamReader(mongodProcess.getInputStream(), System.out);
            StreamReader err = new StreamReader(mongodProcess.getErrorStream(), System.err);
            output.start();
            err.start();

            // Finalmente, iniciamos a interface gráfica.
            JavaFxApp.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mongodProcess.destroy();
            System.exit(0);
        }
    }
}