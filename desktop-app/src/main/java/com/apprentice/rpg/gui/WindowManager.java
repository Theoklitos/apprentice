package com.apprentice.rpg.gui;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.database.IDataSynchronizer;
import com.apprentice.rpg.gui.character.player.creation.INewPlayerCharacterFrameControl;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.database.IDatabaseSettingsFrameControl;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktop;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.apprentice.rpg.gui.dice.DiceRollerFrame;
import com.apprentice.rpg.gui.dice.DiceRollerFrameControl;
import com.apprentice.rpg.gui.log.ILogFrameControl;
import com.apprentice.rpg.gui.log.LogFrame;
import com.apprentice.rpg.gui.main.IEventBarControl;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.vault.player.PlayerVaultFrame;
import com.apprentice.rpg.gui.vault.type.ITypeAndBodyPartFrameControl;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrame;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.body.IType;
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

	private ApprenticeDesktop desktop;
	private final IMainControl mainControl;
	private final IApprenticeDesktopControl desktopControl;
	private final IEventBarControl eventBarControl;
	private final IGlobalWindowState globalWindowState;
	private final IWindowUtils windowUtils;
	private final Vault vault;
	private final Injector injector;
	private final ApprenticeEventBus eventBus;

	@Inject
	public WindowManager(final Injector injector) {
		this.injector = injector;
		vault = injector.getInstance(Vault.class);
		windowUtils = injector.getInstance(IWindowUtils.class);
		globalWindowState = injector.getInstance(IGlobalWindowState.class);
		eventBus = injector.getInstance(ApprenticeEventBus.class);
		mainControl = injector.getInstance(IMainControl.class);
		desktopControl = injector.getInstance(IApprenticeDesktopControl.class);
		eventBarControl = injector.getInstance(IEventBarControl.class);
		registerEventHandlers();
	}

	@Override
	public void closeAllFrames() {
		desktop.closeAllFrames();
		for (final WindowStateIdentifier indentifier : globalWindowState.getAllFrameIdentifiers()) {
			globalWindowState.setWindowOpen(indentifier, false);
		}
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
				desktop = new ApprenticeDesktop(desktopControl);
				desktopControl.setView(desktop);
				desktopControl.setBackgroundFromConfig();
				final MainFrame mainFrame =
					new MainFrame(globalWindowState, getReferenceToSelf(), mainControl, eventBarControl, desktop);
				mainFrame.setVisible(true);
				restoreOpenFrames();
			}
		});
	}

	@Override
	public void openFrame(final WindowStateIdentifier openFrameIdentifier) {
		final Class<?> internalFrame = openFrameIdentifier.getWindowClass();
		final String methodName = "show" + internalFrame.getSimpleName();
		Method openFrameMethod;
		try {
			LOG.debug("Calling method " + methodName + "()");
			openFrameMethod = getClass().getMethod(methodName);
			openFrameMethod.invoke(this);
		} catch (final IllegalAccessException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		} catch (final InvocationTargetException e) {
			throw new ApprenticeEx(e);
		} catch (final SecurityException e) {
			throw new ApprenticeEx(e);
		} catch (final NoSuchMethodException e) {
			throw new ApprenticeEx(e);
		}
	}

	/**
	 * registers handlers on the {@link ApprenticeEventBus}
	 */
	private void registerEventHandlers() {
		eventBus.register(injector.getInstance(IDataSynchronizer.class));
	}

	/**
	 * re-opens any frames that were previously closed
	 */
	protected void restoreOpenFrames() {
		for (final WindowStateIdentifier openFrameIdentifier : globalWindowState.getOpenInternalFrames()) {
			globalWindowState.setWindowOpen(openFrameIdentifier, false);
			openFrame(openFrameIdentifier);
		}
	}

	@Override
	public void showDatabaseSettingsFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final IDatabaseSettingsFrameControl control = injector.getInstance(IDatabaseSettingsFrameControl.class);
				final DatabaseSettingsFrame databaseFrame = new DatabaseSettingsFrame(globalWindowState, control);
				control.setView(databaseFrame);
				desktop.add(databaseFrame);
			}
		});
	}

	@Override
	public void showDiceRollerFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final DiceRollerFrameControl control = injector.getInstance(DiceRollerFrameControl.class);
				final DiceRollerFrame diceFrame = new DiceRollerFrame(globalWindowState, control);
				control.setView(diceFrame);
				desktop.add(diceFrame);
			}
		});

	}

	@Override
	public void showLogFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final ILogFrameControl control = injector.getInstance(ILogFrameControl.class);
				final LogFrame logFrame = new LogFrame(globalWindowState);
				control.setView(logFrame);
				desktop.add(logFrame);
			}
		});
	}

	@Override
	public void showNewPlayerCharacterFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (vault.getAll(IType.class).size() == 0) {
					if (windowUtils
							.showWarningQuestionMessage(
									"There are no character types in the database.\nYou need to create at least one before proceeding.\nOpen the type creation window now?",
									"No Types Found")) {
						showTypeAndBodyPartFrame();
					}
				} else {
					final INewPlayerCharacterFrameControl control =
						injector.getInstance(INewPlayerCharacterFrameControl.class);
					final NewPlayerCharacterFrame newPCFrame =
						new NewPlayerCharacterFrame(globalWindowState, control, getReferenceToSelf());
					control.setView(newPCFrame);
					desktop.add(newPCFrame);
				}
			}
		});
	}

	@Override
	public void showPlayerVaultFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final IVaultFrameControl control = injector.getInstance(IVaultFrameControl.class);
				final PlayerVaultFrame pvFrame = new PlayerVaultFrame(globalWindowState, control);
				control.setView(pvFrame);
				desktop.add(pvFrame);
			}
		});

	}

	@Override
	public void showTypeAndBodyPartFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final ITypeAndBodyPartFrameControl control = injector.getInstance(ITypeAndBodyPartFrameControl.class);
				final TypeAndBodyPartFrame tbpFrame = new TypeAndBodyPartFrame(globalWindowState, control);
				control.setView(tbpFrame);
				desktop.add(tbpFrame);
			}
		});

	}
}
