package com.apprentice.rpg.model.armor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * A full set of armor, consisting of several {@link ArmorPiece}s and made for a specific {@link IType}
 * 
 * @author theoklitos
 * 
 */
public class Armor extends BaseApprenticeObject {

	private static final String DEFAULT_ARMOR_NAME = "No Armor";

	// @SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(Armor.class);

	private String typeName;
	private final Map<BodyPart, IArmorPiece> armorToBodyPartMapping;

	/**
	 * will use default armor name
	 */
	public Armor(final IType type) {
		this(DEFAULT_ARMOR_NAME, type);
	}

	public Armor(final String name, final IType type) {
		super(name);
		Checker.checkNonNull("An armor needs a type!", true, type);
		this.typeName = type.getName();
		this.armorToBodyPartMapping = Maps.newLinkedHashMap();
		setType(type);
		setPrototype(true);
	}

	/**
	 * Attempts to fit this armor piece in the given armor.
	 * 
	 * @throws ArmorDoesNotFitEx
	 *             if the armor piece cannot fit anywhere OR if it can fit in more than one places
	 */
	public void addArmorPiece(final IArmorPiece armorPiece) throws ArmorDoesNotFitEx {
		final BodyPart matchingBodyPart = checkForFit(armorPiece);
		setArmorPieceForBodyPart(matchingBodyPart, armorPiece);
	}

	/**
	 * finds the part that this piece is meant for, and handles exception throwing
	 * 
	 * @throws ArmorDoesNotFitEx
	 */
	private BodyPart checkForFit(final IArmorPiece armorPiece) throws ArmorDoesNotFitEx {
		BodyPart matchingBodyPart = null;
		int fittings = 0;
		for (final BodyPart bodyPart : armorToBodyPartMapping.keySet()) {
			if (armorPiece.fits(bodyPart)) {
				matchingBodyPart = bodyPart;
				fittings++;
			}
		}
		if (fittings == 0) {
			throw new ArmorDoesNotFitEx("Armor piece " + armorPiece.getName() + " does not fit in armor " + getName());
		} else if (fittings > 1) {
			throw new ArmorDoesNotFitEx("Armor piece " + armorPiece.getName() + " has ambiguous fittings");
		}
		return matchingBodyPart;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Armor) {
			final Armor otherArmor = (Armor) other;
			return super.equals(otherArmor)
				&& typeName == otherArmor.typeName
				&& ApprenticeCollectionUtils.areAllElementsEqual(armorToBodyPartMapping,
						otherArmor.armorToBodyPartMapping);
		} else {
			return false;
		}
	}

	/**
	 * returns a box with the {@link IArmorPiece} the character is wearing in this bodypart, if any. Empty box
	 * otherwise.
	 */
	public Box<IArmorPiece> getArmorPieceForBodyPart(final BodyPart bodyPart) {
		final IArmorPiece result = armorToBodyPartMapping.get(bodyPart);
		if (result == null) {
			return Box.empty();
		} else {
			return Box.with(result);
		}
	}

	/**
	 * returns the armor pieces and the body parts they are mapped to. Note: this is a copy of the original
	 * map, not a reference
	 */
	public Map<BodyPart, IArmorPiece> getArmorToBodyPartMapping() {
		return Maps.newHashMap(armorToBodyPartMapping);
	}

	/**
	 * returns a reference to this armor's body parts
	 */
	public List<BodyPart> getBodyParts() {
		return Lists.newArrayList(armorToBodyPartMapping.keySet());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(armorToBodyPartMapping);
	}

	/**
	 * removes the armor piece for this body part. Returns true if something was indeed removed.
	 */
	public boolean removeArmorPiece(final BodyPart bodyPart) {
		return armorToBodyPartMapping.remove(bodyPart) != null;
	}

	/**
	 * Sets what armor piece goes into this body part.
	 * 
	 * @throws BodyPartDoesNotExistExif
	 *             the type defined for this armor does not have such a bodypart
	 * @throws ArmorDoesNotFitEx
	 *             if the armor piece is
	 *             not made to fit in this bodypart.
	 */
	public void setArmorPieceForBodyPart(final BodyPart bodyPart, final IArmorPiece armorPiece)
			throws ArmorDoesNotFitEx, BodyPartDoesNotExistEx {
		if (!armorToBodyPartMapping.keySet().contains(bodyPart)) {
			throw new BodyPartDoesNotExistEx("Armor " + getName() + " does not have a \"" + bodyPart.getName() + "\"");
		}
		if (armorPiece != null && !armorPiece.fits(bodyPart)) {
			String designatorString = "";
			if (armorPiece.getBodyPartDesignator().hasContent()) {
				designatorString = "Its designated for a \"" + armorPiece.getBodyPartDesignator().getContent() + "\".";
			}
			throw new ArmorDoesNotFitEx("Armor piece " + getName() + " cannot fit in " + bodyPart.getName()
				+ designatorString);
		}
		armorToBodyPartMapping.put(bodyPart, armorPiece);
	}

	/**
	 * Will look inside this armor's type for a body part of the given name, and will assign the armor piece
	 * to it
	 * 
	 * @throws BodyPartDoesNotExistExif
	 *             the type defined for this armor does not have such a bodypart
	 * @throws ArmorDoesNotFitEx
	 *             if the armor piece is
	 *             not made to fit in this bodypart.
	 */
	public void setArmorPieceForBodyPart(final String bodyPartName, final IArmorPiece armorPiece)
			throws ArmorDoesNotFitEx, BodyPartDoesNotExistEx {
		BodyPart desiredBodyPart = null;
		for (final BodyPart bodyPart : armorToBodyPartMapping.keySet()) {
			if (bodyPart.getName().toLowerCase().equals(bodyPartName.toLowerCase())) {
				desiredBodyPart = bodyPart;
				break;
			}
		}
		if (desiredBodyPart == null) {
			throw new BodyPartDoesNotExistEx("Armor " + getName() + " does not have a \"" + bodyPartName + "\"");
		}
		setArmorPieceForBodyPart(desiredBodyPart, armorPiece);
	}

	/**
	 * Changes the type this armor is made for. Might invalidate and thus delete armor pieces that do not fit.
	 */
	public void setType(final IType type) {
		typeName = type.getName();
		final Iterator<Entry<BodyPart, IArmorPiece>> iterator = armorToBodyPartMapping.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<BodyPart, IArmorPiece> entry = iterator.next();
			final BodyPart bodyPart = entry.getKey();
			if (!type.getBodyParts().contains(bodyPart)) {
				LOG.debug("Armor " + getName() + " does not have a " + bodyPart
					+ " anymore, so the mapping was removed.");
				iterator.remove();
			}
		}
		for (final BodyPart bodyPart : type.getBodyParts()) {
			if (!armorToBodyPartMapping.keySet().contains(bodyPart)) {
				LOG.debug("Armor " + getName() + " does not have a " + bodyPart + ", so the mapping was added.");
				armorToBodyPartMapping.put(bodyPart, null);
			}
		}
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		if (getName().equals(DEFAULT_ARMOR_NAME)) {
			return "Empty armor.";
		}
		result.append(getName() + ", made for type " + typeName + ". ");
		if (armorToBodyPartMapping.size() == 0) {
			result.append("No armor pieces defined.");
		}
		final Iterator<Entry<BodyPart, IArmorPiece>> iterator = armorToBodyPartMapping.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<BodyPart, IArmorPiece> entry = iterator.next();
			String armorName = "Nothing";
			if (entry.getValue() != null) {
				armorName = entry.getValue().getName();
			}
			result.append(entry.getKey().getName() + ":" + armorName);
			if (iterator.hasNext()) {
				result.append(", ");
			} else {
				result.append(".");
			}
		}
		return result.toString();
	}

}
