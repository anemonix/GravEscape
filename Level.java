//Robert Young, Thai Co Nguyen, and Parth Chopra, May-June 2011
//Acc CS Final Project - GravEscape

   import java.util.*;
   import java.awt.*;
   import javax.swing.*;


   public class Level
   {
      public ArrayList<Weapon> myWeapons;
      public ArrayList<Ground> myGround;
      public ArrayList<Checkpoint> myCheckpoints;
      private double xScroll;
      private double xWidth;
      private TestSubject phil;
   
      public Level(TestSubject blah, double width)
      {
         myWeapons = new ArrayList<Weapon>();
         myGround = new ArrayList<Ground>();
         myCheckpoints = new ArrayList<Checkpoint>();
         phil = blah;
         xWidth = width;
      }
      
      public double getWidth()
      {
         return xWidth;
      }
   	
      public double getScroll()
      {
         return xScroll;
      }
   	
      public void setScroll(double newScroll)
      {
         xScroll = newScroll;
      }
   
      public boolean weaponsAreInContact()
      {
         for(int i = 0; i < myWeapons.size(); i++)
            if(myWeapons.get(i).weaponIsInContact(phil, myGround, myWeapons))
               return true;
         return false;
      }
      
      public Checkpoint getMostRecentCheckpoint()
      {
         for(int i = myCheckpoints.size() - 1; i >= 0; i--)
            if(myCheckpoints.get(i).pastCheckpoint(phil))
               return myCheckpoints.get(i);
      			
         return null;
      }
   	
      public void draw(Graphics g, JPanel panel, int frameY)
      {
         for(int i = 0; i < myWeapons.size(); i++)
         {
            myWeapons.get(i).draw(g, panel, (int)getScroll(), (int)getWidth(), frameY);
         }
            
         for(int i = 0; i < myGround.size(); i++)
         {
            myGround.get(i).draw(g, getScroll());
         }
      		
         for(int i = 0; i < myCheckpoints.size(); i++)
         {
            myCheckpoints.get(i).draw(g, panel, xScroll, phil);
         }
      }
      
      public void groundAffect()
      {
         for(int i = 0; i < myGround.size(); i++)
         {
            myGround.get(i).affect(phil);
         }
      }
      
      public void weaponsAffect()
      {
         for(int i = 0; i < myWeapons.size(); i++)
         {
            if(myWeapons.get(i) instanceof RocketLauncher)
               ((RocketLauncher)(myWeapons.get(i))).move(phil);
         }
      }
      
      public void gravityAffect(Gravity grav)
      {
         for(int i = 0; i < myWeapons.size(); i++)
            if(myWeapons.get(i) instanceof GravityMoveable)
               grav.actOn((GravityMoveable)(myWeapons.get(i)));
      }
   }