package com.apprentice.rpg.dao.vaults;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.model.body.IType;

/**
 * Contains all the types of beings in this rog system, for example, "Humanoid", "Winged Humanoid",
 * "Quadropod",
 * etc
 * 
 * @author theoklitos
 * 
 * @deprecated use the singular {@link Vault} which is used to work with all types
 */
@Deprecated
public interface ITypeVault extends IVault<IType> {
	// marker
}
