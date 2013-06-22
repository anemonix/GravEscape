   import java.util.*;
   import javax.swing.*;
   import java.awt.*;
  
    public abstract class Weapon {
    
       public abstract boolean weaponIsInContact(TestSubject character, ArrayList<Ground> ground, ArrayList<Weapon> weapon);
   	 
       public abstract void draw(Graphics pen, JPanel panel, double xScroll, int framex, int framey);
   
       public abstract int getX();
       
       public abstract int getY();
       
       public abstract double getRadius();
   } 

