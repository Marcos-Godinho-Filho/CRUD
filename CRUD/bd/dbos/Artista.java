package bd.dbos;

public class Artista implements Cloneable
{
    // declaração dos atributos
    private int    idArtista;
    private String nome;
    private String banda;


    // setters de todos os atributos da classe (idArtista, nome e banda)
    public void setIdArtista (int idArtista) throws Exception
    {
        if (idArtista <= 0)
            throw new Exception("Id de artista invalido!");
        
        this.idArtista = idArtista;
    }

    public void setNome (String nome) throws Exception
    {
        if (nome == "")
            throw new Exception("Nome de artista invalido!");

        this.nome = nome;
    }

    public void setBanda (String banda) throws Exception
    {
        if (banda == "")
            throw new Exception("Banda invalida!");

        this.banda = banda;
    }


    // construtor com parâmetro
    public Artista (int idArtista, String nome, String banda) throws Exception
    {
        this.setIdArtista(idArtista);
        this.setNome(nome);
        this.setBanda(banda);
    }


    // getters de todos os atributos da classe (idArtista, nome e banda)
    public int getIdArtista () 
    {
        return this.idArtista;
    }
    
    public String getNome () 
    {
        return this.nome;
    }
    
    public String getBanda () 
    {
        return this.banda;
    }


    @Override
    public boolean equals (Object obj)  // verifica se o objeto passado como parâmetro é igual à classe
    {
        if (this == obj) return true;  
        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;  // se a classe do objeto for diferente da que ela está sendo comparada, elas não são iguais

        Artista art = (Artista) obj;

        // verifica se algum dos atributos das classes são diferentes, se algum for, a classe e o objeto não são iguais
        if (this.idArtista != art.idArtista) return false;
        if (this.nome != art.nome) return false;
        if (this.banda != art.banda) return false;

        return true;
    }


    @Override
    public String toString()  
    {
        String ret = "";

        ret = "idArtista: " + this.idArtista + "\n";
        ret +=  "Nome: " + this.nome + "\n";
        ret += "Banda: " + this.banda;

        return ret;
    }


    public int hashCode()  
    {
        int ret = 29;

        ret = 23 * ret + Integer.valueOf(this.idArtista).hashCode();
        ret = 23 * ret + String.valueOf(this.nome).hashCode();
        ret = 23 * ret + String.valueOf(this.banda).hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }


    // cosntrutor de cópias
    public Artista (Artista modelo) throws Exception
    {
        this.idArtista = modelo.idArtista;
        this.nome = modelo.nome;
        this.banda = modelo.banda;
    }


    public Object clone()
    {
        Artista ret = null;

        try
        {
            ret = new Artista (this);
        }
        catch (Exception erro)
        {} // Sei que não vai dar erro

        return ret;
    }
}