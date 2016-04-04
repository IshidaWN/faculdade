package projeto_integrador;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

/*
PROJETO INTEGRADOR 
*/


public class main {
    
    public static float fps = 50;
    public static float calculos[] = {1, 30, 1, 30, 60, 1, 50};
    public static boolean b = false;

    
    public static void main(String[] args) {
                
        main janela = new main();
        
        while (true) {
            janela.cria_janela();
            
            calculos[0] = 1;
            calculos[1] = 30;
            calculos[2] = 1;
            calculos[3] = 30;
            calculos[4] = 60;
            calculos[5] = 1;
            calculos[6] = 50;
            b = false;
        }
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
        long tempo;
        boolean sobe = true, game_start = false;
        calculos[6] = angulo_rad(calculos[6]);
        
        JFrame janela = new JFrame("Projeto intgrador");
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(janela.EXIT_ON_CLOSE);
        
        button button_class = new button();
        JButton button = button_class.button();
        button.setEnabled(true);
        
        JLabel bird = red_bird();
        bird.setBounds(x, y, 32, 32);
        
        JLabel cenario = cenario();
        cenario.setBounds(0, 0, 800, 600);
        
        JLabel barra = barra();
        barra.setBounds(20, 40, 28, 240);
        
        JLabel pointer = pointer();
        pointer.setBounds(20, y_pointer, 28, 7);
        
        JPanel jogo = new JPanel(null);
        janela.add(button);
        jogo.add(bird);
        jogo.add(pointer);
        jogo.add(barra);
        jogo.add(cenario);
        
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
            
            sleep(10);
            
            if (b == true) {
                
                calculos[4] = (float) (((y_pointer - 280) * (-1)) / 2.4f);
                game_start = true;
            }
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
            
            if (calculos[4] < 1 || x > 850) {
                System.out.println("fim do jogo");
                JOptionPane.showMessageDialog(null, "Você é horrível, não acertou NADA!!!");
                sleep(1000);
                game_start = false;
            }
            sleep(1);
        }
        janela.setVisible(false);
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
        
        return cenario;
    }
    
    public void button_pressed() {
        b = true;
    }
    
    public void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

class ButtonHandler implements ActionListener {
	
    private JButton button;
	
    public ButtonHandler(JButton button){
	
        this.button = button;
    }
	
    @Override
    public void actionPerformed(ActionEvent evento) {
        
        if (evento.getSource() == button) {
            main main = new main();
            main.button_pressed();
        }
    }
}

class button{
    
    private JButton button = new JButton("");
    private ButtonHandler handler;

    public JButton button() {
        
        handler = new ButtonHandler(button);
        button.addActionListener(handler);
        return button;
    }
}
