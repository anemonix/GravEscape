//Robert Young, Thai Co Nguyen, and Parth Chopra, May-June 2011
//Acc CS Final Project - GravEscape

   import java.awt.*;

//thank you to FCPS for use of this code in Ground
//thank you to Mr. Ero for use of BumperCollision code in Ground

   public class Ground
   {
    //private fields, all doubles, for a Bumper
      private int myX;
      private int myY;
      private int myXWidth;
      private int myYWidth;
      private Color myColor;     
   
     //constructors
      public Ground(int x, int y, int xWidth, int yWidth)
      {
         myX = x;
         myY = y;
         myXWidth = xWidth;
         myYWidth = yWidth;
         myColor = Color.GRAY;
      }
      
     // accessor methods  (one for each field)
      public int getX() 
      { 
         return myX;
      }
      public int getY()      
      { 
         return myY;
      }
      public int getXWidth() 
      { 
         return myXWidth;
      }
      public Color getColor() 
      { 
         return myColor;
      }
      public int getYWidth() 
      { 
         return myYWidth;
      }
   
     // modifier methods  (one for each field)
      public void setX(int x)
      {
         myX = x;
      } 
      public void setY(int y)
      {
         myY = y;
      } 
      public void setColor(Color c)
      {
         myColor = c;
      }
      public void setYWidth(int d)
      {
         myYWidth = d;
      }
      public void setXWidth(int r)
      {
         myXWidth = r;
      }
   
     // instance methods      
       // draws a rectangular this on the buffer
      public void draw(Graphics myBuffer, double xScroll) 
      {
         myBuffer.setColor(getColor());
         myBuffer.fillRect((int)(getX() - xScroll), getY(), getXWidth(), getYWidth());
      }   
      
   
   	// returns true if any part of the TestSubject is inside the this ground
      public boolean isInside(TestSubject phil, double x, double y)
      {
         return (y + phil.HEIGHT > this.getY()) &&
            		(y < this.getY() + this.getYWidth()) &&
            		(x + phil.WIDTH > this.getX()) &&
            		(x < this.getX() + this.getXWidth());
      }
   	
      public void affect(TestSubject phil)
      {
      // see if the phil hit the bumper!
         if(isInside(phil, phil.getX(), phil.getY()))
         {	
            double oldX = phil.getX();
            double oldY = phil.getY();
            	
            // back the phil up until it is just outside the ground
            while(isInside(phil, oldX, oldY))
            {
               oldX -= phil.getXSpeed()/10.0;
               oldY -= phil.getYSpeed()/10.0;
            }
         	
         	//if phil hits the top
            if(oldY + phil.HEIGHT <= this.getY())
            {
               phil.setYSpeed(0);
               phil.setY(this.getY() - phil.HEIGHT);
            }
            
            //if phil hits bottom side
            else if(oldY >= this.getY() + this.getYWidth())
            {
               phil.setYSpeed(0);
               phil.setY(this.getY() + this.getYWidth());
            }
            
            //if phil hits right side
            else if(oldX >= this.getX() + this.getXWidth())
            {
               phil.setXSpeed(0);
               phil.setX(this.getX() + this.getXWidth());
            }
            
            //if phil hits left side
            else if(oldX  + phil.WIDTH <= this.getX())
            {
               phil.setXSpeed(0);
               phil.setX(this.getX() - phil.WIDTH);
            }
         }
      }     
   }

