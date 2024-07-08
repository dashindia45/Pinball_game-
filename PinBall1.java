import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class PinBall1 {
// Create window objects
private Frame frame = new Frame(" Pinball games ");
// Window width
private final int WIDTH = 300;
// Window height
private final int HEIGHT = 400;
//create a variable which stores the score
private int score=0;
private int c=1;
// The width and height of the board
private  int RACKET_WIDTH = 70;
private final int RACKET_HEIGHT = 20;
// Record the coordinates of the board
private int racketX = 120;
// The board can only move horizontally , Cannot move vertically
private final int racketY = 340;
// The size of the ball , That is, the diameter and length of the circle
private final int BALL_SIZE = 16;
// Record the coordinates of the ball
private int ballX = 120;
private int ballY = 20;
// Record the ball in x and y The speed of movement in the axial direction
private int speedX = 5;
private int speedY = 10;
// Identifies whether the game is over ,true For the end
private boolean isOver = false;
// Declare timer
private Timer timer;
// Custom class , Inherit canvas, Act as a Sketchpad
private class MyCanvas extends Canvas{
	//function for change background color
	public void setBackground(Color c){
		super.setBackground(c);
	}
	//setting background based on level
	Color col=new Color(0,0,0);
@Override
public void paint(Graphics g) {
// Draw content
//if(score>c*5){
//	col=new Color(130,0,130);
	//level naming by score
	
 //   g.setFont(new Font("Times",Font.BOLD,15));
 //   g.drawString("Level"+c,130,20);
//}
setBackground(col);
if(isOver){// Game over

g.setColor(Color.blue);
g.setFont(new Font("Times",Font.BOLD,30));
g.drawString(" Game over !",50,200);
g.setColor(Color.red);
//after game over display score
g.drawString("Score:- "+score,60,230);
}else{// In the game
// Draw the ball
g.setColor(Color.RED);
g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);
// Draw board
g.setColor(Color.blue);
//drawing racket
g.fillRect(racketX,racketY,RACKET_WIDTH,RACKET_HEIGHT);
g.setColor(Color.red);
//displaying score
g.setFont(new Font("Times",Font.BOLD,15));
//every time display score
g.drawString("Score:- "+score,210,20);
g.setFont(new Font("Times",Font.BOLD,15));
//chaging level based on score
    g.drawString("Level"+c,130,20);
//background change by level
}
}
}
// Create a painting area
MyCanvas drawArea = new MyCanvas();
public void init(){
// Complete the change of racket coordinates
KeyListener listener = new KeyAdapter() {
@Override
public void keyPressed(KeyEvent e) {
// Get the currently pressed key
int keyCode = e.getKeyCode();
if(keyCode == KeyEvent.VK_LEFT){
// Left arrow moving to the left
if(racketX > 0){// When not on the left
racketX -= 20;
}
}
if(keyCode == KeyEvent.VK_RIGHT){
// Right arrow moving to the right
if(racketX < (WIDTH - RACKET_WIDTH)){// Table width minus racket width
racketX += 20;
}
}
}
};
// to frame Windows and drawArea Painting area registration listener
frame.addKeyListener(listener);
drawArea.addKeyListener(listener);
// Control of spherical coordinates
ActionListener task = new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
// Update the spherical coordinates , Redraw picture
// Correct the positive and negative velocity according to the boundary range
// When the ball touches the left and right boundaries, modify the direction of motion of the ball
if(ballX <= 0 || ballX >= (WIDTH - BALL_SIZE)){
speedX = -speedX;// Move in the opposite direction
}
// When the ball touches the upper boundary or below the horizontal line of the board and does not touch the board, change the direction of motion of the ball
if(ballY <= 0 || (ballY > racketY - BALL_SIZE && ballX > racketX && ballX < racketX + RACKET_WIDTH)){
speedY = -speedY;// Move in the opposite direction
//when ball hits racket then add score by +1
if(ballY>0)

	score++;

if(score>5*c&&RACKET_WIDTH>10){
	RACKET_WIDTH=RACKET_WIDTH-10*c;
	drawArea.col=new Color(150,0,150);
	c++;
}
}

if(ballY > racketY - BALL_SIZE && (ballX < racketX || ballX > racketX + RACKET_WIDTH)){
if(ballY == HEIGHT){// When the ball falls to the bottom position, it stops the game
// The current ball is beyond the horizontal line of the racket and beyond the width of the racket , Game over
timer.stop();// Stop timer
System.out.println(" The game has finished running !");
// Modify the game to mark the end
isOver = true;
// Redraw picture
drawArea.repaint();
}
}
// Update the coordinates of the ball
ballX += speedX;
ballY += speedY;
// Redraw the interface
drawArea.repaint();
}
};
// every other 100 Execution in milliseconds task The listener refreshes the drawing interface
timer = new Timer(110,task);
timer.start();// Start execution
// Set the size of the sketchpad
drawArea.setSize(WIDTH,HEIGHT);
frame.add(drawArea);
frame.setLocation(700,250);// The window position is centered
frame.pack();// Best size
frame.setVisible(true);// Window display
// Click on the X End operation
frame.addWindowListener(new WindowAdapter() {
@Override
public void windowClosing(WindowEvent e) {
System.out.println(" The game has finished running !");
System.exit(0);
}
});
}
public static void main(String[] args) {
new PinBall1().init();
}
}