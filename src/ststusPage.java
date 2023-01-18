import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.util.Vector;

public class ststusPage extends JDialog{
    private JTable tblDetail;
    private JProgressBar pbRented;
    private JProgressBar pbFree;
    private JProgressBar pbPaid;
    private JProgressBar pbUnpaid;
    private JButton btnRentedDetail;
    private JButton btnFreeDetail;
    private JButton btnUnpaidDetail;
    private JButton btnPaidDetail;
    public JPanel statusPane;
    private JLabel lblGeneratedAmount;
    private JLabel lblRentedAmount;
    private JLabel lblFreeAmount;
    private JLabel lblPaidAmount;
    private JLabel lblUnpaidAmount;
public ststusPage() {

    rentee rn= new rentee();
    store st= new store();

    long millis=System.currentTimeMillis();
    java.sql.Date dates=new java.sql.Date(millis);

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        st.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/buildingDB?user=root&password=root");
        st.statement= st.connection.createStatement();
        // generated amount of money
        st.resultSet= st.statement.executeQuery("SELECT SUM(price) as totalPrice FROM store WHERE rented=true;");
        while (st.resultSet.next())
        {
            lblGeneratedAmount.setText("this building is generating " + st.resultSet.getString("totalPrice") + " birr per month");
        }
        // total no of store
        st.resultSet= st.statement.executeQuery("SELECT count(*) as totalStore FROM store;");
        int totalNoOfStore=0;
        while (st.resultSet.next())
        {
           totalNoOfStore= st.resultSet.getInt("totalStore");
        }
        // rented store
        st.resultSet= st.statement.executeQuery("SELECT count(*) as rentedStore FROM store WHERE rented= true;");
        float noOfRentedStore;
        while (st.resultSet.next())
        {
            noOfRentedStore= st.resultSet.getInt("rentedStore");
            float rentedPercentage= (noOfRentedStore / totalNoOfStore) * 100;
            lblRentedAmount.setText(rentedPercentage + "% of store is rented");
            pbRented.setMinimum(0);
            pbRented.setMaximum(100);
            pbRented.setValue((int) rentedPercentage);
        }
        // free store
        st.resultSet= st.statement.executeQuery("SELECT count(*) as freeStore FROM store WHERE rented= false;");
        float noOfFreeStore;
        while (st.resultSet.next())
        {
            noOfFreeStore= st.resultSet.getInt("freeStore");
            float freePercentage= (noOfFreeStore / totalNoOfStore) * 100;
            lblFreeAmount.setText(freePercentage + "% of store is free");
            pbFree.setMinimum(0);
            pbFree.setMaximum(100);
            pbFree.setValue((int) freePercentage);
        }
        // total no of rentee
        st.resultSet= st.statement.executeQuery("SELECT count(*) as totalRentee FROM rentee;");
        int totalNoOfRentee =0;
        while (st.resultSet.next())
        {
            totalNoOfRentee= st.resultSet.getInt("totalRentee");

        }
        // paid rentee
        st.resultSet= st.statement.executeQuery("select count(*) as paid from rentee where contEndDate > '"+ dates +"' or contEndDate = '"+ dates +"' ;");
        float noOfPaidRentee;
        while (st.resultSet.next())
        {
            noOfPaidRentee= st.resultSet.getFloat("paid");
            float paidPercentage= (noOfPaidRentee / totalNoOfRentee) * 100;
            lblPaidAmount.setText(paidPercentage + "% of rentees have paid");
            pbPaid.setMinimum(0);
            pbPaid.setMaximum(100);
            pbPaid.setValue((int) paidPercentage);
        }
        // unpaid amount of rentee
        st.resultSet= st.statement.executeQuery("select count(*) as unpaid from rentee where contEndDate < '"+ dates +"' ;");
        float noOfUnpaidRentee;
        while (st.resultSet.next())
        {
            noOfUnpaidRentee= st.resultSet.getFloat("unpaid");
            float unpaidPercentage= (noOfUnpaidRentee / totalNoOfRentee) * 100;
            lblUnpaidAmount.setText(unpaidPercentage + "% of rentees have unpaid");
            pbUnpaid.setMinimum(0);
            pbUnpaid.setMaximum(100);
            pbUnpaid.setValue((int) unpaidPercentage);
        }



    }
    catch (ClassNotFoundException ex)
    {
        ex.printStackTrace();
    }
    catch (Exception ex) {
        ex.printStackTrace();
    }

        btnRentedDetail.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // display details of rented stores
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
                    st.resultSet= st.statement.executeQuery("select * from store where rented= true");
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
                    tblDetail.setModel(new DefaultTableModel(data,titles));
                    tblDetail.setDefaultEditor(Object.class,null);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    });
    btnFreeDetail.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        // display free stores
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
                st.resultSet= st.statement.executeQuery("select * from store where rented= false");
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
                tblDetail.setModel(new DefaultTableModel(data,titles));
                tblDetail.setDefaultEditor(Object.class,null);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    btnPaidDetail.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
                rn.resultSet= rn.statement.executeQuery("select * from rentee where contEndDate > '"+ dates +"' or contEndDate = '"+ dates +"' ;");
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
                tblDetail.setModel(new DefaultTableModel(data,titles));
                tblDetail.setDefaultEditor(Object.class,null);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    btnUnpaidDetail.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
                rn.resultSet= rn.statement.executeQuery("select * from rentee where contEndDate < '"+ dates +"' ;");
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
                tblDetail.setModel(new DefaultTableModel(data,titles));
                tblDetail.setDefaultEditor(Object.class,null);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
}
}
