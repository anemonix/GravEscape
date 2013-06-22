   import javax.swing.*;
   import java.awt.*;
   import java.awt.geom.AffineTransform;
   import java.util.*;

   
   public class RotatingSpikes extends Weapon {
   
      private int myX;
      private int myY;
      private double myRadius;
      private ImageIcon spikes;
      private double rotateAngle;
      private AffineTransform transformer;
      
   
      public RotatingSpikes(int startingX, int startingY, int radius) {
         
         if(radius == 25 || radius == 50 || radius == 100 || radius == 150)
            myRadius = radius;
         	
         else
         {
            System.out.println("Incorrect Radius Measurement: " + radius);
            System.exit(1);
         }
         
      	int dia = (int)(myRadius * 2);
      	
         spikes = new ImageIcon("gears" + dia + ".png");
         //spikes = new ImageIcon("gears200.png");
      		
         myX = startingX;
         myY = startingY;
         
         rotateAngle = Math.toRadians(100);
         transformer = new AffineTransform();
      }
      
   	//accessor methods
   	
      public int getX() {
         return myX;
      }
      public int getY() {
         return myY;
      }
      public double getRadius() {
         return myRadius;
      }
      public double getRotateAngle() {
         return rotateAngle;
      }
      public AffineTransform getTransformer() {
         return transformer;
      }
      public Image getImage() {
         return (spikes.getImage());
      }
      
   	//modifier methods
   	
      public void setX(int x) {
         myX = x;
      }
      public void setY(int y) {
         myY = y;
      }
      public void setRadius(double radius) {
         myRadius = radius;
      }
      public void setRotateAngle(double angle) {
         rotateAngle = angle;
      }
             
      public boolean weaponIsInContact(TestSubject character, ArrayList<Ground> ground, ArrayList<Weapon> weapon) {
      
         if(distance(character.getX(), character.getY(), getX(), getY()) <= getRadius() ||
         	distance(character.getX(), character.getY() + character.HEIGHT, getX(), getY()) <= getRadius() ||
         	distance(character.getX() + character.WIDTH, character.getY(), getX(), getY()) <= getRadius() ||
         	distance(character.getX() + character.WIDTH, character.getY() + character.HEIGHT, getX(), getY()) <= getRadius())
            return true;
         return false;
      }
      
   
      public void rotate() {
       
         transformer.rotate(Math.toRadians(rotateAngle), getRadius(), getRadius());
      }
   
      public void draw(Graphics g, JPanel panel, double xScroll, int framex, int framey) {
      
         rotate();
         Graphics2D pen = (Graphics2D)g.create();
         pen.translate((int)(myX - getRadius() - xScroll), myY - getRadius());
      
         pen.drawImage(getImage(), getTransformer(), panel);  
      }	
   	
   	      // returns distance between (x1, y1) and (x2, y2)
      private double distance(double x1, double y1, double x2, double y2)
      {
         return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      }
   }
