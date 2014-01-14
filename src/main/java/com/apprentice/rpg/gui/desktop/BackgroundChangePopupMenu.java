package com.apprentice.rpg.gui.desktop;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.google.inject.Inject;

public final class BackgroundChangePopupMenu extends MouseAdapter {

	private final JPopupMenu popupMenu;
	private final IApprenticeDesktopControl desktopControl;

	@Inject
	public BackgroundChangePopupMenu(final IApprenticeDesktopControl control) {
		this.desktopControl = control;

		popupMenu = new JPopupMenu();
		final JMenu colorMenu = new JMenu("Change Background Color");
		populateColorMenu(colorMenu);
		popupMenu.add(colorMenu);
		final JMenu imageMenu = new JMenu("Change Background Image");
		populateImageMenu(imageMenu);
		popupMenu.add(imageMenu);
	}

	private void doPop(final MouseEvent e) {
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}

	public JPopupMenu getBackgroundChangeMenu() {
		return popupMenu;
	}

	private boolean isClickWithinBounds(final Point locationOnScreen) {
		return desktopControl.getDesktopBounds().contains(locationOnScreen);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (e.isPopupTrigger() && isClickWithinBounds(e.getPoint())) {
			doPop(e);
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (e.isPopupTrigger() && isClickWithinBounds(e.getPoint())) {
			doPop(e);
		}
	}

	private void populateColorMenu(final JMenu colorMenu) {
		final JMenuItem white = new JMenuItem("White");
		white.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				desktopControl.setBackgroundColor(Color.WHITE);

			}
		});
		colorMenu.add(white);

		final JMenuItem black = new JMenuItem("Black");
		black.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				desktopControl.setBackgroundColor(Color.BLACK);

			}
		});
		colorMenu.add(black);

		final JMenuItem blue = new JMenuItem("Blue");
		blue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				desktopControl.setBackgroundColor(Color.BLUE);

			}
		});
		colorMenu.add(blue);
	}

	private void populateImageMenu(final JMenu imageMenu) {
		final JMenuItem defaultImage = new JMenuItem("Use Default Image");
		defaultImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				desktopControl.setDefaultDesktopBackground();
			}
		});
		imageMenu.add(defaultImage);

		final JMenuItem fromFile = new JMenuItem("From File...");
		fromFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				final int result = fileChooser.showDialog(null, "Use Image for Background");
				if (result == JFileChooser.APPROVE_OPTION) {
					final File selectedImage = fileChooser.getSelectedFile();
					desktopControl.setBackgroundImage(selectedImage.getAbsolutePath());
				}
			}
		});
		imageMenu.add(fromFile);
	}
}
