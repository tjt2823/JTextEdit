import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * JTextEdit is a simple text editor that can also compile & run Java programs.
 * It's written purely in Java. The GUI is built using Swing and AWT.
 * 
 * Features of JTextEdit include:
 * - New File, Open File
 * - Save, Save As
 * - Cut, Copy, Paste, Delete
 * - Select All
 * - Find & Replace
 * - Compile, Run
 * 
 * @author Tom Thomas
 */
class JTextEdit extends Frame implements ActionListener
{
	/* The main text workspace */
	private TextArea ta = new TextArea();
	
	/* text that has been copied or cut */
	String clipBoard;
	
	/* File that is currently open */
	File openFile = null;

	/**
	 * Initializes the JTextEdit window
	 */
	public JTextEdit()
	{
		MenuBar mb = new MenuBar();
		setLayout(new BorderLayout());
		add("Center", ta);
		setMenuBar(mb);
		
		Menu m1 = new Menu("File");
		Menu m2 = new Menu("Edit");
		Menu m3 = new Menu("Tools");
		Menu m4 = new Menu("Help");
		
		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		
		MenuItem mi1[] = { new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
				new MenuItem("Exit") };
		
		MenuItem mi2[] = { new MenuItem("Delete"), new MenuItem("Cut"), new MenuItem("Copy"), new MenuItem("Paste"),
				new MenuItem("Find & Replace"), new MenuItem("Select All")};
		
		MenuItem mi3[] = { new MenuItem("Compile"), new MenuItem("Run") };
		
		MenuItem mi4[] = { new MenuItem("About JTextEdit") };
		
		for (int i = 0; i < mi1.length; i++)
		{
			m1.add(mi1[i]);
			mi1[i].addActionListener(this);
		}
		
		for (int i = 0; i < mi2.length; i++)
		{
			m2.add(mi2[i]);
			mi2[i].addActionListener(this);
		}
		
		for (int i = 0; i < mi3.length; i++)
		{
			m3.add(mi3[i]);
			mi3[i].addActionListener(this);
		}
		
		for (int i = 0; i < mi4.length; i++)
		{
			m4.add(mi4[i]);
			mi4[i].addActionListener(this);
		}

		addWindowListener(new WindowsAdapter(this));
		setSize(500, 500);
		setTitle("untitled JTextEdit");
		setVisible(true);
	}
	
	/**
	 * Returns the text area
	 * @return
	 */
	public TextArea getTA()
	{
		return ta;
	}
	
	/**
	 * Opens a file from your computer
	 */
	private void openFile()
	{
		try
		{
			FileDialog fd = new FileDialog(this, "Select File", FileDialog.LOAD);
			fd.setVisible(true);
			
			String fileName = fd.getFile();
			String fileDir = fd.getDirectory();
			
			if(fileName != null)
			{
				openFile = new File(fileDir + fileName);
				
				Scanner in = new Scanner(openFile);
				ta.setText("");
				
				while (in.hasNextLine())
					ta.append(in.nextLine() + "\n");
				
				in.close();
				this.setTitle(openFile.getAbsolutePath() + " JTextEdit File");
			}
			
		} catch (IOException e)
		{
			new ErrorDialog(this, e.getMessage()+": Something went wrong!");
		}
	}
	
	/**
	 * Saves file. Accepts boolean to check if it's save as operation
	 * 
	 * @param saveAs
	 */
	private void saveFile(boolean saveAs)
	{
		if (openFile == null)
			saveAs = true;
		
		if(saveAs)
		{
			FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
			dialog1.setVisible(true);
			String fileDir = dialog1.getDirectory();
			String fileName = dialog1.getFile();
			
			if(fileName != null)
				openFile = new File(fileDir + fileName);
			
			else
				return;
		}
		
		String text = ta.getText();
		
		try
		{
			FileWriter fW = new FileWriter(openFile);
			fW.write(text);
			fW.close();
		} catch (IOException e)
		{
			new ErrorDialog(this, e.getMessage());
		}
		
		this.setTitle(openFile.getAbsolutePath() + " JTextEdit File");
	}
	
	/**
	 * Compiles the java file. It saves the text file before compiling.
	 * Assumes that you will run this only on a .java file
	 */
	private void compile()
	{
		Runtime rt = Runtime.getRuntime();
		
		try
		{
			Process pr = rt.exec("cmd /c start cmd /k javac \"" + openFile.getAbsolutePath() + "\"");
		} catch (IOException e)
		{
			new ErrorDialog(this, e.getMessage());
		}
	}
	
	/**
	 * Compiles and runs the java file. It saves the text file before compiling.
	 * Assumes that you will run this only on a .java file
	 */
	private void run()
	{
		Runtime rt = Runtime.getRuntime();
		
		try
		{
			Process pr = rt.exec("cmd /k start cmd /k \"javac \"" + openFile.getAbsolutePath() + "\""
					+ " && cd \"" + openFile.getAbsolutePath().replace(openFile.getName(), "") + "\""
					+ " && java " + openFile.getName().replace(".java", "") + "\"");
		} catch (IOException e)
		{
			new ErrorDialog(this, e.getMessage());
		}
	}

	/**
	 * Action listener method for the menu items
	 */
	public void actionPerformed(ActionEvent ae)
	{
		String arg = ae.getActionCommand();
		
		if (arg.equals("New"))
		{
			if (!ta.getText().equals(""))
			{
				saveFile(false);
			}
			
			dispose();
			JTextEdit jte = new JTextEdit();
			jte.setSize(500, 500);
			jte.setVisible(true);
		}
		
		else if (arg.equals("Open"))
		{
			if (!ta.getText().equals(""))
			{
				saveFile(false);
			}
			
			openFile();
		}
		
		else if (arg.equals("Save"))
		{
			saveFile(false);
		}
		
		else if (arg.equals("Save As"))
		{
			saveFile(true);
		}
		
		else if (arg.equals("Exit"))
		{
			saveFile(false);
			dispose();
			System.exit(0);
		}
		
		else if (arg.equals("Cut"))
		{
			clipBoard = ta.getSelectedText();
			int i = ta.getText().indexOf(clipBoard);
			ta.replaceRange("", i, i + clipBoard.length());
		}
		
		else if (arg.equals("Copy"))
		{
			clipBoard = ta.getSelectedText();
		}
		
		else if (arg.equals("Paste"))
		{
			int pos = ta.getCaretPosition();
			ta.insert(clipBoard, pos);
		}
		
		else if (arg.equals("Delete"))
		{
			String delText = ta.getSelectedText();
			int i = ta.getText().indexOf(delText);
			ta.replaceRange("", i, i + delText.length());
		}
		
		else if (arg.equals("Find & Replace"))
		{
			new FindDialog(this);
		}
		
		else if (arg.equals("Select All"))
		{
			ta.selectAll();
		}
		
		else if (arg.equals("Compile"))
		{
			if (!ta.getText().equals(""))
			{
				saveFile(false);
				compile();
			}
		}
		
		else if (arg.equals("Run"))
		{
			if (!ta.getText().equals(""))
			{
				saveFile(false);
				run();
			}
		}
		
		else if (arg.equals("About JTextEdit"))
		{
			Dialog d1 = new Dialog(this, "About JTextEdit");
			TextArea t = new TextArea();
			t.setEditable(false);
			
			String aboutText = "JTextEdit is a simple text editor that can also compile & run Java programs*.\n"
					+ "It's written purely in Java. The GUI is built using Swing and AWT.\n"
					+ "\nFeatures of JTextEdit include:\n"
					+ "\t- New File, Open File\n"
					+ "\t- Save, Save As\n"
					+ "\t- Cut, Copy, Paste, Delete\n"
					+ "\t- Select All\n"
					+ "\t- Find & Replace\n"
					+ "\t- Compile, Run*\n"
					+ "\n* Note: For JTextEdit to be able to compile and run Java programs, you need to add\n"
					+ "the jdk to your PATH.";
			
			t.setText(aboutText);
			d1.add("Center", t);
			d1.setSize(500, 500);
			d1.setVisible(true);
			d1.addWindowListener(new WindowsAdapter(d1));
		}
	}
}