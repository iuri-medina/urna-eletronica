package urna_eletronica.model;

public class Candidato {
    private int id;
    private String nome;
    private int partidoId;
    private int numero;

    public Candidato(String nome, int partidoId, int numero) {
        this.nome = nome;
        this.partidoId = partidoId;
        this.numero = numero;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getPartidoId() { return partidoId; }
    public void setPartidoId(int partidoId) { this.partidoId = partidoId; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
}