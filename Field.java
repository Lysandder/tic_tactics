package tic_tactics;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Field extends JFrame {
  private static byte[][][] cells = new byte[4][4][7];

  public Field () {
    super("Tic-Tactics");
    super.setSize(380, 900);
    super.setLocationRelativeTo(null);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container container = super.getContentPane();
    container.setBackground(Color.BLACK);
    container.setLayout(new GridLayout(4, 1, 0, 0));

    // Marking not usable cells
    for(int i = 0; i < 4; i++) {
      cells[i][0][0] = -1;
      cells[i][0][1] = -1;
      cells[i][0][2] = -1;
      cells[i][1][0] = -1;
      cells[i][1][1] = -1;
      cells[i][1][6] = -1;
      cells[i][2][0] = -1;
      cells[i][2][5] = -1;
      cells[i][2][6] = -1;
      cells[i][3][4] = -1;
      cells[i][3][5] = -1;
      cells[i][3][6] = -1;
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

  // private byte[] about_to_win() {}
  // private byte[] make_move() {}

  private class Panel extends JPanel {
    Panel (byte i) { // Gets layer number coordinate (starting from 0)
      this.setLayout(new GridLayout(4, 7));
      this.setBackground(Color.BLACK);
      this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

      for(int j = 0; j < 4; j++) {
        for(int k = 0; k < 7; k++) {
          final int layer = i;
          final int row = j;
          final int col = k;
          
          JPanel cell = new JPanel();
          cell.setLayout(new BorderLayout());
          cell.setBackground(Color.BLACK);

          // // temporary showing values of cells
          // JLabel label = new JLabel();
          // label.setText(String.valueOf(cells[layer][row][col]));
          // label.setForeground(Color.WHITE);
          // cell.add(label);

          if(cells[layer][row][col] == -1) {
            cell.setBorder(new LineBorder(Color.BLACK));
          }
          else if(cells[layer][row][col] == 0) {
            cell.setBorder(new LineBorder(Color.GRAY));

            cell.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked (MouseEvent e) {
                cells[layer][row][col] = 1;

                // // temp
                // label.setText(String.valueOf(cells[layer][row][col]));

                cell.removeMouseListener(this);
                cell.setBackground(Color.BLUE);
                // if(did_blue_win()) {}

                // coor = make_move();
                // move_layer = coor[0];
                // move_row = coor[1];
                // move_col = coor[2];
                // cells[move_layer][move_row][move_col] = 2;
                // // how to change the color and remove mouse listener for it???
                // if(did_red_win()) {}
                // // add a label for the winning sign & the end of the game
              }
            });
          }

          this.add(cell);
        }
      }
    }
  }
}
