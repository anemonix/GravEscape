//Robert Young, Thai Co Nguyen, and Parth Chopra, May-June 2011
//Acc CS Final Project - GravEscape

//MUSIC PLAYER CLASS
/*---Using javazoom, an 3rd party external java library, this class allws the game to play MP3 files for BGM---*/

   import javazoom.jl.player.Player;
   import java.io.*;

   public class MusicPlayer
   {
   //pathname for the music
      public InputStream gameMusic;
      public Player streamPlayer;
      public PlayerThread playerThread;
      private String musicPath;
      public int randomMusic;
   	
   	//accessor for which music it's playing
      public boolean isPlayingThisPiece(String pieceOfMusic)
      {
         return pieceOfMusic.equals(musicPath);
      }
   	
      public void initializeMusic(String musicMP3)
      {
         musicPath = musicMP3;
         try
         {
         //abstract pathname
            randomMusic = (int)(Math.random()*4) + 1;
            if(randomMusic == 1)
            {
               gameMusic = new FileInputStream("Caramell - Boogie Bam Dance.mp3");
               //gameMusic = new FileInputStream("Ropocalypse 2.mp3");
               System.out.println(randomMusic);
            }
            else if(randomMusic == 2)
            {
               gameMusic = new FileInputStream("Baby  on RECORDER  .mp3");
               //gameMusic = new FileInputStream("Rocket.mp3");
               System.out.println(randomMusic);
            }
            else if(randomMusic == 3)
            {
               gameMusic = new FileInputStream("Pachelbel 39s Canon in D - piano version.mp3");
               //gameMusic = new FileInputStream("Harmful or Fatal.mp3");
               System.out.println(randomMusic);
            }
            else if(randomMusic == 4)
            {
               gameMusic = new FileInputStream("SKRILLEX - Scary Monsters And Nice Sprites.mp3");
               //gameMusic = new FileInputStream("In a Heartbeat.mp3");
               System.out.println(randomMusic);
            }
         }
            catch(Exception exception)
            {
               System.out.println(exception);
               System.out.println("Music failed to load...");
            }
      }
      public void playMusic()
      {
         try
         {
         //player is a player of gameMusic pathname to a file
            streamPlayer = new Player(gameMusic);
            playerThread = new PlayerThread();
            playerThread.start(); // timer.start for the music stream
         	
         }
            catch(Exception exception)
            {
               System.out.println("Music failed to play...");
            }
      }
      public boolean isCompleted()
      {
         return streamPlayer.isComplete();
      }
   	
      public void stopMusic()
      {
         streamPlayer.close();
      }
      public class PlayerThread extends Thread
      {
         public void run()
         {
            try
            {
               streamPlayer.play();
            }
               catch(Exception exception)
               {
                  System.out.println("PlayerThread Error...");
               }
         		
            if(streamPlayer.isComplete())
            {
               streamPlayer.close();
               initializeMusic(musicPath);
               playMusic(); //loops
            }
         }
      }
   }
   // Thanks to Chris Seok for helping Thai Co discover javazoom and writing this MusicPlayer class