package com.apprentice.rpg.database;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.Configuration;
import com.db4o.ext.ExtObjectContainer;
import com.google.common.collect.Lists;

/**
 * tests for {@link Db4oConnection}
 * 
 * @author theoklitos
 * 
 */
public final class TestDb4oConnection {

	private static final String LOCATION = "testfile";
	private EmbeddedObjectContainer container;
	private Db4oConnection database;
	private Mockery mockery;

	@Test
	public void databaseIsChanged() {
		final String newLocation = "newTestFile";
		expectDbClose();
		database.setDatabase(newLocation);
		assertEquals(newLocation, database.getLocation());
		new File(newLocation).delete(); // sadly a file is created, its a static call
	}

	@Test
	public void dbCommitsAndCloses() {
		expectDbClose();
		database.closeDB();
	}

	@Test
	public void deleteObject() {
		final String object = "";
		mockery.checking(new Expectations() {
			{
				oneOf(container).delete(object);
			}
		});
		database.delete(object);
	}

	private void expectDbClose() {
		mockery.checking(new Expectations() {
			{
				oneOf(container).commit();
				oneOf(container).close();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	public void loadClass() {
		final ObjectSet<String> allStrings = mockery.mock(ObjectSet.class);
		final Collection<String> allStringsCol = Lists.newArrayList();

		mockery.checking(new Expectations() {
			{
				oneOf(container).query(String.class);
				will(returnValue(allStrings));
				final int size = 10;
				oneOf(allStrings).size();
				will(returnValue(size));
				oneOf(allStrings).subList(0, size);
				will(returnValue(allStringsCol));
			}
		});
		final Collection<String> result = database.load(String.class);
		assertEquals(allStringsCol, result);
	}

	@Test
	public void locationIsCorrect() {
		assertEquals(LOCATION, database.getLocation());
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		container = mockery.mock(EmbeddedObjectContainer.class);
		final Configuration config = mockery.mock(Configuration.class);
		final ExtObjectContainer extContainer = mockery.mock(ExtObjectContainer.class);
		mockery.checking(new Expectations() {
			{
				allowing(container).ext();
				will(returnValue(extContainer));
				allowing(extContainer).configure();
				will(returnValue(config));
				allowing(config).updateDepth(Integer.MAX_VALUE);
				allowing(config).exceptionsOnNotStorable(false);
			}
		});
		database = new Db4oConnection(container, LOCATION);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}
}
