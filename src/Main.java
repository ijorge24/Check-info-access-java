import java.io.*;
import java.util.*;


public class Main {

    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args){
        int opcio;
        String linea;
        do{
            showMenuMain();
            opcio = keyboard.nextInt();
            keyboard.nextLine();
            switch (opcio) {
                case 1 -> {     //Crear fitxer aleatori
                    System.out.println("Nom del fitxer resultat?");
                    linea = keyboard.nextLine();
                    System.out.println("Número de elements?");
                    opcio = keyboard.nextInt();
                    keyboard.nextLine();
                    new GeneradorFitxer(linea.equals("")?"random.txt":linea, opcio<100?300:opcio);
                }
                case 2 -> {     //Joc de proves automàtic
                    System.out.println("Nom del fitxer sobre quin fer el joc de proves automàtic");
                    linea = keyboard.nextLine();
                    jocProvesAutomatic(linea);
                }
                case 3 -> {     //Joc de proves manual
                    System.out.println("Nom del fitxer sobre quin fer el joc de proves manual");
                    linea = keyboard.nextLine();
                    jocProvesManual(linea);
                }
                case 0 -> System.out.println("Joc de proves acabat!!!");
                default -> System.out.println("Opció incorrecta, torna a provar");
            }
        }while(opcio != 0);
    }

    public static void jocProvesAutomatic(String nomFitxer){
        int opcio, any, mes, dia, hora, min, sec;
        long startNanoTime;
        double elapsedTime;
        List<String> auxList, auxList1;
        String line,recurs,usuari,aux;
        Calendar data= Calendar.getInstance();
        TADcjtRecursos tad = null;
        File fichero = new File(nomFitxer);
        try{
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            line = br.readLine();
            opcio = Integer.parseInt(line);
            line = null;
            do{
                if(line!=null){
                    System.out.println("No vàlid, indiqui amb E o D");
                }
                System.out.println("(E)statica o (D)inamica?");
                line = keyboard.nextLine().toUpperCase(Locale.ROOT);
            }while(!line.equals("D") && !line.equals("E"));
            switch (line){
                case "D" -> {
                    tad= new TADDinamica();
                }
                case "E" -> {
                    tad= new TADEstatica(opcio + 30);
                }
            }
            startNanoTime = System.nanoTime();
            while ((line = br.readLine()) != null) {
                StringTokenizer strtok=new StringTokenizer(line,",");
                usuari=strtok.nextToken();
                recurs=strtok.nextToken();
                aux=strtok.nextToken(); //Dia-Mes-Any-Hora-Minut-Segon
                String [] split= aux.split("-");
                dia = Integer.parseInt(split[0]);
                mes = Integer.parseInt(split[1])-1;
                any = Integer.parseInt(split[2]);
                hora = Integer.parseInt(split[3]);
                min = Integer.parseInt(split[4]);
                sec = Integer.parseInt(split[5]);
                data.set(any, mes, dia, hora, min, sec);
                tad.afegir(usuari,recurs,(Calendar) data.clone());
            }

            line = tad.recursMesConsultat();
            System.out.println("Recurs més consultat: "+line);
            tad.esborrarPerRecurs(line);
            System.out.println("Borrem recurs més consultat");
            line = tad.recursMesConsultat();
            System.out.println("Seguent recurs més consultat: "+line);
            auxList = tad.consultaUsuaris(line);
            dia = auxList.size();
            System.out.println("I l'han consultat "+dia+ " persones: \n"+auxList);
            any = 0;
            for(String user:auxList){
                if(tad.recursosVisualitzats(user).contains(line))
                    any++;
            }
            System.out.println("S'ha mirat tot els usuaris de la llista i " + (auxList.size()==any?"tothom fa l'assignatura":"hi ha hagut un error"));
            System.out.println("Exemple amb usuari: "+auxList.get(0));
            System.out.println(tad.recursosVisualitzats(auxList.get(0)));
            System.out.println("Algun usuari ha consultat "+ line +" l'abril del 2000?");
            for(int i = 1; i < 31; i++){
                data.set(2000,4,i);
                auxList1 = tad.consultesUsuarisPerData(line, data);
                if(auxList1.size()!=0){
                    System.out.println("Dia "+ i + ": " +auxList1);
                }
                tad.esborrarPerData(data);
            }
            System.out.println("Ara també hem borrat les dades visitades aquell mes");
            System.out.println("Si l'havien consultat persones, ara haurien de sortir-ne menys a la llista de consultats");
            auxList = tad.consultaUsuaris(line);
            System.out.println("I l'han consultat "+(dia - auxList.size())+ " persones menys ("+auxList.size()+"): \n"+auxList);
            elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
            System.out.println("Elapsed time: "+ elapsedTime);
            br.close();
        } catch (IOException e) {
            System.out.println("An error occurred. " + e.toString());
        }
    }

    public static void jocProvesManual(String nomFitxer){
        int opcio, any, mes, dia, hora, min, sec;
        boolean auxBool;
        long startNanoTime;
        double elapsedTime;
        List<String> auxList;
        String line,recurs,usuari,aux;
        Calendar data= Calendar.getInstance();
        TADcjtRecursos dinamica, estatica;
        File fichero = new File(nomFitxer);
        try{
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            dinamica= new TADDinamica();
            line = br.readLine();
            opcio = Integer.parseInt(line);
            System.out.println("Introdueix tamany TADEstatica, minim automàtic: " + opcio);
            any = keyboard.nextInt();
            keyboard.nextLine();
            estatica= new TADEstatica(opcio>any?opcio:any);
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                StringTokenizer strtok=new StringTokenizer(line,",");
                usuari=strtok.nextToken();
                recurs=strtok.nextToken();
                aux=strtok.nextToken(); //Dia-Mes-Any-Hora-Minut-Segon
                String [] split= aux.split("-");
                dia = Integer.parseInt(split[0]);
                mes = Integer.parseInt(split[1])-1;
                any = Integer.parseInt(split[2]);
                hora = Integer.parseInt(split[3]);
                min = Integer.parseInt(split[4]);
                sec = Integer.parseInt(split[5]);
                data.set(any, mes, dia, hora, min, sec);
                dinamica.afegir(usuari,recurs,(Calendar) data.clone());
                estatica.afegir(usuari,recurs,(Calendar) data.clone());
            }

            do{
                showMenuManual();
                opcio = keyboard.nextInt();
                keyboard.nextLine();
                switch (opcio) {
                    case 1 -> {     //Afegir una nova consulta
                        System.out.println("Recurs consultat: ");
                        recurs = keyboard.nextLine();
                        System.out.println("Usuari que l'ha consultat");
                        usuari = keyboard.nextLine();
                        System.out.println("Data de la consulta");
                        System.out.println("Any");
                        any = keyboard.nextInt();
                        System.out.println("Mes");
                        mes = keyboard.nextInt();
                        System.out.println("Dia");
                        dia = keyboard.nextInt();
                        System.out.println("Hora");
                        hora = keyboard.nextInt();
                        System.out.println("Minut");
                        min = keyboard.nextInt();
                        System.out.println("Segon");
                        sec = keyboard.nextInt();
                        keyboard.nextLine();
                        data.set(any, mes, dia, hora, min, sec);
                        startNanoTime = System.nanoTime();
                        dinamica.afegir(usuari, recurs, (Calendar) data.clone());
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Dinamica ha tardat: " + elapsedTime + "ms, a afegir els valors amb el recurs");
                        startNanoTime = System.nanoTime();
                        estatica.afegir(usuari, recurs, (Calendar) data.clone());
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Estatica ha tardat: " + elapsedTime + "ms, a afegir els valors amb el recurs");


                    }
                    case 2 -> {     //Esborrar segons el recurs
                        System.out.println("Introduiu quin recurs vols esborrar");
                        System.out.println("Recurs: ");
                        line = keyboard.nextLine();
                        startNanoTime = System.nanoTime();
                        dinamica.esborrarPerRecurs(line);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Dinamica ha tardat: " + elapsedTime + "ms, a esborrar els valors amb el recurs");
                        startNanoTime = System.nanoTime();
                        estatica.esborrarPerRecurs(line);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Estatica ha tardat: " + elapsedTime + "ms, a esborrar els valors amb el recurs");
                    }
                    case 3 -> {     //Esborrar per data
                        System.out.println("Introduiu la data que voleu esborrar en números");
                        System.out.println("Any: ");
                        any = keyboard.nextInt();
                        System.out.println("Mes: ");
                        mes = keyboard.nextInt() - 1;
                        System.out.println("Dia: ");
                        dia = keyboard.nextInt();
                        keyboard.nextLine();
                        data.set(any, mes, dia);
                        startNanoTime = System.nanoTime();
                        dinamica.esborrarPerData(data);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Dinamica ha tardat: " + elapsedTime + "ms, a esborrar els valors amb la data");
                        startNanoTime = System.nanoTime();
                        estatica.esborrarPerData(data);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Estatica ha tardat: " + elapsedTime + "ms, a esborrar els valors amb la data");
                    }
                    case 4 -> {     //L'ususari ha consultat algun recurs?
                        System.out.println("Introduiu l'usuari que voleu consultar");
                        System.out.println("Usuari: ");
                        line = keyboard.nextLine();
                        startNanoTime = System.nanoTime();
                        auxBool = estatica.recursVisualizat(line);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("El temps que ha tardat ha estat estaticament " + elapsedTime + " ms amb la resposta seguent:");
                        System.out.println(auxBool);
                        startNanoTime = System.nanoTime();
                        auxBool = dinamica.recursVisualizat(line);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("El temps que ha tardat ha estat dinamicament " + elapsedTime + " ms amb la resposta seguent:");
                        System.out.println(auxBool);
                    }
                    case 5 -> {     //Recursos consultats per un usuari
                        System.out.println("Introduiu l'usuari que voleu consultar");
                        System.out.println("Usuari: ");
                        line = keyboard.nextLine();
                        startNanoTime = System.nanoTime();
                        auxList = estatica.recursosVisualitzats(line);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("El temps que ha tardat ha estat estaticament " + elapsedTime + " ms amb la resposta seguent:");
                        System.out.println(auxList);
                        startNanoTime = System.nanoTime();
                        auxList = dinamica.recursosVisualitzats(line);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("El temps que ha tardat ha estat dinamicament " + elapsedTime + " ms amb la resposta seguent:");
                        System.out.println(auxList);
                    }
                    case 6 -> {     //Recurs mes consultat
                        startNanoTime = System.nanoTime();
                        aux = estatica.recursMesConsultat();
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("El recurs més consultat estaticament es " + aux + " que ha tardat " + elapsedTime + " ms");
                        startNanoTime = System.nanoTime();
                        aux = dinamica.recursMesConsultat();
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("El recurs més consultat dinamicament es " + aux + " que ha tardat " + elapsedTime + " ms");
                    }
                    case 7 -> {     //Quins usuaris han consultat un recurs en una data concreta?
                        System.out.println("Introduiu la data que voleu consultar en números");
                        System.out.println("Any: ");
                        any = keyboard.nextInt();
                        System.out.println("Mes: ");
                        mes = keyboard.nextInt() - 1;
                        System.out.println("Dia: ");
                        dia = keyboard.nextInt();
                        keyboard.nextLine();
                        data.set(any, mes, dia);
                        System.out.println("Introduiu quin recurs voleu saber qui l'ha consultat");
                        aux = keyboard.nextLine();
                        startNanoTime = System.nanoTime();
                        auxList = estatica.consultesUsuarisPerData(aux, data);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Estatica ha tardat: " + elapsedTime + "ms, i la resposta ha estat");
                        System.out.println(auxList);
                        startNanoTime = System.nanoTime();
                        auxList = dinamica.consultesUsuarisPerData(aux, data);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Dinamica ha tardat: " + elapsedTime + "ms, i la resposta ha estat");
                        System.out.println(auxList);
                    }
                    case 8 -> {     //Quins usuaris han consultat un recurs en concret?
                        System.out.println("Introduiu quin recurs voleu saber qui l'ha consultat");
                        aux = keyboard.nextLine();
                        startNanoTime = System.nanoTime();
                        auxList = estatica.consultaUsuaris(aux);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Estatica ha tardat: " + elapsedTime + "ms, i la resposta ha estat");
                        System.out.println(auxList);
                        startNanoTime = System.nanoTime();
                        auxList = dinamica.consultaUsuaris(aux);
                        elapsedTime = (System.nanoTime() - startNanoTime) * 1.0e-6;
                        System.out.println("Dinamica ha tardat: " + elapsedTime + "ms, i la resposta ha estat");
                        System.out.println(auxList);
                    }
                    case 9 -> {
                        estatica.imprimir();
                        dinamica.imprimir();
                    }
                    case 0 -> System.out.println("Joc de proves acabat!!!");
                    default -> System.out.println("Opció incorrecta, torna a provar");
                }
            }while(opcio != 0);
            br.close();
        } catch (IOException e) {
            System.out.println("An error occurred. " + e.toString());
        }
    }

    public static void showMenuMain(){
        System.out.println("\nMain:");
        System.out.println("1.Crear fitxer aleatori");
        System.out.println("2.Joc de proves automàtic");
        System.out.println("3.Joc de proves manual");
        System.out.println("0.Sortir");
    }

    public static void showMenuManual(){
        System.out.println("\nJoc de proves manual:");
        System.out.println("1.Afegir una nova consulta");
        System.out.println("2.Esborrar segons el recurs");
        System.out.println("3.Esborrar per data");
        System.out.println("4.L'usuari ha consultat algun recurs?");
        System.out.println("5.Recursos consultats per un usuari");
        System.out.println("6.Recurs mes consultat");
        System.out.println("7.Quins usuaris han consultat un recurs en una data concreta?");
        System.out.println("8.Quins usuaris han consultat un recurs en concret?");
        System.out.println("9.Mostrar dades");
        System.out.println("0.Sortir");
    }
}
