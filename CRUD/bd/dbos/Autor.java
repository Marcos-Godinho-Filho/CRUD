package bd.dbos;

public class Autor implements Cloneable
{
    // declaração dos atributos
    private int    idAutor;
    private String nome;
    private String genero;


    // setters de todos os atributos da classe (idautor, nome e genero)
    public void setIdAutor (int idAutor) throws Exception
    {
        if (idAutor <= 0)
            throw new Exception("Id de autor invalido!");
        
        this.idAutor = idAutor;
    }

    public void setNome (String nome) throws Exception
    {
        if (nome == "")
            throw new Exception("Nome de autor invalido!");

        this.nome = nome;
    }

    public void setGenero (String genero) throws Exception
    {
        if (genero == "")
            throw new Exception("genero invalida!");

        this.genero = genero;
    }


    // construtor com parâmetro
    public Autor(int idAutor, String nome, String genero) throws Exception
    {
        this.setIdAutor(idAutor);
        this.setNome(nome);
        this.setGenero(genero);
    }


    // getters de todos os atributos da classe (idAutor, nome e genero)
    public int getIdAutor ()
    {
        return this.idAutor;
    }
    
    public String getNome () 
    {
        return this.nome;
    }
    
    public String getGenero ()
    {
        return this.genero;
    }


    @Override
    public boolean equals (Object obj)  // verifica se o objeto passado como parâmetro é igual à classe
    {
        if (this == obj) return true;  
        if (obj == null) return false;

        if (this.getClass() != obj.getClass()) return false;  // se a classe do objeto for diferente da que ela está sendo comparada, elas não são iguais

        Autor art = (Autor) obj;

        // verifica se algum dos atributos das classes são diferentes, se algum for, a classe e o objeto não são iguais
        if (this.idAutor != art.idAutor) return false;
        if (this.nome != art.nome) return false;
        if (this.genero != art.genero) return false;

        return true;
    }


    @Override
    public String toString()  
    {
        String ret = "";

        ret = "idAutor: " + this.idAutor + "\n";
        ret +=  "Nome: " + this.nome + "\n";
        ret += "Gênero: " + this.genero;

        return ret;
    }


    public int hashCode()  
    {
        int ret = 29;

        ret = 23 * ret + Integer.valueOf(this.idAutor).hashCode();
        ret = 23 * ret + String.valueOf(this.nome).hashCode();
        ret = 23 * ret + String.valueOf(this.genero).hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }


    // cosntrutor de cópias
    public Autor(Autor modelo) throws Exception
    {
        this.idAutor = modelo.idAutor;
        this.nome = modelo.nome;
        this.genero = modelo.genero;
    }


    public Object clone()
    {
        Autor ret = null;

        try
        {
            ret = new Autor(this);
        }
        catch (Exception erro)
        {} // Sei que não vai dar erro

        return ret;
    }
}