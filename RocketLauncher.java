   import java.awt.*;
   import javax.swing.*;
   import java.awt.event.*;
   import java.util.*;


    public class RocketLauncher extends Weapon {
   
      private int myX;
      private int myY;
   
      private double angle;
   
      private double deltaX;
      private double deltaY;
   
      private double dx; //speed * math.sin(angle)
      private double dy; //speed * math.cos(angle)
      
      private double mySpeed;
      
      private Rocket rocket;
   	   
         
       public RocketLauncher(TestSubject character, int x, int y) {
      
         myX = x;
         myY = y;
         
         deltaX = (int)(myX - character.getX());
         deltaY = (int)(myY - character.getY());
         //System.out.println(myX + " " + character.getX() + " " +myY + " " +character.getY());
         
         angle = Math.atan(deltaX/deltaY);
         if(deltaY > 0)
            angle = angle+135;
         else 
            angle = angle - 25;
      
      
         mySpeed = 7;
         // setGun(character);
         rocket = new Rocket(myX, myY, 6);
         
      }
   
   //accessor
       public int getX() {
         return myX;
      }
       public int getY() {
         return myY;
      }
       public double getAngle() {
         return angle;
      }
       public double getDeltaX() {
         return deltaX;
      }
       public double getDeltaY() {
         return deltaY;
      }
       public double getdx() {
         return dx;
      }
       public double getdy() {
         return dy;
      }
       public double getSpeed() {
         return mySpeed;
      }
       public double getRadius() {
         return 0;
      }
       public Rocket getRocket()
      {
         return rocket;
      }
      
      //modifier
       public void setX(int x) {
         myX = x;
      }
       public void setY(int y) {
         myY = y;
      }
       public void setAngle(double angle) {
         angle = angle;
      }
       public void setdx(double dx) {
         dx = dx;
      }
       public void setdy(double dy) {
         dy = dy;
      }
       public void setSpeed(double speed) {
         mySpeed = speed;
      }
      //methods
       public void setGun(TestSubject character) {
          
         deltaX = myX - character.getX();
         deltaY = myY - character.getY();
         
         angle = Math.atan(deltaX/deltaY);
         if(deltaY > 0)
            angle = angle+135;
         else 
            angle = angle - 25;
      	
      }
       public void move(TestSubject character) {
       
         setGun(character);
         dx = mySpeed * Math.sin(angle) /*+ Math.toRadians(5)*/;
         dy = mySpeed * Math.cos(angle) /*+ Math.toRadians(5)*/;
       
         rocket.setX((int)(rocket.getX() + dx));
         rocket.setY((int)(rocket.getY() + dy));  
         
      
      }
       public void draw(Graphics pen, JPanel panel, double xScroll, int framex, int framey) {
      	
      
         pen.setColor(Color.ORANGE);   	
         pen.fillRect((int)(myX - xScroll), myY, 10, 10);
         
         
         rocket.draw(pen, xScroll, framex, framey, myX, myY);
         
      }
     
       public boolean weaponIsInContact(TestSubject character, ArrayList<Ground> ground, ArrayList<Weapon> weapons) {
         
         return rocket.weaponIsInContact(character, ground, weapons);
      }
   
   }