package comp1206.sushi.server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import comp1206.sushi.server.Postcodes.RemoveListener;

public class Drones extends JPanel implements ListSelectionListener{
	
	private JList nameList;
	private JList statusList;
	private DefaultListModel nameListModel;
	private DefaultListModel statusListModel;
	private JButton removeButton;
	private JTextField droneName = new JTextField(8);
	private JTextField droneStatus = new JTextField(8);
	private static final String addString = "Add";
	private static final String removeString = "Remove";
	
	public Drones() {
		super(new BorderLayout());
		
		// create list model for name and status of drones
		nameListModel = new DefaultListModel();
		statusListModel = new DefaultListModel();
		nameListModel.addElement("Drone1");
		nameListModel.addElement("Drone2");
		nameListModel.addElement("Drone3");
		statusListModel.addElement("Idle");
		statusListModel.addElement("Idle");
		statusListModel.addElement("Flying");
		
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
		buttonPane.add(droneName);
		buttonPane.add(droneStatus);
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
	
	private class AddListener implements ActionListener, DocumentListener{
		
		private JButton button;
		
		public AddListener(JButton button) {
			this.button = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = droneName.getText();
			String status = droneStatus.getText();
			
			// check if the drone entered in was valid
			if (name.equals("") || nameListModel.contains(name)) {
                Toolkit.getDefaultToolkit().beep();
                droneName.requestFocusInWindow();
                droneName.selectAll();
                return;
            }
			
			if (!status.equals("Idle") && !status.equals("Flying")) {
                Toolkit.getDefaultToolkit().beep();
                droneStatus.requestFocusInWindow();
                droneStatus.selectAll();
                return;
            }

           nameListModel.addElement(name);
           statusListModel.addElement(status);
            
			//resets the textfield
			droneName.requestFocusInWindow();
			droneName.setText("");
			droneStatus.setText("");
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
                droneStatus.requestFocusInWindow();
                droneStatus.selectAll();
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