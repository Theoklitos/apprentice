package com.apprentice.rpg.dao.time;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;

/**
 * tests for the {@link ModificationTimeVault}
 * 
 * @author theoklitos
 * 
 */
public final class TestTimeToNameableMapper {

	private TimeToNameableMapper vault;

	@Test
	public void setGetTimings() {
		final BodyPart type = new BodyPart("part1");
		assertEquals(ModificationTimeVault.NO_TIMING_DESCRIPTION, vault.getPrettyUpdateTime(type));
		final DateTime now = new DateTime();
		vault.updated(now, type);
		assertEquals(DateTimeFormat.forPattern(TimeToNameableMapper.DATETIME_FORMAT).print(now), vault.getPrettyUpdateTime(type));		
	}

	@Before
	public void setup() {
		vault = new TimeToNameableMapper();
	}

}
