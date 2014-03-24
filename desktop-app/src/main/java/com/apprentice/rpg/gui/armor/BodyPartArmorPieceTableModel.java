package com.apprentice.rpg.gui.armor;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.google.common.collect.Maps;

/**
 * A model for the {@link BodyPartArmorPieceTable}
 * 
 * @author theoklitos
 * 
 */
public class BodyPartArmorPieceTableModel extends DefaultTableModel implements TableModel {

	public static final String BODY_PART_COLUMN_NAME = "Body Part";
	public static final String ARMOR_PIECE_COLUMN_NAME = "Armor Piece";

	private static final long serialVersionUID = 1L;

	private final LinkedHashMap<BodyPart, IArmorPiece> bodyPartToArmorPieceMapping;

	public BodyPartArmorPieceTableModel() {
		bodyPartToArmorPieceMapping = Maps.newLinkedHashMap();
		setColumnCount(2);
	}

	/**
	 * self-explanatory
	 */
	public void clearAllRows() {
		setRowCount(0);
	}
	
	@Override
	public String getColumnName(final int columnNumber) {
		if (columnNumber == 0) {
			return BODY_PART_COLUMN_NAME;
		} else {
			return ARMOR_PIECE_COLUMN_NAME;
		}
	}

	/**
	 * returns the data the user inputted
	 */
	public LinkedHashMap<BodyPart, IArmorPiece> getModelData() {
		return bodyPartToArmorPieceMapping;
	}

	/**
	 * Iterates through the map the number of times specified and returns the resulting item
	 */
	private Entry<BodyPart, IArmorPiece> getNumberedEntry(final int number) {
		if (number > getRowCount()) {
			throw new ApprenticeEx("Row " + number + " requested, only " + getRowCount() + " rows exist.");
		}
		final Iterator<Entry<BodyPart, IArmorPiece>> iterator = bodyPartToArmorPieceMapping.entrySet().iterator();
		for (int i = 0; i < getRowCount(); i++) {
			final Entry<BodyPart, IArmorPiece> entry = iterator.next();
			if (i == number) {
				return entry;
			}
		}
		throw new ApprenticeEx("Row " + number + " could not be accessed.");
	}

	@Override
	public int getRowCount() {
		if (bodyPartToArmorPieceMapping == null) {
			return 0;
		} else {
			return bodyPartToArmorPieceMapping.size();
		}
	}

	@Override
	public Object getValueAt(final int row, final int column) {
		final Entry<BodyPart, IArmorPiece> entry = getNumberedEntry(row);
		if (column == 0) {
			return entry.getKey();
		} else {
			final IArmorPiece armorPiece = entry.getValue();
			if (armorPiece != null) {
				return armorPiece.getName() + " (" + armorPiece.getDamageReduction() + ")";
			} else {
				return "Nothing";
			}
		}
	}

	/**
	 * Sets the table rows for the body parts inside this type
	 */
	public void initializeForType(final IType type) {
		clearAllRows();
		bodyPartToArmorPieceMapping.clear();
		for (final BodyPart bodyPart : type.getBodyParts()) {
			bodyPartToArmorPieceMapping.put(bodyPart, null);
		}
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return column == 1;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int column) {
		if (column == 1) {
			final Entry<BodyPart, IArmorPiece> entry = getNumberedEntry(row);
			bodyPartToArmorPieceMapping.put(entry.getKey(), (IArmorPiece) value);
		}
		fireTableDataChanged();
	}
}
