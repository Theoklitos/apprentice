package com.apprentice.rpg.gui.vault.type;

import org.apache.log4j.Logger;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.google.inject.Inject;

/**
 * Controls the {@link exportItems}
 * 
 * @author theoklitos
 * 
 */
public class TypeAndBodyPartFrameControl extends AbstractControlForView<ITypeAndBodyPartFrame> implements
		ITypeAndBodyPartFrameControl {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(TypeAndBodyPartFrameControl.class);
	
	private final TypeAndBodyPartFrameState state;

	@Inject
	public TypeAndBodyPartFrameControl(final IServiceLayer serviceLayer) {
		super(serviceLayer);
		state = new TypeAndBodyPartFrameState();
	}

	@Override
	public TypeAndBodyPartFrameState getState() {
		return state;
	}
}
