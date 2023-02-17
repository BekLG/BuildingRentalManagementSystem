import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Vector;

public class renteePage extends JDialog {
    public JPanel renteePane;
    private JTextField tfName;
    private JTextField tfId;
    private JTextField tfPhoneNo;
    private JButton btnAddRentee;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTable tblRentee;
    private JTextField tfContEndingDate;
    private JComboBox cmbStoreNo;
    private JButton btnClear;

    public renteePage() {

        rentee rn= new rentee();
        store st= new store();
        refreshStoreTable();

        long millis=System.currentTimeMillis();
        java.sql.Date dates=new java.sql.Date(millis);
        refreshCmbStore();

        btnAddRentee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                rn.id=0;
                rn.storeNo=0;
                try  {
                    rn.id= Integer.parseInt(tfId.getText());
                    rn.storeNo= ((Integer) cmbStoreNo.getSelectedItem()).intValue();
                    rn.name= tfName.getText();
                    rn.phoneNo= tfPhoneNo.getText();
                    rn.contStartDate= String.valueOf(dates);
                    rn.contEndDate= tfContEndingDate.getText();
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                }
                if (rn.id==0 || rn.storeNo==0 || rn.name.isEmpty() || rn.phoneNo.isEmpty() || rn.contStartDate.isEmpty() || rn.contEndDate.isEmpty())
                {
                    JOptionPane.showMessageDialog(renteePane,
                            "please enter all fields",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        st.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");

                        st.preparedStatement = st.connection.prepareStatement("insert into rentee(id, name, phoneNo, storeNo, contStartDate, contEndDate) values(?,?,?,?,?,?);");
                        st.preparedStatement.setInt(1, rn.id);
                        st.preparedStatement.setString(2,rn.name);
                        st.preparedStatement.setString(3,rn.phoneNo);
                        st.preparedStatement.setInt(4, rn.storeNo);
                        st.preparedStatement.setString(5,rn.contStartDate);
                        st.preparedStatement.setString(6,rn.contEndDate);
                        st.preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(renteePane,
                                "new rentee added successfully",
                                "success",
                                JOptionPane.PLAIN_MESSAGE);
                        // take the store
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        rn.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
                        rn.preparedStatement = rn.connection.prepareStatement(" UPDATE store SET rented = true WHERE storeNo = ?;");
                        rn.preparedStatement.setInt(1, rn.storeNo);
                        rn.preparedStatement.executeUpdate();

//                        Class.forName("org.h2.Driver");
//                        rn.connection = DriverManager.getConnection("jdbc:h2:./data/buildingDB;USER=root;PASSWORD=root");
//                        rn.preparedStatement = rn.connection.prepareStatement("UPDATE store SET rented = true WHERE storeNo = ?");
//                        rn.preparedStatement.setInt(1, rn.storeNo);
//                        rn.preparedStatement.executeUpdate();


                        refreshStoreTable();
                        refreshCmbStore();
                    }
                    catch (SQLIntegrityConstraintViolationException ex)
                    {
                        JOptionPane.showMessageDialog(renteePane,
                                "rentee id already exists",
                                "error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    catch (ClassNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        tblRentee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    int selectedRowIndex= tblRentee.getSelectedRow();
                    tfId.setText(tblRentee.getValueAt(selectedRowIndex,0).toString());
                    tfName.setText(tblRentee.getValueAt(selectedRowIndex,1).toString());
                    tfPhoneNo.setText(tblRentee.getValueAt(selectedRowIndex,2).toString());
                    cmbStoreNo.getModel().setSelectedItem(tblRentee.getValueAt(selectedRowIndex,3));
                    tfContEndingDate.setText(tblRentee.getValueAt(selectedRowIndex,5).toString());
                }
                catch(ClassCastException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rn.id=0;
                rn.storeNo=0;
                try  {
                    rn.id= Integer.parseInt(tfId.getText());
                    String strNo2 = cmbStoreNo.getSelectedItem().toString();
                    rn.storeNo= Integer.parseInt(strNo2);
                    rn.name= tfName.getText();
                    rn.phoneNo= tfPhoneNo.getText();
                    rn.contStartDate= String.valueOf(dates);
                    rn.contEndDate= tfContEndingDate.getText();
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                }
                if (rn.id==0 || rn.storeNo==0 || rn.name.isEmpty() || rn.phoneNo.isEmpty() || rn.contStartDate.isEmpty() || rn.contEndDate.isEmpty())
                {
                    JOptionPane.showMessageDialog(renteePane,
                            "please enter all fields",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        st.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
                        st.preparedStatement = st.connection.prepareStatement("update rentee set id= ?, name=?, phoneNo=?, storeNo=?, contEndDate=? where id=? ;");
                        st.preparedStatement.setInt(1, rn.id);
                        st.preparedStatement.setString(2,rn.name);
                        st.preparedStatement.setString(3,rn.phoneNo);
                        st.preparedStatement.setInt(4, rn.storeNo);
                        st.preparedStatement.setString(5,rn.contEndDate);
                        st.preparedStatement.setInt(6, rn.id);
                        st.preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(renteePane,
                                " rentee updated successfully",
                                "success",
                                JOptionPane.PLAIN_MESSAGE);
                        refreshStoreTable();
                        refreshCmbStore();
                    }
                    catch (ClassNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rn.id=0;
                rn.storeNo=0;
                try {
                    rn.id= Integer.parseInt(tfId.getText());
                    String strNo2 = cmbStoreNo.getSelectedItem().toString();
                    rn.storeNo= Integer.parseInt(strNo2);
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                }
                if (rn.id ==0 || rn.storeNo==0 )      //if fields are not filled
                {
                    JOptionPane.showMessageDialog(renteePane,
                            "please select the row to delete",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        rn.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");

                        rn.preparedStatement= rn.connection.prepareStatement("delete from rentee where id= ? ;");
                        rn.preparedStatement.setInt(1,rn.id);
                        rn.preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(renteePane,
                                "rentee deleted successfully",
                                "success",
                                JOptionPane.PLAIN_MESSAGE);
                        //   release the store
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        rn.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
                        rn.preparedStatement = rn.connection.prepareStatement(" UPDATE store SET rented = false WHERE storeNo = ?;");
                        //update store set rented= false where storeNo=? ;
                        rn.preparedStatement.setInt(1, rn.storeNo);
                        rn.preparedStatement.executeUpdate();

                        refreshStoreTable();
                        refreshCmbStore();
                    }
                    catch (ClassNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch (ClassCastException ex)
                    {
                        ex.printStackTrace();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfId.setText("");
                tfName.setText("");
                tfPhoneNo.setText("");
                tfContEndingDate.setText("");
                cmbStoreNo.setSelectedIndex(0);
            }
        });
    }
    void refreshStoreTable()
    {
        Vector<Object> titles= new Vector<>();
        titles.add("id ");
        titles.add("name");
        titles.add("phone no");
        titles.add("store no");
        titles.add("contract start date");
        titles.add("contract end date");

        try {
            rentee rn= new rentee();
            Class.forName("com.mysql.cj.jdbc.Driver");
            rn.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
            rn.statement= rn.connection.createStatement();
            rn.resultSet= rn.statement.executeQuery("select * from rentee");
            Vector<Vector<Object>> data= new Vector<Vector<Object>>();

            while (rn.resultSet.next())
            {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex=1; columnIndex<=6;columnIndex ++)
                {
                    vector.add(rn.resultSet.getString(columnIndex));
                }
                data.add(vector);
            }
            tblRentee.setModel(new DefaultTableModel(data,titles));
            tblRentee.setDefaultEditor(Object.class,null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void refreshCmbStore()
    {
        store st= new store();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            st.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
            st.statement = st.connection.createStatement();
            st.resultSet = st.statement.executeQuery("select storeNo from store where rented=false");
            cmbStoreNo.removeAllItems();
            while (st.resultSet.next()) {
                cmbStoreNo.addItem(st.resultSet.getObject(1));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
