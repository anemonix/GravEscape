//Robert Young, Parth Chopra, Thai-Co Nguyen
//GravEscape

   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   
   public class TestSubject implements GravityMoveable
   {
   	//constants
      public static final double MAX_X_SPEED = 10.0;
      public static final double MAX_Y_SPEED = 10.0;
      public static final int HEIGHT = 40;
      public static final int WIDTH = 40;
      
      //fields
      private double myX, myY, myXSpeed, myYSpeed;
      private int myNumJumps, arrayX, arrayY, runningCount;
      private boolean normGrav;
      private ImageIcon[][] drawImages = 
      {{new ImageIcon("upsideDownLeft0.png"), new ImageIcon("upsideDownLeft1.png"), new ImageIcon("upsideDownLeft2.png"), new ImageIcon("upsideDownLeft3.png"),  new ImageIcon("upsideDownLeft4.png"),
            new ImageIcon("upsideDownStanding.png"),
            new ImageIcon("upsideDownRight0.png"), new ImageIcon("upsideDownRight1.png"), new ImageIcon("upsideDownRight2.png"), new ImageIcon("upsideDownRight3.png"), new ImageIcon("upsideDownRight4.png")},
         {new ImageIcon("normalLeft0.png"), new ImageIcon("normalLeft1.png"), new ImageIcon("normalLeft2.png"), new ImageIcon("normalLeft3.png"), new ImageIcon("normalLeft4.png"),
            new ImageIcon("normalStanding.png"),
            new ImageIcon("normalRight0.png"), new ImageIcon("normalRight1.png"), new ImageIcon("normalRight2.png"), new ImageIcon("normalRight3.png"), new ImageIcon("normalRight4.png")}};
       
       //constructors
      public TestSubject(double xLoc, double yLoc)
      {
         myX = xLoc;
         myY = yLoc;
         myXSpeed = 0;
         myYSpeed = 0;
         arrayX = 5;
         arrayY = 1;
         normGrav = true;
         runningCount = 0;
      }
    
    //accessors
      public double getX()
      {
         return myX;
      }
      
      public double getY()
      {
         return myY;
      }
      
      public double getXSpeed()
      {
         return myXSpeed;
      }
   
      public double getYSpeed()
      {
         return myYSpeed;
      }
      
      public int getNumJumps()
      {
         return myNumJumps;
      }
      
   	//modifiers
      public void setX(double x)
      {
         myX = x;
      }
      
      public void setY(double y)
      {
         myY = y;
      }
      
      public void setXSpeed(double xSpeed)
      {
         myXSpeed = xSpeed;
      }
      
      public void setYSpeed(double ySpeed) 
      {
         myYSpeed = ySpeed;
      }
      
      public void setNumJumps(int jumps)
      {
         myNumJumps = jumps;
      }
   
   	//instance methods
   	
      public void switchGrav()
      {
         normGrav = !normGrav;
      }
   	
      public boolean canJump()
      {
         return myNumJumps < 2;
      }
   	   
      public void jumpUp()
      {
         myNumJumps++;
         myYSpeed -= 4.5;
      }
      
      public void jumpDown()
      {
         myNumJumps++;
         myYSpeed += 4.5;
      }
      
      public boolean isOnGround()
      {
         return Math.abs(myYSpeed) == .2 || myYSpeed == 0;
      }
   	      
      public void draw(Graphics g, double xScroll)
      {	
         if(normGrav)
            arrayY = 1;
         else
            arrayY = 0;
            
         if(this.getXSpeed() > .2)
         {
            if(arrayX < 6)
            {
               arrayX = 6;
               runningCount = 0;
            }
            
            else
            {
               runningCount++;
               if(runningCount == 10)
               {
                  if(arrayX == 10)
                     arrayX = 6;
                  else
                     arrayX++;
                  runningCount = 0;
               }
            }
         }
         
         else if(this.getXSpeed() < -.2)
         {
            if(arrayX > 4)
            {
               arrayX = 4;
               runningCount = 0;
            }
            
            else
            {
               runningCount++;
               if(runningCount == 10)
               {
                  if(arrayX == 0)
                     arrayX = 4;
                  else
                     arrayX--;
                  runningCount = 0;
               }
            }
         }
         
         else
         {
            arrayX = 5;
         }
      	
         
         g.drawImage(drawImages[arrayY][arrayX].getImage(), (int)(getX() - xScroll), (int)getY(), WIDTH, HEIGHT, null);
         //g.drawImage(drawImages[1][6].getImage(), (int)(getX() - xScroll), (int)getY(), WIDTH, HEIGHT, null);
      }
   }