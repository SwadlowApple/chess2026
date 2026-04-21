package com.example;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Checker extends Piece {
    private boolean king = false;
    private boolean jumped = false;
    private final boolean color;
    private BufferedImage img;
    private int mod;
    private boolean canJump;
    //Name: Fouad Kadry
    //Piece name: Checker
    //It jumps over pieces, andd claims them, moving forward until it reaches the end of the board, then 
    // being crowned and being able to jump anywhere. It can also move one diagonally, but only if
    //it can't claim jump. If it can it must do so repeatedly until it can't, no matter if you do
    // or don't want to stop
    
    public Checker(boolean isWhite, String img_file) {
        super(isWhite, img_file);
        canJump = false;
        this.color = isWhite;
         if (isWhite) {
            mod = -1;
         }
         else {
            mod = 1;
         }
       
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    public String toString() {
        if (mod == -1) {
            if (king) {
                return ("I am a white crowned checker");
            } else {
                return ("I am a white uncrowned checker");
            }

        } else {
            if (king) {
                return ("I am a black crowned checker");
            } else {
                return ("I am a black uncrowned checker");
            }
        }
    }
    public void crown() {
        king = true;
    }
    
    //Precondition: must have a squarearray representing a board and a square in that array, which must be occupied by a checker, as the inputs
    //Postcondition: Returns a boolean based on if the piece can jump from the square it's on
    public Boolean canJump(Square[][] board, Square start) {
        Square b = start;
        int rowMod = -1 * mod;
        int colMod = -1 * mod;
            for (int z = 0; z<=3; z++) {
                //checks diagonals to see if we have space in that direction
                if(b.getRow()+(2*rowMod)>=0 && b.getCol()+(2*colMod)>=0 && b.getRow()+(2*rowMod)<=7 && b.getCol()+(2*colMod)<=7){
                    //checks to see if there are pieces so we can jump, and if we can returns true
                    if(board[b.getRow()+rowMod][b.getCol()+colMod].isOccupied() && board[b.getRow()+rowMod][b.getCol()+colMod].getOccupyingPiece().getColor()!=color && !board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)].isOccupied()) {
                        return true;
                    }
                }
                //works with for loop to get all posibilities
                if (colMod == (1*mod) && king) {
                    colMod = -1*mod;
                    rowMod = 1*mod;
                }
                else {
                    colMod = (1*mod);
                }
            }
            //if we can't jump, returns false as default
    return false;
}
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    //Precondition: Input of an Arraylist of squares making up a board, and 
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> jumpSquares = new ArrayList<>();
        ArrayList<Square> allSquares = new ArrayList<>();
        Square b = start;
        int m = 0;
        int count = 0;
        int y = 0;
        int rowMod = -1 * mod;
        int colMod = -1 * mod;
        //The while loop is to keep it active to check all posibilities for jumps
        while(m==0) {
            for (int z = 0; z<=3; z++) {
                //checks if it can jump without going out of bounds
                if(b.getRow()+(2*rowMod)>=0 && b.getCol()+(2*colMod)>=0 && b.getRow()+(2*rowMod)<=7 && b.getCol()+(2*colMod)<=7){
                    //checks if there is a piece of opposite color one diagonal of us and no piece preventing a jump 2 diagonal of us so we can jumps
                    if(board[b.getRow()+rowMod][b.getCol()+colMod].isOccupied() && board[b.getRow()+rowMod][b.getCol()+colMod].getOccupyingPiece().getColor()!=color && !board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)].isOccupied()) {
                        //For Loops check to make sure we don't already have it so this doesn't go infinite and takes less spaces
                        for (int x = 0; x < jumpSquares.size(); x++) {
                            if(board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]==jumpSquares.get(x)) {
                                y++;
                            }
                            else if(board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]==board[start.getRow()][start.getCol()]) {
                                y++;
                            }
                        }
                        for (int x = 0; x < allSquares.size(); x++) {
                            if(board[b.getRow()+rowMod][b.getCol()+colMod]==allSquares.get(x)) {
                                y++;
                            }
                            else if(board[b.getRow()+rowMod][b.getCol()+colMod]==board[start.getRow()][start.getCol()]) {
                                y++;
                            }
                        }
                        //says that if we don't have it we add it and we check for jumps there
                        if (y == 0) {
                            jumpSquares.add(board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]);
                            allSquares.add(board[b.getRow()+(rowMod)][b.getCol()+(colMod)]);
                        }
                        y =0;
                    }
                    //checks if we threaten where pieces can move
                    if (!board[b.getRow()+rowMod][b.getCol()+colMod].isOccupied() && !board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)].isOccupied()) {
                        for (int x = 0; x < allSquares.size(); x++) {
                            if(board[b.getRow()+rowMod][b.getCol()+colMod]==allSquares.get(x)) {
                                y++;
                            }
                            else if(board[b.getRow()+rowMod][b.getCol()+colMod]==board[start.getRow()][start.getCol()]) {
                                y++;
                            }
                        }
                        //says that if we don't have it we add it
                        if (y == 0) {
                            allSquares.add(board[b.getRow()+(rowMod)][b.getCol()+(colMod)]);
                        }
                        y =0;
                    }
                }
                //The code uses this to for loop through all posibilities
                if (colMod == (1*mod) && king) {
                    colMod = -1*mod;
                    rowMod = 1*mod;
                }
                else {
                    colMod = (1*mod);
                }
            }
            //Checks if we ran out of options, and if we did, returns
            if (count == jumpSquares.size()) {
                m++;
            }
            else {
                b = jumpSquares.get(count);
            }
            count++;
            rowMod = -1*mod;
            colMod = -1*mod;
        }
        //returns the threatened squares
     return allSquares;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
    //Precondition: Inputs must be a board and a start square on that board with a checker on in
    //Postcondition: returns all the places that on the board where the piece could legally move on its turn 
    // in an Arraylist of Squares that are on the board
    public ArrayList<Square> getLegalMoves(Board board, Square start){
        ArrayList<Square> jumpSquares = new ArrayList<>();
        ArrayList<Square> allSquares = new ArrayList<>();
        Square b = start;
        int m = 0;
        int count = 0;
        int y = 0;
        int rowMod = -1*mod;
        int colMod = -1*mod;
        while(m==0) {
            for (int z = 0; z<=3; z++) {
                //up and to the left is in bounds for taking jumps
                if(b.getRow()+(2*rowMod)>=0 && b.getCol()+(2*colMod)>=0 && b.getRow()+(2*rowMod)<=7 && b.getCol()+(2*colMod)<=7){
                    //there is a piece of opposite color down and to the right of us and no piece preventing a jump
                    if(board.getSquareArray()[b.getRow()+rowMod][b.getCol()+colMod].isOccupied() && board.getSquareArray()[b.getRow()+rowMod][b.getCol()+colMod].getOccupyingPiece().getColor()!=color && !board.getSquareArray()[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)].isOccupied()) {
                        for (int x = 0; x < jumpSquares.size(); x++) {
                            if(board.getSquareArray()[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]==jumpSquares.get(x)) {
                                y++;
                            }
                        }
                        if (y == 0) {
                            jumpSquares.add(board.getSquareArray()[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]);
                            allSquares.add(board.getSquareArray()[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]);
                        }
                        y =0;
                    }
                }
                if (colMod == (1*mod) && king) {
                    colMod = -1*mod;
                    rowMod = 1*mod;
                }
                else {
                    colMod = 1*mod;
                }
            }
            if (count == jumpSquares.size()) {
                m++;
            }
            else {
                b = jumpSquares.get(count);
            }
            count++;
            rowMod = -1*mod;
            colMod = -1*mod;
        }
        colMod = -1*mod;
        rowMod = -1*mod;
        if (jumpSquares.size() == 0) {
            for (int x = 0; x<=3; x++) {
                if (start.getRow()+rowMod>=0 && start.getCol()+colMod>=0 && start.getRow()+rowMod<=7 && start.getCol()+colMod<=7) {
                    if (board.getSquareArray()[start.getRow()+rowMod][start.getCol()+colMod].isOccupied()) {
                    }
                    else {
                        allSquares.add(board.getSquareArray()[start.getRow()+rowMod][start.getCol()+colMod]); 
                    }
                }
                if (colMod == (1*mod) && king) {
                    colMod = (-1*mod);
                    rowMod = (1*mod);
                }
                else if (colMod == (1*mod)) {
                    x+=2;
                }
                else {
                    colMod = (1*mod);
                }
            }
        }
        return allSquares;
    }
}