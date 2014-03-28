package com.apprentice.rpg.dao.time;

import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.google.common.collect.Maps;

/**
 * Stores information regarding when a {@link Nameable} was stored/created/updated
 * 
 * @author theoklitos
 * 
 */
public final class TimeToNameableMapper implements ModificationTimeVault {

	private static Logger LOG = Logger.getLogger(TimeToNameableMapper.class);

	protected static final String DATETIME_FORMAT = "dd MMM, HH:mm:ss";
	protected static final String UNKNOWN_TIMING_DESCRIPTION = "Unknown";
	private final Map<Nameable, Long> updateTimes;

	public TimeToNameableMapper() {
		updateTimes = Maps.newHashMap();
	}

	@Override
	public String getPrettyUpdateTime(final Nameable item) {
		try {
			final Long result = updateTimes.get(item);
			if (result == null) {
				return NO_TIMING_DESCRIPTION;
			} else {
				final DateTimeFormatter formatter = DateTimeFormat.forPattern(DATETIME_FORMAT);
				return formatter.print(new DateTime(result));
			}
		} catch (final IllegalArgumentException e) {
			// if somehow the timestamp is messed up
			return UNKNOWN_TIMING_DESCRIPTION;
		}
	}

	@Override
	public void updatedAt(final DateTime when, final Nameable item) {
		updateTimes.put(item, when.getMillis());
		LOG.debug("Set " + item.getClass().getSimpleName() + " last modification time to "
			+ DateTimeFormat.forPattern(DATETIME_FORMAT).print(when));
	}
}
