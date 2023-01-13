import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.util.Vector;

public class renteePage extends JDialog {
    public JPanel renteePane;
    private JTextField tfName;
    private JTextField tfId;
    private JTextField tfPhoneNo;
    private JButton btnAddRentee;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JTable tblRentee;
    private JTextField tfContEndingDate;
    private JComboBox cmbStoreNo;
public renteePage() {

    store st= new store();
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        st.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
        st.statement = st.connection.createStatement();
        st.resultSet = st.statement.executeQuery("select storeNo from store where rented=false");
        while (st.resultSet.next()) {
            cmbStoreNo.addItem(st.resultSet.getObject(1));
        }
    }
    catch (Exception ex) {
        ex.printStackTrace();
    }
    btnAddRentee.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           //
        }
    });
}
}
