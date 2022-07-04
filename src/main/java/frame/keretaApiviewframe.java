package frame;

import javax.swing.*;

public class keretaApiviewframe extends JFrame{
    private JPanel mainPanel;
    private JTextField caritextField1;
    private JButton caributton;
    private JTable viewTable;
    private JButton tambahButton;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton batalButton;
    private JButton cetakButton;
    private JButton tutupButton;

    public keretaApiviewframe(){
        init();
    }

    public void init(){
        setContentPane(mainPanel);
        setTitle("Data KeretaApi");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
