package com.apprentice.rpg.gui.description;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.apprentice.rpg.backend.IServiceLayer;

/**
 * bordered panel that can be used to view and set a string variable
 * 
 * @author theoklitos
 * 
 */
public abstract class ModifiableTextFieldPanel extends JPanel {

	/**
	 * used to communicate what type this textfield will be
	 */
	public enum DescriptionPanelType {
		TEXTFIELD,
		LABEL;
	}

	/**
	 * when its a label and the user right clicks, this is shown
	 * 
	 */
	private class ModifieableTextFieldPopUp extends JPopupMenu {
		private static final long serialVersionUID = 1L;
		JMenuItem item;

		private ModifieableTextFieldPopUp(final String keyword) {
			item = new JMenuItem("Edit " + keyword);
			add(item);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent event) {
					modifyText();
				}
			});
		}
	}

	private static final long serialVersionUID = 1L;

	private JTextArea content;
	private final String title;
	private DescriptionPanelType type;

	private final IServiceLayer control;

	public ModifiableTextFieldPanel(final String title, final DescriptionPanelType type, final IServiceLayer control) {
		this.type = type;
		this.control = control;
		this.title = title;
		initComponents();
	}
	
	/**
	 * returns the passed {@link IServiceLayer}
	 */
	protected IServiceLayer getControl() {
		return control;
	}

	/**
	 * Returns the text in the central text component
	 */
	public String getText() {
		return content.getText();
	}

	protected DescriptionPanelType getType() {
		return type;
	}

	protected void initComponents() {
		removeAll();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), title, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		content = new JTextArea();
		final Color backgroundColor = UIManager.getColor("Panel.background");
		content.setBackground(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		if (type.equals(DescriptionPanelType.LABEL)) {
			content.setOpaque(false);
			content.setEditable(false);
			content.setCursor(null);
			content.setFocusable(false);
			content.setComponentPopupMenu(new ModifieableTextFieldPopUp(title));
			content.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					if (e.getClickCount() == 2 && !e.isConsumed()) {
						e.consume();
						modifyText();
					}
				}
			});
		} else if (type.equals(DescriptionPanelType.TEXTFIELD)) {
			content = new JTextArea();
		}
		final JScrollPane scrollPane = new JScrollPane(content);
		add(scrollPane);
	}

	/**
	 * when the user double clicks or calls the popup
	 */
	protected abstract void modifyText();

	/**
	 * sets what is to be displayed in the lable/textfield in this component
	 */
	public final void setText(final String text) {
		content.setText(text);
	}

	protected void setType(final DescriptionPanelType type) {
		this.type = type;
	}

}
