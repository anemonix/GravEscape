//Robert Young, Thai Co Nguyen, and Parth Chopra, May-June 2011
//Acc CS Final Project - GravEscape

//SCOREBOARD CLASS
/*---This class is the scoreboard panel, used to display lives, score, and name---*/

   import java.awt.*;
   import javax.swing.*;

    public class Scoreboard extends JPanel
   {
   //Field Initialization
      private JLabel nameLabel, scoreLabel;
      public JLabel[] livesArray;
      private int score = 0;
   
   //Constructor
       public Scoreboard()
      {
      //Layout and Visibility Options
         setLayout(new FlowLayout());
         setOpaque(false);
      
      //Label for Player Name
         nameLabel = new JLabel("Player: ", SwingConstants.LEFT);
         nameLabel.setForeground(Color.white);
         nameLabel.setFont(new Font("Sans-Serif", Font.BOLD, 18));      
         add(nameLabel);
      
      //Score Label
         scoreLabel = new JLabel("0     ", SwingConstants.RIGHT);
         scoreLabel.setFont(new Font("Sans-Serif", Font.BOLD, 18));
         scoreLabel.setForeground(Color.white);
         add(scoreLabel);
      }
      
   
   	//Instance Methods
       public int getScore()
      {
         return score;
      }
       public void setName(String name)
      {
         nameLabel.setText(name);
      }
       public void reset()
      {
         score = 0;
         increaseScore(0);
      }
       public void increaseScore(int increase)
      {
         score += increase;
         scoreLabel.setText("" + score + "     ");
      }
   }