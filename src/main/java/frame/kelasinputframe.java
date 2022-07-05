package frame;

import helpers.ComboBoxItem;
import helpers.Koneksi;

import javax.swing.*;
import java.sql.*;

public class kelasinputframe extends JFrame {
    private JPanel mainPanel;
    private JTextField idTextField;
    private JTextField jenisTextField;
    private JButton simpanButton;
    private JButton batalButton;
    private JComboBox jenisComboBox;
    private JRadioButton cepatRadioButton;
    private JRadioButton sangatCepatRadioButton;
    private JTextField HargaTextField;
    private JLabel HargaLabel;

    private ButtonGroup kecepatanButtonGroup;

    private int id;

    public void setId(int id) {
        this.id = id;
    }


    public kelasinputframe() {
        simpanButton.addActionListener(e -> {
            String jenis = jenisTextField.getText();
            if (jenis.equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Isi tipe kelas", "" +
                                "validasi validasi data kosong",
                        JOptionPane.WARNING_MESSAGE);
                jenisTextField.requestFocus();
                return;
            }

            ComboBoxItem item = (ComboBoxItem) jenisComboBox.getSelectedItem();
            int keretaApiId = item.getValue();
            if(keretaApiId == 0){
                JOptionPane.showMessageDialog(null,
                        "Pilih jenisKA",
                                "validasi data kosong",
                        JOptionPane.WARNING_MESSAGE);
                jenisComboBox.requestFocus();
                return;
            }

            String kecepatan = "";
            if (cepatRadioButton.isSelected()){
                kecepatan = "cepat";
            }else if (sangatCepatRadioButton.isSelected()){
                kecepatan = "sangat cepat";
            }else {
                JOptionPane.showMessageDialog(null,
                        "Pilih kecepatan",
                        "validasi data ksosng",
                        JOptionPane.WARNING_MESSAGE);
                jenisTextField.requestFocus();
                return;
            }

            String Harga = HargaTextField.getText();


            Connection c = Koneksi.getConnection();
            PreparedStatement ps;
            try {
                if (id == 0) {
                    String cekSQL = "SELECT * FROM kelas WHERE tipe = ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, jenis);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data tipe kelas sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        String insertSQL = "INSERT INTO kelas SET tipe = ?, keretaApi_id = ?, kecepatan = ?,Harga = ?";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, jenis);
                        ps.setInt(2, keretaApiId);
                        ps.setString(3, kecepatan);
                        ps.setString(4, Harga);
                        ps.executeUpdate();
                        dispose();
                    }
                } else {
                    String cekSQL = "SELECT * FROM kelas WHERE tipe = ?, AND id != ?";
                    ps = c.prepareStatement(cekSQL);
                    ps.setString(1, jenis);
                    ps.setInt(2, id);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Data tipe kelas sudah ada",
                                "Validasi data sama",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        String updateSQL = "UPDATE kelas SET tipe = ?, keretaApi_id = ?, kecepatan = ?, Harga = ? WHERE id = ?";
                        ps = c.prepareStatement(updateSQL);
                        ps.setString(1, jenis);
                        ps.setInt(2, keretaApiId);
                        ps.setString(3, kecepatan);
                        ps.setString(4, Harga);
                        ps.setInt(5, id);
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
        kustomisasiKomponen();
        init();
    }

    public void init() {
        setContentPane(mainPanel);
        setTitle("Input kelas");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiKomponen() {
        Connection c = Koneksi.getConnection();
        String findSQL = "SELECT * FROM kelas WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                jenisTextField.setText(rs.getString("tipe"));
                int keretaApiId = rs.getInt("keretaApi_id");
                for (int i = 0; i < jenisComboBox.getItemCount(); i++) {
                    jenisComboBox.setSelectedIndex(1);
                    ComboBoxItem Item = (ComboBoxItem) jenisComboBox.getSelectedItem();
                    if (keretaApiId == Item.getValue()) {
                        break;
                    }
                }
                String kecepatan = rs.getString("kecepatan");
                if (kecepatan != null) {
                    if (kecepatan.equals("cepat")){
                        cepatRadioButton.setSelected(true);
                    }else if (kecepatan.equals("SangatCepat")){
                        cepatRadioButton.setSelected(true);
                    }
                }
                HargaTextField.setText(rs.getString("harga_tiket"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void kustomisasiKomponen() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM keretaapi ORDER BY jenis";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            jenisComboBox.addItem(new ComboBoxItem(0, "Pilih Jenis KA"));
            while (rs.next()) {
                jenisComboBox.addItem(new ComboBoxItem(
                        rs.getInt("id"),
                        rs.getString("jenis")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        kecepatanButtonGroup = new ButtonGroup();
        kecepatanButtonGroup.add(cepatRadioButton);
        kecepatanButtonGroup.add(sangatCepatRadioButton);

    }
}