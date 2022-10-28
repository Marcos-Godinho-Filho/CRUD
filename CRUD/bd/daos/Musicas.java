package bd.daos;

import java.sql.*;
import java.util.ArrayList;

import bd.*;
import bd.core.*;
import bd.dbos.*;

import javax.swing.*;

public class Musicas {
    
    // verifica se já foi cadastrado uma musica com o mesmo id que foi passado como parâmetro
    public static boolean cadastrado(int idMusica) throws Exception {
        boolean retorno = false;

        // chama os métodos necessários para que ocorrá um SELECT, passando como parâmetro o id dos dados que deseja-se verificar
        try {
            String sql;

            sql = "SELECT * FROM APPMUSICA.MUSICA WHERE IDMUSICA = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idMusica);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            retorno = resultado.first();
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar a música");
        }

        return retorno;
    }

    public static void incluir(Musica musica) throws Exception {
        if (musica == null)  // se o objeto (musica) que deseja incluir for null, ou seja, não há os atributos necessários para que a inclusão seja feita
            throw new Exception("Música não fornecida");  // então, sera lançada uma exceção

        if (cadastrado(musica.getIdMusica()))  // verifica se o autor que deseja incluir já foi cadastrado
            throw new Exception("Música já cadastrada!"); // caso já exista, será lançada uma exceção

        // chama os métodos necessários para que ocorrá o INSERT, passando como parâmetro os dados que o usuário deseja incluir
        try {
            String sql;

            sql = "INSERT INTO APPMUSICA.MUSICA (IDMUSICA, NOME, DURACAO, IDautor) VALUES (?,?,?,?)";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, musica.getIdMusica());
            BDSQLServer.COMANDO.setString(2, musica.getNomeMusica());
            BDSQLServer.COMANDO.setInt(3, musica.getDuracao());
            BDSQLServer.COMANDO.setInt(4, musica.getIdAutor());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao inserir a música");
        }
    }

    public static void excluir(int idMusica) throws Exception {
        if (!cadastrado(idMusica))  // verifica se existe no banco de dados a musica que deseja excluir 
            throw new Exception("Não cadastrado");  // se não existir, lança uma exceção, pois não tem como deletar algo que não existe

        // chama os métodos necessários para que ocorrá o DELETE, passando como parâmetro o id dos dados que o usuário deseja deletar, 
        // pois o id será usado para identificar o dado que que será deletado
        try {
            String sql;

            sql = "DELETE FROM APPMUSICA.MUSICA WHERE IDMUSICA = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idMusica);

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao excluir música");
        }
    }

    public static void alterar(Musica musica) throws Exception {
        if (musica == null)  // se o objeto (musica) que deseja incluir for null, não há dados para serem alterados
            throw new Exception("Música não fornecida");  // então, será lançada uma exceção

        if (!cadastrado(musica.getIdMusica()))  // verifica se existe no banco de dados a musica que deseja alterar 
            throw new Exception("Não cadastrado");

        // chama os métodos necessários para que ocorrá o UPDATE, passando como parâmetro os dados que o usuário deseja alterar
        try {
            String sql;

            sql = "UPDATE APPMUSICA.MUSICA SET NOME = ?, DURACAO = ?, IDautor = ? WHERE IDMUSICA = ?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setString(1, musica.getNomeMusica());
            BDSQLServer.COMANDO.setInt(2, musica.getDuracao());
            BDSQLServer.COMANDO.setInt(3, musica.getIdAutor());
            BDSQLServer.COMANDO.setInt(4, musica.getIdMusica());

            BDSQLServer.COMANDO.executeUpdate();
            BDSQLServer.COMANDO.commit();
        } catch (SQLException erro) {
            BDSQLServer.COMANDO.rollback();
            throw new Exception("Erro ao atualizar dados da música");
        }
    }

    // seleciona os dados da musica que possui o mesmo id que foi passado como parâmetro
    public static Musica getMusica(int idMusica) throws Exception {
        Musica musica = null;

        try {
            String sql;

            sql = "SELECT * FROM APPMUSICA.MUSICA WHERE IDMUSICA=?";

            BDSQLServer.COMANDO.prepareStatement(sql);

            BDSQLServer.COMANDO.setInt(1, idMusica);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            if (!resultado.first())
                throw new Exception("Não cadastrado");

            musica = new Musica(resultado.getInt("IDMUSICA"),
                    resultado.getString("NOMEMUSICA"),
                    resultado.getInt("DURACAO"),
                    resultado.getInt("IDAUTOR"));
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar música");
        }

        return musica;
    }

    // retorna um arraylist contendo todos os autors inseridos
    public static ArrayList<Musica> getArrayListMusicas() throws Exception {

        ArrayList<Musica> musicas = new ArrayList<>();

        // faz um SELECT, que torna todos os dados existentes na tebela
        try 
        {
            String sql;

            sql = "SELECT * FROM APPMUSICA.MUSICA";

            BDSQLServer.COMANDO.prepareStatement(sql);

            MeuResultSet resultado = (MeuResultSet) BDSQLServer.COMANDO.executeQuery();

            while (resultado.next())  // enquanto ainda houverem linhas de dados, cria-se uma nova música e a adiciona no vetor
            {
                Musica musica = new Musica(
                        resultado.getInt("idMusica"),
                        resultado.getString("nome"),
                        resultado.getInt("duracao"),
                        resultado.getInt("idAutor")
                );

                musicas.add(musica);
            }

        } catch (SQLException erro) {
            throw new Exception("Erro ao recuperar músicas");
        }

        return musicas;  // retorna o vetor de autores
    }

    // Retorna MeuResultSet contendo todas as musicas
    public static MeuResultSet getMusicas () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                    "FROM AppMusica.Musica";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar musicas");
        }

        return resultado;
    }
}