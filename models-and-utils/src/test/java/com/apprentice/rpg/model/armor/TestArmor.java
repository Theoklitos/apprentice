package com.apprentice.rpg.model.armor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.dice.RollWithSuffix;

/**
 * Tests for the {@link Armor}r
 * 
 * @author theoklitos
 * 
 */
public final class TestArmor {

	public DataFactory factory;
	public Armor armor;
	private ArmorPiece wingArmorPiece;

	@Test(expected = ArmorDoesNotFitEx.class)
	public void addAmbiguousPart() {
		armor.addArmorPiece(wingArmorPiece);
	}

	@Test
	public void equality() {
		final IType daemon = factory.getTypes().get(1);
		final IArmorPiece breastplate = factory.getArmorPieces().get(0);
		final IArmorPiece helmet = factory.getArmorPieces().get(1);
		armor.setArmorPieceForBodyPart(factory.getBodyParts().get(0), helmet);
		armor.setArmorPieceForBodyPart("torso", breastplate);

		final Armor identicalArmor = new Armor("Half plate", daemon);
		identicalArmor.setArmorPieceForBodyPart(factory.getBodyParts().get(0), helmet);
		identicalArmor.setArmorPieceForBodyPart("torso", breastplate);

		assertEquals(armor, identicalArmor);

		identicalArmor.setArmorPieceForBodyPart("left wing", wingArmorPiece);

		assertFalse(armor.equals(identicalArmor));
	}

	@Test
	public void remove() {
		final Armor plateArmor = factory.getArmors().get(0);
		assertEquals(6, plateArmor.getArmorToBodyPartMapping().size());
		final BodyPart head = factory.getBodyParts().get(0);
		plateArmor.removeArmorPiece(head);
		assertTrue(plateArmor.getArmorPieceForBodyPart(head).isEmpty());
	}

	@Test
	public void setArmorParts() {
		final IArmorPiece breastplate = factory.getArmorPieces().get(0);
		final IArmorPiece helmet = factory.getArmorPieces().get(1);
		final BodyPart head = factory.getBodyParts().get(0);
		armor.setArmorPieceForBodyPart(head, helmet);
		armor.setArmorPieceForBodyPart("torso", breastplate);
		assertEquals(9, armor.getArmorToBodyPartMapping().size());
		assertEquals(helmet, armor.getArmorPieceForBodyPart(head).getContent());
		assertEquals(breastplate, armor.getArmorPieceForBodyPart(factory.getBodyParts().get(1)).getContent());
	}

	@Test
	public void setArmorToDifferentType() {
		final IArmorPiece breastplate = factory.getArmorPieces().get(0);
		final IArmorPiece helmet = factory.getArmorPieces().get(1);
		armor.setArmorPieceForBodyPart(factory.getBodyParts().get(0), helmet);
		armor.setArmorPieceForBodyPart("torso", breastplate);
		armor.setArmorPieceForBodyPart("left wing", wingArmorPiece);
		armor.setArmorPieceForBodyPart("right wing", wingArmorPiece);

		assertEquals(9, armor.getArmorToBodyPartMapping().size());
		armor.setType(factory.getTypes().get(0));
		assertEquals(6, armor.getArmorToBodyPartMapping().size());
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		final IType daemon = factory.getTypes().get(1);
		armor = new Armor("Half Plate", daemon);
		wingArmorPiece = new ArmorPiece("wingArmor", 5, new RollWithSuffix("D4"), "Wing");
	}

}
