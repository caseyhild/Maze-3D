import java.util.*;
public class Maze
{
    private int level = 1;
    private int mazeWidth = 5;
    private int mazeHeight = 5;
    private int[][] maze;
    private int currentX = 1;
    private int currentY = 1;

    public Maze()
    {

    }

    public void newMaze()
    {
        //size of maze increases by 2 per level
        mazeWidth = 2 * level + 3;
        mazeHeight = 2 * level + 3;
        maze = new int[mazeWidth][mazeHeight];
        //checks each square
        int[][] checked = new int[mazeWidth][mazeHeight];
        //squares to returns to once there is no new square to go to
        ArrayList<Integer> stackX = new ArrayList<>();
        ArrayList<Integer> stackY = new ArrayList<>();
        //possible finish squares
        ArrayList<Integer> possibleX = new ArrayList<>();
        ArrayList<Integer> possibleY = new ArrayList<>();
        //certain squares are part of the maze to start
        //the others are walls, but some walls change into part
        //of the maze to create a path
        for(int mazeX = 0; mazeX < mazeWidth; mazeX++)
        {
            for(int mazeY = 0; mazeY < mazeHeight; mazeY++)
            {
                maze[mazeX][mazeY] = 1;
                if(mazeX % 2 == 1 && mazeY % 2 == 1)
                    maze[mazeX][mazeY] = 0;
                if(maze[mazeX][mazeY] != 0)
                    checked[mazeX][mazeY] = 1;
            }
        }
        //start location is checked and added to stack
        checked[currentX][currentY] = 1;
        stackX.add(currentX);
        stackY.add(currentY);
        int choices;
        boolean finished;
        do
        {
            int neighbor;
            do
            {
                //sees how many neighbors are not visited yet and
                //picks one randomly
                neighbor = (int) (Math.random() * 4 + 1);
                choices = 0;
                if(currentY >= 3 && checked[currentX][currentY - 2] == 0)
                    choices++;
                if(currentX <= mazeWidth - 4 && checked[currentX + 2][currentY] == 0)
                    choices++;
                if(currentY <= mazeHeight - 4 && checked[currentX][currentY + 2] == 0)
                    choices++;
                if(currentX >= 3 && checked[currentX - 2][currentY] == 0)
                    choices++;
                if(choices != 0)
                {
                    //moves current location to neighbor square if possible
                    if(neighbor == 1 && currentY >= 3)
                    {
                        if(checked[currentX][currentY - 2] == 0)
                        {
                            maze[currentX][currentY - 1] = 0;
                            currentY -= 2;
                            checked[currentX][currentY] = 1;
                        }
                        else
                        {
                            neighbor = 0;
                        }
                    }
                    else if(neighbor == 2 && currentX <= mazeWidth - 4)
                    {
                        if(checked[currentX + 2][currentY] == 0)
                        {
                            maze[currentX + 1][currentY] = 0;
                            currentX += 2;
                            checked[currentX][currentY] = 1;
                        }
                        else
                        {
                            neighbor = 0;
                        }
                    }
                    else if(neighbor == 3 && currentY <= mazeHeight - 4)
                    {
                        if(checked[currentX][currentY + 2] == 0)
                        {
                            maze[currentX][currentY + 1] = 0;
                            currentY += 2;
                            checked[currentX][currentY] = 1;
                        }
                        else
                        {
                            neighbor = 0;
                        }
                    }
                    else if(neighbor == 4 && currentX >= 3)
                    {
                        if(checked[currentX - 2][currentY] == 0)
                        {
                            maze[currentX - 1][currentY] = 0;
                            currentX -= 2;
                            checked[currentX][currentY] = 1;
                        }
                        else
                        {
                            neighbor = 0;
                        }
                    }
                    else
                    {
                        neighbor = 0;
                    }
                }
                //repeats until it finds a valid neighbor or has no possible choices
            }while(neighbor == 0);
            //adds current location to stack
            stackX.add(currentX);
            stackY.add(currentY);
            if(choices == 0)
            {
                // sets finish square option to the square it
                // is in when it first runs out of possible choices
                possibleX.add(currentX);
                possibleY.add(currentY);
                //recursive backtracking
                //goes back through the stack until it finds a 
                //square with at least 1 possible choice
                int square = stackX.size() - 1;
                boolean stop = false;
                int stackChoices;
                while(!stop)
                {
                    stackChoices = 0;
                    if(stackY.get(square) >= 3 && checked[stackX.get(square)][stackY.get(square) - 2] == 0)
                        stackChoices++;
                    if(stackX.get(square) <= mazeWidth - 4 && checked[stackX.get(square) + 2][stackY.get(square)] == 0)
                        stackChoices++;
                    if(stackY.get(square) <= mazeHeight - 4 && checked[stackX.get(square)][stackY.get(square) + 2] == 0)
                        stackChoices++;
                    if(stackX.get(square) >= 3 && checked[stackX.get(square) - 2][stackY.get(square)] == 0)
                        stackChoices++;
                    if(stackChoices >= 1)
                    {
                        currentX = stackX.get(square);
                        currentY = stackY.get(square);
                        stop = true;
                    }
                    square--;
                }
            }
            //maze is complete if all squares have been checked
            finished = true;
            for(int mazeX = 0; mazeX < mazeWidth; mazeX++)
            {
                for(int mazeY = 0; mazeY < mazeHeight; mazeY++)
                {
                    if (checked[mazeX][mazeY] == 0) {
                        finished = false;
                        break;
                    }
                }
            }
        }while(!finished);
        //random of the possible finish locations
        int finishX;
        int finishY;
        if(!possibleX.isEmpty())
        {
            int randomFinish = (int) (Math.random() * possibleX.size());
            finishX = possibleX.get(randomFinish);
            finishY = possibleY.get(randomFinish);
        }
        else
        {
            finishX = currentX;
            finishY = currentY;
        }
        //makes the finish square have a value of -1 so other classes
        //can check where the finish square is
        maze[finishX][finishY] = -1;
    }

    public int[][] getMaze()
    {
        //gets the maze
        return maze;
    }

    public int getWidth()
    {
        //gets width of maze
        return mazeWidth;
    }

    public int getHeight()
    {
        //gets height of maze
        return mazeHeight;
    }

    public void setLevel(int lvl)
    {
        //sets a new level
        level = lvl;
    }
}