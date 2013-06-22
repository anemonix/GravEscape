   import java.awt.*;
   import javax.swing.*;
   import java.awt.image.BufferedImage;
   import java.util.*;


    
   public class Rocket {
    
      private int myX;
      private int myY;
      
      private int xOriginal;
      private int yOriginal;
    
      private int myRadius;
    
      private int dx;
      private int dy;
      
      private ImageIcon rocket = new ImageIcon("spikes.jpg");
   
    
      public Rocket(int x, int y, int radius) {
      
         myX = x;
         myY = y;
         xOriginal = x;
         yOriginal = y;
         myRadius = radius;
         
      
      }
    
    
      public int getX() {
         return myX;
      }
      public int getY() {
         return myY;
      }
      public int getdx() {
         return dx;
      }
      public int getdy() {
         return dy;
      }
      public int getRadius() {
         return myRadius;
      }
      public ImageIcon getImage() {
         return rocket;
      }
      //modifier
      public void setX(int x) {
         myX = x;
      }
      public void setY(int y) {
         myY = y;
      }
      public void setdx(double dx) {
         dx = dx;
      }
      public void setdy(double dy) {
         dy = dy;
      }
      public void setRadius(int radius) {
         myRadius = radius;
      }
   
      public void draw(Graphics pen, double xScroll, int framex, int framey, int rocketX, int rocketY) {
      	
         if(getX() > framex || getY() > framey) {
            setX(rocketX);
            setY(rocketY);
         } 
      
         pen.setColor(Color.RED);
         pen.fillOval((int)(myX - myRadius - xScroll),(int)(myY - myRadius), 2 * myRadius, 2 * myRadius); 
      
      }
      public boolean weaponIsInContact(TestSubject character, ArrayList<Ground> ground, ArrayList<Weapon> weapons) 
      {
         for(int k = 0; k < ground.size(); k++) 
         {
            Ground g = ground.get(k);
            if(myX + getRadius() > g.getX() && myX - getRadius() < g.getX() + g.getXWidth() &&
            myY + getRadius() > g.getY() && myY - getRadius() < g.getY() + g.getYWidth())
            {
               myX = xOriginal;
               myY = yOriginal;
               break;
            }
         }
         
         if(myX + getRadius() > character.getX() && myX - getRadius() < character.getX() + character.WIDTH &&
         	myY + getRadius() > character.getY() && myY - getRadius() < character.getY() + character.HEIGHT)
         {
            myX = xOriginal;
            myY = yOriginal;
            return true;
         }
               
         return false;
               
         /*for(int k = 0; k < ground.size(); k++) 
         {
         	
            Ground g = ground.get(k);
            if(  myX <= Math.abs((g.getX() + g.getXWidth() )) && 
              myX >= Math.abs(g.getX()) &&
              myY >= Math.abs(g.getY()) &&
              myY <= Math.abs((g.getY() + g.getYWidth() )) )
            {
              
               myX = xOriginal;
               myY = yOriginal;
               groundCrash();
            }
         }
      	
         if(character.getX() >= Math.abs((myX - myRadius)) && character.getX() <= Math.abs((myX + myRadius)) &&
         character.getY() <= Math.abs((myY + myRadius)) && character.getY() >= Math.abs((myY - myRadius))) {
            characterCrash();
            return true;
         }
            
         for(int k = 0; k < weapons.size(); k++) {
         
            if(weapons.get(k).getX() >= Math.abs((myX - myRadius)) && weapons.get(k).getX() <= Math.abs((myX + myRadius)) &&
            weapons.get(k).getY() <= Math.abs((myY + myRadius)) && weapons.get(k).getY() >= Math.abs((myY - myRadius))) {
               
               groundCrash();  
               //return true;
               
            }
         }
         return false;*/
      }
      public void groundCrash() {
      
      }
      public void characterCrash() {
      
      }
      
   	// returns distance between (x1, y1) and (x2, y2)
      private double distance(double x1, double y1, double x2, double y2)
      {
         return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      }
   
   }