package frame;

import helpers.Koneksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class keretaApiviewframe extends JFrame {
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

    public keretaApiviewframe() {
        hapusButton.addActionListener(e -> {
            int barisTerpilih = viewTable.getSelectedRow();
            if(barisTerpilih < 0){
                JOptionPane.showMessageDialog(null,"Pilih data dulu");
                return;
            }
            int pilihan = JOptionPane.showConfirmDialog(null,
                    "Yakin mau hapus?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION
            );
            if (pilihan == 0 ){
                TableModel tm = viewTable.getModel();
                int id = Integer.parseInt(tm.getValueAt(barisTerpilih,0).toString());
                Connection c =Koneksi.getConnection();
                String deleteSQL = "DELETE FROM keretaapi WHERE id = ?";
                try {
                    PreparedStatement ps = c.prepareStatement(deleteSQL);
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        caributton.addActionListener(e -> {
            Connection c = Koneksi.getConnection();
            String keyword = "%" + caritextField1.getText() + "%";
            String searchSQL = "SELECT * FROM keretaapi WHERE jenis like ?";
            try {
                PreparedStatement ps = c.prepareStatement(searchSQL);
                ps.setString(1, keyword);
                ResultSet rs = ps.executeQuery();
                DefaultTableModel dtm = (DefaultTableModel) viewTable.getModel();
                dtm.setRowCount(0);
                Object[] row = new Object[2];
                while (rs.next()){
                    row[0] = rs.getInt("id");
                    row[1] = rs.getString("jenis");
                    dtm.addRow(row);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        tutupButton.addActionListener(e -> {
            dispose();
        });
        batalButton.addActionListener(e -> {
            isiTabel();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                isiTabel();
            }
        });
        isiTabel();
        init();
    }

    public void init() {
        setContentPane(mainPanel);
        setTitle("Data KeretaApi");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void isiTabel() {
        Connection c = Koneksi.getConnection();
        String selectSQL = "SELECT * FROM keretaapi";

        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(selectSQL);
            String header[] = {"Id", "Jenis"};
            DefaultTableModel dtm = new DefaultTableModel(header, 0);
            viewTable.setModel(dtm);

            viewTable.getColumnModel().getColumn(0).setMaxWidth(32);

            Object[] row = new Object[2];
            while (rs.next()) {
                row[0] = rs.getInt("Id");
                row[1] = rs.getString("Jenis");
                dtm.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
