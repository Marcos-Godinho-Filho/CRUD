package bd.dbos;

public class Musica implements Cloneable 
{
    // declaração dos atributos
    private int idMusica, duracao, idAutor;
    private String nomeMusica;


    // setters de todos os atributos da classe (idMusica, duracaoao, idautor e nomeMusica)
    public void setIdMusica(int idMusica) throws Exception {
        if (idMusica <= 0)
            throw new Exception("IdMusica inválido");

        this.idMusica = idMusica;
    }

    public void setDuracao(int duracao) throws Exception {
        if (duracao <= 0)
            throw new Exception("Duração inválida");

        this.duracao = duracao;
    }

    public void setIdAutor(int idAutor) throws Exception {
        if (idAutor <= 0)
            throw new Exception("IdAutor inválido");

        this.idAutor = idAutor;
    }

    public void setNomeMusica(String nomeMusica) throws Exception {
        if (nomeMusica == null || nomeMusica.equals(""))
            throw new Exception("Nome da música não fornecido");

        this.nomeMusica = nomeMusica;
    }


    // getters de todos os atributos da classe (idMusica, duracaoao, idautor e nomeMusica)
    public int getIdMusica() {
        return this.idMusica;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public int getIdAutor() {
        return this.idAutor;
    }

    public String getNomeMusica() {
        return this.nomeMusica;
    }


    // construtor com parâmetro
    public Musica(int idMusica, String nomeMusica, int duracao, int idAutor) throws Exception {
        this.setIdMusica(idMusica);
        this.setDuracao(duracao);
        this.setIdAutor(idAutor);
        this.setNomeMusica(nomeMusica);
    }


    public String toString() {
        String ret = "";

        ret += "idMusica: " + this.idMusica + "\n";
        ret += "Nome da Música : " + this.nomeMusica + "\n";
        ret += "Duração: " + this.duracao + "\n";
        ret += "idAutor: " + this.idAutor;

        return ret;
    }


    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Musica))
            return false;

        Musica mus = (Musica) obj;

        if (this.idMusica != mus.idMusica)
            return false;

        if (this.duracao != mus.duracao)
            return false;

        if (this.idAutor != mus.idAutor)
            return false;

        if (this.nomeMusica.equals(mus.nomeMusica))
            return false;

        return true;
    }


    public int hashCode() {
        int ret = 382;

        ret = 11 * ret + Integer.valueOf(this.idMusica).hashCode();
        ret = 11 * ret + Integer.valueOf(this.duracao).hashCode();
        ret = 11 * ret + Integer.valueOf(this.idAutor).hashCode();
        ret = 11 * ret + this.nomeMusica.hashCode();

        return ret;
    }


    // construtor de clone
    public Musica(Musica modelo) throws Exception {
        if (modelo == null)
            throw new Exception("Objeto ausente");

        this.idMusica = modelo.idMusica;
        this.duracao = modelo.duracao;
        this.idAutor = modelo.idAutor;
        this.nomeMusica = modelo.nomeMusica;
    }


    public Object clone() {
        Musica ret = null;

        try {
            ret = new Musica(this);
        } catch (Exception erro) {
        } // não tratamos excecao pois temos certeza que this nunca é null]

        return ret;
    }
}