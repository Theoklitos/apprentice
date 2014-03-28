package com.apprentice.rpg.model.playerCharacter.audit;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Objects;

/**
 * Contains a message with a timestamp
 * 
 * @author theoklitos
 * 
 */
public class AuditTrailMessage {

	public static final String AUDIT_TRAIL_FORMAT = "dd MMM, HH:mm:ss";

	private final long timestamp;
	private final String message;

	public AuditTrailMessage(final DateTime dateTime, final String message) {
		this.message = message.trim();
		this.timestamp = dateTime.getMillis();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof AuditTrailMessage) {
			final AuditTrailMessage otherAuditTrailMessage = (AuditTrailMessage) other;
			return Objects.equal(message, otherAuditTrailMessage.message)
				&& timestamp == otherAuditTrailMessage.timestamp;
		} else {
			return false;
		}
	}

	/**
	 * when was this audit message created?
	 */
	public DateTime getEventTime() {
		return new DateTime(timestamp);
	}

	/**
	 * the audit message
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(message, message);
	}

	@Override
	public String toString() {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(AUDIT_TRAIL_FORMAT);
		final String prettyDateTime = formatter.print(getEventTime());
		return prettyDateTime + " : " + message;
	}

}
