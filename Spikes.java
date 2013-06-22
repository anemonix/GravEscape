   import javax.swing.*;
   import java.awt.*;
   import java.util.*;
   
   public class Spikes extends Weapon {
   
      private int myX;
      private int myY;
      private int myWidth;
      private int myHeight;
   	
      private ImageIcon setSpikes = new ImageIcon("spike.png");
      private int imageWidth = setSpikes.getIconWidth();
      private ImageIcon[] spikes;
   	
      public Spikes(int x, int y, int length) {
       
         myX = x;
         myY = y;
         myHeight = setSpikes.getIconHeight();
         spikes = new ImageIcon[length];
         myWidth = length * imageWidth;
         for(int k = 0; k < spikes.length; k++)
            spikes[k] = setSpikes;
      
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
         return myHeight;
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
      public void setWidth(int width) {
         myWidth = width;
      }
      public void setHeight(int height) {
         myHeight = height;
      }
      
   	
   	
      public boolean weaponIsInContact(TestSubject character, ArrayList<Ground> ground, ArrayList<Weapon> weapon) {
       
         if(character.getX() + character.WIDTH > myX && character.getX() < (myX + myWidth) &&
            	character.getY() + character.HEIGHT > myY && character.getY() < myY + myHeight)
            return true;
         return false;
      }
   	 
      public void draw(Graphics pen, JPanel panel, double xScroll, int framex, int framey) {
       
         for(int k = 0; k < spikes.length; k++) {
            pen.drawImage(spikes[k].getImage(),(int)( ( myX + (k * imageWidth) ) - xScroll ), myY, panel);
         }
      }
   
   }