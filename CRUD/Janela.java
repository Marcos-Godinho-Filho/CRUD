import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


import bd.daos.*;
import bd.dbos.*;

public class Janela extends JFrame {
    protected static final long serialVersionUID = 1L;

    ArrayList<Musica> musicas = null;
    ArrayList<Autor> autores = null;

    private int posicaoMusica = 0;
    private int posicaoAutor = 0;

    char opcao;

    // Instanciamos os botões que serão usados
    protected JButton btnCreate = new JButton("Create"),
            btnRead = new JButton("Read"),
            btnUpdate = new JButton("Update"),
            btnDelete = new JButton("Delete"),
            btnSalvarMusica = new JButton("Salvar"),
            btnSalvarAutor = new JButton("Salvar"),
            btnDireitaMusica = new JButton(">"),
            btnEsquerdaMusica = new JButton("<"),
            btnDireitaAutor = new JButton(">"),
            btnEsquerdaAutor = new JButton("<");

    // Instanciamos os labels que serão usados
    protected JLabel lbMensagem = new JLabel("Mensagem:");

    protected JLabel lbMusica = new JLabel("Música"),
            lbIdMusica = new JLabel("idMusica"),
            lbNomeMusica = new JLabel("Nome"),
            lbDuracao = new JLabel("Duracao"),
            lbIdAutorMusica = new JLabel("IdAutor"),
            lbAutor = new JLabel("Autor"),
            lbIdAutor = new JLabel("IdAutor"),
            lbNomeAutor = new JLabel("Nome"),
            lbGenero = new JLabel("Gênero"),
            lbInv = new JLabel("");

    // Instanciamos os textboxes que serão usados
    protected JTextField txtIdMusica = new JTextField(),
            txtNomeMusica = new JTextField(),
            txtDuracao = new JTextField(),
            txtIdAutorMusica = new JTextField(),
            txtIdAutor = new JTextField(),
            txtGenero = new JTextField(),
            txtNomeAutor = new JTextField(),
            txtInv = new JTextField();

    public Janela() {
        super("AppMusica");

        // Setando os eventListeners para os botoes
        btnCreate.addActionListener(new Criacao());
        btnRead.addActionListener(new Leitura());
        btnUpdate.addActionListener(new Atualizacao());
        btnDelete.addActionListener(new Delecao());
        btnSalvarMusica.addActionListener(new SalvamentoMusica());
        btnSalvarAutor.addActionListener(new Salvamentoautor());
        btnDireitaMusica.addActionListener(new ProximaMusica());
        btnDireitaAutor.addActionListener(new ProximoAutor());
        btnEsquerdaMusica.addActionListener(new AnteriorMusica());
        btnEsquerdaAutor.addActionListener(new AnteriorAutor());

        // Criando a nav para os botoes de operacoes
        JPanel navBotoes = new JPanel();
        FlowLayout flwBotoes = new FlowLayout();
        navBotoes.setLayout(flwBotoes);

        navBotoes.add(btnCreate);
        navBotoes.add(btnRead);
        navBotoes.add(btnUpdate);
        navBotoes.add(btnDelete);

        // Criando a parte onde vao ficar as informaoes de musicas
        JPanel musica = new JPanel();
        GridLayout grdMusica = new GridLayout(4, 1);
        musica.setLayout(grdMusica);

        musica.add(lbMusica);

        JPanel navProxAntMusica = new JPanel();
        FlowLayout flwNavProxAntMusica = new FlowLayout();
        navProxAntMusica.setLayout(flwNavProxAntMusica);
        navProxAntMusica.add(btnEsquerdaMusica);
        navProxAntMusica.add(btnDireitaMusica);

        musica.add(navProxAntMusica);

        JPanel dgMusica = new JPanel();
        GridLayout grdInfoMusica = new GridLayout(4, 2);
        dgMusica.setLayout(grdInfoMusica);

        dgMusica.add(lbIdMusica);
        dgMusica.add(txtIdMusica);

        dgMusica.add(lbNomeMusica);
        dgMusica.add(txtNomeMusica);

        dgMusica.add(lbDuracao);
        dgMusica.add(txtDuracao);

        dgMusica.add(lbIdAutorMusica);
        dgMusica.add(txtIdAutorMusica);

        musica.add(dgMusica);

        musica.add(btnSalvarMusica);

        // Criando a parte onde vao ficar as informaoes de autor
        JPanel autor = new JPanel();
        GridLayout grdAutor = new GridLayout(4, 1);
        autor.setLayout(grdAutor);

        autor.add(lbAutor);

        JPanel navProxAntAutor = new JPanel();
        FlowLayout flwNavProxAntAutor = new FlowLayout();
        navProxAntAutor.setLayout(flwNavProxAntAutor);
        navProxAntAutor.add(btnEsquerdaAutor);
        navProxAntAutor.add(btnDireitaAutor);

        autor.add(navProxAntAutor);

        JPanel dgAutor = new JPanel();
        GridLayout grdInfoautor = new GridLayout(4, 2);
        dgAutor.setLayout(grdInfoautor);

        dgAutor.add(lbIdAutor);
        dgAutor.add(txtIdAutor);

        dgAutor.add(lbNomeAutor);
        dgAutor.add(txtNomeAutor);

        dgAutor.add(lbGenero);
        dgAutor.add(txtGenero);

        txtInv.setVisible(false);
        dgAutor.add(lbInv);
        dgAutor.add(txtInv);

        autor.add(dgAutor);

        autor.add(btnSalvarAutor);

        JPanel musicaAutor = new JPanel();
        FlowLayout flwMusicaAutor = new FlowLayout(FlowLayout.CENTER, 50, 50);
        musicaAutor.setLayout(flwMusicaAutor);
        musicaAutor.add(musica);
        musicaAutor.add(autor);

        Container cntForm = this.getContentPane();
        cntForm.setLayout(new BorderLayout());

        lbMensagem.setFont(new Font("Arial", Font.BOLD, 16));

        cntForm.add(navBotoes, BorderLayout.NORTH);
        cntForm.add(musicaAutor, BorderLayout.CENTER);
        cntForm.add(lbMensagem, BorderLayout.SOUTH);

        try {
            musicas = Musicas.getArrayListMusicas();
            autores = Autores.getArrayListautors();

            Atualizar();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!", JOptionPane.ERROR_MESSAGE);
        }

        opcao = 'R';
        ConfigurarBotoesCRUD(opcao);

        this.addWindowListener(new FechamentoDeJanela());
        this.addWindowListener(new AberturaDeJanela());

        this.setSize(800, 500);
        this.setVisible(true);
    }

    // vefifica as diferentes operações que o usuário pode realizer no programa
    // (create, read, update e delete), habilitando e desabilitando botões e
    // textBoxes e mudando o nome dos botões para auxiliar o usuário, de acordo com
    // cada situação
    private void ConfigurarBotoesCRUD(char opcao) {
        if (opcao == 'C' || opcao == 'U') {
            txtGenero.setEditable(true);
            txtDuracao.setEditable(true);
            txtIdAutor.setEditable(true);
            txtIdAutorMusica.setEditable(true);
            txtIdMusica.setEditable(true);
            txtNomeAutor.setEditable(true);
            txtNomeMusica.setEditable(true);
            btnSalvarAutor.setEnabled(true);
            btnSalvarMusica.setEnabled(true);
            btnDireitaAutor.setEnabled(false);
            btnDireitaMusica.setEnabled(false);
            btnEsquerdaAutor.setEnabled(false);
            btnEsquerdaMusica.setEnabled(false);

            if (opcao == 'C') {
                btnSalvarAutor.setText("Create");
                btnSalvarMusica.setText("Create");

                lbMensagem.setText("Mensagem: Preencha os campos para inserir o registro desejado.");
            } else {
                btnDireitaAutor.setEnabled(true);
                btnDireitaMusica.setEnabled(true);
                btnEsquerdaAutor.setEnabled(true);
                btnEsquerdaMusica.setEnabled(true);
                txtIdAutor.setEditable(false);
                txtIdMusica.setEditable(false);
                btnSalvarAutor.setText("Update");
                btnSalvarMusica.setText("Update");

                lbMensagem.setText("Mensagem: Preencha os campos para atualizar o registro desejado.");
            }
        } else if (opcao == 'R' || opcao == 'D') {
            btnSalvarAutor.setText("Salvar");
            btnSalvarMusica.setText("Salvar");

            txtGenero.setEditable(false);
            txtDuracao.setEditable(false);
            txtIdAutor.setEditable(false);
            txtIdAutorMusica.setEditable(false);
            txtIdMusica.setEditable(false);
            txtNomeAutor.setEditable(false);
            txtNomeMusica.setEditable(false);
            btnDireitaAutor.setEnabled(true);
            btnDireitaMusica.setEnabled(true);
            btnEsquerdaAutor.setEnabled(true);
            btnEsquerdaMusica.setEnabled(true);

            btnSalvarAutor.setEnabled(false);
            btnSalvarMusica.setEnabled(false);

            lbMensagem.setText("Mensagem: Navegue pelos registros ou aperte os botões do topo para realizar outras operações.");

            if (opcao == 'D') {
                btnSalvarAutor.setText("Delete");
                btnSalvarMusica.setText("Delete");

                btnSalvarAutor.setEnabled(true);
                btnSalvarMusica.setEnabled(true);

                lbMensagem.setText("Mensagem: Delete o registro desejado.");
            }
        }
    }

    // habitlita e desabilita os botões de navegação
    private void TestarBotoes() {
        if (musicas != null) {
            if (musicas.size() == 0)  // se o vetor não for nulo, verifica se ele está vazio 
            {
                // se o vetor estiver vazio, os botões usados para navegar serão desabilitados
                btnDireitaMusica.setEnabled(false);  
                btnEsquerdaMusica.setEnabled(false);
                LimparTela();
            } else {
                // se houver dados no vetor, os botões serão habilitados/desabilitados de acordo com as verificações
                btnEsquerdaMusica.setEnabled(posicaoMusica != 0);
                btnDireitaMusica.setEnabled(posicaoMusica != musicas.size() - 1);
                btnSalvarMusica.setEnabled(musicas.size() != 0);
            }
        } else {
            // se o vetor for nulo, os botões de navegação serão desabilitados
            btnDireitaMusica.setEnabled(false);
            btnEsquerdaMusica.setEnabled(false);
        }

        if (autores != null) {
            if (autores.size() == 0)  // se o vetor não for nulo, verifica se ele está vazio
            {
                // se o vetor estiver vazio, os botões usados para navegar serão desabilitados
                btnDireitaAutor.setEnabled(false);
                btnEsquerdaAutor.setEnabled(false);
                LimparTela();
            } else {
                // se houver dados no vetor, os botões serão habilitados/desabilitados de acordo com as verificações
                btnEsquerdaAutor.setEnabled(posicaoAutor != 0);
                btnDireitaAutor.setEnabled(posicaoAutor != autores.size() - 1);
                btnSalvarAutor.setEnabled(autores.size() != 0);
            }

        } else {
            // se o vetor for nulo, os botões de navegação serão desabilitados
            btnDireitaAutor.setEnabled(false);
            btnEsquerdaAutor.setEnabled(false);
        }
    }

    private void Atualizar() {
        TestarBotoes();

        if (opcao == 'C') // se o usuário for inserir algum dado, a tela será limpada para que ele possa digitar nos textbox
        {
            LimparTela();
        } 
        else
        {
            if (musicas != null) {
                if (!musicas.isEmpty()) // musicas.size != 0  (se o vetor não for nulo, verifica se ele está vazio)
                {
                    // se estiver vazio, não aparecerá nada nos textbox, pois não há dados para serem exibidos
                    txtIdMusica.setText(musicas.get(posicaoMusica).getIdMusica() + "");
                    txtNomeMusica.setText(musicas.get(posicaoMusica).getNomeMusica());
                    txtDuracao.setText(musicas.get(posicaoMusica).getDuracao() + "");
                    txtIdAutorMusica.setText(musicas.get(posicaoMusica).getIdAutor() + "");
                }
            }

            if (autores != null)
            {
                if (!autores.isEmpty())  // se o vetor não for nulo, verifica se ele está vazio
                {
                    // se estiver vazio, não aparecerá nada nos textbox, pois não há dados para serem exibidos
                    txtIdAutor.setText(autores.get(posicaoAutor).getIdAutor() + "");
                    txtNomeAutor.setText(autores.get(posicaoAutor).getNome());
                    txtGenero.setText(autores.get(posicaoAutor).getGenero());
                }
            }
        }
    }

    // não aparecerá nada nps textbox, limpando os campos
    private void LimparTela() 
    {
        txtIdMusica.setText("");
        txtNomeMusica.setText("");
        txtDuracao.setText("");
        txtIdAutorMusica.setText("");

        txtIdAutor.setText("");
        txtNomeAutor.setText("");
        txtGenero.setText("");
    }

    protected class FechamentoDeJanela extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    protected class AberturaDeJanela extends  WindowAdapter {
        public void windowOpened(WindowEvent e) {
            Atualizar();
        }
    }


    protected class Criacao implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // quando o usuário clicar no botão "Create", os métodos que auxiliarão o usuário na inserção dos dados será chamado
            opcao = 'C';
            ConfigurarBotoesCRUD(opcao);
            LimparTela();
        }
    }

    protected class Leitura implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // quando o usuário clicar no botão "Read", os métodos que configuram os botões para que o usuário navegue entre os dados serão chamados
            opcao = 'R';
            ConfigurarBotoesCRUD(opcao);
            Atualizar();
        }
    }

    protected class Atualizacao implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // quando o usuário clicar no botão "Update", os métodos que configuram os botões para que o usuário altere algum dado serão chamados
            opcao = 'U';
            ConfigurarBotoesCRUD(opcao);
            Atualizar();
        }
    }

    protected class Delecao implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //quando o usuário clicar no botão "Delete", os métodos que configuram os botões para que o usuário delete algum dado serão chamados
            opcao = 'D';
            ConfigurarBotoesCRUD(opcao);
            Atualizar();
        }
    }

    protected class SalvamentoMusica implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try 
            {
                int idMusica = Integer.parseInt(txtIdMusica.getText());
                String nomeMusica = txtNomeMusica.getText();
                int duracao = Integer.parseInt(txtDuracao.getText());
                int idautor = Integer.parseInt(txtIdAutorMusica.getText());

                Musica musica = null;  // cria uma nova música com os dados que o usuário digitou
                try {
                    musica = new Musica(idMusica, nomeMusica, duracao, idautor);

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO", JOptionPane.ERROR_MESSAGE);
                }

                if (opcao == 'C') {
                    try {
                        Musicas.incluir(musica);  // inclui uma música no banco de dados
                        JOptionPane.showMessageDialog(rootPane, "Inclusão feita com sucesso!", "TUDO OCORREU BEM!",
                                JOptionPane.INFORMATION_MESSAGE);
                        opcao = 'R';
                        musicas.add(musica); // inclui uma música no vetor

                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (opcao == 'U') {
                    try {
                        Musicas.alterar(musica);  // altera a música no banco de dados
                        JOptionPane.showMessageDialog(rootPane, "Atualização feita com sucesso!", "TUDO OCORREU BEM!",
                                JOptionPane.INFORMATION_MESSAGE);
                        musicas.set(posicaoMusica, musica);  // a música da posição passada como parâmetro passa a ser a música com os dados digitados pelo usuário
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (opcao == 'D') {
                    try {
                        Musicas.excluir(idMusica);  // deleta a música do banco de dados
                        JOptionPane.showMessageDialog(rootPane, "Deleção feita com sucesso!", "TUDO OCORREU BEM!",
                                JOptionPane.INFORMATION_MESSAGE);
                        musicas.remove(posicaoMusica);  // remove apenas o dado da posição que o usuário deseja excluir
                        if (posicaoMusica == musicas.size()) // se deletar o último registro, a posicao passa a ser a "nova última música"
                            posicaoMusica = musicas.size() - 1;  
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (musicas.size() == 0)  // se o vetor estiver vazio, limpa a tela
                        LimparTela();
                }
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(rootPane, "Preencha corretamente os campos!", "CAMPO(S) INCORRETO(S)!",
                        JOptionPane.ERROR_MESSAGE);
            }
            ConfigurarBotoesCRUD(opcao);
            Atualizar();  
        }
    }

    protected class Salvamentoautor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int idautor = Integer.parseInt(txtIdAutor.getText());
                String nomeautor = txtNomeAutor.getText();
                String genero = txtGenero.getText();

                Autor autor = null;
                // cria um novo autor com os dados que o usuário digitou 
                try 
                {
                    autor = new Autor(idautor, nomeautor, genero);
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO", JOptionPane.ERROR_MESSAGE);
                }

                if (opcao == 'C') {
                    try {
                        Autores.incluir(autor);  // inclui um autor no banco de dados
                        JOptionPane.showMessageDialog(rootPane, "Inclusão feita com sucesso!", "TUDO OCORREU BEM!",
                                JOptionPane.INFORMATION_MESSAGE);
                        autores.add(autor);  // inclui um autor no vetor
                        opcao = 'R';

                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    posicaoAutor = autores.size() - 1;
                } else if (opcao == 'U') {
                    try {
                        Autores.alterar(autor);  // altera o autor no banco de dados
                        JOptionPane.showMessageDialog(rootPane, "Atualização feita com sucesso!", "TUDO OCORREU BEM!",
                                JOptionPane.INFORMATION_MESSAGE);
                        autores.set(posicaoAutor, autor);   // o autor da posição passada como parâmetro passa a ser o autor com os dados digitados pelo usuário
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (opcao == 'D') {
                    try {
                        Autores.excluir(idautor);  // deleta o autor do banco de dados
                        JOptionPane.showMessageDialog(rootPane, "Deleção feita com sucesso!", "TUDO OCORREU BEM!",
                                JOptionPane.INFORMATION_MESSAGE);
                        autores.remove(posicaoAutor);  // remove apenas o dado da posição que o usuário deseja excluir
                        if (posicaoAutor == autores.size())  // se deletar o último registro, a posicao passa a ser a "novo último autor"
                            posicaoAutor = autores.size() - 1;
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(rootPane, erro.getMessage(), "OCORREU UM ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if (autores.size() == 0)  // se o vetor estiver vazio, limpa a tela
                        LimparTela();
                }
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(rootPane, "Preencha corretamente os campos!", "CAMPO(S) INCORRETOS!",
                        JOptionPane.ERROR_MESSAGE);
            }
            ConfigurarBotoesCRUD(opcao);
            Atualizar();
        }
    }


    // botões usados para que o usuário navegue entre os dados, que, ao serem clicados, 
    // chamam o método que atulaiza a tela, para que novos dados seja exibidos
    protected class ProximaMusica implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            posicaoMusica += 1; // avançamos uma posição no arraylist de músicas
            Atualizar(); // atualizamos a tela de acordo com a nova posição
        }
    }

    protected class ProximoAutor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            posicaoAutor += 1; // avançamos uma posição no arraylist de autors
            Atualizar(); // atualizamos a tela de acordo com a nova posição
        }
    }

    protected class AnteriorMusica implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            posicaoMusica -= 1; // retrocedemos uma posição no arraylist de músicas
            Atualizar(); // atualizamos a tela de acordo com a nova posição
        }
    }

    protected class AnteriorAutor implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            posicaoAutor -= 1; // retrocedemos uma posição no arraylist de autors
            Atualizar(); // atualizamos a tela de acordo com a nova posição
        }
    }
}
