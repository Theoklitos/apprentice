package com.apprentice.rpg.gui.dice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.TextfieldWithColorWarning;
import com.apprentice.rpg.gui.log.LogFrameControl;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.windowState.GlobalWindowState;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.util.Box;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Allows the player to roll dice
 * 
 * @author theoklitos
 * 
 */
public class DiceRollerFrame extends ApprenticeInternalFrame implements IDiceRollerFrame {

	private static final long serialVersionUID = -5791860999612306424L;
	private final JCheckBox chckbxDiceSound;
	private final JCheckBox chckbxboxNarrateResult;
	private final ButtonGroup buttonGroup;
	private final SingleDicePanel d4;
	private final SingleDicePanel d6;
	private final SingleDicePanel d8;
	private final SingleDicePanel d10;
	private final SingleDicePanel d12;
	private final SingleDicePanel d6times2;
	private final SingleDicePanel d20;
	private final SingleDicePanel d100;
	private JRadioButton textRadioButton;
	private TextfieldWithColorWarning diceTextField;
	private final IDiceRollerFrameControl control;
	private final JTextArea history;
	private final JPanel resultPanel;
	private final JLabel lblResult;
	private final JScrollPane historyScrollPane;
	private final JButton btnRoll;

	public DiceRollerFrame(final IGlobalWindowState globalWindowState, final IDiceRollerFrameControl control) {
		super(new GlobalWindowState(new WindowUtils()), "Dice Roller");
		this.control = control;
		setResizable(false);

		getContentPane().setLayout(
				new FormLayout(
						new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("65dlu:grow"), RowSpec.decode("35px"),
							FormFactory.LINE_GAP_ROWSPEC, }));

		final JPanel dicePanel = new JPanel();
		getContentPane().add(dicePanel, "2, 2, fill, fill");

		final JPanel middlePanelPanel = new JPanel();
		middlePanelPanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("180px"),
			ColumnSpec.decode("70px:grow"), ColumnSpec.decode("220px"), }, new RowSpec[] {
			FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("fill:65dlu:grow"), }));

		final JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		middlePanelPanel.add(settingsPanel, "1, 2, fill, fill");
		settingsPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("pref:grow"), }, new RowSpec[] { FormFactory.UNRELATED_GAP_ROWSPEC,
			FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		chckbxDiceSound = new JCheckBox("Dice Sound");
		settingsPanel.add(chckbxDiceSound, "2, 2, fill, fill");
		chckbxDiceSound.setSelected(true);

		chckbxboxNarrateResult = new JCheckBox("Narrate Result");
		settingsPanel.add(chckbxboxNarrateResult, "2, 4, left, top");
		chckbxboxNarrateResult.setSelected(true);

		getContentPane().add(middlePanelPanel, "2, 4, fill, fill");

		resultPanel = new JPanel(new BorderLayout(5, 5));
		resultPanel
				.setBorder(new TitledBorder(null, "Result", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		lblResult = new JLabel("", JLabel.CENTER);
		lblResult.setForeground(Color.red);
		lblResult.setFont(lblResult.getFont().deriveFont(Font.BOLD).deriveFont(20F));
		resultPanel.add(lblResult, BorderLayout.CENTER);

		middlePanelPanel.add(resultPanel, "2, 2, fill, fill");

		final JPanel historyPanel = new JPanel();
		historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.X_AXIS));
		historyPanel.setBorder(new TitledBorder(null, "History", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setEditable(false);
		historyScrollPane = new JScrollPane(history);
		historyPanel.add(historyScrollPane);
		middlePanelPanel.add(historyPanel, "3, 2, fill, fill");

		final JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 5, fill, fill");

		btnRoll = new JButton("Roll");
		btnRoll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent evemt) {
				doRoll();
			}
		});
		buttonPanel.add(btnRoll);

		final JButton btnLoadedRoll = new JButton("Loaded Roll");
		btnLoadedRoll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				doLoadedRoll();
			}
		});
		buttonPanel.add(btnLoadedRoll);

		buttonGroup = new ButtonGroup();
		dicePanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("255px"), ColumnSpec.decode("1px"),
			ColumnSpec.decode("255px"), ColumnSpec.decode("1px"), }, new RowSpec[] { FormFactory.LINE_GAP_ROWSPEC,
			RowSpec.decode("35px"), FormFactory.LINE_GAP_ROWSPEC, RowSpec.decode("35px"),
			FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("35px"), FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("fill:35px"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("35px"), }));

		d4 = new SingleDicePanel(buttonGroup, "D4");
		d6 = new SingleDicePanel(buttonGroup, "D6");
		d8 = new SingleDicePanel(buttonGroup, "D8");
		d10 = new SingleDicePanel(buttonGroup, "D10");
		d12 = new SingleDicePanel(buttonGroup, "D12");
		d6times2 = new SingleDicePanel(buttonGroup, "2D6");
		d20 = new SingleDicePanel(buttonGroup, "D20");
		d100 = new SingleDicePanel(buttonGroup, "D100");
		dicePanel.add(d4, "1, 2, left, fill");
		dicePanel.add(d6, "3, 2, left, center");
		dicePanel.add(d8, "1, 4, left, fill");
		dicePanel.add(d10, "3, 4, left, top");
		dicePanel.add(d12, "1, 6, left, fill");
		dicePanel.add(d6times2, "3, 6, left, top");
		dicePanel.add(d20, "1, 8, left, fill");
		dicePanel.add(d100, "3, 8, left, top");
		final JPanel diceTextPanel = createDiceTextPanel(buttonGroup);
		dicePanel.add(diceTextPanel, "1, 10, 3, 1, fill, center");
	}

	private JPanel createDiceTextPanel(final ButtonGroup group) {
		final JPanel diceTextPanel = new JPanel();
		textRadioButton = new JRadioButton("As Text:");
		textRadioButton.setFont(textRadioButton.getFont().deriveFont(Font.BOLD));
		group.add(textRadioButton);
		diceTextPanel.add(textRadioButton);
		diceTextField = new TextfieldWithColorWarning(Roll.class);
		diceTextField.setColumns(33);
		diceTextPanel.add(diceTextField);
		diceTextField.addFocusListener(new ButtonSelectionFocusListener(textRadioButton));
		return diceTextPanel;
	}

	/**
	 * when the DM wants to cheat a little bit
	 */
	private void doLoadedRoll() {
		final Box<LoadedRoll> loadedRollBox = getWindowUtils().showLoadedRollDialog();
		if (loadedRollBox.hasContent()) {
			final LoadedRoll loadedRoll = loadedRollBox.getContent();
			showRollResult(loadedRoll.getRoll(), loadedRoll.getResult());
		}
	}

	/**
	 * main functionality
	 */
	protected void doRoll() {
		final Box<Roll> selectedRollBox = getSelectedRoll();
		if (selectedRollBox.hasContent()) {
			final Roll selectedRoll = selectedRollBox.getContent();
			final int result = control.roll(selectedRoll);
			showRollResult(Box.with(selectedRoll), result);
		}
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(537, 400);
	}

	/**
	 * looks inside the {@link SingleDicePanel}s and determines what was selected & inputted
	 */
	private Box<Roll> getSelectedRoll() {
		if (d4.isSelected()) {
			return Box.with(d4.getRoll());
		} else if (d6.isSelected()) {
			return Box.with(d6.getRoll());
		} else if (d8.isSelected()) {
			return Box.with(d8.getRoll());
		} else if (d10.isSelected()) {
			return Box.with(d10.getRoll());
		} else if (d12.isSelected()) {
			return Box.with(d12.getRoll());
		} else if (d6times2.isSelected()) {
			return Box.with(d6times2.getRoll());
		} else if (d20.isSelected()) {
			return Box.with(d20.getRoll());
		} else if (d100.isSelected()) {
			return Box.with(d100.getRoll());
		} else if (textRadioButton.isSelected()) {
			final Roll textRoll = new Roll(diceTextField.getText().trim());
			return Box.with(textRoll);
		} else {
			return Box.empty();
		}
	}

	/**
	 * shows the result in the history panel in a nice way
	 */
	private void logDiceRollInHistoryPanel(final Box<Roll> selectedRollBox, final int result) {
		final String time = DateTimeFormat.forPattern(LogFrameControl.LOG_TIME_PATTERN).print(new DateTime());
		String diceDescription = "";
		if (selectedRollBox.hasContent()) {
			diceDescription = "[" + selectedRollBox.getContent().toString() + "]";
		} else {
			diceDescription = "[Loaded Roll]";
		}
		final String newLine = time + diceDescription + ": " + result;
		String endline = "";
		if (!StringUtils.isBlank(history.getText())) {
			endline = "\n";
		}
		final String updatedText = history.getText() + endline + newLine;
		history.setText(updatedText);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				historyScrollPane.getVerticalScrollBar().setValue(117);
			}
		});
	}

	/**
	 * removes any icon and sets the given number as text
	 */
	private void setDiceRollResultLabel(final int result) {
		lblResult.setIcon(null);
		lblResult.setText(String.valueOf(result));
	}

	/**
	 * should TTS be used
	 */
	private boolean shouldNarrateResult() {
		return chckbxboxNarrateResult.isSelected();
	}

	/**
	 * should the dice sound be heard
	 */
	private boolean shouldPlayDiceRollSound() {
		return chckbxDiceSound.isSelected();
	}

	/**
	 * performs all the necessary steps to display the given result
	 */
	private void showRollResult(final Box<Roll> selectedRoll, final int result) {
		final Runnable diceAnimationAndSoundThread = new Runnable() {

			@Override
			public void run() {
				boolean logged = false;
				try {
					btnRoll.setEnabled(false);
					lblResult.setText("");
					getWindowUtils().setDiceRollingImage(lblResult);
					Thread.sleep(500);
					setDiceRollResultLabel(result);
					logDiceRollInHistoryPanel(selectedRoll, result);
					logged = true;
					if (shouldPlayDiceRollSound()) {
						control.playDiceRollingSound();
					}
					if (shouldNarrateResult()) {
						control.tts(result);
					}
				} catch (final Exception e) {
					// the finally clause will handle the flow
				} finally {
					setDiceRollResultLabel(result);
					if (!logged) {
						logDiceRollInHistoryPanel(selectedRoll, result);
					}
					btnRoll.setEnabled(true);
				}
			}
		};
		new Thread(diceAnimationAndSoundThread, "Dice Roll Thread").start();
	}
}
