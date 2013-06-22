   import java.awt.*;
   import javax.swing.*;

   public class Checkpoint {
    
      private double myX;   // x and y coordinates of the top of the flag
      private double myY;
      
   	private boolean isPast;
   	
      private ImageIcon flagPole = new ImageIcon("pole.png");
      private ImageIcon raisedFlag = new ImageIcon("flagpole.png");
      
   
      public Checkpoint(int x, int y) {
      
         myX = x;
         myY = y;
      }
      public double getX() {
         return myX;
      }
      public double getY() {
         return myY;
      }
             
   	 // modifier methods
      public void setX(double x)
      {
         myX = x;
      } 
      public void setY(double y)
      {
         myY = y;
      }
    
           //methods
   
      public boolean pastCheckpoint(TestSubject character) {
      
         if(character.getX() >= getX() ) {
         	isPast = true;
            return true;
         }
         return isPast;
      }
      public void draw(Graphics pen, JPanel panel, double xScroll, TestSubject phil) {
      
         if(pastCheckpoint(phil))
            pen.drawImage(raisedFlag.getImage(), (int)(myX - xScroll), (int)myY, 40, 80, panel);
         else
            pen.drawImage(flagPole.getImage(), (int)(myX - xScroll), (int)myY, 40, 80, panel);
      
      }
    
   }