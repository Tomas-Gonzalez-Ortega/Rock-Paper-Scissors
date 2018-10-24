/*
 * BEGINNING COMMENTS
 * Classes to implement a small game "Rock, paper, scissors"
 * @author Tomas Gonzalez
 * @version 2017-05-14 06:41pm
 * @see https://d2l.langara.bc.ca/d2l/le/calendar/88736/event/2546119/detailsview#2546119
 * Solves CPSC 1181 assignement 1
 * Instructor : Jeremy Hilliker
 */
//       clear; del *.class; javac RPS*.java; java -ea RPSTester

// PACKAGE AND IMPORT STATEMENTS
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

// CLASS AND INTERFACE DECLARATIONS

/**
    A class that implements the game "Rock, paper, scissors"
 */
public class RPS {
   public final static int ROCK = 0;
   public final static int PAPER = 1;
   public final static int SCISSORS = 2;
   private final static String[] CHOICES = {"ROCK", "PAPER", "SCISSORS"};
   //Scanner sc = new Scanner(System.in);
   private final static Random rand = new Random();

   /**
    * Starts the game
    * @param args line arguments -- ignored in this assignment
    */
   public static void main(String[] args) {
      if(args.length > 0) {
         reset(Integer.parseInt(args[0]));
      }
      playGame(new Scanner(System.in));
   }

   /**
    * Determines who wins this round of "Rock, papers, scissors" game comparing 2 arguments
    * @param a player a's choice or R, P or S
    * @param b player b's choice or R, P or S
    * @return -1 if player's b beats a, 0 if it is a draw or 1 if player a beats player b
    */
   public static int determineWinner(int a, int b) {
      if (a == b)
         return 0;
      if ((a == ROCK) && (b == SCISSORS))
         return 1;
      if ((a == ROCK) && (b == PAPER))
         return -1;
      if ((a == PAPER) && (b == ROCK))
         return 1;
      if ((a == PAPER) && (b == SCISSORS))
         return -1;
      if ((a == SCISSORS) && (b == PAPER))
         return 1;
      if ((a == SCISSORS) && (b == ROCK))
         return -1;
      return -50; // unreachable
   }

   /**
    * Determines if character c is contained in the array a
    * @param c character to be verified if its contained in the array a
    * @param a char player's choice or R, P or S
    * @return true if the character is cointained, else returns false
    */
   public static boolean contains(char c, char[] a)
   {
      // determines if a given character ca is in the array
      for (char ca : a)
      {
         if (c == ca)
         {
            return true;
         }
      }
      return false;
   }

   /**
    * Retrieves the input from the user and matches it
    * @param prompt String to retreive from the user
    * @param options, an array of characters to see if it matches or not
    * @param sc The Scanner method to input the line from the user
    * @return char letter if found
    */
   public static char getInput(String prompt, char[] options, Scanner sc)
   {
      // Display and complete the message and assign it to a variable called choice
      String choice = " ( ";
      for (int i=0; i < options.length-1; i++)
         choice += options[i] + ", ";
      choice += options[options.length-1] + " ):";
      
      char letter = '0';
      // if its not one of those, repeat. (use contains (above))
      while (true)
      {
         // prompts user for an input that matches one of the given characters
         System.out.println(prompt + choice);
         String sequence = sc.nextLine();
         if(sequence.length() == 1) {
            letter = sequence.charAt(0);
            if(contains(letter, options)) {
               return letter;
            }
         }
      }
   }

   /**
    * Checks the options chosen by the player and the computer and then determine a winner as long as the result of determineWinner method isn't a draw
    * @param sc The Scanner method to input the choice from the user
    * @return p if the player scores
    * @return c if the computer scores
    * @return q if the player decides to quit
    */
   public static char playRound(Scanner sc)
   {
      int winner;
      char[] c = { 'r', 'p' , 's', 'q' };
      // repeat if it was a draw
      do
      {
         int player = 0;
         
         // ask the user for a choice: r,p,s (use getInput)
         char playerChoice = getInput("Choose", c, sc);
         
         if (playerChoice == 'q')
         {
            System.out.println("Quitting");
            return 'q';
         }
         // call makeChoice to get the computer's choice
         int computer = makeChoice();
         if (computer == ROCK)
            System.out.println("Computer chose: ROCK");
         if (computer == PAPER)
            System.out.println("Computer chose: PAPER");
         if (computer == SCISSORS)
            System.out.println("Computer chose: SCISSORS");
         
         if (playerChoice == 'r')
            System.out.println("  Player chose: ROCK");
         if (playerChoice == 'p')
         {
            player = 1;
            System.out.println("  Player chose: PAPER");
         }
         if (playerChoice == 's')
         {
            player = 2;
            System.out.println("  Player chose: SCISSORS");
         }
         
         // determine & return the winner (use determineWinner)
         winner = determineWinner(player, computer);
         if(winner == 1)
         {
            System.out.println("Player scores!");
            return 'p';
         }
         if(winner == -1)
         {
            System.out.println("Computer scores!");
            return 'c';
         }
         else
            System.out.println("Draw!");
      }
      while (winner == 0);
      return '\0'; // unreachable
   }

   /**
    * This method inputs a maximum score from the user to decide up to what amount of points the game will be deployed and is played until one of the players reach that maximum or the user quits
    * @param sc The Scanner method to input the maximum amount of points to win the game
    * @return p if the player wins
    * @return c if the computer wins
    * @return q if the game is cancelled
    */
   public static char playGame(Scanner sc)
   {
      // a game consists of a number of rounds
      int playerPoints = 0;
      int computerPoints = 0;
      // prompt the user for a maximum score to win
      System.out.println("How many points to win?");
      int maximumScore = sc.nextInt();
      System.out.println("Score: p=" + playerPoints + " c=" + computerPoints);
      // play rounds until that score is reached (use playRound)
      while ((playerPoints < maximumScore) && (computerPoints < maximumScore))
      {
         char score = playRound(sc);
         
         if (score == 'p')
         {
            playerPoints++;
         }
         if (score == 'c')
         {
            computerPoints++;
         }
         if (score == 'q')
         {
            return 'q';
         }
      }
      
      // display & return winner
      if (playerPoints == maximumScore)
      {
         System.out.println("Player wins! " + playerPoints + " : " + computerPoints);
         return 'p';
      }
      if (computerPoints == maximumScore)
      {
         System.out.println("Computer wins! " + playerPoints + " : " + computerPoints);
         return 'c';
      }
      return '\0';
   }

   // DONT CHANGE THE METHODS BELOW.  They work fine and are needed by the test suite

   public static void reset(int seed) {
      rand.setSeed(seed);
   }

   public static int makeChoice() {
      return rand.nextInt(CHOICES.length);
   }

   public static String toString(int rps) {
      return CHOICES[rps];
   }

   public static int mapToChoice(char c) {
      switch (c) {
         case 'r': return ROCK;
         case 'p': return PAPER;
         case 's': return SCISSORS;
      }
      assert false;
      return 'x'; // unreachable
   }
}
