package org.pf1etools.roll20npc.ui.controls;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

public class HintTextArea extends JTextArea
{
	private final String hint;
	
	public HintTextArea(String hint)
	{
		this.hint = hint;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (StringUtils.isBlank(super.getText()))
		{
			int h = getHeight();
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Insets ins = getInsets();
			FontMetrics fm = g.getFontMetrics();
			int c0 = getBackground().getRGB();
			int c1 = getForeground().getRGB();
			int m = 0xfefefefe;
			int c2 = ((c0&m)>>>1)+((c1&m)>>>1);
			g.setColor(new Color(c2, true));
			g.drawString(hint, ins.left, ins.top+15);
		}
	}
}