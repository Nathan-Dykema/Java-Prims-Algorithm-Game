package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;


public class TileManager 
{
	public static File fileObj = new File("MAPGEN.txt");

	GamePanel gp;
	public tile[] tile;
	
	public static int mapTileNumObjects[][];
	public static int mapTileNum[][];
	
	public static int playerStartX = 0;
	public static int playerStartY = 0;
	int playerSpawnCounter = 0;
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		
		tile = new tile[10]; //store types
		
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // will store the map (text numbers) into this array
		mapTileNumObjects = new int[gp.maxWorldCol][gp.maxWorldRow];
		
	
	
		try {                                                // creates text file
			
		 if (fileObj.createNewFile() || fileObj.exists()) 
	     {
	       
			 
			 for(int row = 0; row < mapTileNum.length; row++)     // writes the numbers to mapTileNum (  3  is nothingness  )
				{
					for(int col = 0; col < mapTileNum[col].length - 1; col++)
					{
						 mapTileNum[row][col] = 3;
					}
					
				}
			 
			 
			 
			 
			
			 
			mapTileNum = createMapNums(mapTileNum);
			
			fixMap(); //fixes small things after the map is made. (walls or obstructions to be deleted)
			
			setPlayerStartLocation(); //sets players starting location (should be top left most room)
			
			
			
			
			
			
			
			
			
			
			
			for(int row = 0; row < mapTileNum.length; row++)     // writes the numbers to the console to see
			{
				for(int col = 0; col < mapTileNum[col].length - 1; col++)
				{
					mapTileNumObjects[row][col] = mapTileNum[row][col]; //copies to this array
					
					
					if(mapTileNum[row][col] == 3)
					{
						System.out.print("  ");
					}
					else
					{
						System.out.print(mapTileNum[row][col] + " ");
					}
					
                    
				}
				System.out.println();
				
			}
			
			
			
			FileWriter inputFileWriter = new FileWriter(fileObj); 
			 
			String contents = "";
			
			for(int row = 0; row < mapTileNum.length; row++)     // writes the numbers to the text file
			{
				for(int col = 0; col < mapTileNum[col].length - 1; col++)
				{
					contents += mapTileNum[row][col] + " ";
				}
				
				inputFileWriter.write(contents);
				
				contents = "";
			}
			
			

			getTileImage();
			
			loadMap("/maps/MAPGEN.txt"); 
			
			
	    
	        
	        inputFileWriter.flush();
	        inputFileWriter.close();
	        
	        
	     
	     }
	    
	    }
	     catch(IOException e)
	    {
	    	
	    }
	
		
		
		
	}
	
	
	public void getTileImage()
	{
		
			setup(0, "tile_brick_1", false);
			
			setup(1, "tile_hole_1", true);
			
			setup(2, "tile_wall_1", true);
			
			setup(3, "OutOfBounds", true);
			
	}
	
	
	public void setup(int index, String imageName, boolean collision)
	{
		UtilityTool uTool = new UtilityTool();
		
		try
		{
			tile[index] = new tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.TileSize, gp.TileSize);
			tile[index].collision = collision;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	 public void loadMap(String filePath)
	 
	{
	try {
		
		
		WatchService watchService = FileSystems.getDefault().newWatchService();
		
		Path path = Paths.get(filePath);
	

      path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY); // trying to update the txt file

      WatchKey key;
      while ((key = watchService.take()) != null) 
      {
          for (WatchEvent<?> event : key.pollEvents())
          {
              System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
          }
          key.reset();
      }
		
		////////////////////////////
		                                                                                              // v reading file then placing image v
			InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) //read line of numbers
			{
				String line = reader.readLine();
				
				while(col < gp.maxWorldCol) // change into ints instead of Strings
				{
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num; //store into this
					col++;
				}
				if(col == gp.maxWorldCol)
				{
					col = 0;
					row++;
				}
			}
			reader.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	
	
	
	public void draw(Graphics2D g2)
	{
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
		{
			int tileNum = mapTileNum[worldCol][worldRow]; // position of a tile in txt
			
			int worldx = worldCol * gp.TileSize;
			int worldy = worldRow * gp.TileSize;
			int screenx = worldx - gp.player.worldx + gp.player.screenx; // place position of tile in correct spot
			int screeny = worldy - gp.player.worldy + gp.player.screeny;
			
			if(worldx + gp.TileSize > gp.player.worldx - gp.player.screenx &&  // better performance. doesn't display tiles if not on screen.
			   worldx - gp.TileSize < gp.player.worldx + gp.player.screenx &&
			   worldy + gp.TileSize > gp.player.worldy - gp.player.screeny &&
			   worldy - gp.TileSize < gp.player.worldy + gp.player.screeny)
			{
				g2.drawImage(tile[tileNum].image, screenx, screeny, null);
			}
			
			
			worldCol++; //draw next tile
			
			
			if(worldCol == gp.maxWorldCol) //draw each line of tiles
			{
				worldCol = 0;
				worldRow++;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	public static int[][] createMapNums(int[][] mapTileNum) // randomizes map
	{
		ArrayList<Integer> visitedNodesArray = new ArrayList<Integer>(); // every 2 are coordinates
		ArrayList<Integer> unvisitedNodesArray = new ArrayList<Integer>(); // every 2 are coordinates
		
	   
	
		
		Random ranNum1 = new Random(); // row
		Random ranNum2 = new Random(); // col
		
		int randomRow = 0;
		int randomCol = 0;

		boolean firstRandomNum = false;
		//Loops through all of the connections except the starting one and adds them to the unvisited array
		for (int i = 1; i < mapTileNum.length-1; i++) 
		{
			if(i % 25 == 0)
			{
				
				int previousRandomRow = randomRow;
				int previousRandomCol = randomCol;
				
				randomRow = ranNum1.nextInt(GamePanel.maxWorldRow - 30); //2 random numbers for a position in the mapTileNum array. will give 16 rooms, maxWorldRow x maxWorldCol is max size of array. 
				randomCol = ranNum2.nextInt(GamePanel.maxWorldCol - 30);
				
				if((previousRandomRow - randomRow) <= 30)
				{
					randomRow += 30;
				}
				if((previousRandomCol - randomCol) <= 30)
				{
					randomCol += 30;
				}
				
				
				if(randomRow < 20)
				{
					randomRow += 20;
				}
				if(randomCol < 20)
				{
					randomCol += 20;
				}
				
				if(firstRandomNum == false) // the first number to start with
				{
					visitedNodesArray.add(randomRow);
					visitedNodesArray.add(randomCol);
					firstRandomNum = true;
				}
				
				unvisitedNodesArray.add(randomRow);
				unvisitedNodesArray.add(randomCol); // every 2 are coordinates
				
			}
			
		}

		//Loops until all the nodes have been visited
		while (unvisitedNodesArray.size() != 0) 
		{
			int currentMinConnection = Integer.MAX_VALUE;
			int currentMinNodeUnvisitedRow = 0;
			int currentMinNodeUnvisitedCol = 0;
			
			
			
			int visitedNodeRowTEMP = 0; // has same value as visitedNodeRow, but used outside of for loop to avoid problems
			int visitedNodeColTEMP = 0; // has same value as visitedNodeCol, but used outside of for loop to avoid problems

			//Loops through all the visited nodes and checks all of their non-zero connections with the unvisited nodes until it finds the minimum
			for (int i = 0; i < visitedNodesArray.size(); i += 2) 
			{
				int visitedNodeRow = visitedNodesArray.get(i);
				int visitedNodeCol = visitedNodesArray.get(i+1); //get row and col from array
				
				visitedNodeRowTEMP = visitedNodeRow; // for later
				visitedNodeColTEMP = visitedNodeCol; // for later
				
				
				 
				  for(int j = 0; j < unvisitedNodesArray.size(); j += 2) // for every unvisitedNode (each 2 spots in the array)
				{
					  int unVisitedNodeRow = unvisitedNodesArray.get(j);
					  int unVisitedNodeCol = unvisitedNodesArray.get(j+1);
					  
					  int differenceRow = 0;
					  int differenceCol = 0;
					  int totalDifference = 0;
					  
					if(visitedNodeRow > unVisitedNodeRow && visitedNodeCol > unVisitedNodeCol || visitedNodeRow == unVisitedNodeRow && visitedNodeCol > unVisitedNodeCol || visitedNodeCol == unVisitedNodeCol && visitedNodeRow > unVisitedNodeRow) // (visited has bigger row & col)  or  (equal row  & bigger col)   or   (equal col & bigger row)                                       
					{
						differenceRow = visitedNodeRow - unVisitedNodeRow;
						differenceCol = visitedNodeCol - unVisitedNodeCol;
						totalDifference = differenceRow + differenceCol; //want to make connection to smallest totalDifference
					}
					else if(visitedNodeRow < unVisitedNodeRow && visitedNodeCol < unVisitedNodeCol || visitedNodeRow == unVisitedNodeRow && visitedNodeCol < unVisitedNodeCol || visitedNodeCol == unVisitedNodeCol && visitedNodeRow < unVisitedNodeRow) // (visited has smaller row & col)  or  (equal row  & smaller col)   or   (equal col & smaller row)                                      
					{
						differenceRow = unVisitedNodeRow - visitedNodeRow;
						differenceCol = unVisitedNodeCol - visitedNodeCol;
						totalDifference = differenceRow + differenceCol; //want to make connection to smallest totalDifference
					}
					
					if (totalDifference < currentMinConnection && totalDifference != 0 ) // find smallest connection
					{
						currentMinConnection = totalDifference;
						currentMinNodeUnvisitedRow = unVisitedNodeRow;
						currentMinNodeUnvisitedCol = unVisitedNodeCol;
					
						
					}
				}
			}

			//Adds the node with the minimum connection to the visited nodes and removes it from the unvisited nodes
			visitedNodesArray.add(currentMinNodeUnvisitedRow);
			visitedNodesArray.add(currentMinNodeUnvisitedCol);
			
			int roomStartRow = currentMinNodeUnvisitedRow; // the first node that the room will start being made at
			int roomStartCol = currentMinNodeUnvisitedCol;
	
			unvisitedNodesArray.remove(unvisitedNodesArray.indexOf(currentMinNodeUnvisitedRow));
			unvisitedNodesArray.remove(unvisitedNodesArray.indexOf(currentMinNodeUnvisitedCol));
			
		
			
			//visitedNodeRowTEMP , visitedNodeColTEMP
			//currentMinNodeUnvisitedRow, currentMinNodeUnvisitedCol
			
			
			int row = visitedNodeRowTEMP; // give simpler name     //    row / col is the start
			int col = visitedNodeColTEMP;
			
			
			
			int length = 25; // room size
			int width = 24;
			// all from visited to the current minimum length node
			if(visitedNodeRowTEMP > currentMinNodeUnvisitedRow && visitedNodeColTEMP > currentMinNodeUnvisitedCol) // will go LEFT then UP from visited
			{


				makeRoom(length,width,roomStartRow,roomStartCol);
				
				
				while(col != currentMinNodeUnvisitedCol) // making a hallway
				{
					
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 2;   
					col--;
					visitedNodeColTEMP--;
				}
				
				if(col == currentMinNodeUnvisitedCol) // making corner
				{
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 2;   
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP - 1] = 2;
				}
				boolean first = true;
				while(row != currentMinNodeUnvisitedRow)     // making a hallway
				{ 
					if(first == true)
					{
						mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 0;
					}
					else
					{
						mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 2;
					}
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP - 1] = 2; 
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
	        		 
					row--;
					visitedNodeRowTEMP--;
					first = false;
				}
				/////////////////////////////////////////////////////////////////////////////////////////////////////
				
			}
			
			
			if(visitedNodeRowTEMP < currentMinNodeUnvisitedRow && visitedNodeColTEMP > currentMinNodeUnvisitedCol) // will go LEFT then DOWN from visited
			{
				 
				makeRoom(length,width,roomStartRow,roomStartCol);
				
				while(col != currentMinNodeUnvisitedCol) // making a hallway
				{
					
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 2;   
					col--;
					visitedNodeColTEMP--;
				}
				
				if(col == currentMinNodeUnvisitedCol) // making corner
				{
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 2;   
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP - 1] = 2;
				}
				boolean first = true;
				while(row != currentMinNodeUnvisitedRow)     // making a hallway
				{ 
					if(first == true)
					{
						mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 0;
					}
					else
					{
						mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 2;
					}
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP - 1] = 2; 
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
	        		 
					row++;
					visitedNodeRowTEMP++;
					first = false;
				}
				/////////////////////////////////////////////////////////////////////////////////////////////////////
				
			}
			
			
			if(visitedNodeRowTEMP == currentMinNodeUnvisitedRow && visitedNodeColTEMP > currentMinNodeUnvisitedCol) // will go just LEFT
			{

				makeRoom(length,width,roomStartRow,roomStartCol);
				
				while(col != currentMinNodeUnvisitedCol)  // making a hallway
				{
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 2; 
					col--;
					visitedNodeColTEMP--;
				}
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if(visitedNodeColTEMP == currentMinNodeUnvisitedCol && visitedNodeRowTEMP > currentMinNodeUnvisitedRow) // will go just UP
			{

				makeRoom(length,width,roomStartRow,roomStartCol);
				
				
				
				while(row != currentMinNodeUnvisitedRow) // making a hallway
				{
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP - 1] = 2;
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP + 1] = 0;
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP + 1] = 2; 
					row--;
					visitedNodeRowTEMP--;
				}
			}
			                                                                                         //opposite if statements
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if(visitedNodeRowTEMP < currentMinNodeUnvisitedRow && visitedNodeColTEMP < currentMinNodeUnvisitedCol) // will go RIGHT then DOWN from visited
			{
				

					makeRoom(length,width,roomStartRow,roomStartCol);
				
				while(col != currentMinNodeUnvisitedCol)  // making a hallway
				{
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 2; 
					col++;
					visitedNodeColTEMP++;
				}
				
				if(col == currentMinNodeUnvisitedCol) // making corner
				{
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP - 1] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP + 1] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2 - 1][visitedNodeColTEMP - 1] = 2; 
				}
				
				boolean first = true; 
				while(row != currentMinNodeUnvisitedRow)   // making a hallway
				{
					
					if(first == true)
					{
						mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 0;
					}
					else
					{
						mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP - 1] = 2;
					}
					
					
					
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP + 1] = 2; 
					
					row++;
					visitedNodeRowTEMP++;
					first = false;
				}
				/////////////////////////////////////////////////////////////////////////////////////////////////////
				
			}
			
			if(visitedNodeRowTEMP == currentMinNodeUnvisitedRow && visitedNodeColTEMP < currentMinNodeUnvisitedCol) // will go just RIGHT
			{
				makeRoom(length,width,roomStartRow,roomStartCol);
				
				while(col != currentMinNodeUnvisitedCol) // making a hallway
				{
					mapTileNum[visitedNodeRowTEMP - length/2][visitedNodeColTEMP] = 2;
					mapTileNum[visitedNodeRowTEMP - length/2 + 1][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - length/2 + 2][visitedNodeColTEMP] = 2; 
					col++;
					visitedNodeColTEMP++;
				}
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if(visitedNodeColTEMP == currentMinNodeUnvisitedCol && visitedNodeRowTEMP < currentMinNodeUnvisitedRow) // will go just DOWN
			{
				while(row != currentMinNodeUnvisitedRow) // making a hallway
				{
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP - 1] = 2; 
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - width/2 + 1][visitedNodeColTEMP] = 0;
					mapTileNum[visitedNodeRowTEMP - width/2][visitedNodeColTEMP + 1] = 2; 
					
					row++;
					visitedNodeRowTEMP++;
				}
			}
			
			
		}
		
		return mapTileNum;
	}
	
	public static void makeRoom(int length, int width, int roomStartRow, int roomStartCol)
	{
		int leftCol = roomStartCol - (width / 2); // col
		int topRow = roomStartRow - length;  // row top
		int bottomRow = roomStartRow;  // row bottom (aka roomStartRow)
		
		if(bottomRow < 0)
		{
			bottomRow = 20;
		}

		if(topRow < 0)
		{
			topRow = 20;
		}
		
		
		
		for(int i = 0; i <= length; i++)
		{
			
			
			if(i == 0 || i == length) // for bottom wall and top wall
			{
				for(int temp = 0; temp <= width ; temp++)
				{
					if(i == 0)
					{
						mapTileNum[bottomRow][leftCol + temp] = 2;
					}
			        else if(i == length)
					{
				   		mapTileNum[topRow][leftCol + temp] = 2;
					}
					
				}
				
				mapTileNum[roomStartRow][roomStartCol] = 0;
			}
			else
			{
				if(leftCol < 0)
				{
					leftCol = 20;
				}
				
				for(int temp = 0; temp <= width ; temp++) // for left to right of room 
				{
					if(temp == 0)
					{
						mapTileNum[bottomRow - i][leftCol] = 2;
					}
					else if(temp == width)
					{
						mapTileNum[bottomRow - i][leftCol + width] = 2;
					}
					else
					{
						mapTileNum[bottomRow - i][leftCol + temp] = 0;
					}
					
				}
				
			}

			
		}
	}

	public static void fixMap()
	{
		
		for(int row = 5; row < mapTileNum.length - 5; row++)    
		{
			for(int col = 5; col < mapTileNum[col].length - 5; col++)
			{
				if(mapTileNum[row][col] == 2 && mapTileNum[row][col-1] == 0 && mapTileNum[row][col+1] == 0)
				{
					mapTileNum[row][col] = 0;
				}
				if(mapTileNum[row][col] == 2 && mapTileNum[row-1][col] == 0 && mapTileNum[row+1][col] == 0)
				{
					mapTileNum[row][col] = 0;
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row-1][col] == 3)
				{
					mapTileNum[row-1][col] = 2;
				}
				
				if(mapTileNum[row][col] == 0 && mapTileNum[row][col-1] == 2 && mapTileNum[row][col+1] == 2 && mapTileNum[row+1][col] == 2 && mapTileNum[row+1][col+1] == 0) //down hall fix
				{
					mapTileNum[row+1][col-1] = 2;
					mapTileNum[row+2][col-1] = 2;
					mapTileNum[row+1][col] = 0;
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row][col-1] == 2 && mapTileNum[row][col+1] == 2 && mapTileNum[row+1][col] == 2 && mapTileNum[row+1][col+1] == 0)
				{
					mapTileNum[row+1][col-1] = 2;
					mapTileNum[row+2][col-1] = 2;
					mapTileNum[row+1][col] = 0;
				}
				
				
				if(mapTileNum[row][col] == 0 && mapTileNum[row+1][col] == 3) //going DOWN
				{
					boolean anythingThere = true;
					
					for(int Row = row; Row < mapTileNum.length; Row++) //check if there even is a room down there to not waste time on a long hallway to nothing
					{
						if(mapTileNum[Row][col] == 2)
						{
							anythingThere = true;
							break;
						}
						if(Row > mapTileNum.length - 30)
						{
							anythingThere = false;
							break;
						}
					}
					
					
					for(int Row = row; Row < mapTileNum.length-1; Row++)     
					{
						if(anythingThere == false)
						{
							mapTileNum[Row][col] = 2;
							break;
						}
						if(mapTileNum[Row][col] == 2)
						{
							mapTileNum[Row][col] = 0;
							mapTileNum[Row][col-1] = 2;
							mapTileNum[Row][col+1] = 2;
							break;
						}
						else
						{
							mapTileNum[Row][col] = 0;
							mapTileNum[Row][col-1] = 2;
							mapTileNum[Row][col+1] = 2;
						}
						
					}
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row-1][col] == 3) //going UP
				{
					boolean anythingThere = true;

					
					for(int Row = row; Row < mapTileNum.length-1; Row--) //check if there even is a room down there to not waste time on a long hallway to nothing
					{
						
						
						if(mapTileNum[Row][col] == 2)
						{
							anythingThere = true;
							break;
						}
						if(Row < 30)
						{
							anythingThere = false;
							break;
						}
					}
					
					for(int Row = row; Row < mapTileNum.length-1; Row--)     
					{
						if(anythingThere == false)
						{
							mapTileNum[Row][col] = 2;
							break;
						}
						
						if(mapTileNum[Row][col] == 2)
						{
							mapTileNum[Row][col] = 0;
							mapTileNum[Row][col-1] = 2;
							mapTileNum[Row][col+1] = 2;
							break;
						}
						else
						{
							mapTileNum[Row][col] = 0;
							mapTileNum[Row][col-1] = 2;
							mapTileNum[Row][col+1] = 2;
						}
						if(Row < 30)
						{
							mapTileNum[Row][col] = 2;
							break;
						}
					}
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row][col-1] == 3) //going LEFT
				{
					boolean anythingThere = true;

					for(int Col = col; Col < mapTileNum[Col].length-1; Col--) //check if there even is a room down there to not waste time on a long hallway to nothing
					{
						if(mapTileNum[row][Col] == 2)
						{
							anythingThere = true;
							break;
						}
						if(Col < 30)
						{
							anythingThere = false;
							break;
						}
					}
					
					for(int Col = col; Col < mapTileNum[Col].length-1; Col--)     
					{
						if(anythingThere == false)
						{
							mapTileNum[row][col] = 2;
							break;
						}
						
						if(mapTileNum[row][Col] == 2)
						{
							mapTileNum[row][Col] = 0;
							mapTileNum[row-1][Col] = 2;
							mapTileNum[row+1][Col] = 2;
							break;
						}
						else
						{
							mapTileNum[row][Col] = 0;
							mapTileNum[row-1][Col] = 2;
							mapTileNum[row+1][Col] = 2;
						}
						if(row < 30)
						{
							mapTileNum[row][Col] = 2;
							break;
						}
					}
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row][col+1] == 3) //going RIGHT
				{
					boolean anythingThere = true;

					for(int Col = col; Col < mapTileNum[Col].length-1; Col++) //check if there even is a room down there to not waste time on a long hallway to nothing
					{
						if(mapTileNum[row][Col] == 2)
						{
							anythingThere = true;
							break;
						}
						if(Col > mapTileNum[Col].length - 30)
						{
							anythingThere = false;
							break;
						}
					}
					
					for(int Col = col; Col < mapTileNum[Col].length-1; Col++)     
					{
						if(anythingThere == false)
						{
							mapTileNum[row][col] = 2;
							break;
						}
						
						if(mapTileNum[row][Col] == 2)
						{
							mapTileNum[row][Col] = 0;
							mapTileNum[row-1][Col] = 2;
							mapTileNum[row+1][Col] = 2;
							break;
						}
						else
						{
							mapTileNum[row][Col] = 0;
							mapTileNum[row-1][Col] = 2;
							mapTileNum[row+1][Col] = 2;
						}
						if(row < 30)
						{
							mapTileNum[row][Col] = 2;
							break;
						}
					}
					
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row+1][col] == 3 && mapTileNum[row+1][col-1] == 3 && mapTileNum[row+1][col+1] == 3     ||     mapTileNum[row][col] == 0 && mapTileNum[row+1][col] == 2 && mapTileNum[row+1][col-1] == 3 && mapTileNum[row+1][col+1] == 3)
				{
					mapTileNum[row][col] = 2;
					mapTileNum[row+1][col] = 3;
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row-1][col] == 3 && mapTileNum[row-1][col-1] == 3 && mapTileNum[row-1][col+1] == 3     ||     mapTileNum[row][col] == 0 && mapTileNum[row-1][col] == 2 && mapTileNum[row-1][col-1] == 3 && mapTileNum[row-1][col+1] == 3)
				{
					mapTileNum[row][col] = 2;
					mapTileNum[row-1][col] = 3;
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row][col-1] == 3 && mapTileNum[row+1][col-1] == 3 && mapTileNum[row-1][col-1] == 3     ||     mapTileNum[row][col] == 0 && mapTileNum[row][col-1] == 2 && mapTileNum[row+1][col-1] == 3 && mapTileNum[row-1][col-1] == 3)
				{
					mapTileNum[row][col] = 2;
					mapTileNum[row][col-1] = 3;
				}
				if(mapTileNum[row][col] == 0 && mapTileNum[row][col+1] == 3 && mapTileNum[row+1][col+1] == 3 && mapTileNum[row+1][col-1] == 3     ||     mapTileNum[row][col] == 0 && mapTileNum[row][col+1] == 2 && mapTileNum[row+1][col+1] == 3 && mapTileNum[row+1][col-1] == 3)
				{
					mapTileNum[row][col] = 2;
					mapTileNum[row][col+1] = 3;
				}
				if(mapTileNum[row][col] == 2 && mapTileNum[row][col-1] == 0 && mapTileNum[row][col+1] == 0)
				{
					mapTileNum[row][col] = 0;
				}
				if(mapTileNum[row][col] == 2 && mapTileNum[row][col-1] == 2 && mapTileNum[row][col-2] == 0 && mapTileNum[row][col+1] == 0 || mapTileNum[row][col] == 2 && mapTileNum[row][col+1] == 2 && mapTileNum[row][col-1] == 0 && mapTileNum[row][col+2] == 0)
				{
					mapTileNum[row][col] = 0;
				}
				
                
			}
		
		}
		
		
	}

	public void setPlayerStartLocation()
	{
		if(playerSpawnCounter == 0)
		{
			for(int row = 5; row < mapTileNum.length - 2; row++)    
			{
				for(int col = 5; col < mapTileNum[col].length - 2; col++)
				{
					if( mapTileNum[row-1][col] == 2 && mapTileNum[row-1][col+1] == 2 && mapTileNum[row-1][col+2] == 2 &&
					   mapTileNum[row][col] == 0 && mapTileNum[row][col+1] == 0 && mapTileNum[row][col+2] == 0 &&
					   mapTileNum[row+1][col] == 0 && mapTileNum[row+1][col+1] == 0 && mapTileNum[row+1][col+2] == 0 &&	
					   mapTileNum[row+2][col] == 0 && mapTileNum[row+2][col+1] == 0 && mapTileNum[row+2][col+2] == 0)
					{
						playerStartX = (row+2) * gp.TileSize;
						playerStartY = (col+2) * gp.TileSize;
						
						playerSpawnCounter = 1;

						break;
					}
					
				}
				if(playerSpawnCounter == 1)
				{
					break;
				}
				
			}
			
		}
		
		
		
		
		
	}
}


