import java.util.*;

public class TADDinamica implements TADcjtRecursos{
    Node primer;    //ordenat de nou
    Node ultim;     //a antic
    int numElem;

    public TADDinamica() {
        primer = null;
        ultim = null;
        numElem = 0;
    }

    @Override
    public void afegir(String codiUsuari, String recurs, Calendar data){
        Node nou = new Node(codiUsuari, recurs, data);
        if(primer == null){
            primer = nou;
            ultim = nou;
        }else{
            nou.setSeguent(primer);
            primer = nou;
            ordenar(primer);

        }
    }


    private void ordenar(Node aOrdenar){
        Node anterior = null;
        Node aux;
        while (aOrdenar.getSeguent()!= null &&aOrdenar.getData().before(aOrdenar.getSeguent().getData())) {
            if(anterior == null){
                primer = aOrdenar.getSeguent();
            }else{
                anterior.setSeguent(aOrdenar.getSeguent());
            }
            anterior = aOrdenar.getSeguent();
            aux = anterior.getSeguent();
            anterior.setSeguent(aOrdenar);
            aOrdenar.setSeguent(aux);
        }

    }

    @Override
    public void esborrarPerData(Calendar data)
    {
        Node ant, aux;
        boolean fin=false, confirm=false;
        while(primer.getData().get(Calendar.DATE)==data.get(Calendar.DATE) && primer.getData().get(Calendar.MONTH)==data.get(Calendar.MONTH) &&primer.getData().get(Calendar.YEAR)==data.get(Calendar.YEAR)){
            primer = primer.getSeguent();
            if (!fin) fin=true;
        }
        ant = primer;
        aux = ant.getSeguent();
        while (!fin && aux!=null){
            if(aux.getData().get(Calendar.DATE)==data.get(Calendar.DATE) && aux.getData().get(Calendar.MONTH)==data.get(Calendar.MONTH) &&aux.getData().get(Calendar.YEAR)==data.get(Calendar.YEAR)){
                ant.setSeguent(aux.getSeguent());
                aux= aux.getSeguent();
                confirm=true;
            }
            else{
                if (confirm) fin=true;
                ant = aux;
                aux = ant.getSeguent();
            }
        }
    }

    @Override
    public void esborrarPerRecurs(String recurs) {
        Node ant, aux;
        while (primer.getRecurs().equals(recurs)){
            primer = primer.getSeguent();
        }
        ant=primer;
        aux = ant.getSeguent();
        while (aux!=null){
            if(aux.getRecurs().equals(recurs)){
                ant.setSeguent(aux.getSeguent());
                aux= aux.getSeguent();
            }
            else{
                ant = aux;
                aux = ant.getSeguent();
            }

        }
    }

    @Override
    public boolean recursVisualizat (String codiUsuari)
    {
        boolean hiEs = false;
		Node aux = primer;
		while (aux != null && !hiEs) {
			if (aux.getCodiUsuari().compareTo(codiUsuari)==0){
                hiEs = true;
            }else{
                aux = aux.getSeguent();
            }
		}
		return hiEs;
	}

    @Override
    public List<String> recursosVisualitzats (String codiUsuari)
    {
        Node aux = primer;
        List<String> recursos = new ArrayList<>();
		while (aux != null) {
			if (aux.getCodiUsuari().compareTo(codiUsuari)==0){
                if (!recursos.contains(aux.getRecurs())){
                    recursos.add(aux.getRecurs());
                }
            }
            aux = aux.getSeguent();
		}
        return recursos;
    }

    @Override
    public String recursMesConsultat() {
        Node aux=primer;
        String maxRecurs = null;
        int maxRecursVegades = 0;
        HashMap<String, Integer> recursos = new HashMap<>();
        while (aux!=null){
            recursos.putIfAbsent(aux.getRecurs(), 0);
            recursos.put(aux.getRecurs(), recursos.get(aux.getRecurs()) + 1);
            aux=aux.getSeguent();
        }
        for (HashMap.Entry<String, Integer> entry : recursos.entrySet()) {
            if(maxRecursVegades<entry.getValue()){
                maxRecurs = entry.getKey();
                maxRecursVegades = entry.getValue();
            }
        }
        return maxRecurs;
    }

    @Override
    public List<String> consultesUsuarisPerData(String recurs, Calendar data) {
        Node aux = primer;
        List<String> usuaris = new ArrayList<>();
		while (aux != null) {
			if (aux.getRecurs().compareTo(recurs)==0){
                if (!usuaris.contains(aux.getCodiUsuari())){
                    if(aux.getData().get(Calendar.YEAR) == data.get(Calendar.YEAR) && aux.getData().get(Calendar.MONTH)
                            == data.get(Calendar.MONTH) &&aux.getData().get(Calendar.DATE) == data.get(Calendar.DATE)){
                        usuaris.add(aux.getCodiUsuari());
                    }
                }
            }
			aux = aux.getSeguent();
		}
        return usuaris;
    }

    @Override
    public List<String> consultaUsuaris(String recurs)
     {
        Node aux = primer;
        List<String> usuaris = new ArrayList<>();
		while (aux != null) {
			if (aux.getRecurs().compareTo(recurs)==0){
                if (!usuaris.contains(aux.getCodiUsuari())){
                    usuaris.add(aux.getCodiUsuari());
                }
            }
            aux = aux.getSeguent();
		}
        return usuaris;
    }

    @Override
    public void imprimir(){
        System.out.println("Dinamica: ");
        Node test = this.primer;
        while(test != null){
            System.out.println("D: "+ test.getCodiUsuari() + " " + test.getRecurs() + " " + test.getData().getTime());
            test = test.getSeguent();
        }
    }
}
