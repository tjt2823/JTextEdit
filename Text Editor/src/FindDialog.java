import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog window that is used for searching text in the document.
 * 
 * @author Tom Thomas
 */
public class FindDialog extends Dialog implements ActionListener
{
	private JTextField searchField;
	private JTextField replaceField;
	private JTextEdit parent;
	
	FindDialog(JTextEdit parent)
	{
		super(parent, "Find in text", false);
		this.parent = parent;
		setResizable(true);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setSize(300, 100);
		addWindowListener(new WindowsAdapter(this));
		
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(100, 50));
		
		JButton search = new JButton("Find Next");
		search.addActionListener(this);
		search.setAlignmentX(CENTER_ALIGNMENT);
		search.setAlignmentY(BOTTOM_ALIGNMENT);
		
		replaceField = new JTextField();
		replaceField.setPreferredSize(new Dimension(100, 50));
		
		JButton replace = new JButton("Replace");
		replace.addActionListener(this);
		replace.setAlignmentX(CENTER_ALIGNMENT);
		replace.setAlignmentY(BOTTOM_ALIGNMENT);
		
		JPanel findPanel = new JPanel();
		findPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		findPanel.add(searchField);
		findPanel.add(search);
		
		JPanel replacePanel = new JPanel();
		replacePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		replacePanel.add(replaceField);
		replacePanel.add(replace);
		
		add(findPanel);
		add(replacePanel);
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Method that finds the string entered in the search field and
	 * selects it
	 */
	private void searchSelect()
	{
		TextArea tA = parent.getTA();
		int index = tA.getText().indexOf(searchField.getText());
		tA.requestFocus();		
		
		if(index != -1)
			tA.select(index, index + searchField.getText().length());
		
		else
			tA.select(0, 0);
	}
	
	/**
	 * Method that replaces selected text with the provided
	 * replacement text.
	 */
	private void replace()
	{
		TextArea tA = parent.getTA();
		String replacement = replaceField.getText();
		String selectedString = tA.getSelectedText();
		String s = tA.getText();
		int position = tA.getSelectionStart();
		
		tA.setText(s.substring(0, position) + replacement + s.substring(position + selectedString.length()));
		tA.requestFocus();
		tA.select(position, position + replacement.length());
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String arg = ae.getActionCommand();
		
		switch (arg)
		{
			case "Find Next":
				searchSelect();
				break;
			case "Replace":
				replace();
		}
	}
}