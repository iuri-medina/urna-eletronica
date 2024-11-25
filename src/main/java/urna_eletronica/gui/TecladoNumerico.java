package urna_eletronica.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TecladoNumerico extends JPanel {

    public TecladoNumerico(ActionListener listener) {
        setLayout(new BorderLayout(10, 10)); 
        setBackground(Color.LIGHT_GRAY); 


        JPanel painelNumerico = new JPanel(new GridLayout(4, 3, 5, 5));
        String[] botoesNumericos = {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "", "0", "" 
        };

        for (String texto : botoesNumericos) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.setFocusPainted(false);
            botao.setBackground(Color.WHITE);
            botao.setPreferredSize(new Dimension(80, 80)); 
            botao.setActionCommand(texto);
            botao.addActionListener(listener);


            painelNumerico.add(botao);
        }


        JPanel painelAcoes = new JPanel(new GridLayout(1, 3, 10, 10));
        String[] botoesAcoes = {"BRANCO", "CORRIGE", "CONFIRMA"};

        for (String texto : botoesAcoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 20));
            botao.setFocusPainted(false);
            botao.setPreferredSize(new Dimension(80, 80)); 
            botao.setForeground(Color.WHITE); 

            
            switch (texto) {
                case "BRANCO":
                    botao.setBackground(Color.GRAY);
                    break;
                case "CORRIGE":
                    botao.setBackground(Color.RED);
                    break;
                case "CONFIRMA":
                    botao.setBackground(new Color(0, 128, 0)); // Verde
                    break;
            }

            botao.setActionCommand(texto);
            botao.addActionListener(listener);
            painelAcoes.add(botao);
        }


        add(painelNumerico, BorderLayout.CENTER);
        add(painelAcoes, BorderLayout.SOUTH);
    }
}
