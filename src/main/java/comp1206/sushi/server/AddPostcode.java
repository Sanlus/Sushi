package comp1206.sushi.server;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import comp1206.sushi.server.Postcodes;
import comp1206.sushi.server.ServerWindow;

public class AddPostcode extends JFrame {
	
	private ServerInterface server;
	private Postcodes postcodePanel = new Postcodes(server);
	private static final String addString = "Add";
	private JTextField postcode = new JTextField("Postcode");
	
	public AddPostcode(String s, ServerInterface server, Postcodes postcodePanel) {
		
		// store reference
		super("Add Button");
		this.server = server;
		this.postcodePanel = postcodePanel;
		postcode = new JTextField("Postcode");
		JButton addButton = new JButton(addString);
		AddListener addListener = new AddListener(addButton, this.postcodePanel);
		addButton.setActionCommand(addString);
		addButton.addActionListener(addListener);
		postcode.selectAll();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		panel.add(postcode);
		panel.add(addButton);
		this.add(panel);
		
		// display
		setSize(400,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
private class AddListener implements ActionListener, DocumentListener{
		
		private JButton button;
		private Postcodes postcodePanel;
		
		public AddListener(JButton button, Postcodes postcodePanel) {
			this.button = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = postcode.getText();
			
			// check if the postcode entered in was valid
			if (name.equals("")) {
                Toolkit.getDefaultToolkit().beep();
                postcode.requestFocusInWindow();
                postcode.selectAll();
                return;
            }
			Object[] newRow = {name,0};
            postcodePanel.getTableModel().addRow(newRow);
            System.out.println(postcodePanel);
            
           
			//resets the textfield
			postcode.requestFocusInWindow();
			postcode.setText("");
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
}
