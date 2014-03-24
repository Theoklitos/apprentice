package com.apprentice.rpg.gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktop;
import com.apprentice.rpg.gui.dice.DiceRollerFrame;
import com.apprentice.rpg.gui.log.LogFrame;
import com.apprentice.rpg.gui.vault.armor.ArmorAndArmorPieceFrame;
import com.apprentice.rpg.gui.vault.player.PlayerCharacterVaultFrame;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrame;
import com.apprentice.rpg.gui.vault.weapon.WeaponAndAmmunitionVaultFrame;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.util.Box;

public final class MainFrame extends JFrame implements ControllableView {

	private static final long serialVersionUID = 1;

	private final IMainControl mainControl;
	private final ApprenticeDesktop desktop;
	private final IWindowManager windowManager;
	private final IGlobalWindowState globalWindowState;

	public MainFrame(final IMainControl mainControl, final IWindowManager windowManager,
			final IGlobalWindowState globalWindowState) {
		mainControl.getEventBus().register(this);
		this.globalWindowState = globalWindowState;
		this.globalWindowState.getWindowUtils().setParent(this);
		this.windowManager = windowManager;
		this.mainControl = mainControl;
		desktop = new ApprenticeDesktop(mainControl.getDesktopControl());

		setTitle("Apprentice v0.7? - Built 9 February 2014");
		globalWindowState.getWindowUtils().setDefaultIcon(this);
		setSizeAndPosition();
		initMenus();
		initComponents();
		setShutdownHook();
	}

	/**
	 * Adds the given frame to the desktop. Its preferable to have the window manager call this.
	 */
	public void addInternalFrame(final JInternalFrame internalFrame) {
		desktop.add(internalFrame);
		globalWindowState.getWindowUtils().centerInternalComponent(this, internalFrame, 50);
		desktop.validate();
	}

	private void initComponents() {
		final ApprenticeDesktop desktop = new ApprenticeDesktop(mainControl.getDesktopControl());
		mainControl.getEventBus().register(desktop);
		final JPanel grandPane = new JPanel(new BorderLayout());
		grandPane.add(desktop, BorderLayout.CENTER);
		final EventBarControl control = (EventBarControl) Logger.getRootLogger().getAppender("eventBar");
		final EventBar eventBar = new EventBar(control);
		grandPane.add(eventBar, BorderLayout.SOUTH);
		add(grandPane);
	}

	private void initMenus() {
		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnPlayerCharacters = new JMenu("Characters");
		menuBar.add(mnPlayerCharacters);
		final JMenuItem mntmNewPlayer = new JMenuItem("New Player");
		mntmNewPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(NewPlayerCharacterFrame.class);
			}
		});
		mnPlayerCharacters.add(mntmNewPlayer);
		final JMenuItem mntmSummonPlayer = new JMenuItem("Summon Player");
		mntmSummonPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(PlayerCharacterVaultFrame.class);
			}
		});
		mnPlayerCharacters.add(mntmSummonPlayer);
		final JMenuItem mntmNPCs = new JMenuItem("NPCs");
		mntmNPCs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO
			}
		});
		mnPlayerCharacters.add(mntmNPCs);

		final JMenu mnDice = new JMenu("Dice Roller");
		final JMenuItem mnShowDice = new JMenuItem("Open Dice Roller");
		mnShowDice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(DiceRollerFrame.class);
			}
		});
		mnDice.add(mnShowDice);
		menuBar.add(mnDice);

		final JMenu mnVaults = new JMenu("Vaults");
		menuBar.add(mnVaults);
		final JMenuItem mntmPlayers = new JMenuItem("Players");
		mntmPlayers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(PlayerCharacterVaultFrame.class);
			}
		});
		mnVaults.add(mntmPlayers);
		final JMenu mntmItems = new JMenu("Items");
		mnVaults.add(mntmItems);
		final JMenuItem submntmWeapons = new JMenuItem("Weapons");
		submntmWeapons.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				windowManager.showFrame(WeaponAndAmmunitionVaultFrame.class);
			}
		});
		mntmItems.add(submntmWeapons);
		final JMenuItem submntmArmors = new JMenuItem("Armors");
		submntmArmors.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				windowManager.showFrame(ArmorAndArmorPieceFrame.class);
			}
		});
		mntmItems.add(submntmArmors);
		final JMenuItem submntmSpells = new JMenuItem("Spells");
		mntmItems.add(submntmSpells);
		final JMenuItem mntmTypes = new JMenuItem("Types & Body Parts");
		mntmTypes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(TypeAndBodyPartFrame.class);
			}
		});
		mnVaults.add(mntmTypes);

		final JMenu mnConfiguration = new JMenu("Configuration");
		menuBar.add(mnConfiguration);
		final JMenuItem mntmDatabaseSettings = new JMenuItem("Database Settings");
		mntmDatabaseSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(DatabaseSettingsFrame.class);
			}
		});
		mnConfiguration.add(mntmDatabaseSettings);
		final JMenuItem mntmRuleset = new JMenuItem("Rules");
		mntmRuleset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO
			}
		});
		mnConfiguration.add(mntmRuleset);
		final JMenuItem mntmExportImport = new JMenuItem("Export/Import");
		mntmExportImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO
			}
		});
		mnConfiguration.add(mntmExportImport);

		final JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		final JMenuItem mntmAbout = new JMenuItem("About Apprentice");
		mnHelp.add(mntmAbout);
		mnHelp.addSeparator();
		final JMenuItem mnLogging = new JMenuItem("Log");
		mnLogging.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				windowManager.showFrame(LogFrame.class);

			}
		});
		mnHelp.add(mnLogging);
		final JMenuItem mntmContact = new JMenuItem("Submit Bug Report or Feature Request...");
		mnHelp.add(mntmContact);

		final JMenuItem test = new JMenuItem("Inject info");
		test.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final DataFactory data = new DataFactory();
				for (final Nameable nameable : data.getAllNameables()) {
					//System.out.println("Storing [" + nameable.getClass().getSimpleName() + "]:" + nameable);
					mainControl.getVault().update(nameable); // TODO
				}
			}
		});
		menuBar.add(test);
	}

	@Override
	public void refreshFromModel() {
		// nothingd
	}

	private void setShutdownHook() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent we) {
				globalWindowState.setWindowState(new WindowStateIdentifier(MainFrame.class), getBounds(), true);
				mainControl.getEventBus().postShutdownEvent(true);
				mainControl.shutdownGracefully();
				System.exit(0);
			}
		});
	}

	/**
	 * stores information about this frame in the {@link IGlobalWindowState}
	 */
	private void setSizeAndPosition() {
		final Box<WindowState> stateBox = globalWindowState.getWindowState(new WindowStateIdentifier(getClass()));
		setMinimumSize(new Dimension(700, 400));
		if (stateBox.hasContent()) {
			setBounds(stateBox.getContent().getBounds());
		} else {
			setSize(1000, 600);
			globalWindowState.getWindowUtils().centerComponent(this, 100);
		}
	}

}
