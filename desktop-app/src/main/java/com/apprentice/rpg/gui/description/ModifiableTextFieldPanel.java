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

import com.apprentice.rpg.dao.Vault;

/**
 * bordered panel that can be used to view and set a string variable
 * 
 * @author theoklitos
 * 
 */
public abstract class ModifiableTextFieldPanel extends JPanel {

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

	/**
	 * used to communicate what type this textfield will be
	 */
	protected enum TextFieldPanelType {
		TEXTFIELD,
		LABEL;
	}

	private static final long serialVersionUID = 1L;

	private JTextArea content;
	private final Vault vault;
	private final String title;
	private final TextFieldPanelType type;

	public ModifiableTextFieldPanel(final String title, final TextFieldPanelType type, final Vault vault) {
		this.type = type;
		this.vault = vault;
		this.title = title;
		initComponents();
	}

	protected Vault getVault() {
		return vault;
	}

	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), title, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		content = new JTextArea();
		final Color backgroundColor = UIManager.getColor("Panel.background");
		content.setBackground(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		if (type.equals(TextFieldPanelType.LABEL)) {
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
		} else if (type.equals(TextFieldPanelType.TEXTFIELD)) {
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

}
