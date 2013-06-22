//Robert Young, Parth Chopra, Thai-Co Nguyen
//GravEscape

   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   import java.util.Scanner;
   import java.io.*;

//---------------------MAIN DISPLAY/CONTENT PANEL-------------------------------------
   public class GravEscapePanel extends JPanel
   {
   //----------------FIELD INITIALIZATION-----------------------
   
   	//------Music--------//
      private static MusicPlayer musicPlayer = new MusicPlayer();
   
   //----------Fields for Imaging and Graphics
      private static final int FRAME_X = 700;
      private static final int FRAME_Y = 450;
      private BufferedImage myImage;
      private Graphics myBuffer;
      private ImageIcon menuPic = new ImageIcon("menuPic.jpg");
      private ImageIcon highScoresPic = new ImageIcon("highScore.jpg");
      private ImageIcon instructionsPic = new ImageIcon("Instructions.jpg");
      private ImageIcon controlsPic = new ImageIcon("Instructions1.jpg");
      private ImageIcon weaponsPic = new ImageIcon("Instructions2.jpg");
      private ImageIcon creditsPic = new ImageIcon("Credits.jpg");
      private ImageIcon background = new ImageIcon("background.jpg");
      private Timer animator;
      private boolean isMenuUp, isGameUp, isCreditsUp, isHighScoresUp, isInstructionsUp, isControlsUp, isWeaponsUp, isPaused;
      private Gravity myGrav;
      private Level currentLevel;
      private String[] highScoreNames;
      private int[] highScores;
      
      private boolean[] keys = new boolean[4];
      private static final int LEFT = 0;
      private static final int UP = 1;
      private static final int DOWN = 2;
      private static final int RIGHT = 3;
      
   	//------------Fields for Actual Game
      private TestSubject phil;
      private long startTime;
      
   	//-------------MAIN PANEL----------------
      public GravEscapePanel()
      {
         myImage =  new BufferedImage(FRAME_X, FRAME_Y, BufferedImage.TYPE_INT_RGB);
         myBuffer = myImage.getGraphics();
         
         isMenuUp = true;
         myBuffer.drawImage(menuPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
         
         animator = new Timer(13, new AnimatorListener());
         animator.start();
         addKeyListener(new KeyReader());
         setFocusable(true);
         
         Scanner highScoreScanner = null;
         try
         {
            highScoreScanner = new Scanner(new File("Highscores.txt"));
         }
            catch(java.io.FileNotFoundException c){}
               	
         highScoreNames = new String[5];
               	
         for(int i = 0; i < highScoreNames.length; i++)
            highScoreNames[i] = highScoreScanner.nextLine();
               	
         highScores = new int[5];
               	
         for(int i = 0; i < highScores.length; i++)
            highScores[i] = highScoreScanner.nextInt();
               	
         sort(highScores, highScoreNames);
         
      	//----------Music Thread----------//
         musicPlayer.initializeMusic("Harmful or Fatal.mp3");
         musicPlayer.playMusic();
      
      }
      
      public void paintComponent(Graphics g)
      {
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
      }
      
      private class AnimatorListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            if(isHighScoresUp)
            {
               myBuffer.drawImage(highScoresPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
               for(int i = 0; i < highScores.length; i++)
               {
                  myBuffer.setFont(new Font("Arial", Font.PLAIN, 30));
                  int seconds = highScores[i];
                  myBuffer.setColor(Color.WHITE);
                  myBuffer.drawString("" + seconds/60 + ":" + (seconds % 60)/10 + seconds % 10, 250, 150 + 60 * i);
                  myBuffer.drawString(highScoreNames[i], 350, 150 + 60 * i);
               }
            }
            if(isCreditsUp)
               myBuffer.drawImage(creditsPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
            if(isInstructionsUp)
               myBuffer.drawImage(instructionsPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
            if(isMenuUp)
               myBuffer.drawImage(menuPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
            if(isControlsUp)
               myBuffer.drawImage(controlsPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
            if(isWeaponsUp)
               myBuffer.drawImage(weaponsPic.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
         	
            if(isGameUp && !isPaused)
            {
            	//changing things
               phil.setX(phil.getX() + phil.getXSpeed());
               phil.setY(phil.getY() + phil.getYSpeed());
               myGrav.actOn(phil);
               currentLevel.groundAffect();
               currentLevel.gravityAffect(myGrav);
               currentLevel.weaponsAffect();
               
            	//checking for player death
               if(currentLevel.weaponsAreInContact())
               {
                  phil.setXSpeed(0);
                  phil.setYSpeed(0);
                  
                  if(!myGrav.isNormGrav())
                  {
                     myGrav.switchGrav();
                     phil.switchGrav();
                  }
               	
                  if(currentLevel.getMostRecentCheckpoint() == null)
                  {
                     phil.setX(0);
                     phil.setY(200);
                  }
                  
                  else
                  {
                     Checkpoint recent = currentLevel.getMostRecentCheckpoint();
                     phil.setX(recent.getX());
                     phil.setY(recent.getY());
                     currentLevel.setScroll(recent.getX() - 200);
                  }
               }
              
               if(phil.isOnGround())
                  phil.setNumJumps(0);
            	
               if(keys[RIGHT])
               {
                  phil.setXSpeed(phil.getXSpeed() + .5);
               }
               else if(keys[LEFT])
               {	
                  phil.setXSpeed(phil.getXSpeed() - .5);
               }
               else
               {
                  phil.setXSpeed(phil.getXSpeed() * .85);
               }
            	
            	//fixing too high velocity
               if(phil.getXSpeed() > phil.MAX_X_SPEED)
                  phil.setXSpeed(phil.MAX_X_SPEED);
            		
               if(phil.getXSpeed() < -1 * phil.MAX_X_SPEED)
                  phil.setXSpeed(-1 * phil.MAX_X_SPEED);
                  
               if(phil.getYSpeed() > phil.MAX_Y_SPEED)
                  phil.setYSpeed(phil.MAX_Y_SPEED);
            		
               if(phil.getYSpeed() < -1 * phil.MAX_Y_SPEED)
                  phil.setYSpeed(-1 * phil.MAX_Y_SPEED);
            	
            	//fixing out of bounds
               if(phil.getX() < 200)
               {
                  if(phil.getX() < 0)
                     phil.setX(0);
                  currentLevel.setScroll(0);
               }
               
               else if(phil.getX() + phil.WIDTH > currentLevel.getWidth() - 200)
               {	
                  if(phil.getX() > currentLevel.getWidth() - phil.WIDTH)
                  {
                     int totalTime = (int)((System.currentTimeMillis() - startTime)/1000);
                     String name = null;
                     while(name == null)
                        name = JOptionPane.showInputDialog("Congratulations, Enter Name for High Score!");
                     isHighScoresUp = true;
                     isGameUp = false;
                     
                     for(int i = 0; i < highScores.length; i++)
                        if(totalTime < highScores[i])
                        {
                           for(int k = highScores.length - 2; k >= i; k--)
                           {
                              highScores[k + 1] = highScores[k];
                              highScoreNames[k + 1] = highScoreNames[k];
                           }
                           highScores[i] = totalTime;
                           highScoreNames[i] = name;
                           break;
                        }
                     PrintStream highScorePrinter = null;
                     try{
                        highScorePrinter = new PrintStream(new File("Highscores.txt"));
                     }
                        catch(java.io.FileNotFoundException c){}
                     for(int i = 0; i < highScoreNames.length; i++)
                        highScorePrinter.println(highScoreNames[i]);
                     for(int i = 0; i < highScores.length; i++)
                        highScorePrinter.println("" + highScores[i]);
                  }
                  currentLevel.setScroll(currentLevel.getWidth() - FRAME_X);
               }
               
               else if(phil.getX() + phil.WIDTH > FRAME_X - 200 + currentLevel.getScroll() && phil.getXSpeed() > 0)
               {
                  currentLevel.setScroll(currentLevel.getScroll() + phil.getXSpeed());
               }
                  
               else if(phil.getX() < 200 + currentLevel.getScroll() && phil.getXSpeed() < 0)
               {
                  currentLevel.setScroll(currentLevel.getScroll() + phil.getXSpeed());
               }
            		
               if(phil.getY() + phil.HEIGHT > FRAME_Y)
               {
                  phil.setYSpeed(0);
                  phil.setY(FRAME_Y - phil.HEIGHT);
               }
                  
               if(phil.getY() < 0)
               {
                  phil.setYSpeed(0);
                  phil.setY(0);
               }
            	
            	//drawing things
               myBuffer.setColor(Color.BLACK);
               //myBuffer.fillRect(0, 0, FRAME_X, FRAME_Y);
               myBuffer.drawImage(background.getImage(), 0, 0, FRAME_X, FRAME_Y, null);
               phil.draw(myBuffer, currentLevel.getScroll());
               currentLevel.draw(myBuffer, GravEscapePanel.this, FRAME_Y);
               myBuffer.setColor(Color.WHITE);
               myBuffer.setFont(new Font("Arial", Font.PLAIN, 40));
               int seconds = (int)((System.currentTimeMillis() - startTime)/1000);
               myBuffer.drawString(seconds/60 + ":" + (seconds % 60)/10 + seconds % 10, 600, 440);
            }
            
            repaint();
         }
      }
      
      private class KeyReader extends KeyAdapter
      {
         public void keyPressed(KeyEvent e)
         {
            if(isGameUp == true)
            {			
               if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
               {
                  keys[RIGHT] = true;
               }
               
               else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
               {
                  keys[LEFT] = true;
               }
               
               if((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && 
               	phil.canJump() && myGrav.isNormGrav())
               {
                  phil.jumpUp();
               }
               
               else if((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && 
               	phil.canJump() && !myGrav.isNormGrav())
               {
                  phil.jumpDown();
               }
            }
            
            if(isMenuUp == true)
            {
               if(e.getKeyCode() == KeyEvent.VK_S)
               {
                  isMenuUp = false;
                  isGameUp = true;
                  
                  initializeGame();
               }
               
               if(e.getKeyCode() == KeyEvent.VK_I)
               {
                  isMenuUp = false;
                  isInstructionsUp = true;
               }
               
               if(e.getKeyCode() == KeyEvent.VK_H)
               {
                  isMenuUp = false;
                  isHighScoresUp = true;
               }
               
               if(e.getKeyCode() == KeyEvent.VK_C)
               {
                  isCreditsUp = true;
                  isMenuUp = false;
               }
               
               if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
               {
                  System.exit(0);
               }
            }
         }
         
         public void keyReleased(KeyEvent e)
         {
            if((isInstructionsUp || isHighScoresUp || isCreditsUp || isControlsUp || isWeaponsUp) && e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
               isInstructionsUp =  false;
               isHighScoresUp =  false;
               isCreditsUp = false;
               isControlsUp = false;
               isWeaponsUp = false;
               isMenuUp = true;
            }
            
            else if(isInstructionsUp == true)
            {
               if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
               {
                  isInstructionsUp = false;
                  isControlsUp = true;
               }
            }
            else if(isControlsUp == true)
            {
               if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
               {
                  isControlsUp = false;
                  isWeaponsUp = true;
               }
               if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
               {
                  isControlsUp = false;
                  isInstructionsUp = true;
               }
            }
            else if(isWeaponsUp == true)
            {
               if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
               {
                  isWeaponsUp = false;
                  isControlsUp = true;
               }
            }
         
            
            if(isGameUp)
            {
               if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
               {
                  isPaused = true;
                  long totalTime = System.currentTimeMillis() - startTime;
               	
                  if(JOptionPane.showConfirmDialog(new JFrame(),
                  	"Would you like to exit to the menu?","Pause",
                  	JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                  {
                     isMenuUp = true;
                     isGameUp = false;
                  }
                  
                  isPaused = false;
                  startTime = System.currentTimeMillis() - totalTime;
               }
               
               if(e.getKeyCode() == KeyEvent.VK_SPACE && phil.isOnGround())
               {
                  myGrav.switchGrav();
                  phil.switchGrav();
               }
             
               if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
               {
                  keys[RIGHT] = false;
               }
               
               else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
               {
                  keys[LEFT] = false;
               }  
            	
            }
         }
      }
      
      public static void sort(int[] sortArray, String[] other)
      {
         int maxPos;
         for(int k = 0; k < sortArray.length; k++)
         {
            maxPos = findMax(sortArray, sortArray.length - k);
            swap(sortArray, other, maxPos, sortArray.length - k - 1);
         }
      }
      public static int findMax(int[] sortArray, int upper)
      {
         if(sortArray.length == 0)
            throw new RuntimeException("Array size is 0.");
        
         int max = sortArray[0];
         int maxIndex = 0;
         
         for(int k = 1; k < upper; k++)
            if(sortArray[k] > max)
            {
               max = sortArray[k];
               maxIndex = k;
            }
      		
         return maxIndex;
      
      }
      public static void swap(int[] sortArray, String[] other, int a, int b)
      {
         int aValue = sortArray[b];
         String aString = other[b];
         sortArray[b] = sortArray[a];
         other[b] = other[a];
         sortArray[a] = aValue;
         other[a] = aString;
      }
   	
      public void initializeGame() 
      {
         phil = new TestSubject(0.0, 100.0);
         
         myGrav = new Gravity();
         
         startTime = System.currentTimeMillis();
         
         for(int i = 0; i < keys.length; i++)
            keys[i] = false;
         
         initializeLevel(1);
      }
      
      public void initializeLevel(int levelNum)    
      {
         /*Scanner levelFile = null;
         try
         {
            levelFile = new Scanner(new File("Level" + levelNum + ".txt"));
         }
            catch(java.io.FileNotFoundException e)
            {
               System.out.println("Level file not found. System exiting :(");
               System.exit(1);
            }
         
         double width = levelFile.nextDouble();*/
      	
         currentLevel = new Level(phil, 13000.0);
         
      	//0-2500 easiest
      	
         currentLevel.myGround.add(new Ground(0, 400, 1400, 100));
         currentLevel.myGround.add(new Ground(0, 0, 2200, 50));
         
         currentLevel.myGround.add(new Ground(200, 200, 100, 500));
         currentLevel.myGround.add(new Ground(500, 50, 100, 200));
      	
         currentLevel.myGround.add(new Ground(800, 200, 100, 500));
         currentLevel.myGround.add(new Ground(1100, 50, 100, 200));
      	
         currentLevel.myWeapons.add(new Spikes(1400, 440, 60));
         
         currentLevel.myGround.add(new Ground(2180, 400, 320, 100));
      	
         currentLevel.myWeapons.add(new RotatingSpikes(2330, 300, 150));
         currentLevel.myWeapons.add(new Spikes(2200, -12, 25));
      	
         currentLevel.myCheckpoints.add(new Checkpoint(2500, 333));
      	
      	//2500-5000 easy
      	
         currentLevel.myGround.add(new Ground(2500, 400, 2500, 100));
         currentLevel.myGround.add(new Ground(2500, 0, 2500, 50));
         
         currentLevel.myGround.add(new Ground(2600, 0, 50, 300));
          
         //currentLevel.myWeapons.add(new RocketLauncher(phil, 2600, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 2700, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 2800, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 2900, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3000, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3100, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3200, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3300, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3400, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3500, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3600, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3700, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3800, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3900, 100)); 
         //currentLevel.myWeapons.add(new RocketLauncher(phil, 2600, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 2700, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 2800, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 2900, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3000, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3100, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3200, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3300, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3400, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3500, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3600, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3700, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3800, 110)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 3900, 110)); 
         currentLevel.myWeapons.add(new RotatingSpikes(4500, 300, 150)); 
         
         currentLevel.myGround.add(new Ground(2600, 250, 100, 50)); 
         currentLevel.myGround.add(new Ground(2900, 250, 100, 50)); 
         currentLevel.myGround.add(new Ground(3200, 250, 100, 50)); 
         currentLevel.myGround.add(new Ground(3500, 250, 100, 50)); 
         currentLevel.myGround.add(new Ground(3800, 250, 100, 50)); 
         currentLevel.myGround.add(new Ground(4100, 90, 100, 50)); 
         currentLevel.myGround.add(new Ground(4700, 90, 100, 50)); 
         
         currentLevel.myWeapons.add(new Thwomp(4800, 100, 50, 400, true));
         
         currentLevel.myCheckpoints.add(new Checkpoint(5000, 333));
      	
      	//5000 - 7500 medium
         currentLevel.myGround.add(new Ground(5000, 0, 2500, 50)); 
         currentLevel.myGround.add(new Ground(5000, 400, /*14*/2500, 100)); 
         currentLevel.myWeapons.add(new RocketLauncher(phil, 5200, 150)); 
         currentLevel.myGround.add(new Ground(5250, 0, 50, 300)); 
         currentLevel.myGround.add(new Ground(5350, 350, 250, 50)); 
         currentLevel.myWeapons.add(new RotatingSpikes(5400, 350, 25)); 
         currentLevel.myWeapons.add(new Spikes(5500, 40, 20));  
         currentLevel.myWeapons.add(new Spikes(5800, 390, 5)); 
         currentLevel.myWeapons.add(new Spikes(6000, 390, 5)); 
         currentLevel.myWeapons.add(new Spikes(6200, 390, 5)); 
         currentLevel.myWeapons.add(new Spikes(6400, 440, 5)); 
         currentLevel.myWeapons.add(new Spikes(6600, 440, 5)); 
         currentLevel.myWeapons.add(new Spikes(5800, 40, 5)); 
         currentLevel.myWeapons.add(new Spikes(6000, 40, 5)); 
         currentLevel.myWeapons.add(new Spikes(6200, 40, 5)); 
         currentLevel.myWeapons.add(new Spikes(6400, 40, 5)); 
         currentLevel.myWeapons.add(new Spikes(6600, 40, 5)); 
         currentLevel.myWeapons.add(new RotatingSpikes(6200, 150, 100)); 
         currentLevel.myWeapons.add(new RotatingSpikes(6700, 390, 100)); 
         currentLevel.myWeapons.add(new RotatingSpikes(6600, 340, 100)); 
         currentLevel.myWeapons.add(new RotatingSpikes(5550, 340, 100));
         currentLevel.myGround.add(new Ground(5700, 200, 30, 150));   
         currentLevel.myGround.add(new Ground(5900, 100, 30, 100)); 
         
         currentLevel.myWeapons.add(new RotatingSpikes(6900, 360, 100));
         currentLevel.myWeapons.add(new RotatingSpikes(7030, 90, 100));
         currentLevel.myWeapons.add(new RotatingSpikes(7300, 360, 50));
         currentLevel.myWeapons.add(new RotatingSpikes(7400, 90, 50));
      	
         currentLevel.myCheckpoints.add(new Checkpoint(7500, 333));
         
      	      	
      	//7500-10000 very hard
      	
         currentLevel.myGround.add(new Ground(7500, 400, 2500, 100));
         currentLevel.myGround.add(new Ground(7500, 0, 2500, 50));
         
         currentLevel.myWeapons.add(new RotatingSpikes(7800, 50, 50));
         currentLevel.myWeapons.add(new RotatingSpikes(8150, 50, 100));
         currentLevel.myWeapons.add(new RotatingSpikes(8500, 50, 150));
      	
         currentLevel.myWeapons.add(new RotatingSpikes(7800, 400, 150));
         currentLevel.myWeapons.add(new RotatingSpikes(8150, 400, 100));
         currentLevel.myWeapons.add(new RotatingSpikes(8500, 400, 50));
         
         currentLevel.myGround.add(new Ground(9100, 0, 100, 350));
         
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9300, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9350, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9400, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9450, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9500, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9550, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9600, 225));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 9650, 225));
      	
         currentLevel.myGround.add(new Ground(9750, 100, 100, 350));
         
         currentLevel.myCheckpoints.add(new Checkpoint(10000, 333));
         
      	//10000-13000 very hard
      	
         currentLevel.myGround.add(new Ground(7500 + 2500, 400, 3000, 100));
         currentLevel.myGround.add(new Ground(7500 + 2500, 0, 3000, 50));
         
         currentLevel.myGround.add(new Ground(7500 + 2600, 150, 800, 30));
         currentLevel.myWeapons.add(new Spikes(7500 + 2600, 138, 58));
         currentLevel.myGround.add(new Ground(7500 + 3300, 0, 100, 180));
         currentLevel.myWeapons.add(new RotatingSpikes(7500 + 3300, 75, 25));
         currentLevel.myWeapons.add(new Spikes(7500 + 2620, 288, 60));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 7500 + 3000, 350));
         currentLevel.myWeapons.add(new RotatingSpikes(7500 + 3400, 350, 50));
      	
         currentLevel.myGround.add(new Ground(7500 + 3425, 300, 100, 100));
         
         currentLevel.myWeapons.add(new Thwomp(7500 + 3500, 100, 50, 400, true));
         currentLevel.myWeapons.add(new Thwomp(7500 + 3700, 100, 50, 400, false));
         currentLevel.myWeapons.add(new Thwomp(7500 + 3900, 100, 50, 400, true));
         currentLevel.myWeapons.add(new Thwomp(7500 + 4100, 100, 50, 400, false));
         currentLevel.myWeapons.add(new Thwomp(7500 + 4300, 100, 50, 400, true));
         currentLevel.myWeapons.add(new Thwomp(7500 + 4500, 100, 50, 400, false));
         currentLevel.myWeapons.add(new Thwomp(7500 + 4700, 100, 50, 400, true));
         currentLevel.myWeapons.add(new Thwomp(7500 + 4900, 100, 50, 400, false));
         
         currentLevel.myCheckpoints.add(new Checkpoint(12950, 333));
      	
      	         
      	
         
         
      	
      	
      	
      	
      	
      	
      	
      	/*currentLevel.myGround.add(new Ground(0, 400, 10000, 100, Color.GRAY));
         currentLevel.myGround.add(new Ground(0, 0, 10000, 50, Color.GRAY));
         currentLevel.myGround.add(new Ground(500, 200, 100, 100, Color.GRAY));
         currentLevel.myGround.add(new Ground(150, 150, 100, 100, Color.GRAY));
         currentLevel.myGround.add(new Ground(100, 300, 200, 50, Color.GRAY));
      	
         currentLevel.myWeapons.add(new RocketLauncher(phil, 6800, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 6850, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 6900, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 6950, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 7000, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 7050, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 7100, 150));
         currentLevel.myWeapons.add(new RocketLauncher(phil, 7150, 150));
         
         currentLevel.myWeapons.add(new Spikes(500, 300, 200));
         
         currentLevel.myWeapons.add(new RotatingSpikes(1900, 200, 25));
         currentLevel.myWeapons.add(new RotatingSpikes(2500, 200, 50));
         currentLevel.myWeapons.add(new RotatingSpikes(3000, 200, 100));
         currentLevel.myWeapons.add(new RotatingSpikes(3500, 200, 150));
      	
         currentLevel.myCheckpoints.add(new Checkpoint(2000, 332));*/
      	
      
         /*while(levelFile.hasNext())
            //for(int i = 0; i < 15; i++)
         {
            if(levelFile.next().equals("ground"))
            {
               int x = levelFile.nextInt();
               int y = levelFile.nextInt();
               int xWid = levelFile.nextInt();
               int yWid = levelFile.nextInt();
               currentLevel.myGround.add(new Ground(x, y, xWid, yWid, Color.GRAY));
            }
            
            if(levelFile.next().equals("rocket"))
            {
               int x = levelFile.nextInt();
               int y = levelFile.nextInt();
               currentLevel.myWeapons.add(new RocketLauncher(phil, x, y));
            }
            
            if(levelFile.next().equals("checkpoint"))
            {
               int x = levelFile.nextInt();
               int y = levelFile.nextInt();
               currentLevel.myCheckpoints.add(new Checkpoint(x, y));
            }
            
            /*if(levelFile.next().equals("axe"))
            {
              
            }
            
            if(levelFile.next().equals("laser"))
            {
            
            }
            
            if(levelFile.next().equals("spikes"))
            {
            
            }
            
            if(levelFile.next().equals("rotatingSpikes"))
            {
               int x = levelFile.nextInt();
               int y = levelFile.nextInt();
            }
         }*/
      }
   }