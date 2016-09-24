import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * An adapter for window events
 * 
 * @author Tom Thomas
 */
public class WindowsAdapter extends WindowAdapter
{
	private Window w;

	public WindowsAdapter(Window w)
	{
		this.w = w;
	}

	public void windowClosing(WindowEvent we)
	{
		w.dispose();
	}
}