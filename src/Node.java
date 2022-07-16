import java.util.Calendar;

public class Node {
    private Node seguent;
    private String codiUsuari;
    private String recurs;
    private Calendar data;

    public Node(String codiUsuari, String recurs, Calendar data) {
        this.seguent = null;
        this.codiUsuari = codiUsuari;
        this.recurs = recurs;
        this.data = data;
    }

    public Node getSeguent() {
        return seguent;
    }

    public void setSeguent(Node seguent) {
        this.seguent = seguent;
    }

    public String getCodiUsuari() {
        return codiUsuari;
    }

    public void setCodiUsuari(String codiUsuari) {
        this.codiUsuari = codiUsuari;
    }

    public String getRecurs() {
        return recurs;
    }

    public void setRecurs(String recurs) {
        this.recurs = recurs;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
    
    
}
