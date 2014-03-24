package com.apprentice.rpg.model.durable;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.CurrentMaximumPair;

/**
 * tests the abstract {@link DurableItem} and its durability modification
 * 
 * @author theoklitos
 * 
 */
public class TestDurableItem {

	private DurableItem durableItem;

	@Test
	public void decreaseIncreaseHP() {
		durableItem.removeHitPoints(5);
		assertEquals(15, durableItem.getDurability().getCurrent());
		durableItem.removeHitPoints(1000000);		
		assertEquals(0, durableItem.getDurability().getCurrent());
		durableItem.getDurability().setMaximum(100);
		assertEquals(0, durableItem.getDurability().getCurrent());
		durableItem.setHitPoints(50);
		assertEquals(50, durableItem.getDurability().getCurrent());
		durableItem.addHitPoints(50);
		assertEquals(100, durableItem.getDurability().getCurrent());
		durableItem.addHitPoints(250);
		assertEquals(100, durableItem.getDurability().getCurrent());
	}

	@Before
	public void setup() {
		final int originalDurability = 20;
		durableItem = new DurableItem("test item", new CurrentMaximumPair(originalDurability)) {
		};

	}

}
