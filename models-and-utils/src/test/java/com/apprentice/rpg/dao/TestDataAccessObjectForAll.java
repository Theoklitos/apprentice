package com.apprentice.rpg.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.parsing.exportImport.ImportObject;
import com.google.common.collect.Sets;

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
	private Collection<BodyPart> parts;
	private Collection<IType> types;
	private IType type1;
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
				oneOf(connection).saveAndCommit(newPart);
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
		parts = Sets.newHashSet();
		knownPart = new BodyPart("head");
		parts.add(knownPart);
		parts.add(new BodyPart("arms"));
		parts.add(new BodyPart("legs"));
		final BodyPartToRangeMap mapping = new BodyPartToRangeMap();
		mapping.setPartForRange(1, 100, knownPart);
		types = Sets.newHashSet();
		type1 = new Type("type1", mapping);
		types.add(type1);

		mockery.checking(new Expectations() {
			{
				allowing(connection).load(BodyPart.class);
				will(returnValue(parts));
				allowing(connection).load(Type.class);
				will(returnValue(types));
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
				oneOf(connection).saveAndCommit(knownPart);
			}
		});
		knownPart.setName("new name");
		dao.update(knownPart);
	}

	@Test
	public void updateFromImportObject() {
		final ImportObject io = new ImportObject();
		io.addAll(Sets.newHashSet(knownPart));
		io.addAll(types);

		mockery.checking(new Expectations() {
			{
				oneOf(connection).saveAndCommit(knownPart);
				oneOf(connection).saveAndCommit(type1);
			}
		});

		dao.update(io);
	}

	@Test(expected = NameAlreadyExistsEx.class)
	public void updateNameableSameName() {
		knownPart.setName("arms");
		dao.update(knownPart);
	}
}
