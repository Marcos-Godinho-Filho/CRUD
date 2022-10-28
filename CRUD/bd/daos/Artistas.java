package bd.daos;

import java.sql.*;
import java.util.ArrayList;

import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Artistas {

    // verifica se já foi cadastrado um artista com o mesmo id que foi passado como parâmetro
    public static boolean cadastrado(int idArtista) throws Exception {
        boolean retorno = false;

        // chama os métodos necessários para que ocorrá um SELECT, passando como parâmetro o id dos dados que deseja-se verificar
        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Artista " +
                    "WHERE idArtista = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idArtista);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            retorno = resultado.first(); // pode-se usar resultado.last() ou resultado.next() ou resultado.previous() ou resultado.absotule(numeroDaLinha)

            /* // ou, se preferirmos,

            String sql;

            sql = "SELECT COUNT(*) AS QUANTOS " +
                  "FROM AppMusica.Artista " +
                  "WHERE idArtista = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, idArtista);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            resultado.first();

            retorno = resultado.getInt("QUANTOS") != 0;

            */
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar artista");
        }

        return retorno;
    }


    public static void incluir(Artista artista) throws Exception {
        if (artista == null)  // se o objeto (artista) que deseja incluir for null, ou seja, não há os atributos necessários para que a inclusão seja feita
            throw new Exception("Artista nao fornecido");  // então, sera lançada uma exceção

        if (cadastrado(artista.getIdArtista()))  // verifica se o artista que deseja incluir já foi cadastrado
            throw new Exception("Artista já cadastrado!");  // caso já exista, será lançada uma exceção

        // chama os métodos necessários para que ocorrá o INSERT, passando como parâmetro os dados que o usuário deseja incluir
        try {
            String sql;

            sql = "INSERT INTO AppMusica.Artista" +
                    "(idArtista, nome, banda) " +
                    "VALUES " +
                    "(?,?,?)";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, artista.getIdArtista());
            BDSQLServer.COMANDO.setString(2, artista.getNome());
            BDSQLServer.COMANDO.setString(3, artista.getBanda());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao inserir artista");
        }
    }


    public static void excluir(int idArtista) throws Exception {
        if (!cadastrado(idArtista))  // verifica se existe no banco de dados o artista que deseja excluir 
            throw new Exception("Artista nao cadastrado");  // se não existir, lança uma exceção, pois não tem como deletar algo que não existe

        // chama os métodos necessários para que ocorrá o DELETE, passando como parâmetro o id dos dados que o usuário deseja deletar, 
        // pois o id será usado para identificar o dado que que será deletado
        try {
            String sql;

            sql = "DELETE FROM AppMusica.Artista " +
                    "WHERE idArtista=?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idArtista);

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao excluir artista");
        }
    }


    public static void alterar(Artista artista) throws Exception {
        if (artista == null)  // se o objeto (artista) que deseja incluir for null, não há dados para serem alterados
            throw new Exception("Artista nao fornecido");  // então, será lançada uma exceção

        if (!cadastrado(artista.getIdArtista()))  // verifica se existe no banco de dados o artista que deseja alterar 
            throw new Exception("Artista nao cadastrado");  // se não existir, lança uma exceção, pois não é possível alterar algo que não existe


        // chama os métodos necessários para que ocorrá o UPDATE, passando como parâmetro os dados que o usuário deseja alterar
        try {
            String sql;

            sql = "UPDATE AppMusica.Artista SET nome = ?, banda = ? WHERE idArtista = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setString(1, artista.getNome());
            BDSQLServer.COMANDO.setString(2, artista.getBanda());
            BDSQLServer.COMANDO.setInt(3, artista.getIdArtista());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao atualizar dados de artista " + erro.getMessage());
        }
    }

    // seleciona os dados do artista que possui o mesmo id que foi passado como parâmetro
    public static Artista getArtista(int idArtista) throws Exception {
        Artista artista = null;

        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Artista " +
                    "WHERE idArtista = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idArtista);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (!resultado.first())
                throw new Exception("Artista nao cadastrado");

            artista = new Artista(resultado.getInt("idArtista"),
                    resultado.getString("nome"),
                    resultado.getString("banda"));
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar artista");
        }

        return artista;
    }

    // retorna um arraylist contendo todos os artistas inseridos
    public static ArrayList<Artista> getArrayListArtistas() throws Exception {
        ArrayList<Artista> artistas = new ArrayList<Artista>();

        try {
            String sql;

            // faz um SELECT, que torna todos os dados existentes na tebela
            sql = "SELECT * " +
                    "FROM AppMusica.Artista";

            BDSQLServer.COMANDO.prepareStatement(sql);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            while (resultado.next())  // enquanto ainda houverem linhas de dados, cria-se um novo artista e a adiciona no vetor
            {
                Artista artista = new Artista(
                        resultado.getInt("idArtista"),
                        resultado.getString("nome"),
                        resultado.getString("banda")
                );

                artistas.add(artista);
            }
        } catch (SQLException erro) {
            throw new Exception("Erro ao recuperar artistas");
        }

        return artistas;  // retorna o vetor de artistas
    }

    // retorna MeuResultSet contendendo todos os artistas
    public static MeuResultSet getArtistas () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Artista";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar artistas");
        }

        return resultado;
    }
}