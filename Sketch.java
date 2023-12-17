import processing.core.PApplet;
import processing.core.PImage;

/**
 * To create a game where the player must dodge snowflakes with the ability to melt and slow them down.
 * @author RyanChan25
 */
public class Sketch extends PApplet {
	
  // To create a PImage class for each of the images.
  PImage imgWinterbackground;
  PImage imgHeart;
  PImage imgGameOver;

  // Arrays for the amount of snowflakes drawn and if they can be seen.
  float[] fltSnowY = new float[25];
  float[] fltSnowX = new float[25];
  boolean [] blnBallHideStatus = new boolean[25];

  // Boolean variables to see if the snowflakes are hit, and if the up down and wasd keys are pressed.
  boolean blnSnowHit = false;
  boolean upPressed = false;
  boolean downPressed = false;
  boolean wPressed = false;
  boolean aPressed = false;
  boolean sPressed = false;
  boolean dPressed = false;
  
  // Int variables to see which snowflake got hit, the speed at which snow falls, location of where the player circle spawns, and the amount of lives the player has.
  int intWhichSnow = 0;
  int intSnowSpeed = 3;
  int intCircleX = 200;
  int intCircleY = 380;
  int intLives = 3;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
	  // Size call
    size(400, 400);
    
    // Call to load the images.
    imgWinterbackground = loadImage("Winter Background.jpg");
    imgHeart = loadImage("Heart.png");
    imgGameOver = loadImage("Game Over.png");
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    // Image drawn as the background
    image(imgWinterbackground, 0, 0, 400, 400);
    
    // For loop to initialise the snowflakes on the y coordinate setting them to a random location and a range 400 above the screen.
    for (int intSnowY = 0; intSnowY < fltSnowY.length; intSnowY++) {
      fltSnowY[intSnowY] = random(-400);
    }
    
    // For loop to initialise the snowflakes on the x coordinate setting them to a random location across the screen.
    for (int intSnowX = 0; intSnowX < fltSnowX.length; intSnowX++) {
      fltSnowX[intSnowX] = random(width);
    }
    // For loop to initialise the ballhidestatus and setting it to false.
    for (int intBooleanCount = 0; intBooleanCount < blnBallHideStatus.length; intBooleanCount++){
      blnBallHideStatus[intBooleanCount] = false;
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
    // Image mode changed to center.
    imageMode(CENTER);
    // Image background is drawn repeatedly.
	  image(imgWinterbackground, 200, 200, 400, 400);
    
    // For loop to draw the snowflakes based on the amount of the array.
    for (int i = 0; i < fltSnowY.length; i++) {
      // if statement to set the blnBallHideStatus for each of the snowflakes to false.
      if (blnBallHideStatus[i] == false) {
        stroke(0);
        fill(255);
        ellipse(fltSnowX[i], fltSnowY[i], 25, 25);

        // Variable to set the speed in which the snowflakes falls.
        fltSnowY[i] += intSnowSpeed;

        // if statement to see if the snow has reached the bottom and sends the back to the top with a different x coordinate.
        if (fltSnowY[i] > height) {
          fltSnowY[i] = 0;
          fltSnowX[i] = random(width);
        }    
        
        // if statemnet to stop the player detection with snowflakes once lives reach 0.
        if (intLives != 0) {
          // if statement to lower the players lives by one if the player is hit by snowflakes removing the snowflakes that was hit.
          if (dist(intCircleX, intCircleY, fltSnowX[i],fltSnowY[i]) <= 25) {
            intLives -= 1;
            blnBallHideStatus[i] = true;
          }
        }
        
        
        // if statement to hide the snowflakes that was hit.
        if (blnSnowHit == true) {
          blnBallHideStatus[intWhichSnow] = true;
        }
      }
    }
    // if statements to change the speed in which the snowflakes falls using the up and down arrow keys.
    if (downPressed) {
      intSnowSpeed = intSnowSpeed + 1;
      if (intSnowSpeed > 5) {
        intSnowSpeed = 5;
      }
    }
    else if (upPressed) {
      intSnowSpeed = intSnowSpeed - 1;
      if (intSnowSpeed < 1) {
        intSnowSpeed = 1;
      }
    }
    
    // if statements to control the movement of the player using the wasd keys.
    if (wPressed) {
      intCircleY -= 3;
    }
    if (sPressed) {
      intCircleY += 3;
    }
    if (aPressed) {
      intCircleX -= 3;
    }
    if (dPressed) {
      intCircleX += 3;
    }
    
    // ellipse to draw the blue player circle.
    stroke(0);
    fill(0, 0, 255);
    ellipse(intCircleX, intCircleY, 25, 25);

    // if statement to add edge detection for the player circle.
    if (intCircleX > width) {
      intCircleX = width;
    }
    else if (intCircleX < 0) {
      intCircleX = 0;
    }

    if (intCircleY > height) {
      intCircleY = height;
    }
    else if (intCircleY < 0) {
      intCircleY = 0;
    }

    // if statement to show the amount of hearts the player has and when it is 
    if (intLives == 3) {
      image(imgHeart, 320, 20, 40, 40);
      image(imgHeart, 350, 20, 40, 40);
      image(imgHeart, 380, 20, 40, 40);
    }
    else if (intLives == 2) {
      image(imgHeart, 320, 20, 40, 40);
      image(imgHeart, 350, 20, 40, 40);
    }
    else if (intLives == 1) {
      image(imgHeart, 320, 20, 40, 40);
    }
    else if (intLives == 0) {
      background(255);
      image(imgGameOver, 200, 200, 250, 250);
    }
  }
  
  /**
   * Method to see if the mouse if clicked on a snowflake hiding the snowflake hit.
   */
  public void mousePressed() {
    // for loop to find the snowflake that is pressed.
    for (int i = 0; i < fltSnowY.length; i++) {
      // if statement to see if the mouse is hovering over a snowflake and changes the visibiltiy of the snowball pressed.
      if (dist((float) mouseX, (float) mouseY, fltSnowX[i], fltSnowY[i]) <= 25) {
        blnSnowHit = true;
        intWhichSnow = i;
      }
    }
  }
    
  /**
   * Method that checks when the keys have been released and changes the boolean value to true if so.
   */
  public void keyPressed() {
    // if statements to see if the up and down keys are pressed, setting the value to true.
    if (keyCode == UP) {
      upPressed = true;
    }
    else if (keyCode == DOWN) {
      downPressed = true;
    }
    
    // if statements to see if the wasd keys are pressed, setting the value to true.
    if (key == 'w' || key == 'W') {
      wPressed = true;
    }
    else if (key == 'a' || key == 'A') {
      aPressed = true;
    }
    else if (key == 's' || key == 'S'){
      sPressed = true;
    }
    else if (key == 'd' || key == 'D'){
      dPressed = true;
    }
  }

  /**
   * Method that checks when the keys have been released and changes the boolean value to false if so.
   */
  public void keyReleased() {
    // if statements to see if the up and down keys are released, setting the value to false.
    if (keyCode == UP) {
      upPressed = false;
    }
    else if (keyCode == DOWN) {
      downPressed = false;
    }
    
    // if statements to see if the wasd keys are released, setting the value to false.
    if (key == 'w' || key == 'W') {
      wPressed = false;
    }
    else if (key == 'a' || key == 'A') {
      aPressed = false;
    }
    else if (key == 's' || key == 'S'){
      sPressed = false;
    }
    else if (key == 'd' || key == 'D'){
      dPressed = false;
    }
  }
}