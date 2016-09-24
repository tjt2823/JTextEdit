import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * Dialog window that is used for searching text in the document.
 * 
 * @author Tom Thomas
 */
public class FindDialog extends Dialog implements ActionListener
{
	private JTextField searchField;
	private JTextEdit parent;
	
	FindDialog(JTextEdit parent)
	{
		super(parent, "Find in text", false);
		this.parent = parent;
		setResizable(true);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setSize(300, 100);
		addWindowListener(new WindowsAdapter(this));
		
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(100, 50));
		
		JButton search = new JButton("Find Next");
		search.addActionListener(this);
		search.setAlignmentX(CENTER_ALIGNMENT);
		search.setAlignmentY(BOTTOM_ALIGNMENT);
		
		JButton replace = new JButton("Replace");
		replace.addActionListener(this);
		replace.setAlignmentX(CENTER_ALIGNMENT);
		replace.setAlignmentY(BOTTOM_ALIGNMENT);
		
		add(searchField);
		add(search);
		add(replace);
		
		setVisible(true);
	}
	
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
	
	private void replace()
	{
		
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