   import javax.swing.*;
   import java.awt.*;
   import java.awt.geom.AffineTransform;
   import java.util.*;
    
   public class Thwomp extends Weapon {
   
      private int myX, myY, myWidth, myTop, myBottom, count;
   
      public Thwomp(int x, int width, int top, int bottom, boolean normal) {
      
         myX = x;
         myY = top;
         
         myWidth = width;
      	
         myTop = top;
         myBottom = bottom;
         
         if(normal)
            switchGravity();
      }
      public int getX() {
         return myX;
      }
      public int getY() {
         return myY;
      }
      public int getWidth() {
         return myWidth;
      }
      public int getHeight() {
         return (myBottom-myTop)/2;
      }
      public double getRadius() {
         return 0;
      }
      
      public void setX(int x) {
         myX = x;
      }
      public void setY(int y) {
         myY = y;
      }
      public void setWidth(int x) {
         myWidth = x;
      }
      public void draw(Graphics pen, JPanel panel, double xScroll, int framex, int framey) {
      
         count++;
         if(count > 50) {
            this.switchGravity();
            count = 0;
         }
               
         pen.setColor(Color.YELLOW);
         pen.fillRect((int)(myX - xScroll), myY, myWidth, getHeight()); 
      }
      public void switchGravity() {
      	
         if(myY == myTop) 
            myY = myBottom - getHeight();
         else
            myY = myTop;
      }
      public boolean weaponIsInContact(TestSubject phil, ArrayList<Ground> ground, ArrayList<Weapon> weapon) {
      	
         return (phil.getY() + phil.HEIGHT > this.getY()) &&
            		(phil.getY() < this.getY() + this.getHeight()) &&
            		(phil.getX() + phil.WIDTH > this.getX()) &&
            		(phil.getX() < this.getX() + this.getWidth());
      
      }
   
   }
   	
