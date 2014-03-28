package com.apprentice.rpg.model.playerCharacter.audit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for the {@link AuditTrail}
 * 
 * @author theoklitos
 * 
 */
public final class TestAuditTrail {

	@Test
	public void addedEvent() {
		final AuditTrail trail = new AuditTrail("");
		assertEquals(0, trail.getAuditMessages().size());
		final String message = "player died";
		trail.addMessage(message);

		assertEquals(1, trail.getAuditMessages().size());
		assertEquals(message, trail.getAuditMessages().iterator().next().getMessage());
	}
	
	@Test
	public void changeName() {
		final AuditTrail trail = new AuditTrail("name1");
		assertEquals("name1", trail.getPlayerName());
		
		trail.setPlayerName("DIFFERENTNAME");
		assertEquals("DIFFERENTNAME", trail.getPlayerName());		
	}
}
