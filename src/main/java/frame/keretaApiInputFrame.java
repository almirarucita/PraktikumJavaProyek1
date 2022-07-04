package frame;

import helpers.Koneksi;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class keretaApiInputFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField idTextField;
    private JTextField jenisTextField;
    private JButton simpanButton;
    private JButton batalButton;

    private int id;

    public void setId(int id) {
        this.id = id;
    }


    public keretaApiInputFrame() {
        simpanButton.addActionListener(e -> {
            String jenis = jenisTextField.getText();
            if(jenis.equals("")){
                JOptionPane.showMessageDialog(null,
                        "Isi jenis kereta api", "" +
                                "validasi validasi data kosong",
                        JOptionPane.WARNING_MESSAGE);
                jenisTextField.requestFocus();
                return;
            }

            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if (id == 0) {
                    String cekSQL = "SELECT * FROM keretaapi WHERE jenis = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, jenis);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data jenis keretaapi sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    }else {
                        String insertSQL = "INSERT INTO keretaapi SET jenis = ?";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, jenis);
                        ps.executeUpdate();
                        dispose();
                    }
                } else {
                    String cekSQL = "SELECT * FROM keretaapi WHERE jenis = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, jenis);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data jenis keretaapi sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    }else {
                        String updateSQL = "UPDATE keretaapi SET jenis = ? WHERE id = ?";
                        ps = c.prepareStatement(updateSQL);
                        ps.setString(1, jenis);
                        ps.setInt(2, id);
                        ps.executeUpdate();
                        dispose();

                    }

                }
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

    public void isiKomponen() {
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM keretaapi WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                jenisTextField.setText(rs.getString("jenis"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}