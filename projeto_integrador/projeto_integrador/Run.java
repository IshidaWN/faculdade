package projeto_integrador;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
PROJETO INTEGRADOR 
*/

public class Run {
    
    public static final float FPS = 50f;
    public static final float G = 9.81f;
    public boolean keyPressed;
    
    public static void main(String[] args) {
                
        Run janela = new Run();
        
        while (true) {
            janela.jogo();
            
        }
    }
    
    public static float[] calcularMovimento(float[] entrada) {
        float x, xZero, y, yZero, velocidade, tempo, angulo;
        x = entrada[0];
        xZero = entrada[1];
        y = entrada[2];
        yZero = entrada[3];
        velocidade = entrada[4];
        tempo = entrada[5];
        angulo = entrada[6];
        
        tempo = tempo + (1f / FPS);
        x =(float) (xZero + velocidade * Math.cos(angulo) * tempo);
        y =(float) (yZero + velocidade * Math.sin(angulo) * tempo - ((G * (tempo * tempo)) / 2));
        
        if (y <= 0 && tempo > 0) {
            velocidade =(float) (velocidade * 0.6);
            xZero = x;
            yZero = 1f;
            y = 0f;
            tempo = 0f;
            
        }
        float[] resultado = {x, xZero, y, yZero, velocidade, tempo, angulo};
        return resultado;
    }
    
    public static float angulo_rad(float angulo_graus) {
        float angulo;
        angulo = (float) (angulo_graus * (3.14159265359 / 180));
        
        return angulo;
    }
    
    public void jogo() {
        
    	float calculos[] = {1, 1, 1, 1, 60, 1, 50};
        //boolean keyPressed = false;
        int x = 0, y = 0, yPointer = 280;
        boolean sobe = true, gameStart = false;
        keyPressed = false;
        
        JFrame janela = new JFrame("Projeto integrador");
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(janela.EXIT_ON_CLOSE);
        
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		if (keyPressed == true) {
        			keyPressed = false;
        		}
        		else {
        			keyPressed = true;
        		}
        	}
        });
        button.setEnabled(true);
        
        JLabel bird = carregaIcon("angry-bird.png");
        bird.setBounds(x, y, 32, 32);
        
        JLabel cenario = carregaIcon("Summer_Pignic_Background.png");
        cenario.setBounds(0, 0, 800, 600);
        
        JLabel barraForca = carregaIcon("bar.png");
        barraForca.setBounds(20, 40, 28, 240);
        
        JLabel barraAngulo = carregaIcon("bar.png");
        barraAngulo.setBounds(60, 40, 28, 240);
        
        JLabel ponteiroForca = carregaIcon("pointer.png");
        ponteiroForca.setBounds(20, yPointer, 28, 7);
        
        JLabel ponteiroAngulo = carregaIcon("pointer.png");
        ponteiroAngulo.setBounds(60, yPointer, 28, 7);
        
        JLabel pig = carregaIcon("pig.png");
        pig.setBounds(600, 460, 90, 90);

        JPanel jogo = new JPanel(null);
        janela.add(button);
        jogo.add(bird);
        jogo.add(pig);
        jogo.add(ponteiroForca);
        jogo.add(ponteiroAngulo);
        jogo.add(barraForca);
        jogo.add(barraAngulo);
        jogo.add(cenario);
        
        janela.add(jogo);
        janela.setVisible(true);
        
        while (gameStart == false) {
            
            if (yPointer <= 280 && sobe == true) {
                yPointer--;
                if (yPointer <= 40) {
                    sobe = false;
                }
            }
            else {
                yPointer++;
                if (yPointer >= 280) {
                    sobe = true;
                }
            }
            
            bird.setBounds(x, (y - 500) * (-1), 32, 32);
            ponteiroForca.setBounds(20, yPointer, 28, 7);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pig);
            jogo.add(ponteiroForca);
            jogo.add(ponteiroAngulo);
            jogo.add(barraForca);
            jogo.add(barraAngulo);
            jogo.add(cenario);
            jogo.validate();
            
            sleep(8);
            
            if (keyPressed == true) {
                
            	calculos[4] = (float) (((yPointer - 280) * (-1)) / 2.4f);
            	yPointer = 280;
            	sobe = true;
            	
            	while (keyPressed == true) {
            		if (yPointer <= 280 && sobe == true) {
                        yPointer--;
                        if (yPointer <= 40) {
                            sobe = false;
                        }
                    }
                    else {
                        yPointer++;
                        if (yPointer >= 280) {
                            sobe = true;
                        }
                    }
                    
                    ponteiroAngulo.setBounds(60, yPointer, 28, 7);
                    jogo.removeAll();
                    jogo.add(bird);
                    jogo.add(pig);
                    jogo.add(ponteiroForca);
                    jogo.add(ponteiroAngulo);
                    jogo.add(barraForca);
                    jogo.add(barraAngulo);
                    jogo.add(cenario);
                    jogo.validate();
                    
                    sleep(8);
            	}
            	
                calculos[6] = (float) (((yPointer - 280) * (-1)) / 2.4f) * 0.9f;
                calculos[6] = angulo_rad(calculos[6]);
                gameStart = true;
            }
        }
        
        while (gameStart == true) {
            
            calculos = calcularMovimento(calculos);
            x = (int) (calculos[0]);
            y = (int) (calculos[2]);
            
            System.out.println("x = " + x + "  y = " + y + "  velocidade = " + calculos[4]);
                        
            bird.setBounds(x, ((y - 500) * (-1)), 32, 32);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pig);
            jogo.add(ponteiroForca);
            jogo.add(ponteiroAngulo);
            jogo.add(barraForca);
            jogo.add(barraAngulo);
            jogo.add(cenario);
            jogo.validate();
            
            if (calculos[4] < 1 || x > 850) {
                System.out.println("fim do jogo");
                JOptionPane.showMessageDialog(null, "ERROU!!!");
                gameStart = false;
            }
            
            if ((x >= 590 && y <= 59) && (x <= 650 && y >= 1)) {
                jogo.removeAll();
                jogo.add(bird);
                jogo.add(pig);
                jogo.add(ponteiroForca);
                jogo.add(barraForca);
                jogo.add(cenario);
                jogo.validate();
                JOptionPane.showMessageDialog(null, "Acertou!!!");
                gameStart = false;
            }
            sleep(1);
        }
        janela.setVisible(false);
    }

    public JLabel carregaIcon(String nomeArquivo) {
        URL url = getClass().getResource(nomeArquivo);
        Icon icon = new ImageIcon(url);
        
        JLabel carregaIcon = new JLabel (icon);
        
        return carregaIcon;
    }
    
    public void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}