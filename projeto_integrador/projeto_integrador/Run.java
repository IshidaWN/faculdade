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
        float x, xZero, y, yZero, foca, tempo, angulo;
        x = entrada[0];
        xZero = entrada[1];
        y = entrada[2];
        yZero = entrada[3];
        foca = entrada[4];
        tempo = entrada[5];
        angulo = entrada[6];
        
        tempo = tempo + (1f / FPS);
        x =(float) (xZero + foca * Math.cos(angulo) * tempo);
        y =(float) (yZero + foca * Math.sin(angulo) * tempo - ((G * (tempo * tempo)) / 2));
        
        if (y <= 0 && tempo > 0) {
            foca =(float) (foca * 0.6);
            xZero = x;
            yZero = 1f;
            y = 0f;
            tempo = 0f;
            
        }
        float[] resultado = {x, xZero, y, yZero, foca, tempo, angulo};
        return resultado;
    }
    
    public static float anguloRad(float anguloGraus) {
        float angulo;
        angulo = (float) (anguloGraus * (3.14159265359 / 180));
        
        return angulo;
    }
    
    public void jogo() {
        
    	float calculos[] = {1, 1, 1, 1, 60, 1, 50};
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
        
        JLabel forca = wordLabel("Força");
        forca.setBounds(20, 10, 80, 20);
        JLabel angulo = wordLabel("Ângulo");
        angulo.setBounds(60, 10, 80, 20);
        JLabel num01 = wordLabel("0");
        JLabel num02 = wordLabel("0°");
        JLabel num100 = wordLabel("100");
        JLabel num90 = wordLabel("90°");
        JLabel velocidade = wordLabel("Velocidade = " + 0);
        num01.setBounds(10, 270, 10, 10);
        num02.setBounds(90, 270, 20, 10);
        num100.setBounds(10, 30, 30, 10);
        num90.setBounds(90, 30, 30, 10);
        velocidade.setBounds(10, 300, 280, 10);

        JPanel jogo = new JPanel(null);
        janela.add(button);
        jogo.add(bird);
        jogo.add(pig);
        jogo.add(forca);
        jogo.add(angulo);
        jogo.add(num01);
        jogo.add(num02);
        jogo.add(num100);
        jogo.add(num90);
        jogo.add(velocidade);
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
            
            jogo.setVisible(false);
            
            bird.setBounds(x, (y - 500) * (-1), 32, 32);
            ponteiroForca.setBounds(20, yPointer, 28, 7);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pig);
            jogo.add(forca);
            jogo.add(angulo);
            jogo.add(num01);
            jogo.add(num02);
            jogo.add(num100);
            jogo.add(num90);
            jogo.add(velocidade);
            jogo.add(ponteiroForca);
            jogo.add(ponteiroAngulo);
            jogo.add(barraForca);
            jogo.add(barraAngulo);
            jogo.add(cenario);
            jogo.validate();
            
            jogo.setVisible(true);
            
            sleep(7);
            
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
            		
            		jogo.setVisible(false);
                    
                    ponteiroAngulo.setBounds(60, yPointer, 28, 7);
                    jogo.removeAll();
                    jogo.add(bird);
                    jogo.add(pig);
                    jogo.add(forca);
                    jogo.add(angulo);
                    jogo.add(num01);
                    jogo.add(num02);
                    jogo.add(num100);
                    jogo.add(num90);
                    jogo.add(velocidade);
                    jogo.add(ponteiroForca);
                    jogo.add(ponteiroAngulo);
                    jogo.add(barraForca);
                    jogo.add(barraAngulo);
                    jogo.add(cenario);
                    jogo.validate();
                    
                    jogo.setVisible(true);
                    
                    sleep(7);
            	}
            	
                calculos[6] = (float) (((yPointer - 280) * (-1)) / 2.4f) * 0.9f;
                calculos[6] = anguloRad(calculos[6]);
                gameStart = true;
            }
        }
        
        while (gameStart == true) {
        	
        	jogo.setVisible(false);
            
            calculos = calcularMovimento(calculos);
            x = (int) (calculos[0]);
            y = (int) (calculos[2]);
            
            System.out.println("x = " + x + "  y = " + y + "  velocidade = " + calculos[4]);
            
            velocidade = wordLabel("Velocidade = " + (int) calculos[4]);
            velocidade.setBounds(10, 300, 280, 10);
                        
            bird.setBounds(x, (y - 500) * (-1), 32, 32);
            jogo.removeAll();
            jogo.add(bird);
            jogo.add(pig);
            jogo.add(forca);
            jogo.add(angulo);
            jogo.add(num01);
            jogo.add(num02);
            jogo.add(num100);
            jogo.add(num90);
            jogo.add(velocidade);
            jogo.add(ponteiroForca);
            jogo.add(ponteiroAngulo);
            jogo.add(barraForca);
            jogo.add(barraAngulo);
            jogo.add(cenario);
            jogo.validate();
            
            jogo.setVisible(true);
            
            if (calculos[4] < 1 || x > 850) {
                System.out.println("fim do jogo");
                JOptionPane.showMessageDialog(null, "ERROU!!!");
                gameStart = false;
            }
            
            if ((x >= 590 && y <= 59) && (x <= 650 && y >= 1)) {
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
    
    public JLabel wordLabel(String word) {
    	JLabel wLabel = new JLabel(word);
    	
    	return wLabel;
    }
    
    public void sleep(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
