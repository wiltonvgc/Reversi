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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import sun.audio.*;
import java.io.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import javax.swing.*;
import javazoom.jl.player.Player;
/**
 *
 * @author wilton
 */
public class Tabuleiro extends JPanel implements MouseListener {
    JFrame janela,info,game_over,vencedor;
    int celulas[][];//guarda estado das celulas => 0:sem peça, 1:peça azul, 2:peça vermelha
    boolean cores_tab[][];//guarda cor da celula => false:preto, true:branca
    boolean flag_jogada=true; //flag para verificar quem joga => true:humano, false:computador
    boolean flag_inicio=false;//flag true caso jogo ja tenha comecado
    JLabel azul, cont_azul, verm, cont_verm, vazio, cont_vazio;
    boolean jog=false;
    int cont_clique=0;
    
    
    public Tabuleiro(){
        janela = new JFrame("Tabuleiro - Reversi");
        celulas = new int[8][8];
        cores_tab = new boolean[8][8];
        janela.addMouseListener(this);
        
        /* zera posicoes matriz celulas */
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                celulas[i][j] = 0;
    
    }
    
    /* Metodo para criar tela separada de info sobre o jogo atual */
    public void criaTelaInfo() throws FileNotFoundException{
         
        /* janela para info sobre jogo */
        info = new JFrame("Informações");
        info.setBackground(Color.white);
        info.setSize(200,660);
        info.setBounds(0,0,200,660);
        info.setLocation(890,20);
        info.setResizable(false);
        info.setVisible(true);
        info.setLayout(null);
        
        /* Cria labels */
         Font t = new Font("Calibri", Font.BOLD, 30);
        
         /* AZUL */
        azul = new JLabel("Blue: ");
        azul.setSize(120,100);
        azul.setForeground(Color.blue);
        azul.setLocation(20,20);
        azul.setFont(t);
        info.add(azul);
        
        /* Contador azul */
        cont_azul = new JLabel();
        cont_azul.setSize(120,100);
        cont_azul.setForeground(Color.blue);
        cont_azul.setLocation(110,20);
        cont_azul.setFont(t);
        info.add(cont_azul);
        
        
         /* VERM */
        verm = new JLabel("Red: ");
        verm.setSize(120,100);
        verm.setForeground(Color.red);
        verm.setLocation(20,120);
        verm.setFont(t);
        info.add(verm);
        
        /* Contador VERM */
        cont_verm = new JLabel();
        cont_verm.setSize(120,100);
        cont_verm.setForeground(Color.red);
        cont_verm.setLocation(110,120);
        cont_verm.setFont(t);
        info.add(cont_verm);
        
        
         
         /* VAZIO */
        vazio = new JLabel("Vazio: ");
        vazio.setSize(120,100);
        vazio.setForeground(Color.black);
        vazio.setLocation(20,220);
        vazio.setFont(t);
        info.add(vazio);
        
        /* Contador VAZIO */
        cont_vazio = new JLabel();
        cont_vazio.setSize(120,100);
        cont_vazio.setForeground(Color.black);
        cont_vazio.setLocation(125,220);
        cont_vazio.setFont(t);
        info.add(cont_vazio);
        
    
        
      
        
     }
    
    /* Atualiza placar do jogo */
    public void atualizaInfo(){
        
        int azul=0,verm=0,vaz=0;
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(celulas[i][j]==0){
                    vaz = vaz + 1;
                }
                else if(celulas[i][j]==1){
                    azul = azul +1;
                }else if(celulas[i][j]==2){
                    verm = verm + 1;
                }
            }
        }
        
        /* Atualiza label de cont */
        cont_azul.setText(Integer.toString(azul));
         cont_verm.setText(Integer.toString(verm));
          cont_vazio.setText(Integer.toString(vaz));
        
    }
    
    /* Verifica se algum jogador ganhou */
    public boolean verificaGanhou(){
        
        boolean ganhou=false;
        
        /* Janela game over */
        game_over = new JFrame("Game over");
        game_over.setSize(800,640);
        game_over.setVisible(false);
        game_over.setResizable(false);
       
        /* Panel Game Over*/
        JPanel game_over_panel = new JPanel();
        game_over_panel.setSize(820,660);
       game_over_panel.setBackground(Color.black);
       game_over_panel.setBounds(0,0,820,660);
       game_over_panel.setLayout(null); 
       
       
       /* Texto Game over */
        Font t = new Font("Calibri", Font.BOLD, 85);
        JLabel fim_jogo = new JLabel("Game Over :(");
        fim_jogo.setSize(650,150);
        fim_jogo.setFont(t);
        fim_jogo.setForeground(Color.red);
        fim_jogo.setLocation(100,100);
        game_over_panel.add(fim_jogo);
                
        
       
          
        /* Janela vencedor */
        vencedor = new JFrame("Fim de jogo");
        vencedor.setSize(800,640);
        vencedor.setVisible(false);
        vencedor.setResizable(false);
        
         /* Panel Tela Vencedor*/
        JPanel venceu_panel = new JPanel();
        venceu_panel.setSize(820,660);
       venceu_panel.setBackground(Color.blue);
       venceu_panel.setBounds(0,0,820,660);
       venceu_panel.setLayout(null); 
     
       
       /* Texto Vencedor */
         Font t1 = new Font("Calibri", Font.BOLD, 80);
        JLabel venc = new JLabel("Você venceu! :)");
        venc.setSize(700,150);
        venc.setFont(t1);
        venc.setForeground(Color.white);
        venc.setLocation(70,100);
        venceu_panel.add(venc);
                
       
        
        
        
        
        /* Botao reiniciar vencedor */
        Font f = new Font("Serif", Font.BOLD, 38);
        JButton btn_ini = new JButton("REINICIAR");
        btn_ini.setBackground(Color.green);
         btn_ini.setFont(f);
         btn_ini.setSize(300,80);
         btn_ini.setLocation(250,290);
         
         
          /* Botao reiniciar game over*/
        JButton btn_ini2 = new JButton("REINICIAR");
        btn_ini2.setBackground(Color.green);
         btn_ini2.setFont(f);
         btn_ini2.setSize(300,80);
         btn_ini2.setLocation(250,290);
 
         
        /* Tratamento clique mouse em botao REINICIAR */
        ActionListener reiniciarCliqueGameOver = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fechaJanelaGameOver();
                Reversi novo = new Reversi();
                novo.novoJogo();
                
             }
         };
         
         /* Tratamento clique mouse em botao REINICIAR */
        ActionListener reiniciarCliqueVenceu = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fechaJanelaVencedor();
                Reversi novo = new Reversi();
                novo.novoJogo();
                
             }
         };
        
        
        
        /* Listener botao reiniciar */
         btn_ini.addActionListener(reiniciarCliqueVenceu);
         btn_ini2.addActionListener(reiniciarCliqueGameOver);
         
        game_over_panel.add(btn_ini2);
        venceu_panel.add(btn_ini);
        
        game_over.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vencedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        vencedor.getContentPane().add(venceu_panel);
        game_over.getContentPane().add(game_over_panel);
        
         /* Conta pecas azuis, verm e vazias */
        int azul=0,verm=0,vaz=0;
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(celulas[i][j]==0){
                    vaz = vaz + 1;
                }
                else if(celulas[i][j]==1){
                    azul = azul +1;
                }else if(celulas[i][j]==2){
                    verm = verm + 1;
                }
            }
        }
        
        /* Tabuleiro completo */
        if(azul+verm==64){
            if(azul>verm){
                 //azul ganha
                 ganhou=true;
                 janela.dispose();
                 info.dispose();
                 vencedor.setVisible(true);
                 
            }else{
                 //verm ganha
                 janela.dispose();
                 info.dispose();
                 game_over.setVisible(true);
            }
        }
        /* Nenhuma peca azul */
        else if(azul==0){
            //verm ganha
            janela.dispose();
            info.dispose();
            game_over.setVisible(true);
        }
        /* Nenhum pecao verm */
        else if(verm==0){
             //azul ganha
             ganhou=true;
             janela.dispose();
             info.dispose();
             vencedor.setVisible(true);
        }
        /* Nao ha mais possibilidades de jogadas para ambos os jogadores */
        else if(!verificaPossibilidades(1) && !verificaPossibilidades(2)){
            if(azul>verm){
                 //azul ganha
                 ganhou=true;
                 janela.dispose();
                 info.dispose();
                 vencedor.setVisible(true);
            }else{
                 //verm ganha
                 janela.dispose();
                 info.dispose();
                 game_over.setVisible(true);
            }
        }else{
            //
        }
        
        
        return ganhou;
    }
    
    /* Metodo que fecha janela de game over */
    public void fechaJanelaGameOver(){
        
        
        this.game_over.dispose();
    }
    
    
    /* Metodo que fecha janela de vencedor */
    public void fechaJanelaVencedor(){
        this.vencedor.dispose();
    }
    
    
    
    /* Metodo para desenhar tabuleiro inicial */
    public void criaTabuleiro(){
        
        
        
        this.setBackground(Color.white);
        this.setSize(820,660);
        this.setBounds(0,0,820,660);
        this.setLayout(null); 
        
        JLabel cont = new JLabel("Azuis: ");
        cont.setLocation(830,50);
        cont.setSize(50,50);
        janela.add(cont);
        
        janela.getContentPane().add(this);
        
       
        
        mostraJanela();
        
       try{  
            criaTelaInfo();
       }catch(Exception e){
           
       }
       
    }
    
    /* Desenha em JPanel associado a janela */
    public void paintComponent(Graphics g){
        super.paintComponent(g);  
        
            
        
            /* Desenhar celulas 8 x 8 */
            int x = 10, y = 10;
            boolean color = false;//false:preto, true:branco
        
             /* loop 64 celulas */
            for(int k = 0; k<8; k++){
                  /* loop por linha */
                  for(int i=0;i<8;i++){
                        if(color){
                             g.setColor(Color.white);
                             cores_tab[i][k] = true;
                         if(i!=7)
                              color = false;
                         else
                             color = true;
                    }
                    else{
                         g.setColor(Color.black);
                          cores_tab[i][k] = false;
                           if(i!=7)
                                 color = true;
                            else
                                  color = false;
                      }
                
                        g.fillRect(x, y,100,80);
                         x = x + 100;
                    }
                        y = y + 80;
                         x = 10;
             }
            
              if(!flag_inicio){
                  /* Configuracao inicial de peças */
                 celulas[3][3] = 1;//azul
                 celulas[3][4] = 2;//verm
                 celulas[4][3] = 2;//verm
                 celulas[4][4] = 1;//azul
                
        
                  atualizaTabuleiro(celulas,g);
                  flag_inicio = true;
              
             }//fim if
        
             /* flag ja comecou */
             else{
                 atualizaTabuleiro(celulas,g);
            
            }
              atualizaInfo();
      
    }
    
    /* Atualiza desenho de peças no tabuleiro a partir da matriz de cores celulas */
    public void atualizaTabuleiro(int m[][],Graphics g){
        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                /* pega posicao na tela de posicao i,j da matriz */
                int x_pos = i*100 + 25;
                int y_pos = j*80 + 15;
                
                /* vazio */
                if(m[i][j]==0){
                    boolean cor = cores_tab[i][j];
                    if(cor){
                         g.setColor(Color.white);
                         g.fillRect(i*100+10, j*80+10,100,80);
                    }else{
                        g.setColor(Color.black);
                         g.fillRect(i*100+10, j*80+10,100,80);
                    }
                }
                /* cor azul */
                else if(m[i][j]==1){
                    g.setColor(Color.blue);
                    g.fillOval(x_pos, y_pos,70,70);
                /* cor vermelha */
                }else if(m[i][j]==2){
                    g.setColor(Color.red);
                    g.fillOval(x_pos, y_pos,70,70);
                }
                
            }//fim fot
        }//fim for
        
    }
    
    /* Metodo que recebe coordenadas de clique e retorna posicao na matriz de celulas */
    public Point getPosicaoMatriz(int x,int y){
        int x_aux=0,y_aux=0;//var auxiliares
        int posX,posY;//posicao matriz
        
        /* pega celula pos X */
        for(int i=0;i<=800;i+=100){
            if(x<i){
                x_aux = i-100;
                break;
            }
        }
        
        /* pega celula pos Y*/
        for(int i=0;i<=640;i+=80){
            if(y<i){
                y_aux = i-80;
                break;
            }
        }
        
        posX = x_aux/100;
        posY = y_aux/80;
        
        Point p = new Point(posX,posY);
        return p;
    }
    
    
    /* Metodo que verifica se ha possiblidades para uma determinada cor nas celulas rstantes */
    public boolean verificaPossibilidades(int cor){
        ArrayList<Point> vazios = new ArrayList<Point>();//pontos de celulas vazias
        
        int i,j;
        
        for(i=0;i<8;i++){
            for(j=0;j<8;j++){
                
                if(celulas[i][j]==0){
                    Point p = new Point(i,j);
                    vazios.add(p);
                }//fim if
                
            }//fim for j
        }//fim for i
        
        
        
        //verifica se ha jogadas possiveis para cor
        boolean haPossibilidade = false;
        for(Point p: vazios){
            if(verificaJogada(p.x,p.y,cor,false)){
                haPossibilidade = true;
                break;
            }
         }//fim for
        
        return haPossibilidade;
    }
    
    
    
    
   /* Metodo chamado por cliqque de mouse quando ocorre uma tantiva de jogada doo humano */ 
    public void jogaHumano(int x, int y){
        
       
        /* se flag true, humano pode jogar */
        if(this.flag_jogada){
            Point r = getPosicaoMatriz(x,y);
            
            if(tentaJogada(r.x,r.y,1,false)){
                 this.repaint();
                this.repaint();
                flag_jogada = false;
                atualizaInfo();
                if(!verificaGanhou()){
                    jogaComputador();   
                }
                cont_clique = 0;
             
            }
            /* Se nao ha mais possibilidades de jogada com azul, passa para PC*/
            else if(!verificaPossibilidades(1)){
                 if(!verificaGanhou()){
                    jogaComputador();   
                }
            }
            
        }//fim flag jogada
            
 }//fim metodo

    
/* Metodo para computador definir uma jogada */
public void jogaComputador() {
        int[][] celulas_aux = new int[8][8];
       /* Logica para definir jogada */
       /* cor = 2 vermelho */
       
       boolean aux=false,lastCase=true;
       int i,j;
       int x=-1,y=-1,xLastCase=0,yLastCase=0;
       
       
       // pega os cantos sempre que puder ----------------------
       if (tentaJogada(0,0,2,false)){
           this.repaint();
           flag_jogada = true;
                 
       }
           
       else if (tentaJogada(0,7,2,false)){
           this.repaint();
           flag_jogada = true;
           
       }
       
       else if (tentaJogada(7,0,2,false)){
           this.repaint();
           flag_jogada = true;
           
       }
       
       else if (tentaJogada(7,7,2,false)){
           this.repaint();
           flag_jogada = true;
           
       }
     //-------------------------------------------------------------
     //Evita entregar o canto
       
              
       else{
       celulas_aux = celulas.clone();
       
       for(i=0;i<8;i++){
           for(j=0;j<8;j++){
               //,(6,7)
               if ((i!=1 || j!=1) && (i!=6 || j!=6) && (i!=1 || j!=6) &&(i!=6 || j!=1) 
                       &&(i!=0 || j!=1) &&(i!=1 || j!=0) &&(i!=0 || j!=6) &&(i!=1 || j!=7)
                       &&(i!=6 || j!=0) &&(i!=7 || j!=1) &&(i!=7 || j!=6) &&(i!=6 || j!=7))  {
                if(tentaJogada(i,j,2,false)){
                        this.repaint();
                        flag_jogada = true;
                        aux = true;
                        lastCase=false;
                         break;
                 }
                }
            }
           if(aux){
                break;
            }
       }
       
      if (lastCase==true){
           for(i=0;i<8;i++){
               for(j=0;j<8;j++){
                  if(tentaJogada(i,j,2,false)){
                        this.repaint();
                        flag_jogada = true;
                        aux = true;
                        lastCase=false;
                         break;
                   }
                
                }
               if(aux){
                    break;
                }
           }  
       }
               
       }
       verificaGanhou();
       atualizaInfo();
       flag_jogada = true;
      
}//fim jogaComputador      
    
  
    /* Metodo que verifica se jogada em um ponto e possivel, SEM alterar tabuleiro */
 public boolean verificaJogada(int x,int y,int cor_jogador,boolean refresh){
       
        boolean retorno=true,deu_jogada=false;
        ArrayList<Point> pontos = new ArrayList<Point>();//guarda coordenadas de celulas a terem cores atualizadas
        int cor_jog = cor_jogador;
        
        
        /* verifica se posicao ja tem peca */
        if(celulas[x][y]!=0 && !refresh){
            retorno = false;
        }else{
             /* cor da peca adversaria e do jogador */
             
        
            int pos_jog=-1, pos_adv;
            
            /***** verifica jogada HORIZONTAL DIREITA => ******/
            for(int k=x+1;k<8;k++){
                 
                  //pega posicao primeira peca de mesma cor
                  if(celulas[k][y]==cor_jog){
                      pos_jog = k;
                      break;
                  }
            }//fim for    
                 
            /* verificacoes de jogada */
            /* nao achou peca de mesma cor */
            if(pos_jog==-1){
                  retorno = false;
            }
            /* peca de mesma cor ao lado */
            else if((pos_jog-x)==1){
                  retorno = false;
            }
            
            /* verifica se tem peca vazia entre as pecas de mesma cor */
            for(int j=x+1;j<pos_jog;j++){
                if(celulas[j][y]==0){
                    retorno = false;
                }
                
            }
            
            if(retorno){
                for(int j=x+1;j<pos_jog;j++){
                     Point a = new Point(j,y);//celulas[j][y] = cor_jog;
                     Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                     pontos.add(a);
                     pontos.add(b);
                     retorno = true;
                     deu_jogada = true;
                }
            }
            
          
            /* FIM HORIZONTAL DIREITA => ******/
       
            /******** verifica jogada HORIZONTAL ESQUERDA <= */
               
                 retorno = true;
               
              
                  pos_jog=-1;
                  for(int k=x-1;k>=0;k--){
                 
                        //pega posicao primeira peca de mesma cor
                        if(celulas[k][y]==cor_jog){
                            pos_jog = k;
                            break;
                        }
                    }//fim for
            
                    /* verificacoes de jogada */
                    /* nao achou peca de mesma cor */
                    if(pos_jog==-1){
                        retorno = false;
                  
                    }
                    /* peca de mesma cor ao lado */
                    else if((x-pos_jog)==1){
                           retorno = false;
                   
                    }
            
                    /* verifica se tem peca vazia entre as pecas de mesma cor */
                    for(int j=x-1;j>pos_jog;j--){
                        if(celulas[j][y]==0){
                            retorno = false;
                    
                        }
                
                    }
            
                    if(retorno){
            
                        for(int j=x-1;j>pos_jog;j--){
                            Point a = new Point(j,y);//celulas[j][y] = cor_jog;
                            Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                            pontos.add(a);
                            pontos.add(b);
                            retorno = true;
                             deu_jogada = true;
                      }
                    }
            
            
        /* FIM HORIZONTAL ESQUERDA <= ******/
        
         /******** verifica jogada VERTICAL CIMA  */
        
        
            retorno = true;
               
                pos_jog=-1;
                  for(int k=y-1;k>=0;k--){
                 
                        //pega posicao primeira peca de mesma cor
                        if(celulas[x][k]==cor_jog){
                            pos_jog = k;
                            break;
                        }
                    }//fim for
            
                    /* verificacoes de jogada */
                    /* nao achou peca de mesma cor */
                    if(pos_jog==-1){
                        retorno = false;
                  
                    }
                    /* peca de mesma cor ao lado */
                    else if((y-pos_jog)==1){
                           retorno = false;
                   
                    }
            
                    /* verifica se tem peca vazia entre as pecas de mesma cor */
                    for(int j=y-1;j>pos_jog;j--){
                        if(celulas[x][j]==0){
                            retorno = false;
                    
                        }
                
                    }
            
                    if(retorno){
            
                        for(int j=y-1;j>pos_jog;j--){
                            Point a = new Point(x,j);//celulas[x][j] = cor_jog;
                            Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                            pontos.add(a);
                            pontos.add(b);
                            retorno = true;
                             deu_jogada = true;
                      }
                    }
            
        //fim de verificacao de VERTICAL CIMA
         
         /******* verificacao de VERTICAL BAIXO *******/
         
                retorno = true;
               
                pos_jog=-1;
                for(int k=y+1;k<8;k++){
                 
                        //pega posicao primeira peca de mesma cor
                        if(celulas[x][k]==cor_jog){
                            pos_jog = k;
                            break;
                        }
                    }//fim for
            
                    /* verificacoes de jogada */
                    /* nao achou peca de mesma cor */
                    if(pos_jog==-1){
                        retorno = false;
                  
                    }
                    /* peca de mesma cor ao lado */
                    else if((pos_jog-y)==1){
                           retorno = false;
                   
                    }
            
                    /* verifica se tem peca vazia entre as pecas de mesma cor */
                    for(int j=y+1;j<pos_jog;j++){
                        if(celulas[x][j]==0){
                            retorno = false;
                    
                        }
                
                    }
            
                    if(retorno){
            
                        for(int j=y+1;j<pos_jog;j++){
                            Point a = new Point(x,j);//celulas[x][j] = cor_jog;
                            Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                            pontos.add(a);
                            pontos.add(b);
                            retorno = true;
                             deu_jogada = true;
                      }
                    }
            
     //fim vrificacao de VERTICAL BAIXO
   
    
     /* verificacao DIAGONAL CIMA DIREITA*/
         
     int x_aux, y_aux;//var aux
     
       
     retorno = true;
               
     int pos_jogX=-1, pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
    for(int k=0;k<8;k++){
            x_aux = x_aux + 1;
            y_aux = y_aux - 1;
            
        if(x_aux<8 && y_aux>=0){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((pos_jogX-x_aux)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x+1;j<pos_jogX;j++){
             y_aux = y_aux -1;
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x+1;j<pos_jogX;j++){
                 y_aux = y_aux -1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL CIMA DIREITA
   
    
    
   /* verificacao DIAGONAL CIMA ESQUERDA*/
         
     
     retorno = true;
               
     pos_jogX=-1;
     pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
     
    for(int k=0;k<8;k++){
            x_aux = x_aux - 1;
            y_aux = y_aux - 1;
            
        if(x_aux>=0 && y_aux>=0){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((x_aux-pos_jogX)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x-1;j>pos_jogX;j--){
             y_aux = y_aux -1;
             
        if(y_aux>=0){
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
        }//fim if limite
        else{
            break;
        }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x-1;j>pos_jogX;j--){
                 y_aux = y_aux -1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL CIMA ESQUERDA
    
     /* verificacao DIAGONAL BAIXO DIREITA*/
         
     retorno = true;
               
     pos_jogX=-1; 
     pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
    for(int k=0;k<8;k++){
            x_aux = x_aux + 1;
            y_aux = y_aux + 1;
            
        if(x_aux<8 && y_aux<8){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((pos_jogX-x_aux)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x+1;j<pos_jogX;j++){
             y_aux = y_aux + 1;
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x+1;j<pos_jogX;j++){
                 y_aux = y_aux + 1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL BAIXO DIREITA
    
     /* verificacao DIAGONAL BAIXO ESQUERDA*/
         
     
     retorno = true;
               
     pos_jogX=-1;
     pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
     
    for(int k=0;k<8;k++){
            x_aux = x_aux - 1;
            y_aux = y_aux + 1;
            
        if(x_aux>=0 && y_aux<8){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((x_aux-pos_jogX)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x-1;j>pos_jogX;j--){
             y_aux = y_aux +1;
             
        if(y_aux<8){
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
        }//fim if limite
        else{
            break;
        }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x-1;j>pos_jogX;j--){
                 y_aux = y_aux + 1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL BAIXO ESQUERDA
    
    
    
   }//fim else de verificaco de FALTA DE PECA
    return deu_jogada;
    
 }
    
    
        
 /* Recebe posicao da matriz celulas e tenta jogar. Caso seja permitida, retorna true, senao false */
 public boolean tentaJogada(int x,int y,int cor_jogador,boolean refresh){
       
        boolean retorno=true,deu_jogada=false;
        ArrayList<Point> pontos = new ArrayList<Point>();//guarda coordenadas de celulas a terem cores atualizadas
        int cor_jog = cor_jogador;
        
        
        /* verifica se posicao ja tem peca */
        if(celulas[x][y]!=0 && !refresh){
            retorno = false;
        }else{
             /* cor da peca adversaria e do jogador */
             
        
            int pos_jog=-1, pos_adv;
            
            /***** verifica jogada HORIZONTAL DIREITA => ******/
            for(int k=x+1;k<8;k++){
                 
                  //pega posicao primeira peca de mesma cor
                  if(celulas[k][y]==cor_jog){
                      pos_jog = k;
                      break;
                  }
            }//fim for    
                 
            /* verificacoes de jogada */
            /* nao achou peca de mesma cor */
            if(pos_jog==-1){
                  retorno = false;
            }
            /* peca de mesma cor ao lado */
            else if((pos_jog-x)==1){
                  retorno = false;
            }
            
            /* verifica se tem peca vazia entre as pecas de mesma cor */
            for(int j=x+1;j<pos_jog;j++){
                if(celulas[j][y]==0){
                    retorno = false;
                }
                
            }
            
            if(retorno){
                for(int j=x+1;j<pos_jog;j++){
                     Point a = new Point(j,y);//celulas[j][y] = cor_jog;
                     Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                     pontos.add(a);
                     pontos.add(b);
                     retorno = true;
                     deu_jogada = true;
                }
            }
            
          
            /* FIM HORIZONTAL DIREITA => ******/
       
            /******** verifica jogada HORIZONTAL ESQUERDA <= */
               
                 retorno = true;
               
              
                  pos_jog=-1;
                  for(int k=x-1;k>=0;k--){
                 
                        //pega posicao primeira peca de mesma cor
                        if(celulas[k][y]==cor_jog){
                            pos_jog = k;
                            break;
                        }
                    }//fim for
            
                    /* verificacoes de jogada */
                    /* nao achou peca de mesma cor */
                    if(pos_jog==-1){
                        retorno = false;
                  
                    }
                    /* peca de mesma cor ao lado */
                    else if((x-pos_jog)==1){
                           retorno = false;
                   
                    }
            
                    /* verifica se tem peca vazia entre as pecas de mesma cor */
                    for(int j=x-1;j>pos_jog;j--){
                        if(celulas[j][y]==0){
                            retorno = false;
                    
                        }
                
                    }
            
                    if(retorno){
            
                        for(int j=x-1;j>pos_jog;j--){
                            Point a = new Point(j,y);//celulas[j][y] = cor_jog;
                            Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                            pontos.add(a);
                            pontos.add(b);
                            retorno = true;
                             deu_jogada = true;
                      }
                    }
            
            
        /* FIM HORIZONTAL ESQUERDA <= ******/
        
         /******** verifica jogada VERTICAL CIMA  */
        
        
            retorno = true;
               
                pos_jog=-1;
                  for(int k=y-1;k>=0;k--){
                 
                        //pega posicao primeira peca de mesma cor
                        if(celulas[x][k]==cor_jog){
                            pos_jog = k;
                            break;
                        }
                    }//fim for
            
                    /* verificacoes de jogada */
                    /* nao achou peca de mesma cor */
                    if(pos_jog==-1){
                        retorno = false;
                  
                    }
                    /* peca de mesma cor ao lado */
                    else if((y-pos_jog)==1){
                           retorno = false;
                   
                    }
            
                    /* verifica se tem peca vazia entre as pecas de mesma cor */
                    for(int j=y-1;j>pos_jog;j--){
                        if(celulas[x][j]==0){
                            retorno = false;
                    
                        }
                
                    }
            
                    if(retorno){
            
                        for(int j=y-1;j>pos_jog;j--){
                            Point a = new Point(x,j);//celulas[x][j] = cor_jog;
                            Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                            pontos.add(a);
                            pontos.add(b);
                            retorno = true;
                             deu_jogada = true;
                      }
                    }
            
        //fim de verificacao de VERTICAL CIMA
         
         /******* verificacao de VERTICAL BAIXO *******/
         
                retorno = true;
               
                pos_jog=-1;
                for(int k=y+1;k<8;k++){
                 
                        //pega posicao primeira peca de mesma cor
                        if(celulas[x][k]==cor_jog){
                            pos_jog = k;
                            break;
                        }
                    }//fim for
            
                    /* verificacoes de jogada */
                    /* nao achou peca de mesma cor */
                    if(pos_jog==-1){
                        retorno = false;
                  
                    }
                    /* peca de mesma cor ao lado */
                    else if((pos_jog-y)==1){
                           retorno = false;
                   
                    }
            
                    /* verifica se tem peca vazia entre as pecas de mesma cor */
                    for(int j=y+1;j<pos_jog;j++){
                        if(celulas[x][j]==0){
                            retorno = false;
                    
                        }
                
                    }
            
                    if(retorno){
            
                        for(int j=y+1;j<pos_jog;j++){
                            Point a = new Point(x,j);//celulas[x][j] = cor_jog;
                            Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                            pontos.add(a);
                            pontos.add(b);
                            retorno = true;
                             deu_jogada = true;
                      }
                    }
            
     //fim vrificacao de VERTICAL BAIXO
   
    
     /* verificacao DIAGONAL CIMA DIREITA*/
         
     int x_aux, y_aux;//var aux
     
       
     retorno = true;
               
     int pos_jogX=-1, pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
    for(int k=0;k<8;k++){
            x_aux = x_aux + 1;
            y_aux = y_aux - 1;
            
        if(x_aux<8 && y_aux>=0){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((pos_jogX-x_aux)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x+1;j<pos_jogX;j++){
             y_aux = y_aux -1;
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x+1;j<pos_jogX;j++){
                 y_aux = y_aux -1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL CIMA DIREITA
   
    
    
   /* verificacao DIAGONAL CIMA ESQUERDA*/
         
     
     retorno = true;
               
     pos_jogX=-1;
     pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
     
    for(int k=0;k<8;k++){
            x_aux = x_aux - 1;
            y_aux = y_aux - 1;
            
        if(x_aux>=0 && y_aux>=0){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((x_aux-pos_jogX)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x-1;j>pos_jogX;j--){
             y_aux = y_aux -1;
             
        if(y_aux>=0){
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
        }//fim if limite
        else{
            break;
        }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x-1;j>pos_jogX;j--){
                 y_aux = y_aux -1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL CIMA ESQUERDA
    
     /* verificacao DIAGONAL BAIXO DIREITA*/
         
     retorno = true;
               
     pos_jogX=-1; 
     pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
    for(int k=0;k<8;k++){
            x_aux = x_aux + 1;
            y_aux = y_aux + 1;
            
        if(x_aux<8 && y_aux<8){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((pos_jogX-x_aux)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x+1;j<pos_jogX;j++){
             y_aux = y_aux + 1;
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x+1;j<pos_jogX;j++){
                 y_aux = y_aux + 1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL BAIXO DIREITA
    
     /* verificacao DIAGONAL BAIXO ESQUERDA*/
         
     
     retorno = true;
               
     pos_jogX=-1;
     pos_jogY=-1;
     pos_jog=-1;
               
     x_aux = x;
     y_aux = y;
     
    for(int k=0;k<8;k++){
            x_aux = x_aux - 1;
            y_aux = y_aux + 1;
            
        if(x_aux>=0 && y_aux<8){
            //pega posicao primeira peca de mesma cor
            if(celulas[x_aux][y_aux]==cor_jog){
                   pos_jogX = x_aux;
                   pos_jogY = y_aux;
                   break;
            }
        }//fim if limites
        else{
            break;
        }
    }//fim for
            
    /* verificacoes de jogada */
    /* nao achou peca de mesma cor */
    if(pos_jogX==-1 || pos_jogY==-1){
           retorno = false;
                  
    }
    /* peca de mesma cor ao lado */
    else if((x_aux-pos_jogX)==1 && (y_aux-pos_jogY)==1){
            retorno = false;
                   
    }
                    
                        
    y_aux = y;
    /* verifica se tem peca vazia entre as pecas de mesma cor */
    for(int j=x-1;j>pos_jogX;j--){
             y_aux = y_aux +1;
             
        if(y_aux<8){
             if(celulas[j][y_aux]==0){
                    retorno = false;
                    
                    
            }
        }//fim if limite
        else{
            break;
        }
                
    }
                    
    y_aux = y;
    
    if(retorno){
            
        for(int j=x-1;j>pos_jogX;j--){
                 y_aux = y_aux + 1;
                Point a = new Point(j,y_aux);//celulas[j][y_aux] = cor_jog;
                Point b = new Point(x,y);//celulas[x][y] = cor_jog;
                pontos.add(a);
                pontos.add(b);
                retorno = true;
                deu_jogada = true;
        }
     }//fim de DIAGONAL BAIXO ESQUERDA
    
    
    
   }//fim else de verificaco de FALTA DE PECA
        
   /* atualiza cores */
    for(Point p : pontos){
        celulas[p.x][p.y] = cor_jog;
    }
    
  
        
    return deu_jogada;
    
  }
    
    
    /* Exibe janela de tabuleiro */
     private void mostraJanela(){
           janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setSize(820,660);
            janela.getContentPane().setBackground(Color.green);
            janela.setLayout(null);
            janela.setResizable(false);
            janela.setVisible(true);
       }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        jogaHumano(x,y);
       
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
   
    
}
