package com.apprentice.rpg.util;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * A box for handling return data that might not always be returned
 * 
 * @param E
 *            The type of data that is to be held in the box
 * 
 * @see http://lift.la/scala-option-lift-box-and-how-to-make-your-co
 */
public final class Box<E> {

	/**
	 * Creates an empty box if there is no content (null) or else a box with the given content
	 * 
	 */
	public static <E> Box<E> box(final E content) {
		if (content == null) {
			return empty();
		} else {
			return with(content);
		}
	}

	public static <E> Box<E> empty() {
		return new Box<E>("");
	}

	public static <E> Box<E> empty(final String reason) {
		return new Box<E>(reason);
	}

	public static <E> Box<E> with(final E content) {
		return new Box<E>(content);
	}

	/**
	 * If the box is empty, this is the message it contains to explain its emptiness
	 */
	private final String reason;

	/**
	 * the value
	 */
	private final E content;

	private final boolean hasContent;

	private final boolean isEmpty;

	/**
	 * Constructor for box with content (non-empty)
	 */
	public Box(final E content) {
		this(null, content);
	}

	/**
	 * Constructor for the empty box
	 */
	private Box(final String reason) {
		this(reason, null);
	}

	/**
	 * Main constructor
	 */
	private Box(final String reason, final E content) {
		if (content == null && reason == null) {
			throw new ApprenticeEx("Error while creating box, all null parameters");
		}
		this.reason = reason;
		this.content = content;
		this.hasContent = content != null;
		this.isEmpty = !hasContent();
	}

	// @SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Box) {
			@SuppressWarnings("rawtypes")
			final Box o = (Box) other;
			if (hasContent() && o.hasContent()) {
				return getContent().equals(o.getContent());
			} else {
				return isEmpty && o.isEmpty;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns this box's content, if any. Else, returns null.
	 */
	public E getContent() {
		return content;
	}

	/**
	 * Returns the reason for this box's emptiness. If this box is not empty, the reason is an empty
	 * string.
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Returns whether this box has content or not
	 */
	public boolean hasContent() {
		return hasContent;
	}

	/**
	 * Returns whether this box is empty or not
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	@Override
	public String toString() {
		return "Box[" + (hasContent() ? getContent().toString() : "empty") + "]";
	}

}
