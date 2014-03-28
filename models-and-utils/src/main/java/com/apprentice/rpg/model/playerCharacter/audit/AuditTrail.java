package com.apprentice.rpg.model.playerCharacter.audit;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * Stores information about what happened to the player, timestamped.
 * 
 * @author theoklitos
 * 
 */
public final class AuditTrail {

	private static Logger LOG = Logger.getLogger(AuditTrail.class);

	public Collection<AuditTrailMessage> auditMessages;
	private String playerName;

	public AuditTrail() {
		this("");
	}

	public AuditTrail(final String playerName) {
		this.playerName = playerName;
		auditMessages = Sets.newHashSet();
	}

	/**
	 * logs/stores a new message in the audit trail. Will add timestamp.
	 */
	public void addMessage(final String message) {
		final AuditTrailMessage auditTrailMessage = new AuditTrailMessage(new DateTime(), message);
		auditMessages.add(auditTrailMessage);
		String playerPrefix = "Audit trail message:";
		if (StringUtils.isNotBlank(playerName)) {
			playerPrefix = "Player " + playerName + "audit trail message: ";
		}
		LOG.debug(playerPrefix + auditTrailMessage);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof AuditTrail) {
			final AuditTrail otherAuditTrail = (AuditTrail) other;
			return ApprenticeCollectionUtils.areAllElementsEqual(auditMessages, otherAuditTrail.auditMessages);
		} else {
			return false;
		}
	}

	/**
	 * returns all the audit messages
	 */
	public Collection<AuditTrailMessage> getAuditMessages() {
		return auditMessages;
	}

	protected String getPlayerName() {
		return playerName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(auditMessages);
	}

	/**
	 * changes the name of the player that will be logged in the audit trial messages
	 */
	public void setPlayerName(final String playerName) {
		this.playerName = playerName;
	}
}
