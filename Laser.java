   import java.awt.*;
    
    public class Laser extends Weapon implements GravityMoveable {
    
      private double myStartingX;   // x and y coordinates of center
      private double myStartingY;
      private double myEndingX;
      private double myEndingY;
   	
      private Color myColor; 
      private double myXChange; //changes myEndingX and myEndingY
      private double myYChange; 
    
       public double getStartingX() {
         return myStartingX;
      }
       public double getStartingY() {
         return myStartingY;
      }
       public Color getColor() 
      { 
         return myColor;
      }
       public double getXSpeed() {
         return myXChange;
      }
       public double getYSpeed() {
         return myYChange;
      }
       public double getEndingX() {
         return myEndingX;
      }
       public double getEndingY() {
         return myEndingY;
      }
      
   	 // modifier methods
       public void setStartingX(double x)
      {
         myStartingX = x;
      } 
       public void setStartingY(double y)
      {
         myStartingY = y;
      } 
       public void setColor(Color c)
      {
         myColor = c;
      }
     
       public void setXSpeed(double xSpeed) {
         myXChange = xSpeed;
      }
       public void setYSpeed(double ySpeed) {
         myYChange = ySpeed;
      }
       public void setEndingX(double x) {
         myEndingX = x;
      }
       public void setEndingY(double y) {
         myEndingY = y;
      }
     //methods
       public void laserPath() { //for loop changing the xchange and yChange will move the laser the right way
      
         setEndingX(getEndingX() + myXChange);
         setEndingY(getEndingY() + myYChange);
      
      }
       public boolean weaponIsInContact(TestSubject character, Laser weapon) {
      
         if(character.getX() >= weapon.getStartingX() && character.getX() <= weapon.getEndingX() &&
         character.getY() >= weapon.getStartingY() && character.getY() <= weapon.getEndingY()) 
            return true;
         
         else 
            return false;
      }
    
   }