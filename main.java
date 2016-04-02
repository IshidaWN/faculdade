package projeto_integrador;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

/*
PROJETO INTEGRADOR 

PARA FAZER:
IMPLEMENTAR ESTADOS DE JOGO
IMPLEMENTAR PLATAFORMA
IMPLEMENTAR ALVO
IMPLEMENTAR BARRA DE FORCA
IMPLEMENTAR TODO O RESTO
*/


public class main {
    
    public static float fps = 60;
    public static float calculos[] = {1, 30, 1, 30, 60, 1, 50};

    
    public static void main(String[] args) {

        main janela = new main();
        janela.cria_janela();
        
    }
    

    
    public static float[] calcular_movimento(float[] entrada) {
        float x, x_zero, y, y_zero, v, t, angulo;
        x = entrada[0];
        x_zero = entrada[1];
        y = entrada[2];
        y_zero = entrada[3];
        v = entrada[4];
        t = entrada[5];
        angulo = entrada[6];
        
        
        t = t + (1f / fps);
        x = (float) (x_zero + v * Math.cos(angulo) * t);
        y = (float) (y_zero + v * Math.sin(angulo) * t - ((9.81 * (t * t)) / 2));
        
        if (y <= 0 && t > 0) {
            v = (float) (v * 0.6);
            x_zero = x;
            y_zero = 1f;
            y = 0f;
            t = 0f;
            
        }
        float[] resultado = {x, x_zero, y, y_zero, v, t, angulo};
        return resultado;
    }
    
    public static float angulo_rad(float angulo_graus) {
        //converte o angulo de graus para radianos
        float angulo;
        angulo = (float) (angulo_graus * (3.14159265359 / 180));
        
        return angulo;
    }
    
    public void cria_janela() {
        
        float t;
        int x = 0, y = 0, y_pointer = 280;
        boolean sobe = true, game_start = false;
        calculos[6] = angulo_rad(calculos[6]);
        
        JFrame janela = new JFrame("nome da janela aqui");
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(janela.EXIT_ON_CLOSE);
        
        JLabel bird = red_bird();
        bird.setBounds(x, y, 32, 32);
        
        JLabel cenario = cenario();
        cenario.setBounds(0, 0, 800, 600);
        
        JLabel barra = barra();
        barra.setBounds(20, 40, 28, 240);
        
        JLabel pointer = pointer();
        pointer.setBounds(20, y_pointer, 28, 7);
        
        JLabel tela = tela_inicial();
        tela.setBounds(0, 0, 800, 600);
        
        JPanel jogo = new JPanel(null);
        jogo.add(bird);
        jogo.add(pointer);
        jogo.add(barra);
        jogo.add(cenario);
        jogo.add(tela);
        
        
        janela.add(jogo);
        janela.setVisible(true);
        
        while (game_start == false) {
            
            if (y_pointer <= 280 && sobe == true) {
                y_pointer--;
                if (y_pointer <= 40) {
                    sobe = false;
                }
            }
            else {
                y_pointer++;
                if (y_pointer >= 280) {
                    sobe = true;
                }
            }
            
            bird.setBounds(x, ((y - 500) * (-1)), 32, 32);
            pointer.setBounds(20, y_pointer, 28, 7);
            barra.setBounds(20, 40, 28, 240);
            cenario.setBounds(0, 0, 800, 600);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pointer);
            jogo.add(barra);
            jogo.add(cenario);
            jogo.validate();
            
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            
            }
            //ler o teclado aqui
       
        }
        
        while (game_start == true) {
            
            calculos = calcular_movimento(calculos);
            x = (int) (calculos[0]);
            y = (int) (calculos[2]);
            t = calculos[5];
            
            System.out.println("x = " + x + "  y = " + y + "  t = " + t);   //para debug
            
            
            bird.setBounds(x, ((y - 500) * (-1)), 32, 32);
            pointer.setBounds(20, y_pointer, 28, 7);
            barra.setBounds(20, 40, 28, 240);
            cenario.setBounds(0, 0, 800, 600);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pointer);
            jogo.add(barra);
            jogo.add(cenario);
            jogo.validate();
            
            if (calculos[4] < 5) {
                System.out.println("fim do jogo");
                System.exit(0);
            }
            
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            
            }
            
        }
        
        
    }
    
    public JLabel barra() {
        //carrega a imagem da barra
        URL url = getClass().getResource("bar.png");
        Icon icon = new ImageIcon(url);
        
        JLabel barra = new JLabel(icon);
        
        return barra;
    }
    
    public JLabel pointer() {
        //carrega aquela coisa que se-mexe
        URL url = getClass().getResource("pointer.png");
        Icon icon = new ImageIcon(url);
        
        JLabel pointer = new JLabel(icon);
        
        return pointer;
    }
    
    public JLabel tela_inicial() {
        //carrega a imagem da tela inicial
        URL url = getClass().getResource("tela.png");
        Icon icon = new ImageIcon(url);
        
        JLabel tela_inicial = new JLabel(icon);
        
        return tela_inicial;
    }
    
    public JLabel red_bird() {
        //carrga a foto do red bird
        URL url = getClass().getResource("angry-bird.png");
        Icon icon = new ImageIcon(url);
        
        JLabel bird = new JLabel(icon);
        
        return bird;
    }
    
    public JLabel cenario() {
        //carrega o fundo da janela
        URL url = getClass().getResource("Summer_Pignic_Background.png");
        Icon icon = new ImageIcon(url);
        
        JLabel cenario = new JLabel(icon);
        cenario.setBounds(0, 0, 800, 600);
        //cenario.setVisible(false);
        
        return cenario;
    }
    
}
