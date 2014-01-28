package com.apprentice.rpg.dao.vaults;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
import com.google.common.collect.Lists;

/**
 * Tests for the {@link TypeVault}
 * 
 * @author theoklitos
 * 
 */
@SuppressWarnings("deprecation")
public final class TestTypeVault {

	private TypeVault vault;
	private Vault utils;
	private DatabaseConnection connection;
	private Mockery mockery;
	private List<BodyPart> parts;
	private List<Type> types;
	private Type knownType;

	@Test
	public void create() {
		mockery.checking(new Expectations() {
			{
				oneOf(utils).exists(knownType);
				will(returnValue(false));
				oneOf(connection).save(knownType);				
			}
		});

		vault.create(knownType);
	}
	
	@Test(expected = ItemAlreadyExistsEx.class)
	public void createWhenAlreadyExists() {
		mockery.checking(new Expectations() {
			{
				oneOf(utils).exists(knownType);
				will(returnValue(true));								
			}
		});

		vault.create(knownType);
	}

	@Test
	public void getAllItems() {
		mockery.checking(new Expectations() {
			{
				oneOf(connection).load(IType.class);
				will(returnValue(types));
			}
		});

		final List<IType> allTypes = vault.getAll();
		assertEquals(types, allTypes);
	}

	@Test
	public void getAUniqueItem() {
		mockery.checking(new Expectations() {
			{
				oneOf(utils).getUniqueNamedResult("name1", IType.class);
				will(returnValue(knownType));
			}
		});

		final IType result = vault.getUniqueForName("name1");
		assertEquals(knownType, result);
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		utils = mockery.mock(Vault.class);
		connection = mockery.mock(DatabaseConnection.class);
		vault = new TypeVault(utils, connection);

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
	}

	@Test
	public void update() {
		mockery.checking(new Expectations() {
			{
				oneOf(connection).save(knownType);				
			}
		});

		vault.update(knownType);
	}
}
