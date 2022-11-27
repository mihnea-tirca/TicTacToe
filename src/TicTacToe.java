import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener
{
    JFrame window;
    JPanel panel;
    JButton[][] squares;
    boolean turn;
    JMenuBar menuBar;
    JMenu help;
    JMenuItem howToPlay;
    JMenuItem about;
    JLabel label;

    public TicTacToe() {initGame();}

    public void initGame()
    {
        turn = true;

        window = new JFrame("Tic Tac Toe");
        window.setSize(300,300);

        menuBar = new JMenuBar();

        help = new JMenu("Help");
        menuBar.add(help);
        help.setMnemonic(KeyEvent.VK_H);

        howToPlay = new JMenuItem("How To Play");
        help.add(howToPlay);
        howToPlay.setMnemonic(KeyEvent.VK_H);
        howToPlay.addActionListener(this);

        about = new JMenuItem("About");
        help.add(about);
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(this);

        label = new JLabel("");
        menuBar.add(label);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        squares = new JButton[3][3];
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
            {
                squares[i][j] = new JButton();
                panel.add(squares[i][j]);
                squares[i][j].addActionListener(this);
            }
        window.setJMenuBar(menuBar);
        window.add(panel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        c: for(JButton[] a : squares)
            for(JButton button : a) // pt fiecare buton din cele 9
                if(e.getSource() == button) { // daca butonul este acest buton
                    //daca butonul nu are "X" sau "O" pe el (este gol) si jocul nu s-a terminat
                    if(button.getText().equals("") && isGameOver() == -1) {
                        if(turn) { // daca e randul lui X
                            button.setForeground(Color.blue); // culoarea albastra
                            //aici doar fac sa fie mai mare
                            button.setFont(new Font(button.getFont().getName(),button.getFont().getStyle(),50));
                            //pun textul butonului "X"
                            button.setText("X");
                        }
                        else {
                            button.setForeground(Color.red); // rosu
                            //marime
                            button.setFont(new Font(button.getFont().getName(),button.getFont().getStyle(),50));
                            // "O"
                            button.setText("O");
                        }
                        turn = !turn; // schimb al cui e randul dupa fiecare apasare de buton din cele 9
                        isGameOver(); // aici intreb dupa fiecare apasare de buton daca s-a terminat jocul, daca s-a
                        // terminat o sa scrie "X/0 won" sau "draw"
                        break c;
                    }
                    //daca apas un buton din cele 9 dar jocul s-a terminat, atunci resetez totul, adica:
                    if(isGameOver() != -1) {
                        for(int i=0; i<3; i++)
                            for(int j=0; j<3; j++)
                                squares[i][j].setText(""); // pt fiecare buton resetez textul sa fie gol
                        turn = true; // la noul joc o sa inceapa X
                        label.setText(""); // pun label-ul care are "X/0 won" sau "draw" sa fie gol
                    }
                }
        if(e.getSource() == about) // daca sursa apasarii este in schimb butonul about
        {
            // asta am luat de pe net, basically face un message dialog cu ce vrei tu sa scrie, cu parametrii:
            // frameul in care vrei sa pui(poate fi si null), ce sa scrie, titlul dialogului, si ultimul am uitat, parca e tipul textului
            JOptionPane.showMessageDialog(null,"Tic Tac Toe 1.0\nMihnea Tirca", "About", JOptionPane.PLAIN_MESSAGE);
        }
        if(e.getSource() == howToPlay) // daca sursa apasarii este butonul howtoplay
        {
            // la fel ca mai sus cu alt text
            JOptionPane.showMessageDialog(null, "One player is X, one player is O. Take turns in drawing your " +
                    "symbol in one of the 9 squares.\n You can only draw in an empty square. \n You win if you connect 3 of your symbols either vertically, " +
                    "horizontally, or diagonally.\n If the 9 squares are filled with nobody having won, it's a tie.",
                    "How To Play", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public int isGameOver() {
        String winner = "";
        // intreaba daca sunt 3 simboluri la fel pe fiecare verticala, orizontala si diagonala posibila
        if(squares[0][0].getText().equals(squares[0][1].getText()) && squares[0][1].getText().equals(squares[0][2].getText()) && !squares[0][0].getText().equals(""))
            winner = squares[0][0].getText();
        else if(squares[1][0].getText().equals(squares[1][1].getText()) && squares[1][1].getText().equals(squares[1][2].getText()) && !squares[1][0].getText().equals(""))
            winner = squares[1][0].getText();
        else if(squares[2][0].getText().equals(squares[2][1].getText()) && squares[2][1].getText().equals(squares[2][2].getText()) && !squares[2][0].getText().equals(""))
            winner = squares[2][0].getText();
        else if(squares[0][0].getText().equals(squares[1][0].getText()) && squares[1][0].getText().equals(squares[2][0].getText()) && !squares[0][0].getText().equals(""))
            winner = squares[0][0].getText();
        else if(squares[0][1].getText().equals(squares[1][1].getText()) && squares[1][1].getText().equals(squares[2][1].getText()) && !squares[0][1].getText().equals(""))
            winner = squares[0][1].getText();
        else if(squares[0][2].getText().equals(squares[1][2].getText()) && squares[1][2].getText().equals(squares[2][2].getText()) && !squares[0][2].getText().equals(""))
            winner = squares[0][2].getText();
        else if(squares[0][0].getText().equals(squares[1][1].getText()) && squares[1][1].getText().equals(squares[2][2].getText()) && !squares[0][0].getText().equals(""))
            winner = squares[0][0].getText();
        else if(squares[0][2].getText().equals(squares[1][1].getText()) && squares[1][1].getText().equals(squares[2][0].getText()) && !squares[0][2].getText().equals(""))
            winner = squares[0][2].getText();
        // altfel, daca nu exista niciun castigator, sunt 2 situatii ramase. ori fiecare patrat este
        // acoperit, caz in care este remiza, ori nu s-au acoperit toate si nu a castigat nimeni inca,
        // caz in care jocul nu s-a terminat
        else for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                // deci pentru fiecare buton in parte, daca nu a castigat nimeni inca SI mai exista
                // vreun buton gol, atunci inca nu s-a terminat jocul
                if(squares[i][j].getText().equals(""))
                    return -1; // nu s-a terminat jocul
        // daca s-a terminat jocul, apoi avem urmatoarele situatii:
        // ori intr-unul din if()-uri am aflat cine este castigatorul cu getText(), sau nu a intrat pe niciun if(),
        // ceea ce inseamna ca nu a castigat nimeni, dar jocul s-a terminat, adica e remiza
        switch (winner) {
            case "" -> {
                label.setText("It's a draw.");
                return 0;
            }
            case "X" -> {
                label.setText("X won!");
                return 1;
            }
            case "O" -> {
                label.setText("O won!");
                return 2;
            }
        }
        // aici o valoare random (trebuie sa pun return in afara oricarui if sau switch in caz ca
        // nu intru pe acele ramuri, ca sa nu fie situatie in care sunt intr-o functie int fara return,
        // chiar daca este imposibil sa nu intru pe nicio ramura)
        return 3;
    }

    public static void main(String[] args)
    {
        new TicTacToe();
    }
}
