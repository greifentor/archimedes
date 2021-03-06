/*
 * AbstractIndexChangeOperation.java
 *
 * 17.12.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.meta.chops.indices;

import static corentx.util.Checks.ensure;

import archimedes.legacy.meta.MetaDataIndex;
import archimedes.legacy.meta.chops.AbstractChangeOperation;
import archimedes.legacy.meta.chops.ScriptSectionType;

/**
 * A base class for index change operations.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.12.2015 - Added.
 */

public class AbstractIndexChangeOperation extends AbstractChangeOperation {

	private MetaDataIndex index = null;

	/**
	 * Creates a new basic change operation for complex index change operation.
	 *
	 * @param index   The index which is related to the change operation.
	 * @param section The type of the section which the change operation is related to.
	 *
	 * @changed OLI 17.12.2015 - Added.
	 */
	public AbstractIndexChangeOperation(MetaDataIndex index, ScriptSectionType section) {
		super(section);
		ensure(index != null, "index cannot be null.");
		this.index = index;
	}

	/**
	 * Returns the index which the change operation is related to.
	 *
	 * @return The index which the change operation is related to.
	 *
	 * @changed OLI 17.12.2015 - Added.
	 */
	public MetaDataIndex getIndex() {
		return this.index;
	}

}