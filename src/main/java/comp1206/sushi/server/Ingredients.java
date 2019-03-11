package comp1206.sushi.server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.Postcodes.RemoveListener;

public class Ingredients extends JPanel implements ListSelectionListener{
	
	private ArrayList<Ingredient> ingredientList;
	private ArrayList<Supplier> supplierList;
	private JList nameList;
	private JList statusList;
	private DefaultListModel nameListModel;
	private DefaultListModel statusListModel;
	private JButton removeButton;
	private JTextField supplierName = new JTextField(8);
	private JTextField supplierStatus = new JTextField(8);
	private static final String addString = "Add";
	private static final String removeString = "Remove";
	
	public Ingredients(ArrayList<Ingredient> ingredientList) {
		
		super(new BorderLayout());
		
		// create list of suppliers and postcodes
		this.ingredientList = ingredientList;
		this.supplierList = new ArrayList<Supplier>();
		
		// create list model for name and status of drones
		nameListModel = new DefaultListModel();
		statusListModel = new DefaultListModel();
		
		for (Ingredient i : ingredientList) {
			nameListModel.addElement(i.getName());
			statusListModel.addElement(i.getSupplier().getName());
			supplierList.add(i.getSupplier());
		}
		
		// create namelist
		nameList = new JList(nameListModel);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameList.addListSelectionListener(this);
		nameList.setVisibleRowCount(20);
		JScrollPane nameListScrollPane = new JScrollPane(nameList);
		
		// create statuslist
		statusList = new JList(statusListModel);
		statusList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statusList.addListSelectionListener(this);
		statusList.setVisibleRowCount(20);
		JScrollPane statusListScrollPane = new JScrollPane(statusList);
		
		// create list pane
		JPanel listPane = new JPanel();
		listPane.setLayout(new FlowLayout());
		nameListScrollPane.setPreferredSize(new Dimension(340,500));
		statusListScrollPane.setPreferredSize(new Dimension(340,500));
		listPane.add(nameListScrollPane);
		listPane.add(statusListScrollPane);
		
		// create addbutton
		JButton addButton = new JButton(addString);
		AddListener addListener = new AddListener(addButton);
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
		buttonPane.add(supplierName);
		buttonPane.add(supplierStatus);
		buttonPane.add(removeButton);
		
		// implement the border layout
		add(listPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			
			if (nameList.getSelectedIndex() == -1 || statusList.getSelectedIndex() == -1) {
				removeButton.setEnabled(false);
			}else {
				removeButton.setEnabled(true);
			}
		}
	}
	
	class AddListener implements ActionListener, DocumentListener{
		
		private JButton button;
		
		public AddListener(JButton button) {
			this.button = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = supplierName.getText();
			String status = supplierStatus.getText();
			
			// check if the data entered in was valid
			if (name.equals("") || nameListModel.contains(name)) {
                Toolkit.getDefaultToolkit().beep();
                supplierName.requestFocusInWindow();
                supplierName.selectAll();
                return;
            }

           nameListModel.addElement(name);
           statusListModel.addElement(status);
            
			//resets the textfield
			supplierName.requestFocusInWindow();
			supplierName.setText("");
			supplierStatus.setText("");
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

            int nameIndex = nameList.getSelectedIndex();
            int statusIndex = statusList.getSelectedIndex();
            int checkSameIndex = nameIndex - statusIndex;
            
            if (!(checkSameIndex == 0) || nameIndex == -1){
                Toolkit.getDefaultToolkit().beep();
                supplierStatus.requestFocusInWindow();
                supplierStatus.selectAll();
                return;
            }
            
            nameListModel.remove(nameIndex);
            statusListModel.remove(statusIndex);
 
            int size = nameListModel.getSize();
 
            if (size == 0) {
                removeButton.setEnabled(false);
 
            } else {
                if (nameIndex == nameListModel.getSize()) {
                    nameIndex--;
                }
                nameList.setSelectedIndex(nameIndex);
                nameList.ensureIndexIsVisible(nameIndex);
            }
        }
    }
}