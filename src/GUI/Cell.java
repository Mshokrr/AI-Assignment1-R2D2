package GUI;

import java.awt.Color;

import javax.swing.*;

public class Cell extends JPanel{
	
	JPanel panel;
	JPanel obstacle;
	JPanel pad;
	JPanel rock;
	JPanel teleport;
	JLabel agent;

	public Cell() {
		this.setSize(50, 50);
		this.setBackground(Color.black);
		this.setLayout(null);
		panel = new JPanel();
		panel.setBounds(1, 1, 49, 49);
		panel.setBackground(Color.lightGray);
		panel.setLayout(null);
		
		obstacle = new JPanel();
		obstacle.setBackground(Color.black);
		obstacle.setBounds(5, 5, 39, 39);
		obstacle.setVisible(false);
		panel.add(obstacle);
		
		pad = new JPanel();
		pad.setBackground(Color.red);
		pad.setBounds(10, 10, 29, 29);
		pad.setVisible(false);
		panel.add(pad);
		
		rock = new JPanel();
		rock.setBackground(Color.darkGray);
		rock.setVisible(false);
		
		teleport = new JPanel();
		teleport.setBackground(Color.green);
		teleport.setBounds(10, 10, 29, 29);
		teleport.setVisible(false);
		panel.add(teleport);
		
		agent = new JLabel(new ImageIcon("r2d2.jpg"));
		agent.setBounds(15, 15, 20, 20);
		agent.setVisible(false);
		panel.add(agent);
		
		this.add(panel);
		this.validate();
	}
	
	public void createObstacle(){
		obstacle.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public void createPad(){
		pad.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public void addRockToCell(){	
		rock.setBounds(15, 15, 19, 19);
		rock.setVisible(true);
		this.panel.add(rock);
		this.revalidate();
		this.repaint();
	}
	
	public void addRockToPad(){
		rock.setBounds(35, 35, 19, 19);
		rock.setVisible(true);
		this.pad.add(rock);
		this.revalidate();
		this.repaint();
	}
	
	public void createTeleport(){
		teleport.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public void addAgent(){
		agent.setVisible(true);
		this.revalidate();
		this.repaint();
	}
}
