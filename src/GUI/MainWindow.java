package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Deque;

import javax.swing.*;

import Grid.*;
import Search.GeneralSearch;
import Search.QueuingFunction;

public class MainWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	Grid grid;
	protected Deque<Grid> priview;
	private JButton bf;
	private JButton id;
	private JButton uc;
	private JButton greedy1;
	private JButton greedy2;
	private JButton aStar1;
	private JButton aStar2;
	private JButton next;
	private JButton df;
	private Cell rock;
	private Cell agent;
	private Cell teleport;
	private Cell pad;
	private JLabel padLabel;
	private JLabel teleportLabel;
	private JLabel agentLabel;
	private JLabel obsLabel;
	private Cell obs;
	private JLabel rockLabel;
	private JLabel path;
	
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
		
		obs = new Cell();
		obs.setBounds(20, 20, 50, 50);
		obs.createObstacle();
		obsLabel = new JLabel("Obs");
		obsLabel.setForeground(Color.white);
		obsLabel.setBounds(20, 80, 50, 30);
		
		pad = new Cell();
		pad.setBounds(90, 20, 50, 50);
		pad.createPad();
		padLabel = new JLabel("Pad");
		padLabel.setForeground(Color.white);
		padLabel.setBounds(90, 80, 50, 30);
		
		teleport = new Cell();
		teleport.setBounds(150, 20, 50, 50);
		teleport.createTeleport();
		teleportLabel = new JLabel("Tele");
		teleportLabel.setForeground(Color.white);
		teleportLabel.setBounds(150, 80, 50, 30);
		
		agent = new Cell();
		agent.setBounds(220, 20, 50, 50);
		agent.addAgent();
		agentLabel = new JLabel("Agent");
		agentLabel.setForeground(Color.white);
		agentLabel.setBounds(220, 80, 50, 30);
		
		rock = new Cell();
		rock.setBounds(290, 20, 50, 50);
		rock.addRock();
		rockLabel = new JLabel("Rock");
		rockLabel.setForeground(Color.white);
		rockLabel.setBounds(290, 80, 50, 30);
						
		df = new JButton("DF");
		df.setBounds(350, 100, 100, 30);
		df.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.DF, true);
			}
		});
		
		bf = new JButton("BF");
		bf.setBounds(350, 150, 100, 30);
		bf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.BF, true);
			}
		});
		
		id = new JButton("ID");
		id.setBounds(350, 200, 100, 30);
		id.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.ID, true);
			}
		});
		
		uc = new JButton("UC");
		uc.setBounds(350, 250, 100, 30);
		uc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.UC, true);
			}
		});
		
		greedy1 = new JButton("Greedy 1");
		greedy1.setBounds(350, 300, 100, 30);
		greedy1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.GR1, true);
			}
		});
		
		greedy2 = new JButton("Greedy 2");
		greedy2.setBounds(350, 350, 100, 30);
		greedy2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.GR2, true);
			}
		});
		
		aStar1 = new JButton("A* 1");
		aStar1.setBounds(350, 400, 100, 30);
		aStar1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.AS1, true);
			}
		});
		
		aStar2 = new JButton("A* 2");
		aStar2.setBounds(350, 450, 100, 30);	
		aStar2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				priview = GeneralSearch.search(grid, QueuingFunction.AS2, true);
			}
		});
		
		next = new JButton("Next");
		next.setBounds(500, 350, 100, 30);	
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				drawGrid(priview.pop());
				if(priview.isEmpty())
					next.setEnabled(false);
			}
		});
		
		path = new JLabel("Click on algorithm to get a solution");
		path.setBounds(50, 500, 550, 50);
		path.setForeground(Color.white);
		
		this.drawGrid(Grid.genGrid());

		mainPanel.add(path);
		this.validate();
		this.repaint();
		
	}
	
	public void drawGrid(Grid g){
		this.mainPanel.removeAll();
		this.grid = g;
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
		mainPanel.add(agentLabel);
		mainPanel.add(rockLabel);
		mainPanel.add(rock);
		mainPanel.add(agent);
		mainPanel.add(teleportLabel);
		mainPanel.add(teleport);
		mainPanel.add(padLabel);
		mainPanel.add(pad);
		mainPanel.add(obsLabel);
		mainPanel.add(obs);
		mainPanel.add(df);
		mainPanel.add(bf);
		mainPanel.add(id);
		mainPanel.add(uc);
		mainPanel.add(greedy1);
		mainPanel.add(greedy2);
		mainPanel.add(aStar1);
		mainPanel.add(aStar2);
		mainPanel.add(next);
		this.validate();
		this.repaint();
	}
	
	
	public static void main(String[] args) {
		new MainWindow();
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
