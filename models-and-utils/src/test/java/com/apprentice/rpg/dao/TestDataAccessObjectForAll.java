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
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
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
	private List<IType> types;
	private IType knownType;

	@Test(expected = ItemAlreadyExistsEx.class)
	public void cannotCreateIfItemAlreadyExists() {
		dao.create(knownType);
	}

	@Test
	public void create() {
		final IType newType = new Type("type100", parts);
		mockery.checking(new Expectations() {
			{
				oneOf(connection).save(newType);
			}
		});
		dao.create(newType);
	}
	
	@Test
	public void deleteItemThatDoesntExist() {
		assertFalse(dao.delete(new Type("name1000")));
	}

	@Test
	public void deleteItemThatExists() {
		mockery.checking(new Expectations() {
			{
				oneOf(connection).delete(knownType);
			}
		});
		assertTrue(dao.delete(knownType));
	}

	@Test(expected = TooManyResultsEx.class)
	public void detectDoubleNames() {
		types.add(new Type("name2", parts));

		dao.checkDoubles(types);
	}

	@Test
	public void doesElementWithNameExist() {
		assertFalse(dao.doesNameExist("asdfas", IType.class));
		assertFalse(dao.doesNameExist("wrong name", IType.class));
		assertTrue(dao.doesNameExist("name1", IType.class));
		assertTrue(dao.doesNameExist("name2", IType.class));
	}

	@Test
	public void getAllItemsOfType() {
		assertEquals(types, dao.getAll(IType.class));
	}

	@Test
	public void getUniqueObjectSuccessfully() {
		final IType result = dao.getUniqueNamedResult("name1", IType.class);
		assertEquals(knownType, result);
	}

	@Test
	public void itemDoesNotExist() {
		final IType newType = new Type("newType", parts);
		assertFalse(dao.exists(newType));
	}

	@Test
	public void itemExists() {
		assertTrue(dao.exists(knownType));
	}

	@Test(expected = NoResultsFoundEx.class)
	public void noSuchNameExists() {
		dao.getUniqueNamedResult("name10", IType.class);
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		connection = mockery.mock(DatabaseConnection.class);
		dao = new DataAccessObjectForAll(connection);

		// some test data
		parts = Lists.newArrayList();
		parts.add(new BodyPart("test"));
		types = Lists.newArrayList();
		knownType = new Type("name1", parts);
		types.add(knownType);
		types.add(new Type("name2", parts));
		types.add(new Type("name3", parts));
		types.add(new Type("name4", parts));
		types.add(new Type("name5", parts));

		mockery.checking(new Expectations() {
			{
				allowing(connection).load(IType.class);
				will(returnValue(types));
			}
		});

		mockery.checking(new Expectations() {
			{
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
		types.add(new Type("name1", parts));
		dao.getUniqueNamedResult("name11", IType.class);
	}

	@Test
	public void update() {
		mockery.checking(new Expectations() {
			{
				oneOf(connection).save(knownType);
			}
		});
		final BodyPart part = new BodyPart("newPart");
		knownType.getParts().add(part);
		dao.update(knownType);
	}
}
