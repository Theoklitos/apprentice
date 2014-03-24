package com.apprentice.rpg.gui.armor;

import java.awt.Component;
import java.util.Collection;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * Like a map, contains mappings from {@link BodyPart}s to {@link ArmorPiece}s, that can be changed on the fly
 * 
 * @author theoklitos
 * 
 */
public class BodyPartArmorPieceTable extends JTable {

	private static final long serialVersionUID = 1L;
	private final BodyPartArmorPieceTableModel model;

	public BodyPartArmorPieceTable(final BodyPartArmorPieceTableModel model) {
		super(model);
		this.model = model;
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getColumnModel().getColumn(1).setMinWidth(50);
	}

	/**
	 * Will recreate the checkbox(es) that show a dropdown list of all the armor pices
	 */
	public void setForArmorPiecesAndType(final Collection<IArmorPiece> armorPieces, final IType type) {
		final JComboBox<IArmorPiece> cmbboxArmorPieces = new JComboBox<IArmorPiece>();
		cmbboxArmorPieces.setModel(new DefaultComboBoxModel<IArmorPiece>(new Vector<IArmorPiece>(armorPieces)));
		setRenderer(cmbboxArmorPieces);
		getColumn(BodyPartArmorPieceTableModel.ARMOR_PIECE_COLUMN_NAME).setCellEditor(
				new DefaultCellEditor(cmbboxArmorPieces));
		// add rows
		model.initializeForType(type);
		repaint();
	}

	/**
	 * Sets a cell renderer that displays only the name of the armor piece
	 */
	@SuppressWarnings({ "rawtypes" })
	private void setRenderer(final JComboBox<IArmorPiece> cmbboxArmorPieces) {
		cmbboxArmorPieces.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(final JList list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				Object item = value;

				if (item != null && IArmorPiece.class.isAssignableFrom(item.getClass())) {					
					final IArmorPiece armorPiece = ((IArmorPiece) item);
					item = armorPiece.getName();
				}
				return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
			}
		});
	}

}
