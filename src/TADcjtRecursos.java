import java.util.*;
public interface TADcjtRecursos {
    void afegir (String codiUsuari, String recurs, Calendar data);
    void esborrarPerData(Calendar data);
    void esborrarPerRecurs(String recurs);
    void imprimir();
    boolean recursVisualizat (String codiUsuari);
    List<String> recursosVisualitzats (String codiUsuari);
    String recursMesConsultat();
    List<String> consultesUsuarisPerData(String recurs, Calendar data);
    List<String> consultaUsuaris(String recurs);
    
}

