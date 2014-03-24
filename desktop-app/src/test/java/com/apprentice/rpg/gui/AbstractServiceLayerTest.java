package com.apprentice.rpg.gui;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;

/**
 * Any test that requires the use of a {@link IServiceLayer} and some mockups
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractServiceLayerTest {

	private Mockery mockery;
	private IServiceLayer serviceLayer;
	private ApprenticeEventBus eventBus;
	private Vault vault;

	/**
	 * returns a mocked {@link ApprenticeEventBus}
	 */
	public ApprenticeEventBus getEventBus() {
		return eventBus;
	}

	public Mockery getMockery() {
		return mockery;
	}

	/**
	 * returns a mocked {@link IServiceLayer}
	 */
	public IServiceLayer getServiceLayer() {
		return serviceLayer;
	}

	/**
	 * returns a mocked {@link Vault}
	 */
	public Vault getVault() {
		return vault;
	}

	@Before
	public void setupAbstract() {
		mockery = new Mockery();
		eventBus = mockery.mock(ApprenticeEventBus.class);
		vault = mockery.mock(Vault.class);
		serviceLayer = mockery.mock(IServiceLayer.class);

		mockery.checking(new Expectations() {
			{
				allowing(serviceLayer).getEventBus();
				will(returnValue(eventBus));
			}
		});
	}

	@After
	public void teardownAbstract() {
		mockery.assertIsSatisfied();
	}

}
