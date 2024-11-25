package urna_eletronica.gui;

import urna_eletronica.dao.CandidatoDAO;
import urna_eletronica.dao.PartidoDAO;
import urna_eletronica.dao.VisualizarVotosDAO;
import urna_eletronica.dao.VotoDAO;
import urna_eletronica.model.Candidato;
import urna_eletronica.model.Partido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UrnaEletronicaGUI extends JFrame implements ActionListener {

    // Componentes da aba "Cadastrar Candidato"
    private JTextField nomeCandidatoField = new JTextField(20);
    private JTextField numeroCandidatoField = new JTextField(5);
    private JComboBox<Partido> partidoComboBox = new JComboBox<>();
    private JButton cadastrarCandidatoButton = new JButton("Cadastrar Candidato");

    // Componentes da aba "Cadastrar Partido"
    private JTextField nomePartidoField = new JTextField(20);
    private JTextField siglaPartidoField = new JTextField(5);
    private JButton cadastrarPartidoButton = new JButton("Cadastrar Partido");

    // Componentes da aba "Votar"
    private JTextField displayNumeroVoto = new JTextField(5);
    
    JPanel abaVisualizarVotos = criarAbaVisualizarVotos();

    public UrnaEletronicaGUI() {
        setTitle("Urna Eletrônica");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Votar", criarAbaVotar());
        tabbedPane.addTab("Cadastrar Candidato", criarAbaCadastrarCandidato());
        tabbedPane.addTab("Cadastrar Partido", criarAbaCadastrarPartido());
        tabbedPane.addTab("Visualizar Votos", abaVisualizarVotos);
      
        add(tabbedPane);

        carregarPartidos();
    }

    private JPanel criarAbaVotar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margens externas

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayNumeroVoto.setHorizontalAlignment(JTextField.CENTER);
        displayNumeroVoto.setEditable(false);
        displayNumeroVoto.setFont(new Font("Arial", Font.BOLD, 32));
        displayNumeroVoto.setPreferredSize(new Dimension(0, 60)); // Altura do display
        displayPanel.add(displayNumeroVoto, BorderLayout.CENTER);

        TecladoNumerico teclado = new TecladoNumerico(this);
        panel.add(displayPanel, BorderLayout.NORTH);
        panel.add(teclado, BorderLayout.CENTER);

        return panel;
    }
    
    //actionPerformed filtrando apenas acoes da aba VOTAR
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        int numeroCandidato;

        // Verifica se o comando é relevante (vindo do teclado)
        if (comando.matches("\\d|BRANCO|CORRIGE|CONFIRMA")) {
            switch (comando) {
                case "CORRIGE":
                    displayNumeroVoto.setText("");  // Limpa o display
                    break;
                case "CONFIRMA":               
                    try {
                    	if(displayNumeroVoto.getText().equals("BRANCO") || displayNumeroVoto.getText().equals("0")) {
                    		numeroCandidato = 0;
                    		
                    		VotoDAO votoDao = new VotoDAO();
                    	    
                                int resposta = JOptionPane.showConfirmDialog(
                                        null,
                                        "Deseja votar em branco?",
                                        "Confirmar voto",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE
                                );

                                if (resposta == JOptionPane.YES_OPTION) {
                                    votoDao.votar(numeroCandidato);
                                    JOptionPane.showMessageDialog(null, "Voto confirmado!");
                                    
                                } else {
                                    JOptionPane.showMessageDialog(null, "Voto cancelado.");
                                }
                                displayNumeroVoto.setText("");  // Limpa o display após confirmar
                    	}
                    	
                    	
                    	
                    	else {
                    		numeroCandidato = Integer.parseInt(displayNumeroVoto.getText());
                            VotoDAO votoDao = new VotoDAO();
        

                            if (votoDao.confirmarCandidato(numeroCandidato)) {
                                int resposta = JOptionPane.showConfirmDialog(
                                        null,
                                        "Deseja votar no candidato " + votoDao.getNomeCandidato() + 
                                        " do " + votoDao.getNomePartido() + "?",
                                        "Confirmar voto",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE
                                );

                                if (resposta == JOptionPane.YES_OPTION) {
                                    votoDao.votar(numeroCandidato);
                                    JOptionPane.showMessageDialog(null, "Voto confirmado!");
                                    
                                } else {
                                    JOptionPane.showMessageDialog(null, "Voto cancelado.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Candidato não encontrado no sistema!");
                            }

                            displayNumeroVoto.setText("");  // Limpa o display após confirmar
                        }
                    } 
                    catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Número inválido. Tente novamente.");
                    }
                        break;
                    default:
                        // Adiciona o número clicado ao display
                        displayNumeroVoto.setText(displayNumeroVoto.getText() + comando);
                        break;
                    	}  	                      
            }
        }
    

    private JPanel criarAbaCadastrarCandidato() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Nome:"));
        panel.add(nomeCandidatoField);

        panel.add(new JLabel("Número:"));
        panel.add(numeroCandidatoField);

        panel.add(new JLabel("Partido:"));
        panel.add(partidoComboBox);

        panel.add(new JLabel());
        panel.add(cadastrarCandidatoButton);

        cadastrarCandidatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeCandidatoField.getText();
 
                    int numero = Integer.parseInt(numeroCandidatoField.getText());
                    Partido partido = (Partido) partidoComboBox.getSelectedItem();
                    
                    if(nome.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "É obrigatório preencher o campo nome.");
                        System.out.println("É obrigatório preencher o campo nome.");
                    }
                    
                    else {
                    	Candidato candidato = new Candidato(nome, partido.getId(), numero);
                        new CandidatoDAO().cadastrarCandidato(candidato);
                        JOptionPane.showMessageDialog(null, "Candidato cadastrado com sucesso!");

                        // Limpar campos
                        nomeCandidatoField.setText("");
                        numeroCandidatoField.setText("");
                    }
 
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar candidato: " + ex.getMessage());
                }
            }
        });

        return panel;
    }

    
    private JPanel criarAbaCadastrarPartido() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Nome do Partido:"));
        panel.add(nomePartidoField);

        panel.add(new JLabel("Sigla:"));
        panel.add(siglaPartidoField);

        panel.add(new JLabel());
        panel.add(cadastrarPartidoButton);

        cadastrarPartidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomePartidoField.getText();
                    String sigla = siglaPartidoField.getText();
                    
                    if(nome.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "É obrigatório preencher o campo nome.");
                        System.out.println("É obrigatório preencher o campo nome.");
                    }
                    
                    else if(sigla.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "É obrigatório preencher o campo sigla.");
                        System.out.println("É obrigatório preencher o campo sigla.");
                    }
                    
                    else {
                    	Partido partido = new Partido(nome, sigla);
                        new PartidoDAO().cadastrarPartido(partido);
                        JOptionPane.showMessageDialog(null, "Partido cadastrado com sucesso!");

                        // Limpar campos
                        nomePartidoField.setText("");
                        siglaPartidoField.setText("");
                    }
                   
                    // Recarregar partidos na ComboBox
                    carregarPartidos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
                }
            }
        });

        return panel;
    }
    
    private JPanel criarAbaVisualizarVotos() {
    	JPanel panel = new JPanel (new BorderLayout());
    	
    	// Coluna da tabela
    	String[] colunas = {"Numero do candidato", "Nome do candidato", "Partido", "Votos"};
    	
    	//Dados da tabela (inicialmente vazia)
    	Object[][] dados = carregarDadosTabela();
    	
    	//Criacao da JTable com dados e colunas
    	JTable tabela = new JTable(dados, colunas);
    	JScrollPane scrollPane = new JScrollPane(tabela);
    	
    	//Adiciona a tabela ao painel
    	panel.add(scrollPane, BorderLayout.CENTER);
    	
    	JButton atualizaVotosButton = new JButton("Atualizar Votos");
    	atualizaVotosButton.setPreferredSize(new Dimension(60, 60));
    	
    	panel.add(atualizaVotosButton, BorderLayout.SOUTH);
    	
    	atualizaVotosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           
            	Object[][] novosDados = carregarDadosTabela();
            	tabela.setModel(new javax.swing.table.DefaultTableModel(novosDados, colunas));            	
            	
            	panel.revalidate();
                panel.repaint();
            }
    	});
    	
    	return panel;
    	
    }
    
    private Object[][] carregarDadosTabela() {
    	try {
    		List<Object[]> candidatos = new VisualizarVotosDAO().listarVotosCandidatos();
    		Object[][] dados = new Object[candidatos.size()][4];
    		
    		//Preenche a matriz de dados para a JTable
    		for(int i = 0; i < candidatos.size(); i++) {
    			dados[i] = candidatos.get(i);
    		}
    		
    		return dados;
    	} catch (SQLException e) {
    		System.out.println("Erro ao carregar candidatos. " + e.getMessage());
    		JOptionPane.showMessageDialog(null, "Erro ao carregar candidatos. " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return new Object[0][4]; // Retorna uma tabela vazia em caso de erro
    	}
    }
    
   
 
    // Carregar os partidos na ComboBox da aba "Cadastrar Candidato"
    private void carregarPartidos() {
        try {
            List<Partido> partidos = new PartidoDAO().listarPartidos();
            partidoComboBox.removeAllItems(); // Limpa os itens atuais
            for (Partido partido : partidos) {
                partidoComboBox.addItem(partido);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar partidos: " + e.getMessage());
        }
    }

    // Método principal para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UrnaEletronicaGUI().setVisible(true));
    }
}
