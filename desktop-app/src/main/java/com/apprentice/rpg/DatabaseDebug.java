package com.apprentice.rpg;

import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;

/**
 * Prints the contents of the database
 * 
 * @author theoklitos
 * 
 */
public final class DatabaseDebug {

	public final static void main(final String args[]) {
		final String databaseLocation = "/home/theoklitos/workspace/apprentice/vault";
		final EmbeddedObjectContainer container = Db4oEmbedded.openFile(databaseLocation);
		final ObjectSet<?> result = container.queryByExample(IArmorPiece.class);
		System.out.println("Objects in DB: " + result.size());
		while (result.hasNext()) {
			try {
				final Object next = result.next();
				if (next instanceof ArmorPiece) {
					final IArmorPiece piece = (IArmorPiece) next;
					System.out.println("Name: " + piece.getName() + ", prototype: " + piece.isPrototype());
				}
			} catch (final Exception e) {
				System.out
						.println("==================================================================================");
				System.out.println("Object threw exception: " + e.getClass());
				e.printStackTrace();
				System.out
						.println("==================================================================================");
			}
		}
	}
}
