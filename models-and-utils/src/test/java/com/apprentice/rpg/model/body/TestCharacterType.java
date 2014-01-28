package com.apprentice.rpg.model.body;

import static org.junit.Assert.assertEquals;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link CharacterType}
 * 
 * @author theoklitos
 * 
 */
public final class TestCharacterType {

	private static final Size SIZE = Size.DIMUNITIVE;
	private CharacterType cType;
	private Mockery mockery;
	private IType type;

	@Before
	public void initialize() {
		mockery = new Mockery();
		type = mockery.mock(IType.class);
		cType = new CharacterType(type, SIZE);
	}

	@Test
	public void isInitializedCorrectly() {
		assertEquals(type, cType.getType());
		assertEquals(SIZE, cType.getSize());
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}
}
