import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class storePage extends JDialog {
    public JPanel storePane;
    private JTextField tfFloorNo;
    private JTextField tfStoreNo;
    private JTextField tfArea;
    private JTextField tfPrice;
    private JRadioButton rBtnYes;
    private JRadioButton rBtnNo;
    private JButton btnAddStore;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JTable tblStore;
public storePage() {
    ButtonGroup G = new ButtonGroup();
    G.add(rBtnYes);
    G.add(rBtnNo);
    btnAddStore.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //add store
            store st= new store();
            st.storeNo= Integer.parseInt(tfStoreNo.getText());
            st.floorNo= Integer.parseInt(tfFloorNo.getText());
            st.area= Float.parseFloat(tfArea.getText());
            st.price= Float.parseFloat(tfPrice.getText());

            if (rBtnYes.isSelected())
            {
                st.rented= true;
            }
           else if (rBtnNo.isSelected())
            {
                st.rented= false;
            }
           else     //if radio button is not selected
            {
                JOptionPane.showMessageDialog(storePane,
                        "please enter all fields",
                        "try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (st.storeNo ==0 || st.floorNo==0 || st.area==0 || st.price==0 )      //if fields are not filled
            {
                JOptionPane.showMessageDialog(storePane,
                        "please enter all fields",
                        "try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }


        }
    });
}
}
