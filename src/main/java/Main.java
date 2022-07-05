import frame.kelasviewframe;
import frame.keretaApiviewframe;
import helpers.Koneksi;

public class Main {
    public static void main(String[] args) {
        //Koneksi.getConnection();
        //keretaApiviewframe viewframe = new keretaApiviewframe();
        kelasviewframe viewframe = new kelasviewframe();
        viewframe.setVisible(true );
    }
}
