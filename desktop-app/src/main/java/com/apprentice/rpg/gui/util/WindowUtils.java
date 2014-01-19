package com.apprentice.rpg.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.apprentice.rpg.ApprenticeEx;
import com.apprentice.rpg.StartupApprentice;

public final class WindowUtils {

	public final static String DEFAULT_ICON_FILENAME = "wizard.png";
	public final static String DEFAULT_DESKTOP_FILENAME = "desktop.jpg";
	public final static String INFO_ICON_FILENAME = "information.png";
	public final static String IMAGES_FOLDER = "/images";

	public static void centerComponent(final Component component, final int verticalOffset) {
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final Rectangle screenSize = ge.getScreenDevices()[0].getDefaultConfiguration().getBounds();
		final int w = component.getSize().width;
		final int h = component.getSize().height;
		final int x = (screenSize.width - w) / 2;
		final int y = (screenSize.height - h) / 2;
		component.setLocation(x, y - verticalOffset);
	}

	public static void centerInternalComponent(final Component parent, final Component component,
			final int verticalOffset) {
		final Dimension parentSize = parent.getSize();
		final int w = component.getSize().width;
		final int h = component.getSize().height;
		final int x = (parentSize.width - w) / 2;
		final int y = (parentSize.height - h) / 2;
		component.setLocation(x, y - verticalOffset);
	}

	/**
	 * Shows a simple yes/no dialog. Returns true if yes.
	 */
	public static boolean getConfigrmationDialog(final String question, final String title) {
		return JOptionPane.showConfirmDialog(null, question, title, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}

	/**
	 * @throws {@link ApprenticeEx}
	 * 
	 */
	public static Image getDefaultDesktopBackgroundImage() {
		Image image;
		try {
			image =
				ImageIO.read(new StartupApprentice().getClass().getResource(
						WindowUtils.IMAGES_FOLDER + "/" + WindowUtils.DEFAULT_DESKTOP_FILENAME));
		} catch (final MalformedURLException e) {
			throw new ApprenticeEx(e);
		} catch (final IOException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		}

		return image;
	}

	/**
	 * @throws {@link ApprenticeEx}
	 * 
	 */
	public static Image getImageFromPath(final String path) {
		Image image;
		try {
			image = ImageIO.read(new File(path));
		} catch (final MalformedURLException e) {
			throw new ApprenticeEx(e);
		} catch (final IOException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		}

		if (image == null) {
			throw new ApprenticeEx("Selected file was probably not an image.");
		}

		return image;
	}

	public static void setDefaultIcon(final JFrame frame) {
		final String locationString = IMAGES_FOLDER + "/" + DEFAULT_ICON_FILENAME;
		final URL imageURL = frame.getClass().getResource(locationString);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(imageURL));
	}

	public static void setInformationIcon(final JInternalFrame frame) {
		final String locationString = IMAGES_FOLDER + "/" + INFO_ICON_FILENAME;
		final URL imageURL = frame.getClass().getResource(locationString);
		frame.setFrameIcon(new ImageIcon(imageURL));
	}

	public static void showErrorMessage(final String errorMessage) {
		showErrorMessage(errorMessage, "Error!");
	}

	public static void showErrorMessage(final String errorMessage, final String title) {
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}

	public static int showQuestionMessage(final String question, final String[] options, final String title) {
		return JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null);
	}

	public static void showWarningMessage(final String warningMessage, final String title) {
		JOptionPane.showMessageDialog(null, warningMessage, title, JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * shows a yes/no warning question. Returns true if yes.
	 */
	public static boolean showWarningQuestionMessage(final String question, final String title) {
		return JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, new String[] { "Yes", "No" }, null) == JOptionPane.YES_OPTION;
	}

}
