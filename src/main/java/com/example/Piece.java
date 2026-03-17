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
public class Piece {
    private boolean king = false;
    private boolean jumped = false;
    private final boolean color;
    private BufferedImage img;
    private int mod;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
         if (isWhite) {
            mod = -1;
         }
         else {
            mod = 1;
         }
        try {
            if (this.img == null) {
                System.out.println("trying to open "+img_file);
                this.img = ImageIO.read(new File(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    public void crown() {
        king = true;
    }
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    public Boolean canJump(Square[][] board, Square start) {
        Square b = start;
        int rowMod = -1 * mod;
        int colMod = -1 * mod;
            for (int z = 0; z<=3; z++) {
                //up and to the left is in bounds for taking jumps
                if(b.getRow()+(2*rowMod)>=0 && b.getCol()+(2*colMod)>=0 && b.getRow()+(2*rowMod)<=7 && b.getCol()+(2*colMod)<=7){
                    //there is a piece of opposite color down and to the right of us and no piece preventing a jump
                    if(board[b.getRow()+rowMod][b.getCol()+colMod].isOccupied() && board[b.getRow()+rowMod][b.getCol()+colMod].getOccupyingPiece().getColor()!=color && !board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)].isOccupied()) {
                        return true;
                    }
                }
                if (colMod == (1*mod) && king) {
                    colMod = -1*mod;
                    rowMod = 1*mod;
                }
                else {
                    colMod = (1*mod);
                }
            }
    return false;
}
    
    
    // TO BE IMPLEMENTED!
    //return a list of every square that is "controlled" by this piece. A square is controlled
    //if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> jumpSquares = new ArrayList<>();
        ArrayList<Square> allSquares = new ArrayList<>();
        Square b = start;
        int m = 0;
        int count = 0;
        int y = 0;
        int rowMod = -1 * mod;
        int colMod = -1 * mod;
        while(m==0) {
            for (int z = 0; z<=3; z++) {
                //up and to the left is in bounds for taking jumps
                if(b.getRow()+(2*rowMod)>=0 && b.getCol()+(2*colMod)>=0 && b.getRow()+(2*rowMod)<=7 && b.getCol()+(2*colMod)<=7){
                    //there is a piece of opposite color down and to the right of us and no piece preventing a jump
                    if(board[b.getRow()+rowMod][b.getCol()+colMod].isOccupied() && board[b.getRow()+rowMod][b.getCol()+colMod].getOccupyingPiece().getColor()!=color && !board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)].isOccupied()) {
                        for (int x = 0; x < jumpSquares.size(); x++) {
                            if(board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]==jumpSquares.get(x)) {
                                y++;
                            }
                            else if(board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]==board[start.getRow()][start.getCol()]) {
                                y++;
                            }
                        }
                        if (y == 0) {
                            System.out.println("h");
                            jumpSquares.add(board[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]);
                            allSquares.add(board[b.getRow()+(rowMod)][b.getCol()+(colMod)]);
                        }
                        y =0;
                    }
                }
                if (colMod == (1*mod) && king) {
                    colMod = -1*mod;
                    rowMod = 1*mod;
                }
                else {
                    colMod = (1*mod);
                    System.out.println("well?");
                }
            }
            if (count == jumpSquares.size()) {
                m++;
            }
            else {
                b = jumpSquares.get(count);
            }
            count++;
        }
     return allSquares;
    }
    

    //TO BE IMPLEMENTED!
    //implement the move function here
    //it's up to you how the piece moves, but at the very least the rules should be logical and it should never move off the board!
    //returns an arraylist of squares which are legal to move to
    //please note that your piece must have some sort of logic. Just being able to move to every square on the board is not
    //going to score any points.
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
                            else if(board.getSquareArray()[b.getRow()+(2*rowMod)][b.getCol()+(2*colMod)]==board.getSquareArray()[start.getRow()][start.getCol()]) {
                                y++;
                            }
                        }
                        if (y == 0) {
                            System.out.println("h");
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
                    System.out.println("well?");
                }
            }
            if (count == jumpSquares.size()) {
                m++;
            }
            else {
                b = jumpSquares.get(count);
            }
            count++;
        }
        colMod = -1*mod;
        rowMod = -1*mod;
        if (jumpSquares.size() == 0) {
            for (int x = 0; x<=3; x++) {
                if (start.getRow()+rowMod>=0 && start.getCol()+colMod>=0 && start.getRow()+rowMod<=7 && start.getCol()+colMod<=7); {
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
                    System.out.println("annie r u ok");
                }
            }
        }
        return allSquares;
    }
}