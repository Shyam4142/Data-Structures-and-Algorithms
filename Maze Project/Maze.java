// SortingAlgorithms Source Code
// Name: Shyam Venkatesan
/* Implements a random maze of NxM user-input size, with walls randomly knocked down until a path from entrance to exit is found.
   Then, the path from entrance to exit is found by a BFS algorithm with backtracking, and displayed.
   The maze is displayed as terminal output, and if the user chooses, the path is traced through it.
*/

// Imports the Java util package for scanner, random, etc.
import java.util.*;

// The Maze class
public class Maze 
{
    // Instance variable maze is a 2D array of Cell type that stores the maze cells
    private Cell[][] maze;
    // n and m are the dimensions of the 2D array
    int n, m;
    
    // The Cell nested class is used for the maze cell objects
    class Cell
    {
        // Booleans to represent north, south, east, west walls
        boolean north;
        boolean south;
        boolean east;
        boolean west;
        
        // The cell's 1D value in the array
        int num;
        // Row and column of the cell in the 2D array
        int row;
        int col;
        
        // Cell overloaded constructor takes in num, row, and col integer inputs
        Cell(int num, int row, int col)
        {
            // All directions set to true (wall exists)
            north = south = east = west = true;
            
            // Integer variables set to argument values
            this.num = num;
            this.row = row;
            this.col = col;
        }
    }
    
    class DisjointSet
    {
        // The set integer array stores the DisjointSet
        int[] set;
        
        // DisjointSet overloaded constructor takes in a size integer
        public DisjointSet(int size)
        {
            // Set initialized with integer array of length size
            set = new int[size];
            // Iterates through size and sets the value of each element to current i value
            for(int i = 0; i < size; i++)
            {
                set[i] = i;
            }
        }
        
        // The find method take in an integer input, i and returns an integer
        public int find(int i)
        {
            // If element's value is not i, recursively calls find on set[i] to search for it
            if(set[i] != i)
                set[i] = find(set[i]);
            // Returns set[i] to show it as being found in the set
            return set[i];
        }
        
        // The void union method takes in 2 integer inputs, i and j
        public void union(int i, int j)
        {
            // rootI and rootJ store the root node value for i and j's set
            int rootI = find(i);
            int rootJ = find(j);
            
            // If i and j aren't already in the same set
            if(rootI != rootJ)
            {
                // Root of i given the value of j's root, which merges the 2 sets
                set[rootI] = rootJ;
            }
        }
    }
    
    // Maze overloaded constructor takes integer input, n and m
    Maze(int n, int m)
    {
        // Dimensions set to input values
        this.n = n;
        this.m = m;
        
        // maze array initialized with 2D NxM Cell array
        maze = new Cell[n][m];
        
        // Counter for cell number
        int num = 0;
        // Iterates through rows and columns
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                // maze[i][j] initialized with a Cell object with num input
                maze[i][j] = new Cell(num, i, j);
                // num increments
                num++;
            }
        }
        // The western wall of the entrance is set to false (should be open)
        maze[0][0].west = false;
        // The eastern wall of the exit is set to false (should be open)
        maze[n - 1][m - 1].east = false; 
        
        // Calls the generateMaze helper method
        generateMaze();
    }
    
    // The generateMaze helper method 
    private void generateMaze()
    {
        // Creates a DisjointSet object with size NxM
        DisjointSet ds = new DisjointSet(n * m);
        
        // Creates a Random object, rand
        Random rand = new Random();
        // Loops as long as entrance and exit cells are not in the same set (not connected)
        while(ds.find(0) != ds.find(n * m - 1))
        {
                // Uses rand to find a cell to be used for wall knockdown
                int randomWall = rand.nextInt(n * m - 1);
                // Calculates row and col values for the cell
                int row = randomWall / m;
                int col = randomWall % m;

                // ArrayList of type Cell, neighbors, holds a variable amount of neighbors
                ArrayList <Cell> neighbors = new ArrayList<>();
                // if conditions check the possible neighbor types (above, below, left, right) and adds them to neighbors
                if(row > 0)
                    neighbors.add(maze[row - 1][col]);
                if(row < n - 1)
                    neighbors.add(maze[row + 1][col]);
                if(col > 0)
                    neighbors.add(maze[row][col - 1]);
                if(col < m - 1)
                    neighbors.add(maze[row][col + 1]);
                
                // Shuffles neighbors so that it is iterates through in random order
                Collections.shuffle(neighbors);
                
                // Iterates through each Cell neighbor in the neighbors ArrayList using a for-each loop
                for(Cell neighbor : neighbors)
                {
                   // setX stores the root of the current maze cell 
                   int setX = ds.find(maze[row][col].num);
                   // setY stores the root of the neighbor maze cell
                   int setY = ds.find(neighbor.num);
                   
                   // If the two sets don't have the same root, the code executes
                   if(setX != setY)
                   {
                       // setX and setY are merged with a call to the union method
                       ds.union(setX, setY);
                       // If-else-if statements check where the 2 cells share a wall and set them to false from etiher side
                       if(neighbor.row == row - 1)
                       {
                           // Knocks down wall from current cell
                           maze[row][col].north = false;
                           // Knocks down wall from neighbor cell
                           neighbor.south = false;
                       }
                       else if(neighbor.row == row + 1)
                       {
                           maze[row][col].south = false;
                           neighbor.north = false;
                       }
                       else if(neighbor.col == col - 1)
                       {
                           maze[row][col].west = false;
                           neighbor.east = false;
                       }
                       else if(neighbor.col == col + 1)
                       {
                           maze[row][col].east = false;
                           neighbor.west = false;
                       }
                       // Breaks out of loop since one neighbor's shared wall has already been knocked down
                       break;
                   }
                }
                
        }
    }
    
    // The findPath method returns a String
    public String findPath() {
        // A Queue of type string is created
        Queue<String> pathQueue = new LinkedList<>();
        // A 2D boolean array, visited, is created to track which cells have been visited
        boolean[][] visited = new boolean[n][m];
        // The empty string is enqueued to the pathQueue
        pathQueue.offer("");
        // The entrance coordinate is marked as visited
        visited[0][0] = true;
    
        // Array of integers to change the row/column
        int[] rowChange = {-1, 1, 0, 0};
        int[] colChange = {0, 0, -1, 1};
        
        // Array of char cardinal directions
        char[] direction = {'N', 'S', 'W', 'E'};
        
        // DFS: Continues looping as long as pathQueue isn't empty
        while (!pathQueue.isEmpty()) 
        {
            // path String stores first node's value from pathQueue
            String path = pathQueue.poll();
            // row and col initialized to 0
            int row = 0, col = 0;
            // Converts pathQueue to a char array and does a for-each loop on it
            for (char dir : path.toCharArray()) {
                // Switches on dir char
                switch (dir) {
                    // Increments/decrements row or col depending on dir, then breaks
                    case 'N': 
                        row--; 
                        break;
                    case 'S': 
                        row++; 
                        break;
                    case 'W': 
                        col--; 
                        break;
                    case 'E': 
                        col++; 
                        break;
                }
            }
            
            // If the loop reaches the exit cell, the path String is returned
            if (row == n - 1 && col == m - 1) {
                return path;
            }
    
            // Iterates through each of the 4 directions
            for (int i = 0; i < 4; i++) 
            {
                // Sets row2 and col2 values based on directions with the Change arrays
                int row2 = row + rowChange[i];
                int col2 = col + colChange[i];
                // noWall is a boolean that checks if a wall isn't present in a certain direction, initialized as false
                boolean noWall = false; 
                // if-else-if statements check current direction and if a wall isn't present, if there is a gap, noWall set to true
                if(direction[i] == 'N' && !maze[row][col].north) 
                    noWall = true; 
                else if(direction[i] == 'S' && !maze[row][col].south) 
                    noWall = true; 
                else if(direction[i] == 'E' && !maze[row][col].east) 
                    noWall = true; 
                else if(direction[i] == 'W' && !maze[row][col].west) 
                    noWall = true; 
                // If there is noWall, row2 and col2 are within bounds, and the coordinate hasn't been visited, the code executes
                if (noWall && row2 >= 0 && row2 < n && col2 >= 0 && col2 < m && !visited[row2][col2]) 
                {
                    // The path String and direction char are concatenated and enqueued
                    pathQueue.offer(path + direction[i]);
                    // The coordinate is marked as visited
                    visited[row2][col2] = true;
                }
            }
        }
        // Returns an empty string if a path isn't found (not possible)
        return "";
    }
    
    // The void display method has a path String parameter and boolean state parameter
    public void display(String path, boolean state)
    {
            // Initializes row and col to 0
            int row = 0, col = 0;
            // pathList is an ArrayList of Cell type
            ArrayList<Cell> pathList = new ArrayList<>();
            // Iterates through pathList and adjusts row and col based on direction chars within path String
            for(int i = 0; i < path.length(); i++)
            {
                    if(path.charAt(i) == 'N')
                        row--;
                    else if(path.charAt(i) == 'E')
                        col++;
                    else if(path.charAt(i) == 'S')
                        row++;
                    else if(path.charAt(i) == 'W')
                        col--;
                // Adds Cell from maze at the coordinate to pathList
                pathList.add(maze[row][col]);
            }
        
        // Iterates from 0 to N through each row
        for(int i = 0; i < n; i++)
        {
            // Iterates from 0 to M through each col
            for(int j = 0; j < m; j++)
            {
                // If the Cell has a north wall, a wall is printed
                if(maze[i][j].north)
                    System.out.print(" _____");
                // Otherwise, an empty space printed
                else if(!maze[i][j].north)
                    System.out.print("      ");
            }
            // Empty line printed
            System.out.println();
            // Main cell printed 5 times
            for(int k = 0; k < 5; k++)
            {
                // Iterates from 0 to M through each col
                for(int j = 0; j < m; j++)
                {
                    // Prints | or empty space for leftmost cell depending on west wall
                    if(maze[i][j].west && j == 0)
                        System.out.print("|");
                    else if(!maze[i][j].west && j == 0)
                        System.out.print(" ");
                    // Prints an empty space
                    System.out.print("  ");
                    // doesBreak boolean to track breaking from loop
                    boolean doesBreak = false;
                    // If the j iteration is in the middle
                    if(k == 2)
                    {
                        // If state is true (path printing)
                        if(state)
                        {
                            // Iterates through pathList
                            for(int l = 0; l < pathList.size(); l++)
                            {
                                if(pathList.get(l) == maze[i][j])
                                {
                                    // If it's the exit, + is printed
                                    if(i == n - 1 && j == n - 1)
                                    {
                                        System.out.print("+");
                                    }
                                    // Otherwise, * is printed to show the path
                                    else
                                    {
                                        System.out.print("*");
                                    }
                                    // doesBreak set to true and breaks out of loop
                                    doesBreak = true;
                                    break;
                                }
                            }
                            // If it's the entrance, + is printed 
                            if(i == 0 && j == 0)
                                System.out.print("+");
                            // If it's not part of the path, an empty space is printed
                            else if (!doesBreak)
                                System.out.print(" ");
                        }
                        // Otherwise, an empty space is printed
                        else
                            System.out.print(" ");
                    }
                    // Otherwise, an empty space is printed
                    else
                    {
                        System.out.print(" ");
                    }
                    // An empty space is printed
                    System.out.print("  ");
                    // If there's an east wall, | is printed
                    if(maze[i][j].east)
                        System.out.print("|");
                    // Otherwise, an empty space is printed
                    else
                        System.out.print(" ");
                }
                // A new wall is printed
                System.out.println();
            }
            
            // Checks if it's the last row
            if(i == n - 1)
            {
                // Iterates from 0 to M through each col
                for(int j = 0; j < m; j++)
                {
                    // If the Cell has a south wall, the wall is printed
                    if(maze[i][j].south)
                        System.out.print(" _____");
                    // Otherwise, an empty space is printed
                    else if(!maze[i][j].south)
                        System.out.print("      ");
                }
            }
        }
        
    }
    
    // The main method (I used terminal output/input instead of GUI's but it has the same functionality)
    public static void main (String[] args) 
    {
        // A Scanner object, sc is created
        Scanner sc = new Scanner(System.in);
        
        // Prints prompts for N and M, reads them into n and m using the Scanner
        System.out.println("Please enter the N value: ");
        int n = sc.nextInt();
        System.out.println("Please enter the M value: ");
        int m = sc.nextInt();
        
        // A new Maze object, maze, is created with user-input n and m
        Maze maze = new Maze(n, m);
        maze.display("", false);
        System.out.println();
        
        // path String holds return for call to Maze object's findPath method
        String path = maze.findPath();
        // Prints out the path 
        System.out.println("This is the maze path from entrance to exit: " + path);
        System.out.println();
        // Prompt to ask whether user wants the maze's path traced. Reads in user input with Scanner.
        System.out.println("Trace path in maze (Y/N): ");
        char state = sc.next().charAt(0);
        System.out.println();
        
        // If the user entered 'Y', the code executes
        if(state == 'Y')
        {
            // Prints out guide to decode symbol's purpose
            System.out.println("Entrance and exit represented by +, path represented by *");
            // Calls maze's display method with path String and true boolean as arguments, which displays maze with path.
            maze.display(path, true);
        }
    }

}
