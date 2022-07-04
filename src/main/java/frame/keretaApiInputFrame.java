package frame;

import helpers.Koneksi;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class keretaApiInputFrame extends JFrame{
    private JPanel mainPanel;
    private JTextField idTextField;
    private JTextField jenisTextField;
    private JButton simpanButton;
    private JButton batalButton;


    public keretaApiInputFrame(){
        simpanButton.addActionListener(e -> {
            String jenis = jenisTextField.getText();
            Connection c = Koneksi.getConnection();
            PreparedStatement ps;

            String insertSQL = "INSERT INTO keretaapi VALUES (NULL, ? )";
            try {
                ps = c.prepareStatement(insertSQL);
                ps.setString(1,jenis);
                ps.executeUpdate();
                dispose();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        batalButton.addActionListener(e -> {
            dispose();
        });
        init();
    }
    public void init() {
        setContentPane(mainPanel);
        setTitle("Input KeretaApi");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
