import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;


public class BoardApplet extends JApplet implements ActionListener{
	int board[][];
	ArrayList<int[][]> answers;
	int solutionIndex;
	Button next, prev, recursiveSolution;
	Label solutinNo;
	int drawnBoard[][];
	Scanner scan;
	public void init()
	{
		scan = new Scanner( System.in);
		solutionIndex = 0;
		board = new int[8][8];
		
		answers = new ArrayList<int[][]>();
		queens(0,0,board,answers, 0, false);
		System.out.println( answers.size());
		for( int  i = 0; i < answers.size(); i++)
		{
			System.out.println("i: " + i);
			printAnswer(answers.get(i));
		}
		drawnBoard = answers.get(0);
		resize(700,400);
		int size = Math.min((getWidth() / 10), (getHeight()/10));		
		
		prev = new Button("Previous");
		prev.setBounds( size * 12, size * 2, 60, 25 );
		prev.addActionListener( this);
		
		next = new Button("Next");
		next.setBounds( size * 12 + (60 * 2), size * 2, 60, 25 );
		next.addActionListener( this);
		
		solutinNo = new Label("Solution Number: 1");
		solutinNo.setBackground( Color.white);
		solutinNo.setBounds(size * 11 + 45, size , 130, 25 );
		
		recursiveSolution = new Button("Recursive Solution");
		recursiveSolution.setBounds( (int)(size * 12) , size * 3, 150, 25 );
		recursiveSolution.addActionListener( this);

		this.add( next);
		this.add( prev);
		this.add( solutinNo);
		this.add( recursiveSolution);
		

		this.setLayout(null);
		this.getContentPane().setBackground( new Color( 255,255,255));
		this.setName("Board");
		this.setVisible(true);
		
		
	}
	
	public void paint( Graphics g)
	{
		
		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int size = Math.min((getWidth() / 10), (getHeight()/10));
		prev.setBounds( size * 12, size * 2, 60, 25 );
		next.setBounds( size * 12 + (int)(60 * 1.2), size * 2, 60, 25 );
		String s = "a";
		g.setFont( new Font( "Helvetica", 0, 20));
		for( int i = 0; i < 8; i++)
		{
			s = "" + (char)(97 + i);
			
			g.drawString(s, (int)( size * 1.4) + i  * size, (int)(size * 0.75));
			g.drawString(s, (int)( size * 1.4) + i  * size, (int)(size * 9.50));
		} 
		for( int i = 0; i < 8; i++)
		{
			s = "" + (8-i);
			g.drawString(s, (int)( size * 0.5), (int)(size * 1.65)+ i * size);
			g.drawString(s, (int)( size * 9.25), (int)(size * 1.65)+ i * size);
		}
			
		drawBoard(g, size);
	}

	
	private void drawBoard( Graphics g, int size) {
		// TODO Auto-generated method stub
		for( int i=0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				
				g.setColor( new Color( 200,200,200));
				
				if( (i + j)  % 2  == 0)
					g.fillRect(size + i  * size, size + j * size, size, size);
				else
				{
					g.setColor( Color.white);
					g.fillRect(size + i  * size, size + j * size, size, size);
				}
				g.setColor( new Color( 200,200,200));
				g.drawRect(size + i  * size , size + j * size, size, size);
				URL url = null;
				try {
					   // getDocumentbase gets the applet path.
					url = getDocumentBase();
				}catch (Exception e) {}
				if( drawnBoard[i][j] < 10 && drawnBoard[i][j] > 0)
				{
					g.setColor( Color.red);
					//g.fillOval((getWidth() / 10) + i  * (getWidth() / 10), (getHeight() / 10) + j * (getHeight()/10), (getWidth() / 10), (getHeight()/10));
					g.drawImage( getImage(url, "queenBlack.gif"), size + i  * size, size + j * size,size,size, this);
				}
				
				
			}
		}
	}

	public ArrayList<int[][]> queens( int row, int col, int[][] board, ArrayList<int[][]> answers, int iteration, boolean isDrawn )
	{
		/*if( isDrawn)
		{
			drawnBoard = board;
			repaint();
			scan.next();
		}*/
		if( row == 0 && col == 8) //finished
		{
			System.out.println( "iteration: " + iteration);
			return answers;
		}
			
		if( col == 8) //that row is finished go back and try again
		{
			
			int colOfQueen = getColOfQueen(board, row-1);
			
			deleteLevel( row, board);
			
			return queens(row - 1, colOfQueen + 1, board, answers, iteration + 1, isDrawn);
		}
		if( updateboard(row, col, board) == false) //cannot add in that col, try the next one
		{
			
			return queens(row, col + 1, board, answers, iteration + 1, isDrawn);
		}
		if( row == 7) // find an answer, time to update answers list
		{
			System.out.println( "iteration: " + iteration);
			//printAnswer(board);
			//addToAnswer(board, answers);
			int [][] newBoard = new int[board.length][board[0].length];
			for( int i = 0; i < board.length; i++){
				for( int j = 0; j < board[i].length; j++)
					newBoard[i][j] = board[i][j];
			}
			answers.add( newBoard);
			int colOfQueen = getColOfQueen(board, row - 1);
			deleteLevel(row, board);
			
			return queens( row -1 ,colOfQueen + 1, board, answers, iteration + 1, isDrawn);
		}
	
		return queens(row + 1, 0, board, answers, iteration + 1, isDrawn);
		
		
	}
	
	private static void printAnswer(int[][] board) {
		
		System.out.println();
		

		System.out.println();
		for(int  i = 0; i < board.length; i++)
		{
			System.out.print( (board.length - i) + "  ");
			for( int j = 0; j < board[i].length; j++)
			{
				if( board[i][j] < 10)
					System.out.print("X  ");
				else
					System.out.print( "0  ");
			}
			System.out.println();
		}
		System.out.print( "   ");
		for( int i = 0; i < board.length; i++)
			System.out.print( (char)( i + 97) + "  ");
		System.out.println("\n");
		
	}

	// updates the board by updating occupied squares according to the given queen
	public static boolean updateboard( int row, int col, int[][] board) 
	{
		
		if( board[row][col] > 0) //if that row col is occupied return false
			return false;
		board[row][col] = row + 1; // put level to the square which indicates where the queen is placed in that level
		for(int  i = 0; i < board.length; i++)
		{
			for( int j = 0; j < board[i].length; j++)
			{
				//updates all squares as occupied if the queen reaches that square
				if( (i == row && j != col) || ( j == col && i != row) ||
						(( col - j  > 0) && ( ( i ==  row - (col - j)) || ( i ==  row + (col - j)))  ) || 
						(( j - col > 0) && ( ( i == row + ( j - col)) || ( i == row - ( j - col)))) )
				{
					//we put level + 10 to these squares which indicates that this square is first occupied by that level
					if( board[i][j] == 0)
						board[i][j] = 10 + row + 1;
				}
				
			}
			
		}
		return true;
	}
	
	public static int getColOfQueen( int[][] board, int row)
	{
		
		for( int j = 0; j < board[row].length; j++)
		{
			if( board[row][j] == (row + 1))
				return j; //returns the location of the queen placed in row
		}
		return -1;
	}
	
	public static void deleteLevel( int level, int[][] board) // deletes all numbers >= level || numbers( more than 10) >= level + 10
	{
		
		for( int i = 0; i < board.length; i++)
		{
			for( int j = 0; j < board.length; j++)
			{
				// if level == 2, we delete all 1s and 2s and also 11s and 12s
				if( ((board[i][j] < 10) && (board[i][j] >= level)) 
						|| ((board[i][j] >= 10) && (board[i][j] >= level + 10)) )
						board[i][j] = 0;
			}
		}
	}
	
	public static void addToAnswer(int[][] board, ArrayList<int[][]> answers) //add to found answer to answers
	{
		/*String str = "";
		for(int  i = 0; i < board.length; i++)
		{
			
			for( int j = 0; j < board[i].length; j++)
			{
				if( ( board[i][j] > 0) && ( board[i][j] <= 10))
				{
					str += " " + (char)(i + 97) + (board.length - j);
				}

			}

		}*/
		//answers.add( str);
		
		
		
		
	}
	public static void printboard( int[][] board )
	{
		//prints the board
		System.out.println();
		System.out.print( "   ");

		System.out.println();
		for(int  i = 0; i < board.length; i++)
		{
			System.out.print( (board.length - i) + "  ");
			for( int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j]  + " ");
				if( board[i][j] < 10)
					System.out.print(" ");
			}
			System.out.println();
		}
		for( int i = 0; i < board.length; i++)
			System.out.print( (char)( i + 97) + "  ");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == next)
		{
			solutionIndex = (solutionIndex + 1) % answers.size();
			solutinNo.setText( "Solution Number: " + (solutionIndex + 1));
			drawnBoard = answers.get(solutionIndex);
			repaint();
		}
		else if( e.getSource() == prev)
		{
			solutionIndex = (solutionIndex + answers.size() - 1) % answers.size();
			solutinNo.setText( "Solution Number: " + (solutionIndex + 1));
			drawnBoard = answers.get(solutionIndex);
			repaint();
		}
		else if( e.getSource() == recursiveSolution)
		{
			board = new int[8][8];
			
			answers = new ArrayList<int[][]>();
			queens(0,0,board,answers, 0, true);
			
		}
		
	}
}


