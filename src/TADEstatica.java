import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;

public class TADEstatica implements TADcjtRecursos {
    String[] codiUsuari;
    String[] recurs;
    Calendar[] data; //ordenat de vell (a pos 0) a nou (a pos ultim)
    int ultim;
    int max;

    public TADEstatica(int max){
        this.max = max;
        codiUsuari = new String[max];
        recurs = new String[max];
        data = new Calendar[max];
        ultim = 0;
    }


    @Override
    public void afegir(String codiUsuari, String recurs, Calendar data) {
        this.codiUsuari[ultim] = codiUsuari;
        this.recurs[ultim] = recurs;
        this.data[ultim] = data;
        ultim++;
        ordenar(ultim - 1);
    }

    private void ordenar(int pos){
        String auxCodiUsuari;
        String auxRecurs;
        Calendar auxData;
        while(pos>=1 && data[pos].before(data[pos-1])){
            auxCodiUsuari = codiUsuari[pos-1];
            auxRecurs = recurs[pos-1];
            auxData = data[pos-1];

            codiUsuari[pos-1] = codiUsuari[pos];
            recurs[pos-1] = recurs[pos];
            data[pos-1] = data[pos];

            codiUsuari[pos] = auxCodiUsuari;
            recurs[pos] = auxRecurs;
            data[pos] = auxData;

            pos--;
        }
    }

    @Override
    public void esborrarPerData(Calendar dia) {

        for(int i = 0; i < ultim; i++){
            if(data[i].get(Calendar.DATE) == dia.get(Calendar.DATE) && data[i].get(Calendar.YEAR) == dia.get(Calendar.YEAR) && data[i].get(Calendar.MONTH) == dia.get(Calendar.MONTH)){
                for(int j = i; j < ultim; j++){
                    data[j] = data[j+1];
                    codiUsuari[j] = codiUsuari[j+1];
                    recurs[j] = recurs[j+1];
                }
                ultim--;
                i--;
            }
        }
    }

    @Override
    public void esborrarPerRecurs(String recurs) {
        for(int i = 0; i < ultim; i++){
            if(this.recurs[i].equals(recurs)){
                for(int j = i; j < ultim; j++){
                    data[j] = data[j+1];
                    codiUsuari[j] = codiUsuari[j+1];
                    this.recurs[j] = this.recurs[j+1];
                }
                i--;
                ultim--;
            }
        }
    }

    @Override
    public boolean recursVisualizat(String codiUsuari) {
        boolean hiEs = false;
        int i=0;
		while (i<ultim && !hiEs) {
			if (this.codiUsuari[i].compareTo(codiUsuari)==0)
				hiEs = true;
			else
				i++;
		}
		return hiEs;
    }

    @Override
    public List<String> recursosVisualitzats(String codiUsuari) {
       List<String> recursos = new ArrayList<>();
		for (int i=0;i<ultim;i++) {
			if (this.codiUsuari[i].compareTo(codiUsuari)==0)
				if (!recursos.contains(this.recurs[i]))
                recursos.add(this.recurs[i]);
		}
        return recursos;
    }

    @Override
    public String recursMesConsultat() {
        String maxRecurs = null;
        int maxRecursVegades = 0;
        HashMap<String, Integer> recursos = new HashMap<>();
        for(int i = 0; i < ultim; i++){
            recursos.putIfAbsent(recurs[i], 0);
            recursos.put(recurs[i], recursos.get(recurs[i]) + 1);
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
        List<String> usuaris = new ArrayList<>();
		for (int i=0;i<ultim;i++) {
			if (this.recurs[i].compareTo(recurs)==0)
            {
                if (!usuaris.contains(this.codiUsuari[i])) {
                    if (this.data[i].get(Calendar.YEAR) == data.get(Calendar.YEAR) && this.data[i].get(Calendar.MONTH)
                            == data.get(Calendar.MONTH) && this.data[i].get(Calendar.DATE) == data.get(Calendar.DATE)) {
                        usuaris.add(this.codiUsuari[i]);
                    }
                }
            }

		}
        return usuaris;
    }

    @Override
    public List<String> consultaUsuaris(String recurs)  {
        List<String> usuaris = new ArrayList<>();
		for (int i=0;i<ultim;i++) {
			if (this.recurs[i].compareTo(recurs)==0) {
                if (!usuaris.contains(codiUsuari[i])) {
                    usuaris.add(codiUsuari[i]);
                }
            }
		}
        return usuaris;
    }

    @Override
    public void imprimir(){
        System.out.println("Estatic: ");
        for(int i = 0; i < this.ultim; i++){
            System.out.println("E: "+ this.codiUsuari[i] + " " + this.recurs[i] + " " + this.data[i].getTime());
        }
    }
}
