package GuiComponent;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SimpleLabel extends JLabel {
	
	private Font font = new Font("¸¼Àº °íµñ", Font.PLAIN, 15);
	private Font font2 = new Font("¸¼Àº °íµñ", Font.PLAIN, 12);

	private Color fontColor = new Color(120, 120, 120);
	private Color fontColor2 = new Color(150, 150, 150);
	
	public SimpleLabel(String text) {
		super(text);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBigFont();
	}

	public void setBigFont() {
		this.setFont(font);
		this.setForeground(fontColor);
	}
	
	public void setSmallFont() {
		this.setFont(font2);
		this.setForeground(fontColor2);
	}
}
