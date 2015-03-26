package GuiComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class SimpleTextField extends JTextField implements MouseListener, FocusListener {
	private Font font = new Font("¸¼Àº °íµñ", Font.PLAIN, 15);
	private Font font2 = new Font("¸¼Àº °íµñ", Font.PLAIN, 12);
	
	private Color fontColor = new Color(180, 180, 180);
	private Color fontColor2 = new Color(120, 120, 120);
	
	private String text;

	public SimpleTextField() {
		super();
		custom();
	}
	
	public SimpleTextField(String arg) {
		super(arg);
		custom();
		text = arg;
	}
	
	private void custom() {
		this.setBackground(Color.WHITE);
		Border empty = new EmptyBorder(10, 10, 10, 10);
		this.setBorder(empty);
		this.setFont(font);
		this.setForeground(fontColor);
		this.addFocusListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
	}
	
	public void setGrayColor(String text) {
		this.text = text;
		this.setForeground(fontColor);
	}

	public void focusGained(FocusEvent arg0) {
		this.setForeground(fontColor2);
		if(this.getText().equals(text))
			this.setText("");
	}

	public void focusLost(FocusEvent arg0) {
		if(this.getText().equals("")) {
			this.setText(text);
			this.setForeground(fontColor);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
		this.setFocusable(true);
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
