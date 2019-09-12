/*
 * SelectionManager.java
 *
 * 21.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;

import static corentx.util.Checks.ensure;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A container which adds a selected status to an object.
 *
 * @param <T> The type of the object whose selected status is to store.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 21.04.2018 - Added.
 */

public class SelectionManager<T> {

	private T object = null;
	private boolean selected = true;

	/**
	 * Creates a new selector for the passed object and the passed selected state.
	 *
	 * @param object   The object whose selected state is to store.
	 * @param selected Set this flag if the object is selected.
	 * @throws IllegalArgumentException Passing a null value as object.
	 */
	public SelectionManager(T object, boolean selected) {
		super();
		ensure(object != null, "object cannot be null.");
		this.object = object;
		this.setSelected(selected);
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	/**
	 * Returns the object whose selection status is managed.
	 *
	 * @return The object whose selection status is managed.
	 */
	public T getObject() {
		return this.object;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * Checks if the object is selected.
	 *
	 * @return "true" if the object is selected.
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Sets the selected flag to the passed value.
	 *
	 * @param selected The new value for the selected flag.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Returns a string representation for the selection manager.
	 *
	 * @return A string representation for the selection manager.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}