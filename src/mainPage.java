import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPage extends JDialog{
    private JPanel mainPane;
    private JPanel navbarPane;
    private JButton btnStore;
    private JButton btnRentee;
    private JButton btnStatus;
public mainPage() {
    btnStore.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            storePage sp= new storePage();
            sp.setContentPane(sp.storePane);
            sp.setTitle("STORE");
            sp.setBounds(100,70, 1150,600);
            sp.setVisible(true);
            sp.setDefaultCloseOperation(EXIT_ON_CLOSE);

        }
    });
    btnRentee.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            renteePage rp= new renteePage();
            rp.setContentPane(rp.renteePane);
            rp.setTitle("RENTEE");
            rp.setBounds(100,70, 1150,600);
            rp.setVisible(true);
            rp.setDefaultCloseOperation(EXIT_ON_CLOSE);

        }
    });
    btnStatus.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ststusPage sp= new ststusPage();
            sp.setContentPane(sp.statusPane);
            sp.setTitle("BUILDING STATUS");
            sp.setBounds(100,70, 1150,600);
            sp.setVisible(true);
            sp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    });
}

    public static void main(String[] args) {
        try {
            // select Look and Feel
           UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");   // mint.MintLookAndFeel
            // start application
           mainPage mp= new mainPage();
            mp.setContentPane(mp.mainPane);
            mp.setTitle("BUILDING RENTAL MANAGEMENT SYSTEM");
            mp.setBounds(50,50, 1250,700);
            mp.setVisible(true);
            mp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
