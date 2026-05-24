package parkingmanagementsystem;
import javax.swing.SwingUtilities;
import parkingmanagementsystem.gui.ParkingGUI;
public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ParkingGUI::new);
    }
}