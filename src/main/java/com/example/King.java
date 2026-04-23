//Mr. M
//03/20/24
//This class represents the King piece in our chess game. It is able to move
//exactly one square in any direction and controls those squares.
//All functionality associated with checks is relegated to the class Board. (You'll
//have to implement that part yourself!).
package com.example;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class King extends Piece {
// constructor
	public King(boolean isWhite, String img_file) {
		super(isWhite, img_file);
	}

// Pre-condition: b is a non-null board that contains some squares. The
// currentSquare is non-null and present in the board.
// Post-condition: Returns all legally accessable squares by this piece. In
// event that no squares are accessable returns an empty list.
	@Override
	public ArrayList<Square> getLegalMoves(Board b, Square currentSquare) {
		Square [][] board = b.getSquareArray();
		ArrayList<Square> legalMoves = new ArrayList<Square>();
		// Determine the row and column of the current square
				int row = currentSquare.getRow();
				int col = currentSquare.getCol();
		// Check each of the 8 squares surrounding the king
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
		// Skip the square the king is currently on
						if (i == 0 && j == 0) {
							continue;
						}
		// Check if the square is within the bounds of the board
						if (row + i >= 0 && row + i < board.length && col + j >= 0 && col + j < board[row].length) {
							if(!board[row+i][col+j].isOccupied() || board[row+i][col+j].getOccupyingPiece().getColor()!= getColor())
			// Add the square to the list of legal move squares
								legalMoves.add(board[row + i][col + j]);
						}
					}
				}
		return legalMoves;
	}

@Override
public String toString() {
return "A " + super.toString() + " king";
}

//precondition - board is a fully populated 8 by 8 2D array of Square objects(non-null)
//postcondition - an array list of all squares this piece controlls is returned (squares it can attack on it's next turn)

	@Override
	public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
		ArrayList<Square> controlledSquares = new ArrayList<Square>();
// Determine the row and column of the current square
		int row = currentSquare.getRow();
		int col = currentSquare.getCol();
// Check each of the 8 squares surrounding the king
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
// Skip the square the king is currently on
				if (i == 0 && j == 0) {
					continue;
				}
// Check if the square is within the bounds of the board
				if (row + i >= 0 && row + i < board.length && col + j >= 0 && col + j < board[row].length) {
// Add the square to the list of controlled squares
					controlledSquares.add(board[row + i][col + j]);
				}
			}
		}
		return controlledSquares;
	}
}
