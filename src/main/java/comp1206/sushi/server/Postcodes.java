package comp1206.sushi.server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Postcodes extends JPanel implements ListSelectionListener{
	
	private ServerInterface server;
	private JList list;
	private DefaultListModel listModel;
	private DefaultTableModel model;
	private JButton removeButton;
	private static final String addString = "Add";
	private static final String removeString = "Remove";
	
	public Postcodes(ServerInterface server) {
		super(new BorderLayout());
		
		// create header and data for table
		model = new DefaultTableModel();
		String[] columnNames = {"Postcode","Distance"};
		Object[][] data = {{"SOSOSOSO",0},{"SOSOOSSO",0},{"SOSOSOOS",0}};
		
		// create table
		JTable table = new JTable(data, columnNames);
		table.setRowSelectionAllowed(true);
		JScrollPane tableScrollPane = new JScrollPane(table);
		
		// create addbutton
		JButton addButton = new JButton(addString);
		AddListener addListener = new AddListener(addButton,this);
		addButton.setActionCommand(addString);
		addButton.addActionListener(addListener);
		
		// create removebutton
		removeButton = new JButton(removeString);
		removeButton.setActionCommand(removeString);
		removeButton.addActionListener(new RemoveListener());
		
		// create panel for button group
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPane.add(addButton);
		buttonPane.add(removeButton);
		
		// implement the border layout
		add(tableScrollPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			
			if (list.getSelectedIndex() == -1) {
				removeButton.setEnabled(false);
			}else {
				removeButton.setEnabled(true);
			}
		}
	}
	
	private class AddListener implements ActionListener, DocumentListener{
		
		private JButton button;
		private Postcodes postcodes;
		public AddListener(JButton button, Postcodes postcodes) {
			this.button = button;
			this.postcodes = postcodes;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			AddPostcode adding = new AddPostcode("",server,postcodes);
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			
        }

		@Override
		public void insertUpdate(DocumentEvent e) {
				
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			
		}			
	}
	
    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int index = list.getSelectedIndex();
            listModel.remove(index);
 
            int size = listModel.getSize();
 
            if (size == 0) {
                removeButton.setEnabled(false);
 
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }
    
    public DefaultTableModel getTableModel() {
    	return model;
    }
}
