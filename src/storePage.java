import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Vector;

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
    // private JButton btnRefresh;
    private JTable tblStore;
    public storePage() {
        ButtonGroup G = new ButtonGroup();
        G.add(rBtnYes);
        G.add(rBtnNo);
        refreshStoreTable();
        store st= new store();
        st.storeNo =0;
        st.floorNo =0;
        st.area =0;
        st.price =0;
        btnAddStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //add store
                //store st= new store();
                st.storeNo =0;
                st.floorNo =0;
                st.area =0;
                st.price =0;
                try {
                    st.storeNo= Integer.parseInt(tfStoreNo.getText());
                    st.floorNo= Integer.parseInt(tfFloorNo.getText());
                    st.area= Float.parseFloat(tfArea.getText());
                    st.price= Float.parseFloat(tfPrice.getText());
                }
                catch (NumberFormatException ex)
                {
                }
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
                if (st.storeNo ==0 || st.floorNo==0 || st.area==0 || st.price==0)      //if fields are not filled
                {
                    JOptionPane.showMessageDialog(storePane,
                            "please enter all fields",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        st.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");

                        st.preparedStatement= st.connection.prepareStatement("insert into store(storeNo, floorNo, area, price, rented) values(?,?,?,?,?);");
                        st.preparedStatement.setInt(1,st.storeNo);
                        st.preparedStatement.setInt(2,st.floorNo);
                        st.preparedStatement.setFloat(3,st.area);
                        st.preparedStatement.setFloat(4,st.price);
                        st.preparedStatement.setBoolean(5,st.rented);
                        st.preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(storePane,
                                "new store added successfully",
                                "success",
                                JOptionPane.PLAIN_MESSAGE);
                        refreshStoreTable();
                    }
                    catch (SQLIntegrityConstraintViolationException ex)
                    {
                        JOptionPane.showMessageDialog(storePane,
                                "store number already exists",
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
        tblStore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRowIndex= tblStore.getSelectedRow();
                tfStoreNo.setText(tblStore.getValueAt(selectedRowIndex,0).toString());
                tfFloorNo.setText(tblStore.getValueAt(selectedRowIndex,1).toString());
                tfArea.setText(tblStore.getValueAt(selectedRowIndex,2).toString());
                tfPrice.setText(tblStore.getValueAt(selectedRowIndex,3).toString());
                String selected= tblStore.getValueAt(selectedRowIndex,4).toString();

                if(selected.equals("1"))
                {
                    rBtnYes.setSelected(true);
                }
                if(selected.equals("0"))
                {
                    rBtnNo.setSelected(true);
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                st.storeNo =0;
                st.floorNo =0;
                st.area =0;
                st.price =0;
                try {
                    st.storeNo= Integer.parseInt(tfStoreNo.getText());
                    st.floorNo= Integer.parseInt(tfFloorNo.getText());
                    st.area= Float.parseFloat(tfArea.getText());
                    st.price= Float.parseFloat(tfPrice.getText());
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                }
                if (rBtnYes.isSelected())
                {
                    st.rented= true;
                }
                if (rBtnNo.isSelected())
                {
                    st.rented= false;
                }
                if (st.storeNo ==0 || st.floorNo==0 || st.area==0 || st.price==0)      //if fields are not filled
                {
                    JOptionPane.showMessageDialog(storePane,
                            "please enter all fields",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        st.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
                        st.preparedStatement= st.connection.prepareStatement("update store set storeNo= ?, floorNo=?, area=?, price=?, rented=? where storeNo=? ;");
                        st.preparedStatement.setInt(1,st.storeNo);
                        st.preparedStatement.setInt(2,st.floorNo);
                        st.preparedStatement.setFloat(3,st.area);
                        st.preparedStatement.setFloat(4,st.price);
                        st.preparedStatement.setBoolean(5,st.rented);
                        st.preparedStatement.setInt(6,st.storeNo);
                        st.preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(storePane,
                                "store updated successfully",
                                "success",
                                JOptionPane.PLAIN_MESSAGE);
                        refreshStoreTable();
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
                st.storeNo =0;
                try {
                    st.storeNo= Integer.parseInt(tfStoreNo.getText());
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                }
                if (st.storeNo ==0 )      //if fields are not filled
                {
                    JOptionPane.showMessageDialog(storePane,
                            "please select the row to delete",
                            "try again",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        st.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");

                        st.preparedStatement= st.connection.prepareStatement("delete from store where storeNo= ? ;");
                        st.preparedStatement.setInt(1,st.storeNo);

                        st.preparedStatement.executeUpdate();

                        JOptionPane.showMessageDialog(storePane,
                                "store deleted successfully",
                                "success",
                                JOptionPane.PLAIN_MESSAGE);
                        refreshStoreTable();
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
    }
    void refreshStoreTable()
    {
        store st= new store();
        Vector<Object> titles= new Vector<>();

        titles.add("store no");
        titles.add("floor no");
        titles.add("area");
        titles.add("price");
        titles.add("rented");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            st.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
            st.statement= st.connection.createStatement();
            st.resultSet= st.statement.executeQuery("select * from store");
            Vector<Vector<Object>> data= new Vector<Vector<Object>>();

            while (st.resultSet.next())
            {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex=1; columnIndex<=5;columnIndex ++)
                {
                    vector.add(st.resultSet.getString(columnIndex));
                }
                data.add(vector);
            }
            tblStore.setModel(new DefaultTableModel(data,titles));
            tblStore.setDefaultEditor(Object.class,null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
