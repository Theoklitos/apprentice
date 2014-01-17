package com.apprentice.rpg.gui.desktop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.ControllableView;

/**
 * The main window that every gui element resides into
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeDesktop extends JDesktopPane implements ControllableView {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ApprenticeDesktop.class);

	private static final long serialVersionUID = -1;

	private Color color;
	private Image image;
	private final IApprenticeDesktopControl control;

	public ApprenticeDesktop(final IApprenticeDesktopControl control) {
		this.control = control;
	}

	public void add(final JInternalFrame internalFrame) {
		super.add(internalFrame);
		try {
			internalFrame.setSelected(true);
		} catch (final PropertyVetoException e) {
			// do nothing
		}
	}

	public IApprenticeDesktopControl getControl() {
		return control;
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		} else {
			g.drawString("", 50, 50);
		}
	}

	public void setBackgroundColor(final Color color) {
		this.color = color;
		this.updateUI();
	}

	public void setBackgroundImage(final Image image) {
		this.image = image;
		this.repaint();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void updateUI() {
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			final UIDefaults map = new UIDefaults();
			final Painter painter = new Painter() {

				@Override
				public void paint(final Graphics2D g, final Object c, final int w, final int h) {
					g.setColor(color == null ? UIManager.getDefaults().getColor("desktop") : color);
					g.fillRect(0, 0, w, h);
				}
			};
			map.put("DesktopPane[Enabled].backgroundPainter", painter);
			putClientProperty("Nimbus.Overrides", map);
		}
		super.updateUI();
	}

}
