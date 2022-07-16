import java.util.Random;
import java.io.*;

public class GeneradorFitxer {
    private Random semilla;

    public GeneradorFitxer(String name, int size)
    {
        semilla = new Random(System.currentTimeMillis());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name, false))) {
            writer.write(size + "\n");
            for (int i = 0; i < size; i++) {
                String escribir = getDNI() + "," + recursoRand() + "," + generarData()+"\n";
                writer.write(escribir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String letraDNI(int dni) {
        String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E", "T"};
        int indice = dni % 23;
        return letras[indice];
    }

    private String getDNI() {
        int numDNI = semilla.nextInt(10000000, 99999999);
        return numDNI + letraDNI(numDNI);
    }

    public String generarData() {
        int ano = semilla.nextInt(2000, 2020);
        int mes = semilla.nextInt(1, 12);
        int dia = semilla.nextInt(1, 28);
        int hora = semilla.nextInt(1, 24);
        int min = semilla.nextInt(0, 59);
        int sec = semilla.nextInt(0, 59);
        return (dia + "-" + mes + "-" + ano + "-" + hora + "-" + min + "-" + sec);
    }

    private String recursoRand() {
        String[] recursos = {"TAP", "ESO", "IPO", "XD", "MP", "AC", "FSO", "EC", "ED"};
        int indice = semilla.nextInt(recursos.length);
        return recursos[indice];
    }
}
