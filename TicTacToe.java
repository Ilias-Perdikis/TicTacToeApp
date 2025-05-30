import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import java.util.List;
import javax.swing.Timer;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean xPlayer = true;
    private boolean pcPlayer = false;
    private int xWins = 0;
    private int oWins = 0;
    private JLabel scoreLbl;


    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel board = new JPanel(new GridLayout(3,3));
        initializeButtons(board);
        add(board, BorderLayout.CENTER);

        scoreLbl = new JLabel("X-0 : 0-O", SwingConstants.CENTER);
        scoreLbl.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 20));
        add(scoreLbl, BorderLayout.NORTH);

        choosePlayer();
        //xPlayer = true; //X starts first
        xPlayer = new Random().nextBoolean(); // Random first player
        
        if (pcPlayer && !xPlayer) {
            delayComputerTurn();
        }

        setVisible(true);
    }

    private void choosePlayer()
    {
        String[] playerOption = {"VS PLAYER", "VS COMPUTER"};
        int mode = JOptionPane.showOptionDialog(
            this,
            "GAME MODE",
            "TIC TAC TOE",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            playerOption,
            playerOption[0]);
            
        pcPlayer = (mode == 1);
    }

    private void initializeButtons(JPanel p) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setBackground(Color.GRAY);
                buttons[row][col].setFont(new Font("Comic Sans MS", Font.BOLD, 80));
                buttons[row][col].setFocusPainted(true);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                buttons[row][col].addActionListener(this);
                p.add(buttons[row][col]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return;

        if (xPlayer) {
            clicked.setText("X");
            clicked.setForeground(Color.RED);
        }else
        {
            clicked.setText("O");
            clicked.setForeground(Color.GREEN);
        }

        if (checkWin()) {
            String winner;
            if (xPlayer) {
                winner = "X";
                xWins++;
            } else
            {
                winner = "O";
                oWins++;
            }
            updateScore();
            JOptionPane.showMessageDialog(this, winner + " wins!");
            resetGame();

        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        }

        xPlayer = !xPlayer;
        if (pcPlayer && !xPlayer) {
            delayComputerTurn();
        }
    }

    private void updateScore(){
        scoreLbl.setText("X-" + xWins + ":" + oWins + "-O");
    }

    private void delayComputerTurn(){
        Timer t = new Timer(500, _ -> computer());
        t.setRepeats(false);
        t.start();
    }

    private void computer() {
        List<JButton> moves = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    moves.add(buttons[i][j]);
                }
            }
        }
        if (!moves.isEmpty()) {
            JButton pcPlay = moves.get(new Random().nextInt(moves.size()));
            pcPlay.setText("O");
            pcPlay.setForeground(Color.GREEN);

            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "O wins!");
                resetGame();
                return;
            }

            if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetGame();
                return;
            }

            xPlayer = true;
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) return false;
            }
        }
        return true;
    }

    private boolean checkWin() {
        // Rows, Columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText())) return true;

            if (!buttons[0][i].getText().equals("") &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText())) return true;
        }

        // Diagonal
        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText())) return true;

        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText())) return true;

        return false;
    }


    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
        xPlayer = true;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}