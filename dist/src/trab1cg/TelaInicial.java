/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trab1cg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
/**
 *
 * @author wilton,victor
 */
public class TelaInicial extends JFrame implements MouseListener{
   
    Player player;//som fundo
    
    public TelaInicial(){
       this.setTitle("Inicial - Computação Gráfica - UFSCar - 2016/2");
        
    }
    
   
     public void criaTelaInicial(){
        
        /* Fontes personalizadas */
        Font f = new Font("Serif", Font.BOLD, 38);
        Font f1 = new Font("Serif", Font.BOLD, 25);
        Font t = new Font("Arial", Font.BOLD, 100);
        
        /* Panel inicial para conter botoes e texto */
        JPanel  tela_ini = new JPanel();
        tela_ini.setBackground(Color.black);
        tela_ini.setSize(820,660);
        tela_ini.setBounds(0,0,820,660);
        tela_ini.setLayout(null); 
        
        
        
       /* Texto reversi */
        JLabel reversi = new JLabel("Reversi");
        reversi.setSize(500,150);
        reversi.setFont(t);
        reversi.setForeground(Color.blue);
        reversi.setLocation(180,40);
        
      
       
         /* Botao iniciar */
        JButton btn_ini = new JButton("INICIAR");
        btn_ini.setBackground(Color.green);
         btn_ini.setFont(f);
         btn_ini.setSize(300,80);
         btn_ini.setLocation(250,250);
         
         /* Botao instrucoes */
        JButton btn_inst = new JButton("INSTRUÇÕES");
        btn_inst.setBackground(Color.yellow);
         btn_inst.setFont(f1);
         btn_inst.setSize(300,80);
         btn_inst.setLocation(250,350);
         
         /* Botao sair */
        JButton btn_sair = new JButton("SAIR");
        btn_sair.setBackground(Color.red);
         btn_sair.setFont(f);
         btn_sair.setSize(300,80);
        btn_sair.setLocation(250,450);
         
        /* Tratamento clique mouse em botao SAIR */
        ActionListener sairClique = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//encerra aplicacao
             }
         };
        
        /* Adiciona listener para clique em sair */
        btn_sair.addActionListener(sairClique);
        
        
         /* Tratamento clique mouse em botao INICIAR */
        ActionListener iniciarClique = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fechaJanela();
                Tabuleiro t = new Tabuleiro();
                t.criaTabuleiro();
             }
         };
        
        
         /* Tratamento clique mouse em botao INSTRUCOES */
        ActionListener instrucoesClique = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    /* abre pdf de intruções */
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                try {
                    desktop.open(new File("src/trab1cg/Reversi.pdf").getCanonicalFile());
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         };
        btn_inst.addActionListener(instrucoesClique);
        
        
        
        /* Adiciona listener para clique em iniciar*/
        btn_ini.addActionListener(iniciarClique);
        
        
        tela_ini.add(reversi,BorderLayout.CENTER);  
        tela_ini.add(btn_ini, BorderLayout.CENTER);
        tela_ini.add(btn_inst, BorderLayout.CENTER);
        tela_ini.add(btn_sair, BorderLayout.CENTER);
        
        this.getContentPane().add(tela_ini);
        mostraJanela();
   
        
      
      }
    
       private void mostraJanela(){
           this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(820,660);
            this.getContentPane().setBackground(Color.green);
            this.setLayout(null);
            this.setResizable(false);
            this.setVisible(true);
           
       }
   
   //fecha janela frame inicial
    public void fechaJanela(){
           this.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
