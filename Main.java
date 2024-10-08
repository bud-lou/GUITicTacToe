//Imports
import java.awt.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JButton;

public class Main implements ActionListener {
  //public variables
  int[][] board = new int [3][3];
  final int BLANK = 0;
  final int XMOVE = 1;
  final int OMOVE = 2;
  final int XTURN = 0;
  final int OTURN = 1;
  int turn = XTURN;
  JLabel xLabel = new JLabel("X wins: 0");
  JLabel oLabel = new JLabel("O wins: 0");
  String xPlayerName = "X";
  String oPlayerName = "O";
  int xWins = 0;
  int oWins = 0;
  JButton [][] button = new JButton[3][3]; //creates space for buttons
  JButton xChangeName = new JButton("Change X's Name.");
  JButton oChangeName = new JButton("Change O's Name.");
  JTextField xChangeField = new JTextField();
  JTextField oChangeField = new JTextField();
  
    public Main() {
      //J Features
      JFrame frame = new JFrame();
      Container center = new Container();
      Container north = new Container();
      
      
      //*****************Layouts************************
      frame.setSize(400, 400);
      
      //Center Grid Container
      frame.setLayout(new BorderLayout());
      center.setLayout(new GridLayout(3,3));
      for (int i=0; i < button.length; i++) {     //creates board buttons
        for (int j=0; j < button[0].length; j++) {
          button[j][i] = new JButton(j + "," + i);
          center.add(button[j][i]);
          button[j][i].addActionListener(this);
        }
      }
      frame.add(center, BorderLayout.CENTER);
      
      //North Grid Container
      north.setLayout(new GridLayout(3,2));
      north.add(xLabel);
      north.add(oLabel);
      north.add(xChangeName);
      xChangeName.addActionListener(this);
      north.add(oChangeName); 
      oChangeName.addActionListener(this);
      north.add(xChangeField);
      north.add(oChangeField);
      frame.add(north, BorderLayout.NORTH);
      
      //closes program when you close window
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
    }
    
  public static void main(String[] args) {
    new Main();
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    JButton current;
    boolean gridButton = false;
    //Adds "x" or "o" if you click on box
    for (int i=0; i < button.length; i++) {
      for (int j = 0; j < button[0].length; j++) {
        if (event.getSource().equals(button[j][i])) {
          gridButton = true;
          current = button[j][i];
          if (board[j][i] == BLANK) { //can't click on same spot twice
            //mark chosen spot & switch turns
            if (turn == XTURN) {
              current.setText("X");
              current.setEnabled(false); //disables ability to click
              board[j][i] = XMOVE;
              turn = OTURN;
            } else {
              current.setText("O");
              current.setEnabled(false); //disables ability to click
              board[j][i] = OMOVE;
              turn = XTURN;
            }
            //check for wins and ties
            if (checkWin(OMOVE) == true) {
              //O wins and update score
              System.out.println("O won!");
              oWins++;
              oLabel.setText(oPlayerName + " wins:" + oWins);
              clearBoard();
             } else if (checkTie() == true) {
              System.out.println("Tie: No winners!");
              clearBoard();
            } else if (checkWin(XMOVE) == true) {
              //X wins -- now update score
              System.out.println("X won!");
              xWins++;
              xLabel.setText(xPlayerName + " wins:" + xWins);
              clearBoard();
            }
          }
        }
      }
      //changes name if player wishes
      if (gridButton == false) {
        if (event.getSource().equals(xChangeName) == true) {
          //asigns new string to player name
          xPlayerName = xChangeField.getText();
          xLabel.setText(xPlayerName + " wins:" + xWins);
        } else if (event.getSource().equals(oChangeName) == true) {
          //asigns new string to player name
          oPlayerName = oChangeField.getText();
          oLabel.setText(oPlayerName + " wins:" + oWins);
        }
      }
    }
  }

    //Check For Wins
    public boolean checkWin(int player) {
      //checks columns
      for (int colNum = 0; colNum < board[0].length; colNum++) {
        if (board[colNum][0] == player && board[colNum][1] == player && board[colNum][2] == player) {
          return true;
        }
      }
      //checks rows
      for (int rowNum = 0; rowNum < board.length; rowNum++) {
        if (board[0][rowNum] == player && board[1][rowNum] == player && board[2][rowNum] == player) {
        return true;
        }
      }
      //checks diagonals
      if (board[0][0] == player && board[1][1] == player && board[2][2] == player || 
         board[0][2] == player && board[1][1] == player && board[2][0] == player) {
        return true;
      } 
      return false;  //means nobody won
    }

  //Checks for ties
  public boolean checkTie() {
    for (int row = 0; row < board.length; row++) {
      for (int column = 0; column < board[0].length; column++) {
        if (board[row][column] == BLANK) {
          return false;
        } 
      }
    }  
    return true;
  }

  //Clears the board
  public void clearBoard() {
    //loop goes through each box and clears it
    for (int a = 0; a < board.length; a++) {
      for (int b = 0; b < board[0].length; b++) {
        board[a][b] = BLANK;
        button[a][b].setText(a + "," + b);
        button[a][b].setEnabled(true);
      }
    }
    turn = XTURN; //resets turn
  }
}
