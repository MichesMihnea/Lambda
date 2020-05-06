import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
 * @author Miches
 *This class represents the window used by the Admin in order to view the items in the menu
 */
public class DataView extends JFrame{
	public JButton n_remove = new JButton("Remove From Menu");
	public JComboBox<String> n_basics;
	public JPanel content;
	public JTable menu;
	public Test test;

	public DataView (Test test){
		this.test = test;
		try {
			test.calc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		content = new JPanel();
        content.setLayout(new BorderLayout());
		
		List<String> columns = new ArrayList<String>();
        List<String[]> values = new ArrayList<String[]>();

        columns.add("Name");
        columns.add("Price");
        columns.add("Activity");
        
        List <MonitoredData> list = test.getData();
        Object[] o = new Object[3];
        for(MonitoredData mi: list) {
        	o[0] = mi.start;
        	o[1] = mi.end;
        	o[2] = mi.activity;
            values.add(new String[] {o[0].toString(), o[1].toString(), o[2].toString()});
        }
        
        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
        menu = new JTable(tableModel);
        
        content.add(new JScrollPane(menu), BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setContentPane(content);
		this.setSize(300, 200);
		this.setTitle("Warehouse");
		this.setVisible(true);
	}
}