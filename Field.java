package tic_tactics;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Field extends JFrame {
  public Field () {
    super("Tic-Tactics");
    super.setSize(700, 900);
    super.setLocationRelativeTo(null);
    super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container container = super.getContentPane();
    container.setBackground(Color.BLACK);
    container.setLayout(new GridLayout(4, 1, 0, 0));

    Panel layer1 = new Panel((byte) 0);
    Panel layer2 = new Panel((byte) 1);
    Panel layer3 = new Panel((byte) 2);
    Panel layer4 = new Panel((byte) 3);

    container.add(layer1);
    container.add(layer2);
    container.add(layer3);
    container.add(layer4);
  }

  private byte[][][] cells = new byte[4][4][4];

  // private byte[] about_to_win() {}
  // private byte[] make_move() {}

  private class Panel extends JPanel {
    Panel (byte i) { // Gets layer number coordinate (starting from 0)
      this.setLayout(new GridLayout(4, 4));
      this.setBackground(Color.BLACK);

      for(int j = 0; j < 4; j++) {
        for(int k = 0; k < 4; k++) {
          final int layer = i;
          final int row = j;
          final int col = k;
          
          JPanel cell = new JPanel();
          cell.setLayout(new BorderLayout());
          cell.setBackground(Color.BLACK);
          cell.setBorder(new LineBorder(Color.GRAY));
          
          cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
              cells[layer][row][col] = 1;
              cell.removeMouseListener(this);
              cell.setBackground(Color.BLUE);
              // if(did_blue_win()) {}

              // coor = make_move();
              // cells[coor[0]][coor[1]][coor[2]] = -1;
              // // how to change the color and remove mouse listener for it???
              // if(did_red_win()) {}
              // // add a label for the winning sign
            }
          });

          this.add(cell);
        }
      }
    }
  }
}
