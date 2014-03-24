package com.apprentice.rpg.gui.vault.type;

import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.google.common.collect.Sets;

/**
 * tests for the {@link TypeAndBodyPartFrameControl}
 * 
 * @author theoklitos
 * 
 */
public final class TestTypeAndBodyPartFrameState {

	private Collection<IType> types;
	private Collection<BodyPart> parts;
	private TypeAndBodyPartFrameState state;

	@Before
	public void setup() {
		types = Sets.newHashSet();
		parts = Sets.newHashSet();
		state = new TypeAndBodyPartFrameState();
	}

	@Test
	public void something() {
		fail("implement me!");
	}

}
