package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Checkers extends JFrame implements MouseListener, MouseMotionListener{
	
	Image myIm1 = new ImageIcon(Checkers.class.getResource("checkerBoardImage1.png")).getImage(); 
	Image myIm2 = new ImageIcon(Checkers.class.getResource("FourPieces3.png")).getImage(); 
	int[][] board = new int[8][8];// 1 = CR, 2 = CB, 3 = KR, 4 = KB.
	static int currX = 0;
	static int currY = 0;
	static int initialX;
	static int initialY;
	static String whoWon;
	static String whoTurn = "Your Turn.";
	Font f = new Font("", Font.BOLD, 72);
	Random r = new Random();
	
	public Checkers(){
		for(int i = 0; i < 8; i+=2){
			board[i    ][0] = 2;
			board[i + 1][1] = 2;
			board[i    ][2] = 2;	
			board[i + 1][5] = 1;
			board[i    ][6] = 1;
			board[i + 1][7] = 1;
		}//for i
	}//Constructor
	
	public static void main(String[] args){
		go();
	}//main
	
	private static void go(){
		Checkers instance = new Checkers();
		instance.setLayout(null);
		instance.setTitle("Checkers --by Will David--");
		instance.setSize(616, 638);
		instance.setVisible(true);
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instance.run();	
	}//go
	
	private void run(){
		addMouseListener(this);
		addMouseMotionListener(this);
		try{
			Thread.sleep(100);
		}catch(Exception e){
			e.printStackTrace();
		}//catch
		createBufferStrategy(2);
		while(true){
			gameDraw();
		}//while
	}//run
	
	private void gameDraw(){
		BufferStrategy b = getBufferStrategy();
		Graphics2D g = (Graphics2D) b.getDrawGraphics();
		g.drawImage(myIm1, 8, 30, null);
		if(!stillPlaying()){
			for(int x = 0; x < 8; x ++){
				for(int y = 0; y < 8; y ++){
					drawPiece(g, x, y);//draws every piece on loop
				}//for y
			}//for x
			b.show();
			try{
				Thread.sleep(500);
			}catch(Exception e){
			}//sleep 500 ms
			while(!stillPlaying()){
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				g.fillRect(0,0, 616, 638);
				g.setFont(f);
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				g.drawString(whoWon, 120, 150);
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				g.drawString("Click to Play", 85, 300);
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
				g.drawString("Again!", 185, 400);
				b.show();
				try{
					Thread.sleep(600);
				}catch(Exception e){
				}
			}//while
		}else{
			for(int i = 0; i < 8; i ++){//checks for kings
				if(board[i][7] == 2){
					board[i][7] = 4;
				}//if
				if(board[i][0] == 1){
					board[i][0] = 3;
				}//if
			}//for i
			for(int x = 0; x < 8; x ++){
				for(int y = 0; y < 8; y ++){
					drawPiece(g, x, y);//draws every piece on loop
				}//for y
			}//for x
			if(board[brdPosX(initialX)][brdPosY(initialY)] == 1){
				g.drawImage(myIm2, currX - 34, currY - 34, currX + 34, currY + 34, 62, 62, 124, 124, null);
			}//if 1
			if(board[brdPosX(initialX)][brdPosY(initialY)] == 3){
				g.drawImage(myIm2, currX - 34, currY - 34, currX + 34, currY + 34, 62, 0, 124, 62, null);
			}//if 3
			g.drawString(whoTurn, 37, 50);
		}//if you haven't lost
		b.show();
	}//gameDraw
	
	private void drawPiece(Graphics2D g, int x, int y){
		if(board[x][y] == 1){
			g.drawImage(myIm2, 37+68*x, 56+68*y, 37+68*(x+1), 56+68*(y+1), 62, 62, 124, 124, null);
		}//Red Checker
		if(board[x][y] == 2){
			g.drawImage(myIm2, 37+68*x, 56+68*y, 37+68*(x+1), 56+68*(y+1), 0, 62, 62, 124, null);
		}//Black Checker
		if(board[x][y] == 3){
			g.drawImage(myIm2, 37+68*x, 56+68*y, 37+68*(x+1), 56+68*(y+1), 62, 0, 124, 62, null);
		}//Red King
		if(board[x][y] == 4){
			g.drawImage(myIm2, 37+68*x, 56+68*y, 37+68*(x+1), 56+68*(y+1), 0, 0, 62, 62, null);
		}//Black King
	}//drawPieces
	
	public void mousePressed(MouseEvent p) {
		if(stillPlaying()){
			if((brdPosX(p.getX()) >= 0) && (brdPosX(p.getX()) <= 7) && (brdPosY(p.getY()) >= 0) && (brdPosY(p.getY()) <= 7)){
				initialX = p.getX();
				initialY = p.getY();
				if((board[brdPosX(initialX)][brdPosY(initialY)] == 1) || (board[brdPosX(initialX)][brdPosY(initialY)] == 3)){
					currX = p.getX();
					currY = p.getY();
				}else{
					initialX = 0;
					initialY = 0;
				}//if
			}//if ur click is in bounds
		}else{
			for(int x = 0; x < 8; x ++){
				for(int y = 0; y < 8; y ++){
					board[x][y] = 0;
				}//for y
			}//for x
			for(int i = 0; i < 8; i+=2){
				board[i    ][0] = 2;
				board[i + 1][1] = 2;
				board[i    ][2] = 2;	
				board[i + 1][5] = 1;
				board[i    ][6] = 1;
				board[i + 1][7] = 1;
			}//for i
		}//if you are clicking to reset board
	}//mousePressed
	public void mouseDragged(MouseEvent d) {
		if((brdPosX(d.getX()) >= 0) && (brdPosX(d.getX()) <= 7) && (brdPosY(d.getY()) >= 0) && (brdPosY(d.getY()) <= 7)){
			if((board[brdPosX(initialX)][brdPosY(initialY)] == 1) || (board[brdPosX(initialX)][brdPosY(initialY)] == 3)){
				currX = d.getX();
				currY = d.getY();
			}//if
		}//if
	}//mouseDragged
	public void mouseReleased(MouseEvent r) {
		int ix = brdPosX(initialX);
		int iy = brdPosY(initialY);
		int cx = brdPosX(currX);
		int cy = brdPosY(currY);
		if((brdPosX(r.getX()) >= 0) && (brdPosX(r.getX()) <= 7) && (brdPosY(r.getY()) >= 0) && (brdPosY(r.getY()) <= 7)){
			if((board[ix][iy] == 1) || (board[ix][iy] == 3)){
				if(((cx == ix + 1) || (cx == ix - 1)) && (cy == iy - 1) && (board[cx][cy] == 0)){
					board[cx][cy] = board[ix][iy];//equals 1 or 3 based on startingMouse pos
					board[ix][iy] = 0;
					computerTurn();
				}//NE, NW move
				if((cx == ix + 2) && (cy == iy - 2) && ((board[ix + 1][iy - 1] == 2) || (board[ix + 1][iy - 1] == 4))){
					board[cx][cy] = board[ix][iy];
					board[ix + 1][iy - 1] = 0;
					board[ix][iy] = 0;
					computerTurn();
				}//NE jump
				if((cx == ix - 2) && (cy == iy - 2) && ((board[ix - 1][iy - 1] == 2) || (board[ix - 1][iy - 1] == 4))){
					board[cx][cy] = board[ix][iy];
					board[ix - 1][iy - 1] = 0;
					board[ix][iy] = 0;
					computerTurn();
				}//NW jump
				if(board[ix][iy] == 3){
					if(((cx == ix - 1) || (cx == ix + 1)) && (cy == iy + 1) && (board[cx][cy] == 0)){
						board[cx][cy] = board[ix][iy];
						board[ix][iy] = 0;
						computerTurn();
					}//SE, SW move
					if((cx == ix + 2) && (cy == iy + 2) && ((board[ix + 1][iy + 1] == 2) || (board[ix + 1][iy + 1] == 4))){
						board[cx][cy] = board[ix][iy];
						board[ix + 1][iy + 1] = 0;
						board[ix][iy] = 0;
						computerTurn();
					}//SE jump
					if((cx == ix - 2) && (cy == iy + 2) && ((board[ix - 1][iy + 1] == 2) || (board[ix - 1][iy + 1] == 4))){
						board[cx][cy] = board[ix][iy];
						board[ix - 1][iy + 1] = 0;
						board[ix][iy] = 0;
						computerTurn();
					}//SW jump
				}//King Specific Moves
				initialX = 0;
				initialY = 0;
				currX = 0;
				currY = 0;
			}//if
		}else{
			currX = 0;
			currY = 0;
			initialX = 0;
			initialY = 0;
		}//if not in bounds
	}//mouseReleased	
	
	private void computerTurn(){
		whoTurn = "Computer's Turn.";
		try{
			Thread.sleep(500);
		}catch(Exception e){
		}//sleep it so there's a delay after player moves
		whoTurn = "Your Turn.";
		for(int x = 0; x < 8; x ++){
			for(int y = 0; y < 8; y ++){
				if(board[x][y] == 4){//King only jumps:
					if((x < 6) && (y > 1)){
						if((board[x + 1][y - 1] == 1) || (board[x + 1][y - 1] == 3)){//NE jump:
							if(board[x + 2][y - 2] == 0){
								board[x + 2][y - 2] = board[x][y];
								board[x + 1][y - 1] = 0;
								board[x][y] = 0;
								return;
							}//if
						}//if
					}//if
					if((x > 1) && (y > 1)){
						if((board[x - 1][y - 1] == 1) || (board[x - 1][y - 1] == 3)){//NW jump:
							if(board[x - 2][y - 2] == 0){
								board[x - 2][y - 2] = board[x][y];
								board[x - 1][y - 1] = 0;
								board[x][y] = 0;
								return;
							}//if
						}//if
					}//if
				}//if
				if((board[x][y] == 2) || (board[x][y] == 4)){//King or Checker jumps:
					if((x < 6) && (y < 6)){
						if((board[x + 1][y + 1] == 1) || (board[x + 1][y + 1] == 3)){//SE jump:
							if(board[x + 2][y + 2] == 0){
								board[x + 2][y + 2] = board[x][y];
								board[x + 1][y + 1] = 0;
								board[x][y] = 0;
								return;
							}//if
						}//if
					}//if
					if((x > 1) && (y < 6)){
						if((board[x - 1][y + 1] == 1) || (board[x - 1][y + 1] == 3)){//SW jump:
							if(board[x - 2][y + 2] == 0){
								board[x - 2][y + 2] = board[x][y];
								board[x - 1][y + 1] = 0;
								board[x][y] = 0;
								return;
							}//if
						}//if
					}//if
				}//if
			}//for y
		}//for x
		for(int x = 0; x < 8; x ++){
			if(board[x][6] == 2){
				if(x < 7){
					if(board[x + 1][7] == 0){
						board[x + 1][7] = board[x][6];
						board[x][6] = 0;
						return;
					}//if
				}//if
				if(x > 0){
					if(board[x - 1][7] == 0){
						board[x - 1][7] = board[x][6];
						board[x][6] = 0;
						return;
					}//if
				}//if
			}//if
		}//for x
		for(int x = 0; x < 8; x ++){
			for(int y = 0; y < 8; y ++){
				if(board[x][y] == 4){//King only moves:
					if((x > 0) && (y > 0)){
						if(board[x - 1][y - 1] == 0){//NW move:
							board[x - 1][y - 1] = board[x][y];
							board[x][y] = 0;
							return;
						}//if
					}//if
					if((x < 7) && (y > 0)){
						if(board[x + 1][y - 1] == 0){//NE move:
							board[x + 1][y - 1] = board[x][y];
							board[x][y] = 0;
							return;
						}//if
					}//if
				}//if
				if((board[x][y] == 2) || (board[x][y] == 4)){//King or Checker moves:
					if((x < 7) && (y < 7)){
						if(board[x + 1][y + 1] == 0){//SE move:
							board[x + 1][y + 1] = board[x][y];
							board[x][y] = 0;
							return;
						}//if
					}//if
					if((x > 0) && (y < 7)){
						if(board[x - 1][y + 1] == 0){//SW move:
							board[x - 1][y + 1] = board[x][y];
							board[x][y] = 0;
							return;
						}//if
					}//if
				}//if it's black
			}//for y
		}//for x
	}//computerTurn
	
	private boolean stillPlaying(){
		int blackCounter = 0;
		int redCounter = 0;
		for(int x = 0; x < 8; x ++){
			for(int y = 0; y < 8; y ++){
				if((board[x][y] == 2) || (board[x][y] == 4)){
					blackCounter = blackCounter + 1;
				}
				if((board[x][y] == 1) || (board[x][y] == 3)){
					redCounter = redCounter + 1;
				}
			}//for y
		}//for x
		if((blackCounter == 0)){
			whoWon = "You Won!";
			return false;
		}else if(redCounter == 0){
			whoWon = "You Lost!";
			return false;
		}else{
			return true;
		}
	}//stillPlaying
	
	public int brdPosX(int xCo){
		return (int) ((float) (xCo - 37) / 68);
	}//brdPosX
	
	public int brdPosY(int yCo){
		return (int) ((float) (yCo - 56) / 68);
	}//brdPosX
	
	public void mouseClicked(MouseEvent c) {
	}//mouseClicked
	public void mouseEntered(MouseEvent e) {
	}//mouseEntered
	public void mouseExited(MouseEvent e) {
	}//mouseExited
	public void mouseMoved(MouseEvent m) {
	}//mouseMoved
}//class
