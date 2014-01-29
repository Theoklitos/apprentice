package com.apprentice.rpg.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.body.BodyPart;
import com.google.common.collect.Lists;

/**
 * tests for the {@link DataAccessObjectForAll}
 * 
 * @author theoklitos
 * 
 */
public final class TestDataAccessObjectForAll {

	private DatabaseConnection connection;
	private Mockery mockery;
	private DataAccessObjectForAll dao;
	private List<BodyPart> parts;
	private BodyPart knownPart;

	@Test(expected = ItemAlreadyExistsEx.class)
	public void cannotCreateIfItemAlreadyExists() {
		dao.create(knownPart);
	}

	@Test
	public void create() {
		final BodyPart newPart = new BodyPart("wings");
		mockery.checking(new Expectations() {
			{
				oneOf(connection).save(newPart);
			}
		});
		dao.create(newPart);
	}

	@Test
	public void deleteItemThatDoesntExist() {
		assertFalse(dao.delete(new BodyPart("wrong name")));
	}

	@Test
	public void deleteItemThatExists() {
		mockery.checking(new Expectations() {
			{
				oneOf(connection).delete(knownPart);
			}
		});
		assertTrue(dao.delete(knownPart));
	}

	@Test(expected = TooManyResultsEx.class)
	public void detectDoubleNames() {
		parts.add(new BodyPart("arms"));
		dao.checkDoubles(parts);
	}

	@Test
	public void doesElementWithNameExist() {
		assertFalse(dao.doesNameExist("asdfas", BodyPart.class));
		assertFalse(dao.doesNameExist("wrong name", BodyPart.class));
		assertTrue(dao.doesNameExist("arms", BodyPart.class));
		assertTrue(dao.doesNameExist("legs", BodyPart.class));
	}

	@Test
	public void getAllItemsOfType() {
		assertEquals(parts, dao.getAll(BodyPart.class));
	}

	@Test
	public void getUniqueObjectSuccessfully() {
		final BodyPart result = dao.getUniqueNamedResult("head", BodyPart.class);
		assertEquals(knownPart, result);
	}

	@Test
	public void itemDoesNotExist() {
		final BodyPart part = new BodyPart("tail");
		assertFalse(dao.exists(part));
	}

	@Test
	public void itemExists() {
		assertTrue(dao.exists(knownPart));
	}

	@Test(expected = NoResultsFoundEx.class)
	public void noSuchNameExists() {
		dao.getUniqueNamedResult("tail", BodyPart.class);
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		connection = mockery.mock(DatabaseConnection.class);
		dao = new DataAccessObjectForAll(connection);

		// some test data
		parts = Lists.newArrayList();
		knownPart = new BodyPart("head");
		parts.add(knownPart);
		parts.add(new BodyPart("arms"));
		parts.add(new BodyPart("legs"));

		mockery.checking(new Expectations() {
			{
				allowing(connection).load(BodyPart.class);
				will(returnValue(parts));
			}
		});
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

	@Test(expected = TooManyResultsEx.class)
	public void tooManyResultsFound() {
		parts.add(new BodyPart("head"));
		dao.getUniqueNamedResult("head", BodyPart.class);
	}

	@Test
	public void update() {
		mockery.checking(new Expectations() {
			{
				oneOf(connection).save(knownPart);
			}
		});
		knownPart.setName("new name");
		dao.update(knownPart);
	}

	//@Test(expected = NameAlreadyExistsEx.class)
	public void updateNameableSameName() {
		final int i; //enabled when you figure this out 
		knownPart.setName("arms");
		dao.update(knownPart);
	}
}
