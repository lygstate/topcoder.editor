package topcoder.editor.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.border.LineBorder;

public class EditorRoundBorder extends LineBorder {
	/**
	 * RoundBorder serialVersionUID
	 */
	private static final long serialVersionUID = -3016467626337207467L;

	public EditorRoundBorder(Color color) {
		super(color, 1);
		this.roundedCorners = false;
	}

	public EditorRoundBorder(Color color, int thickness) {
		super(color, thickness);
		this.roundedCorners = false;
	}

	public EditorRoundBorder(Color color, int thickness, boolean roundedCorners) {
		super(color, thickness);
		this.roundedCorners = roundedCorners;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Color oldColor = g.getColor();

		g.setColor(this.lineColor);
		for (int i = 0; i < this.thickness; i++) {
			if (!this.roundedCorners)
				g.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
			else {
				g.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i
						- 1, this.thickness, this.thickness);
			}
		}
		g.setColor(oldColor);
	}
}
