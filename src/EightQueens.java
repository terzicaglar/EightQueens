import java.util.ArrayList;


public class EightQueens {
	public static void main( String[] args)
	{
		int board[][];
		board = new int[8][8];
		ArrayList<int[][]> answers = new ArrayList<int[][]>();
		queens(0,0,board,answers, 0);
		System.out.println( answers.size());
		for( int  i = 0; i < answers.size(); i++)
		{
			System.out.println("i: " + i);
			printAnswer(answers.get(i));
		}
		BoardApplet bf = new BoardApplet();
		bf.start();
		
	}
	
	public static ArrayList<int[][]> queens( int row, int col, int[][] board, ArrayList<int[][]> answers, int iteration )
	{
		
		if( row == 0 && col == 8) //finished
		{
			System.out.println( "iteration: " + iteration);
			return answers;
		}
			
		if( col == 8) //that row is finished go back and try again
		{
			
			int colOfQueen = getColOfQueen(board, row-1);
			
			deleteLevel( row, board);
			return queens(row - 1, colOfQueen + 1, board, answers, iteration + 1);
		}
		if( updateboard(row, col, board) == false) //cannot add in that col, try the next one
			return queens(row, col + 1, board, answers, iteration + 1);
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
			return queens( row -1 ,colOfQueen + 1, board, answers, iteration + 1);
		}
		return queens(row + 1, 0, board, answers, iteration + 1);
		
		
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
}
