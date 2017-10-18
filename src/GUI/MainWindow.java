package GUI;

import java.awt.Color;

import javax.swing.*;

import Grid.*;

public class MainWindow extends JFrame{
	
	JPanel mainPanel;
	Grid grid;
	
	public MainWindow(){
		
		this.setTitle("Help R2D2");
		this.setSize(800, 600);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
		this.mainPanel = new JPanel();
		mainPanel.setSize(800, 600);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.black);
		
		Cell obs = new Cell();
		obs.setBounds(20, 20, 50, 50);
		obs.createObstacle();
		mainPanel.add(obs);
		JLabel obsLabel = new JLabel("Obs");
		obsLabel.setForeground(Color.white);
		obsLabel.setBounds(20, 80, 50, 30);
		mainPanel.add(obsLabel);
		
		Cell pad = new Cell();
		pad.setBounds(90, 20, 50, 50);
		pad.createPad();
		mainPanel.add(pad);
		JLabel padLabel = new JLabel("Pad");
		padLabel.setForeground(Color.white);
		padLabel.setBounds(90, 80, 50, 30);
		mainPanel.add(padLabel);
		
		Cell teleport = new Cell();
		teleport.setBounds(150, 20, 50, 50);
		teleport.createTeleport();
		mainPanel.add(teleport);
		JLabel teleportLabel = new JLabel("Tele");
		teleportLabel.setForeground(Color.white);
		teleportLabel.setBounds(150, 80, 50, 30);
		mainPanel.add(teleportLabel);
		
		Cell agent = new Cell();
		agent.setBounds(220, 20, 50, 50);
		agent.addAgent();
		mainPanel.add(agent);
		JLabel agentLabel = new JLabel("Agent");
		agentLabel.setForeground(Color.white);
		agentLabel.setBounds(220, 80, 50, 30);
		mainPanel.add(agentLabel);
		
		Cell rock = new Cell();
		rock.setBounds(290, 20, 50, 50);
		rock.addRock();
		mainPanel.add(rock);
		JLabel rockLabel = new JLabel("Rock");
		rockLabel.setForeground(Color.white);
		rockLabel.setBounds(290, 80, 50, 30);
		mainPanel.add(rockLabel);
				
		grid = new Grid(3,3,1,1);
		
		for (int i = 0; i < grid.getWidth(); i++){
			for (int j = 0; j < grid.getHeight(); j++){
				Cell cell = new Cell();
				cell.setBounds(20+(i*50), 150+(j*50), 50, 50);
				switch (grid.getCells()[i][j].getStatus()) {
				case obstacle:
					cell.createObstacle();
					break;
				case pressurePad:
					cell.createPad();
				case teleport:
					cell.createTeleport();
				default:
					break;
				}
				
				if (grid.getCells()[i][j].getHasRock()){
					if (grid.getCells()[i][j].getStatus() == CellStatus.pressurePad){
						cell.addRockToPad();
					}
					else {
						if (grid.getCells()[i][j].getStatus() == CellStatus.teleport){
							cell.addRockToTeleport();
						}
						else {
							cell.addRock();
						}
					}
				}
				
				if (grid.getAgentPosition().getX() == i && grid.getAgentPosition().getY() == j){
					if (grid.getCells()[i][j].getStatus() == CellStatus.pressurePad){
						cell.addAgentToPad();
					}
					else {
						if (grid.getCells()[i][j].getStatus() == CellStatus.teleport){
							cell.addAgentTeleport();
						}
						else {
							cell.addAgent();
						}
					}
				}
				mainPanel.add(cell);
			}
		}
		
		this.add(mainPanel);
		this.validate();
		this.repaint();
		
	}
	
	public static void main(String[] args) {
		MainWindow w = new MainWindow();
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
