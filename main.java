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
        
        JLabel bird = carrega_icon("angry-bird.png");
        bird.setBounds(x, y, 32, 32);
        
        JLabel cenario = carrega_icon("Summer_Pignic_Background.png");
        cenario.setBounds(0, 0, 800, 600);
        
        JLabel barra = carrega_icon("bar.png");
        barra.setBounds(20, 40, 28, 240);
        
        JLabel pointer = carrega_icon("pointer.png");
        pointer.setBounds(20, y_pointer, 28, 7);
        
        JLabel pig = carrega_icon("pig.png");
        pig.setBounds(600, 460, 90, 90);

        JPanel jogo = new JPanel(null);
        janela.add(button);
        jogo.add(bird);
        jogo.add(pig);
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
            jogo.add(pig);
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
            
            System.out.println("x = " + x + "  y = " + y + "  t = " + t);
                        
            bird.setBounds(x, ((y - 500) * (-1)), 32, 32);
            pointer.setBounds(20, y_pointer, 28, 7);
            barra.setBounds(20, 40, 28, 240);
            cenario.setBounds(0, 0, 800, 600);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pig);
            jogo.add(pointer);
            jogo.add(barra);
            jogo.add(cenario);
            jogo.validate();
            
            if (calculos[4] < 1 || x > 850) {
                System.out.println("fim do jogo");
                JOptionPane.showMessageDialog(null, "ERROU!!!");
                game_start = false;
            }
            
            if ((x >= 590 && y <= 59) && (x <= 650 && y >= 1)) {
                jogo.removeAll();
                jogo.add(bird);
                jogo.add(pig);
                jogo.add(pointer);
                jogo.add(barra);
                jogo.add(cenario);
                jogo.validate();
                JOptionPane.showMessageDialog(null, "Acertou!!!");
                game_start = false;
            }
            sleep(1);
        }
        janela.setVisible(false);
    }

    public JLabel carrega_icon(String nome_arquivo) {
        URL url = getClass().getResource(nome_arquivo);
        Icon icon = new ImageIcon(url);
        
        JLabel carrega_icon = new JLabel (icon);
        
        return carrega_icon;
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
