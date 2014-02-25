package com.apprentice.rpg.dao.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.google.common.collect.Sets;

/**
 * tests for the {@link ImportObject}
 * 
 * @author theoklitos
 * 
 */
public final class TestSimpleVault {

	private class TestNameable extends BaseApprenticeObject {

		public TestNameable(final String name) {
			super(name);
		}

	}

	private SimpleVault svault;

	@Test
	public void addGetSomeNameables() {
		final TestNameable n1 = new TestNameable("n1");
		final TestNameable n2 = new TestNameable("n2");
		final TestNameable n3 = new TestNameable("n3");
		svault.add(n1);
		final Collection<TestNameable> col = Sets.newHashSet(n2, n3);
		svault.addAll(col);

		assertEquals(3, svault.getAllNameables().size());
		assertTrue(svault.getAllNameables().contains(n1));
		assertTrue(svault.getAllNameables().contains(n2));
		assertTrue(svault.getAllNameables().contains(n3));

		assertEquals(3, svault.getAllNameables(TestNameable.class).size());
		assertTrue(svault.getAllNameables(TestNameable.class).contains(n1));
		assertTrue(svault.getAllNameables(TestNameable.class).contains(n2));
		assertTrue(svault.getAllNameables(TestNameable.class).contains(n3));
	}

	@Before
	public void setup() {
		svault = new SimpleVault();
	}
}
