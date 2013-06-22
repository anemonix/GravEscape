//Robert Young, Thai Co Nguyen, and Parth Chopra, May-June 2011
//Acc CS Final Project - GravEscape

//GAME DRIVER
/*---Runs the game!---*/


//thank you to Larry Hensley for helping with random problem solving skillz and entertainment
   import javax.swing.*;

   public class Driver
   {
         //------------------------------DRIVER AND MAIN FRAME------------------------------//
   		
      private static final int PANEL_WIDTH = 700;
      private static final int PANEL_HEIGHT = 450;
   	
      public static void main(String[] args)
      {
        /*---The main driver code for the game, sets up the frame and music---*/
      		//----------Main Frame Initialization----------//
         JFrame gravEscape = new JFrame("GravEscape");
         gravEscape.setSize(PANEL_WIDTH + 8, PANEL_HEIGHT + 38);
         gravEscape.setLocation(200, 50);
         gravEscape.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	
            //----------Adding the Content Panel----------//
         GravEscapePanel gravEscapePanel = new GravEscapePanel();
         gravEscape.setContentPane(gravEscapePanel);
            
            //----------Locks Size, Displays Game----------//
         gravEscape.setResizable(false);
         gravEscape.setVisible(true);
      }
   }