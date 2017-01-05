
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;
import trab1cg.Reversi;
import trab1cg.TelaInicial;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wilton
 */
public class Principal {
        
       static Player player;
    
      public static void main(String [] args){
       Reversi novo = new Reversi();
       novo.novoJogo();
       Principal.tocaSomFundo();
        
     }
      
      public static void tocaSomFundo(){
             /* Som fundo */
       
       try{
             FileInputStream stream = new FileInputStream(new File("src/trab1cg/som_fundo.mp3").getCanonicalFile());
            BufferedInputStream buffer = new BufferedInputStream(stream);
            Principal.player = new Player (buffer);
            System.out.println("Executando...");
            player.play();
            
            System.out.println("Terminado");
       }catch(Exception e){
           System.out.println(e);
       }
       
      
    }
}
