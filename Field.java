package tic_tactics;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Field extends JFrame {
  private static byte[][][] cells = new byte[4][4][7];
  private static JPanel[][][] gui_cells = new JPanel[4][4][7];

  // TEST
  // private static JLabel[][][] label_cells = new JLabel[4][4][7];

  private static final byte NO_CELL = -1;
  private static final byte EMPTY_CELL = 0;
  private static final byte BLUE_CELL = 1;
  private static final byte RED_CELL = 2;

  public Field () {
    super("Tic-Tactics");
    super.setSize(380, 900);
    super.setLocationRelativeTo(null);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container container = super.getContentPane();
    container.setBackground(Color.BLACK);
    container.setLayout(new GridLayout(4, 1, 0, 0));

    // Marking not usable cells
    for(byte i = 0; i < 4; i++) {
      cells[i][0][0] = NO_CELL;
      cells[i][0][1] = NO_CELL;
      cells[i][0][2] = NO_CELL;
      cells[i][1][0] = NO_CELL;
      cells[i][1][1] = NO_CELL;
      cells[i][1][6] = NO_CELL;
      cells[i][2][0] = NO_CELL;
      cells[i][2][5] = NO_CELL;
      cells[i][2][6] = NO_CELL;
      cells[i][3][4] = NO_CELL;
      cells[i][3][5] = NO_CELL;
      cells[i][3][6] = NO_CELL;
    }

    Panel layer1 = new Panel((byte) 0);
    Panel layer2 = new Panel((byte) 1);
    Panel layer3 = new Panel((byte) 2);
    Panel layer4 = new Panel((byte) 3);

    container.add(layer1);
    container.add(layer2);
    container.add(layer3);
    container.add(layer4);
  }

  private void update_cells() {
    for(byte i = 0; i < 4; i++) {
      for(byte j = 0; j < 4; j++) {
        for(byte k = 0; k < 7; k++) {
          if(cells[i][j][k] == BLUE_CELL) {
            gui_cells[i][j][k].setBackground(Color.BLUE);
            for(MouseListener ml : gui_cells[i][j][k].getMouseListeners())
              gui_cells[i][j][k].removeMouseListener(ml);
          }
          else if(cells[i][j][k] == RED_CELL) {
            gui_cells[i][j][k].setBackground(Color.RED);
            for(MouseListener ml : gui_cells[i][j][k].getMouseListeners())
              gui_cells[i][j][k].removeMouseListener(ml);
          }

          // TEST
          // label_cells[i][j][k].setText(String.valueOf(cells[i][j][k]));
        }
      }
    }
  }

  private void stop_game() {
    for(byte i = 0; i < 4; i++) {
      for(byte j = 0; j < 4; j++) {
        for(byte k = 0; k < 7; k++) {
          if(cells[i][j][k] == EMPTY_CELL) {
            for(MouseListener ml : gui_cells[i][j][k].getMouseListeners())
              gui_cells[i][j][k].removeMouseListener(ml);
          }
        }
      }
    }
  }

  private void winner(byte cell, byte winning_line) {
    stop_game();
    // Repeating again
    for(byte i = 0; i < 4; i++) {
      byte layer = WinningLines.WINNING_LINES[winning_line][i][0];
      byte row = WinningLines.WINNING_LINES[winning_line][i][1];
      byte col = (byte)(WinningLines.WINNING_LINES[winning_line][i][2] + (3 - row));
      
      gui_cells[layer][row][col].setBorder(new LineBorder(Color.GREEN, 10));
    }
  }

  private byte[] check_max(byte cell) {
    byte max = 0;
    byte line = -1;

    byte enemy;
    if(cell == BLUE_CELL) {
      enemy = RED_CELL;
    }
    else {
      enemy = BLUE_CELL;
    }

    for(byte i = 0; i < 76; i++) {
      byte counter = 0;
      for(byte j = 0; j < 4; j++) {
        byte layer = WinningLines.WINNING_LINES[i][j][0];
        byte row = WinningLines.WINNING_LINES[i][j][1];
        byte col = (byte)(WinningLines.WINNING_LINES[i][j][2] + (3 - row));

        if(cells[layer][row][col] == cell) {
          counter++;
        }
        else if(cells[layer][row][col] == enemy) { // do not include if blocked
          counter = -4;
        }
      }
      if(max < counter) {
        max = counter;
        line = i;
      }
    }

    // Returning max occupied cells in 1 winning line and the number (0-75) of this line
    return new byte[]{max, line};
  }
  
  private void make_move() {
    byte[] blue_max = check_max(BLUE_CELL);
    byte[] red_max = check_max(RED_CELL);

    // Win
    if(red_max[0] == 3) {
      for(byte i = 0; i < 4; i++) {
        byte layer = WinningLines.WINNING_LINES[red_max[1]][i][0];
        byte row = WinningLines.WINNING_LINES[red_max[1]][i][1];
        byte col = (byte)(WinningLines.WINNING_LINES[red_max[1]][i][2] + (3 - row));

        if(cells[layer][row][col] == EMPTY_CELL) {
          cells[layer][row][col] = RED_CELL;
          winner(RED_CELL, red_max[1]);
          break;
        }
      }
    }

    // Block if blue is winning
    else if(blue_max[0] == 3) {
      // repeating code, need to change later
      for(byte i = 0; i < 4; i++) {
        byte layer = WinningLines.WINNING_LINES[blue_max[1]][i][0];
        byte row = WinningLines.WINNING_LINES[blue_max[1]][i][1];
        byte col = (byte)(WinningLines.WINNING_LINES[blue_max[1]][i][2] + (3 - row));

        if(cells[layer][row][col] == EMPTY_CELL) {
          cells[layer][row][col] = RED_CELL;
          break;
        }
      }
    }

    // already have 1-2 moves
    else if(red_max[0] == 2 || red_max[0] == 1) {
      // repeating code, need to change later
      for(byte i = 0; i < 4; i++) {
        byte layer = WinningLines.WINNING_LINES[red_max[1]][i][0];
        byte row = WinningLines.WINNING_LINES[red_max[1]][i][1];
        byte col = (byte)(WinningLines.WINNING_LINES[red_max[1]][i][2] + (3 - row));

        if(cells[layer][row][col] == EMPTY_CELL) {
          cells[layer][row][col] = RED_CELL;
          break;
        }
      }
    }

    // first move
    else if (red_max[0] == 0) {
      if(cells[2][2][2] == EMPTY_CELL) {
        cells[2][2][2] = RED_CELL;
      }
      else if(cells[3][3][3] == EMPTY_CELL) {
        cells[3][3][3] = RED_CELL;
      }
    }

    update_cells();
  }

  private class Panel extends JPanel {
    Panel (byte i) { // Gets layer number coordinate (starting from 0)
      this.setLayout(new GridLayout(4, 1));
      this.setBackground(Color.BLACK);
      this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

      for(byte j = 0; j < 4; j++) {
        JPanel rowPanel = new JPanel(new GridLayout(1, 7));
        // // play around with the border, to make it look better
        // if(j == 0) {
        //   rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 32)); 
        // }
        // else if(j == 1) {
        //   rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8)); 
        // }
        // else if(j == 2) {
        //   rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0)); 
        // }
        // else if(j == 3) {
        //   rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 0)); 
        // }
        rowPanel.setBackground(Color.BLACK);

        for(byte k = 0; k < 7; k++) {
          final byte layer = i;
          final byte row = j;
          final byte col = k;
          
          JPanel cell = new JPanel();
          cell.setLayout(new BorderLayout());
          cell.setBackground(Color.BLACK);

          // TEST
          // JLabel label = new JLabel();
          // label.setText(String.valueOf(cells[layer][row][col]));
          // label.setForeground(Color.WHITE);
          // cell.add(label);

          if(cells[layer][row][col] == NO_CELL) {
            cell.setBorder(new LineBorder(Color.BLACK));
          }
          else if(cells[layer][row][col] == EMPTY_CELL) {
            cell.setBorder(new LineBorder(Color.GRAY));

            cell.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked (MouseEvent e) {
                cells[layer][row][col] = BLUE_CELL;
                byte[] blue_max = check_max(BLUE_CELL);
                if(blue_max[0] == 4) {
                  winner(BLUE_CELL, blue_max[1]);
                }
                update_cells();
                
                make_move();
              }
            });
          }

          // TEST
          // label_cells[layer][row][col] = label;

          gui_cells[layer][row][col] = cell;
          rowPanel.add(cell);
        }
        this.add(rowPanel);
      }
    }
  }
}
