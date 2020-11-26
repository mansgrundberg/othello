package othello;

import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {

public State state;
public int[][] board;
    Font f;
    public GUI(State state) {
        JFrame frame = new JFrame();
        this.state = state;
        setOpaque(true);
        setBackground(Color.GREEN);
        board = state.getBoard();
        frame.setOpacity(1.0f);
        frame.getContentPane().add(this);
        frame.setSize(new Dimension(board.length* (board.length *10), board.length* (board.length *10)+100));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int fontSize = 70;
        f = new Font("sansSerif", Font.PLAIN, fontSize);


    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(f);

        board = state.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                try {
                    if (state.toColor(board[i][j]) == 'B'){
                        g.setColor(Color.BLACK);
                    }
                    else g.setColor(Color.WHITE);

                    g.drawString(String.valueOf(state.toColor(board[i][j])), i * (board.length*10)+15, j * (board.length*10)+60);
                }catch (ArrayIndexOutOfBoundsException e){

                }
            }
        }

    }

}
