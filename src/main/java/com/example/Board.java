package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;
import java.awt.Toolkit;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String path = "src/main/java/com/example/Pictures/";
    //private static final String RESOURCES_WBISHOP_PNG = path+"wbishop.png";
	//private static final String RESOURCES_BBISHOP_PNG = path+"bbishop.png";
	//private static final String RESOURCES_WKNIGHT_PNG = path+"wknight.png";
	//private static final String RESOURCES_BKNIGHT_PNG = path+"bknight.png";
	//private static final String RESOURCES_WROOK_PNG = path+"wrook.png";
	//private static final String RESOURCES_BROOK_PNG = path+"brook.png";
	//private static final String RESOURCES_WKING_PNG = path+"wking.png";
	//private static final String RESOURCES_BKING_PNG = path+"bking.png";
	//private static final String RESOURCES_BQUEEN_PNG = path+"bqueen.png";
	//private static final String RESOURCES_WQUEEN_PNG = path+"wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = path+"wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = path+"bpawn.png";

    
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
    private boolean canJump = false;
    private int repStop;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    Checker currPiece;
    private Square fromMoveSquare;
    private Square checker;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
     
      //for (.....)  
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.
        
        
        
        int i = 0;
        for(int x = 0; x<8; x++) {
            for (int y = 0; y<8; y++) {
                board[x][y] = new Square(this,(i%2)==0,x,y);
                this.add(board[x][y]);
                i++;
            }
            i++;
        }
        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
    //Precondition: created board before calling this
    //Postcondition: creates checkers pieces correctly for all spaces on a checker board, assuming that you would be playing black
    void initializePieces() {
    	for (int i =0; i<3; i++){
            if (i == 1) {
                for (int j = 1; j<8; j+=2) {
                    board[7-i][j].put(new Checker(false,  RESOURCES_BPAWN_PNG));
                    board[i][j-1].put(new Checker(true, RESOURCES_WPAWN_PNG));
                }
            }
            else {
                for (int j = 0; j<8; j+=2) {
                    board[7-i][j].put(new Checker(false,  RESOURCES_BPAWN_PNG));
                    board[i][j+1].put(new Checker(true, RESOURCES_WPAWN_PNG));
                }
            }
        }
    	
        

    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Checker p) {
        this.currPiece = p;
    }

    public Checker getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
     Image backgroundImage = null; 
     URL imageUrl = null;
     if (currPiece != null) {
      imageUrl = getClass().getResource(path+currPiece.getImage());
     }

     if (imageUrl != null) {
            // This is the cleanest way to get an AWT Image object from a URL
            backgroundImage = Toolkit.getDefaultToolkit().createImage(imageUrl);
        } 
    

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
               // System.out.println("Painting square at " + x + ", " + y);   
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        fromMoveSquare = sq;
        if (repStop == 0) {
            checker = fromMoveSquare;
        }
        else {
            System.out.println("4");
        }
        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            for(Square s: currPiece.getLegalMoves(this, fromMoveSquare)) {
                s.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.blue));
            }
            for(Square s: currPiece.getControlledSquares(board, fromMoveSquare)) {
                s.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
            }
            if (currPiece.getColor() != whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    //Precondition: a mouse event is used as the only input
    //Postcondition: lets the user move the piece that they have picked up to the release square, claiming pieces where it should, provided that they picked up a piece 
    // and the end square is a legal square for the piece to move to, otherwise doing nothing.
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        for (Square[] row: board) {
            for(Square s: row) {
                s.setBorder(null);
            }
        }
        
        //using currPiece
        if(fromMoveSquare != null && currPiece!= null && currPiece instanceof Checker) {
            canJump = currPiece.canJump(board, checker);
            if(currPiece != null && currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare) && fromMoveSquare == checker && whiteTurn == currPiece.getColor()) {
                if (canJump) {
                    repStop = 1;
                            if (currPiece != null && currPiece.canJump(board, checker)) {
                                if (fromMoveSquare.getRow()+2 <= 7 && fromMoveSquare.getCol()+2 <=7 &&endSquare == board[checker.getRow()+2][checker.getCol()+2]) {
                                    System.out.println("1");
                                    endSquare.put(currPiece);
                                    fromMoveSquare.removePiece();
                                    board[fromMoveSquare.getRow()+1][fromMoveSquare.getCol()+1].removePiece();
                                    checker = endSquare;
                                    checker.setDisplay(true);
                                    //currPiece = null;
                                    repaint();
                                    if (!currPiece.canJump(board, endSquare)) {
                                        System.out.println("2");
                                        repStop = 0;
                                    }
                                }
                                else if (fromMoveSquare.getRow()-2 >= 0 && fromMoveSquare.getCol()+2 <=7 &&endSquare == board[fromMoveSquare.getRow()-2][fromMoveSquare.getCol()+2]) {
                                    System.out.println("1");
                                    endSquare.put(currPiece);
                                    fromMoveSquare.removePiece();
                                    board[fromMoveSquare.getRow()-1][fromMoveSquare.getCol()+1].removePiece();
                                    checker = endSquare;
                                    checker.setDisplay(true);
                                    //currPiece = null;
                                    repaint();
                                    if (!currPiece.canJump(board, endSquare)) {
                                        System.out.println("2");
                                        repStop = 0;
                                    }
                                }
                                else if (fromMoveSquare.getRow()+2 <= 7 && fromMoveSquare.getCol()-2 >= 0 &&endSquare == board[fromMoveSquare.getRow()+2][fromMoveSquare.getCol()-2]) {
                                    System.out.println("1");
                                    endSquare.put(currPiece);
                                    fromMoveSquare.removePiece();
                                    board[fromMoveSquare.getRow()+1][fromMoveSquare.getCol()-1].removePiece();
                                    checker = endSquare;
                                    checker.setDisplay(true);
                                    //currPiece = null;
                                    repaint();
                                    if (!currPiece.canJump(board, endSquare)) {
                                        System.out.println("2");
                                        repStop = 0;
                                    }
                                }
                                else if(fromMoveSquare.getRow()-2 >= 0 && fromMoveSquare.getCol()-2 >= 0 &&endSquare == board[fromMoveSquare.getRow()-2][fromMoveSquare.getCol()-2]) {
                                    System.out.println("1");
                                    endSquare.put(currPiece);
                                    fromMoveSquare.removePiece();
                                    board[fromMoveSquare.getRow()-1][fromMoveSquare.getCol()-1].removePiece();
                                    checker = endSquare;
                                    checker.setDisplay(true);
                                    //currPiece = null;
                                    repaint();
                                    if (!currPiece.canJump(board, endSquare)) {
                                        System.out.println("2");
                                        repStop = 0;
                                    }
                                }
                            }
                            else {
                                repStop = 0;
                            }
                }
                else {
                    endSquare.put(currPiece);
                    fromMoveSquare.removePiece();
                }
                if (whiteTurn && repStop == 0) {
                    whiteTurn = false;
                }
                else if (repStop == 0) {
                    whiteTurn = true;
                }
                if (((endSquare.getRow() == 0 && !currPiece.getColor()) || (endSquare.getRow() == 7 && currPiece.getColor())) && repStop == 0) {
                    currPiece.crown();
                }
            }
            else {
                System.out.println("5");
            }
        }
       
        fromMoveSquare.setDisplay(true);
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}