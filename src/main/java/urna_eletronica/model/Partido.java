package urna_eletronica.model;

public class Partido {
    private int id;
    private String nome;
    private String sigla;

    public Partido(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    @Override
    public String toString() {
        return sigla + " - " + nome;
    }
}