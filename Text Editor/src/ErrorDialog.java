import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * A class that extends Dialog and shows an error messages
 * or exceptions in an error window.
 * 
 * @author Tom Thomas
 */
public class ErrorDialog extends Dialog implements ActionListener
{
	ErrorDialog(Frame parent, String error)
	{
		super(parent, "Oops!", true);
		setAlwaysOnTop(true);
		setResizable(true);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setSize(300, 100);
		addWindowListener(new WindowsAdapter(this));
		
		JLabel tP = new JLabel(error);
		tP.setAlignmentX(CENTER_ALIGNMENT);
		tP.setAlignmentY(TOP_ALIGNMENT);
		JButton ok = new JButton("Ok");
		ok.addActionListener(this);
		ok.setAlignmentX(CENTER_ALIGNMENT);
		ok.setAlignmentY(BOTTOM_ALIGNMENT);
		
		add(tP);
		add(ok);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		dispose();
	}
}