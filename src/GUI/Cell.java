package GUI;

import java.awt.Color;

import javax.swing.*;

public class Cell extends JPanel{
	
	JPanel panel;

	public Cell() {
		this.setSize(50, 50);
		this.setBackground(Color.black);
		this.setLayout(null);
		panel = new JPanel();
		panel.setBounds(1, 1, 49, 49);
		panel.setBackground(Color.lightGray);
		panel.setLayout(null);
		this.add(panel);
		this.validate();
	}
	
	public void createObstacle(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setBounds(5, 5, 39, 39);
		this.panel.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	public void createPad(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.red);
		panel.setBounds(10, 10, 29, 29);
		this.panel.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	public void createRock(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.darkGray);
		panel.setBounds(15, 15, 19, 19);
		this.panel.add(panel);
		this.revalidate();
		this.repaint();
	}
}
