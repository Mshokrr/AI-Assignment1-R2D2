package GUI;

import java.awt.Color;

import javax.swing.*;

import Grid.CellStatus;
import Grid.Grid;

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
		
//		for (int i = 0; i < 7; i++){
//			for (int j = 0; j < 7; j++){
//				Cell cell = new Cell();
//				cell.createPad();
//				cell.createRock();
//				cell.setBounds(20+(i*50), 100+(j*50), 50, 50);
//				mainPanel.add(cell);
//			}
//		}
		
		grid = new Grid(5,3,1,1);
		
		for (int i = 0; i < grid.getWidth(); i++){
			for (int j = 0; j < grid.getHeight(); j++){
				Cell cell = new Cell();
				cell.setBounds(20+(i*50), 100+(j*50), 50, 50);
				if (grid.getCells()[i][j].getStatus() == CellStatus.obstacle){
					cell.createObstacle();
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
}
