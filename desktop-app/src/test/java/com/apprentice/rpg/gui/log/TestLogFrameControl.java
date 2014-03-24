package com.apprentice.rpg.gui.log;

import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.gui.AbstractServiceLayerTest;

public class TestLogFrameControl extends AbstractServiceLayerTest {

	private LogFrameControl control;
	private ILogFrame view;
	private Mockery mockery;

	@SuppressWarnings("deprecation")
	@Test
	public void logEventIsAppended() {
		final String testMessage = "testMessage";
		final LoggingEvent infoMessage = new LoggingEvent("test", Category.getRoot(), Level.INFO, testMessage, null);
		mockery.checking(new Expectations() {
			{
				oneOf(view).appendMessage(with(any(String.class)), with(equalTo("INFO")), with(equalTo(testMessage)));
			}
		});
		control.append(infoMessage);
	}

	@Before
	public void setup() {
		mockery = getMockery();
		view = mockery.mock(ILogFrame.class);
		mockery.checking(new Expectations() {
			{
				oneOf(view).clearMessages();
			}
		});
		control = new LogFrameControl();
		control.setView(view);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}
}
