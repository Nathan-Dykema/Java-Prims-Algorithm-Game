package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class Window extends JFrame implements ActionListener
{

	public static void main(String[] args) 
	{
		
		JFrame window = new JFrame ();
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setResizable(false);
		window.setTitle("LOST");
		
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setUpGame();//objects
		gamePanel.startGameThread();
		
		
		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}

}
