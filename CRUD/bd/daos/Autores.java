package bd.daos;

import java.sql.*;
import java.util.ArrayList;

import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Autores {

    // verifica se já foi cadastrado um autor com o mesmo id que foi passado como parâmetro
    public static boolean cadastrado(int idAutor) throws Exception {
        boolean retorno = false;

        // chama os métodos necessários para que ocorrá um SELECT, passando como parâmetro o id dos dados que deseja-se verificar
        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Autor " +
                    "WHERE idAutor = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idAutor);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            retorno = resultado.first(); // pode-se usar resultado.last() ou resultado.next() ou resultado.previous() ou resultado.absotule(numeroDaLinha)

            /* // ou, se preferirmos,

            String sql;

            sql = "SELECT COUNT(*) AS QUANTOS " +
                  "FROM AppMusica.Autor " +
                  "WHERE idAutor = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, idAutor);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            resultado.first();

            retorno = resultado.getInt("QUANTOS") != 0;

            */
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar autor");
        }

        return retorno;
    }


    public static void incluir(Autor autor) throws Exception {
        if (autor == null)  // se o objeto (autor) que deseja incluir for null, ou seja, não há os atributos necessários para que a inclusão seja feita
            throw new Exception("autor nao fornecido");  // então, sera lançada uma exceção

        if (cadastrado(autor.getIdAutor()))  // verifica se o autor que deseja incluir já foi cadastrado
            throw new Exception("Autor já cadastrado!");  // caso já exista, será lançada uma exceção

        // chama os métodos necessários para que ocorrá o INSERT, passando como parâmetro os dados que o usuário deseja incluir
        try {
            String sql;

            sql = "INSERT INTO AppMusica.Autor" +
                    "(idautor, nome, genero) " +
                    "VALUES " +
                    "(?,?,?)";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, autor.getIdAutor());
            BDSQLServer.COMANDO.setString(2, autor.getNome());
            BDSQLServer.COMANDO.setString(3, autor.getGenero());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao inserir autor");
        }
    }


    public static void excluir(int idAutor) throws Exception {
        if (!cadastrado(idAutor))  // verifica se existe no banco de dados o autor que deseja excluir 
            throw new Exception("Autor nao cadastrado");  // se não existir, lança uma exceção, pois não tem como deletar algo que não existe

        // chama os métodos necessários para que ocorrá o DELETE, passando como parâmetro o id dos dados que o usuário deseja deletar, 
        // pois o id será usado para identificar o dado que que será deletado
        try {
            String sql;

            sql = "DELETE FROM AppMusica.Autor " +
                    "WHERE idAutor=?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idAutor);

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao excluir autor");
        }
    }


    public static void alterar(Autor autor) throws Exception {
        if (autor == null)  // se o objeto (autor) que deseja incluir for null, não há dados para serem alterados
            throw new Exception("Autor nao fornecido");  // então, será lançada uma exceção

        if (!cadastrado(autor.getIdAutor()))  // verifica se existe no banco de dados o autor que deseja alterar 
            throw new Exception("Autor nao cadastrado");  // se não existir, lança uma exceção, pois não é possível alterar algo que não existe


        // chama os métodos necessários para que ocorrá o UPDATE, passando como parâmetro os dados que o usuário deseja alterar
        try {
            String sql;

            sql = "UPDATE AppMusica.Autor SET nome = ?, genero = ? WHERE idAutor = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setString(1, autor.getNome());
            BDSQLServer.COMANDO.setString(2, autor.getGenero());
            BDSQLServer.COMANDO.setInt(3, autor.getIdAutor());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao atualizar dados de autor " + erro.getMessage());
        }
    }

    // seleciona os dados do autor que possui o mesmo id que foi passado como parâmetro
    public static Autor getAutor(int idAutor) throws Exception {
        Autor autor = null;

        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Autor " +
                    "WHERE idAutor = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idAutor);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (!resultado.first())
                throw new Exception("Autor nao cadastrado");

            autor = new Autor(resultado.getInt("idAutor"),
                    resultado.getString("nome"),
                    resultado.getString("genero"));
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar autor");
        }

        return autor;
    }

    // retorna um arraylist contendo todos os autors inseridos
    public static ArrayList<Autor> getArrayListautors() throws Exception {
        ArrayList<Autor> autores = new ArrayList<Autor>();

        try {
            String sql;

            // faz um SELECT, que torna todos os dados existentes na tebela
            sql = "SELECT * " +
                    "FROM AppMusica.autor";

            BDSQLServer.COMANDO.prepareStatement(sql);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            while (resultado.next())  // enquanto ainda houverem linhas de dados, cria-se um novo autor e a adiciona no vetor
            {
                Autor autor = new Autor(
                        resultado.getInt("idAutor"),
                        resultado.getString("nome"),
                        resultado.getString("genero")
                );

                autores.add(autor);
            }
        } catch (SQLException erro) {
            throw new Exception("Erro ao recuperar autores");
        }

        return autores;  // retorna o vetor de autores
    }

    // retorna MeuResultSet contendendo todos os autors
    public static MeuResultSet getAutors () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Autor";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar autoress");
        }

        return resultado;
    }
}