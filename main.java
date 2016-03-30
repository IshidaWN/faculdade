package projeto_integrador;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.net.URL;

/*
PROJETO INTEGRADOR 

PARA FAZER:
ARRUMAR TODA ESSA BAGUNCA
IMPLEMENTAR MENU
IMPLEMENTAR ESTADOS DE JOGO
IMPLEMENTAR PLATAFORMA
IMPLEMENTAR ALVO
IMPLEMENTAR BARRA DE FORCA
IMPLEMENTAR TODO O RESTO
*/


public class main {
    
    public static int fps = 60;
    public static float calculos[] = {1, 30, 1, 30, 60, 1, 45};
    public void keyTyped(KeyEvent e) {}

    
    
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
        
        
        t = t + (1 / fps);
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
        int x = 0, y = 0;
        calculos[6] = main.angulo_rad(calculos[6]);
        
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
        pointer.setBounds(20, 280, 28, 7);
        
        JLabel tela = tela_inicial();
        tela.setBounds(0, 0, 800, 600);
        
        JPanel jogo = new JPanel(null);
        jogo.add(tela);
        
        janela.add(jogo);
        janela.setVisible(true);
        
        while (true) {
            
            calculos = calcular_movimento(calculos);
            x = (int) (calculos[0]);
            y = (int) (calculos[2]);
            t = calculos[5];
            
            System.out.println("x = " + x + "  y = " + y + "  t = " + t);   //para debug
            
            
            bird.setBounds(x, ((y - 500) * (-1)), 32, 32);
            pointer.setBounds(20, 280, 28, 7);
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
            calculos[5] = calculos[5] + 0.01f;     //gambiarra de fps
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
    
    public static void testa_funcoes() {
        //essa função eu criei só pra testar os calculos
        //não precisa dela pro codigo funcionar
        //
        //mude o valor das variaveis de acordo com o que voçê precisar
        //é só chamar testa_funções no seu main pra ver os calculos funcionarem
        
        float x, x_zero, y, y_zero, v, t, angulo_graus;
        
        while (true) {
            
            x = 1;
            y = 1;          //só pra variavel não ficar vazia
            
            x_zero = 30;
            y_zero = 30;    //posição inicial do objeto a ser lançado
            
            v = 50;         //velocidade em m/s (ou a força se preferir)
            t = 11;          //tempo desde que o jogo começou (varia de > 0 a 14)
            angulo_graus = 50;      //o angulo de lançamento
            
            angulo_graus = angulo_rad(angulo_graus);
            
            float[] test = {x, x_zero, y, y_zero, v, t, angulo_graus};
            test = calcular_movimento(test);
            
            x = test[0];
            y = test[2];
            t = test[5];
            
            System.out.println("Resultado x = " + x);
            System.out.println("Resultado y = " + y);
            System.out.println("Resultado t = " + t);
            
            
            System.exit(0);
        }
    }
}

