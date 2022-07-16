import java.util.Calendar;
import java.util.Random;
import java.lang.String;
import java.util.Date;

/*using System;
using System.Collections.Generic;*/

public class crear_fichero {
    Calendar[] data;
    String[] codiUsuari;
    int max;
    Random semilla;

    public crear_fichero(int max){
        codiUsuari = new String[max];
        data = new Calendar[max];
        this.max = max;
        semilla = new Random(System.currentTimeMillis());
    }
    private String letraDNI(int dni) {
        String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E", "T"};
        int indice = dni % 23;
        return letras[indice];
    }

    private String getNie(){
        int numDNI = semilla.nextInt(1000, 100000000);
        return Integer.toString(numDNI) + letraDNI(numDNI);
    }

    public String generarData() {
        String fecha;
        int año = semilla.nextInt(2000,2020);
        int mes = semilla.nextInt(1,12);
        int dia = semilla.nextInt(1,28);
        int hora = semilla.nextInt(1,24);
        int min = semilla.nextInt(0,59);
        int sec = semilla.nextInt(0,59);
        return (Integer.toString(año) +"-"+ Integer.toString(mes) +"-"+Integer.toString(dia) +"-"+Integer.toString(hora) +"-"+Integer.toString(min) +"-"+Integer.toString(sec));
    }
    private String RecursoRand() {
        String[] recursos = {"TAP", "ESO", "IPO", "XD", "MP", "AC", "FSO", "EC", "ED"};
        int indice =  semilla.nextInt(recursos.length);
        return recursos[indice];
    }

    }
}