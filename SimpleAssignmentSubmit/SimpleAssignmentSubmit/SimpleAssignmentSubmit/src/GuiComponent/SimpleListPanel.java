package GuiComponent;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SimpleListPanel extends JPanel{
	private Color borderColor = new Color(180, 180, 180);
	private Color backgroundColor = new Color(245, 245, 245);
	
	public SimpleListPanel()
	{

		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor));
	}
}
