package com.apprentice.rpg.gui;

import java.awt.EventQueue;
import java.lang.reflect.Constructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.events.ShowFrameEvent;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Guiced up factory for frames and windows, maintains list of active windows
 * 
 * @author theoklitos
 * 
 */
public final class WindowManager implements IWindowManager {

	private static Logger LOG = Logger.getLogger(WindowManager.class);

	private final Injector injector;
	private final IGlobalWindowState globalWindowState;
	private final IMainControl mainControl;

	@Inject
	public WindowManager(final Injector injector) {
		this.injector = injector;
		this.globalWindowState = injector.getInstance(IGlobalWindowState.class);
		this.mainControl = injector.getInstance(IMainControl.class);
		mainControl.getEventBus().register(this);
	}

	/**
	 * for closures
	 */
	private final IWindowManager getReferenceToSelf() {
		return this;
	}

	@Override
	public void initializeMainFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final MainFrame mainFrame = new MainFrame(mainControl, getReferenceToSelf(), globalWindowState);
				mainFrame.setVisible(true);
				restoreOpenFrames();
			}
		});
	}

	/**
	 * Checks if the frame is open for the specific parameter (if any)
	 */
	private boolean isFrameAlreadyOpen(final WindowStateIdentifier windowStateIdentifier) {
		if (globalWindowState.getWindowState(windowStateIdentifier).hasContent()) {
			if (globalWindowState.getWindowState(windowStateIdentifier).getContent().isOpen()) {
				LOG.debug("Tried to open frame \"" + getClass().getSimpleName() + "\" which was already open.");
				return true;
			}
		}
		return false;
	}

	/**
	 * re-opens any frames that were previously closed
	 */
	@SuppressWarnings("unchecked")
	protected void restoreOpenFrames() {
		for (final WindowStateIdentifier openFrameIdentifier : globalWindowState.getOpenInternalFrames()) {
			globalWindowState.setWindowOpen(openFrameIdentifier, false);
			final Class<?> frameClass = openFrameIdentifier.getWindowClass();
			if (ApprenticeInternalFrame.class.isAssignableFrom(frameClass)) {
				showFrame((Class<? extends ApprenticeInternalFrame<?>>) frameClass, openFrameIdentifier.getParameter());
			}
		}
	}

	@Override
	public void showFrame(final Class<? extends ApprenticeInternalFrame<?>> frameClass) {
		showFrame(frameClass, "");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void showFrame(final Class<? extends ApprenticeInternalFrame<?>> frameClass, final String variable) {
		final Constructor<?> constructor = frameClass.getConstructors()[0];
		final Class<?>[] parameterTypes = constructor.getParameterTypes();
		ApprenticeInternalFrame<?> instance = null;
		if (isFrameAlreadyOpen(new WindowStateIdentifier(frameClass, variable))) {
			return;
		}
		try {
			final IServiceLayer serviceLayerImpl = (IServiceLayer) injector.getInstance(parameterTypes[0]);
			if (parameterTypes.length == 1) {
				instance = (ApprenticeInternalFrame<?>) constructor.newInstance(serviceLayerImpl);
			}
			if (ParameterizedControllableView.class.isAssignableFrom(frameClass) && StringUtils.isNotBlank(variable)) {
				final ParameterizedControllableView<Nameable> parameterizedInstance =
					(ParameterizedControllableView<Nameable>) instance;
				final Nameable parameter =
					mainControl.getVault().getUniqueNamedResult(variable, parameterizedInstance.getType().type);
				parameterizedInstance.display(parameter);
			}
			mainControl.getDesktopControl().add(instance);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new ApprenticeEx(e);
		}
	}

	@Subscribe
	public void showFrameEvent(final ShowFrameEvent event) {
		showFrame(event.getInternalFrame(), event.getParameter());
	}

}
