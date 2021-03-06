/*
 * DropComplexUniqueConstraint.java
 *
 * 17.12.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.meta.chops.uniques;

import archimedes.legacy.meta.MetaDataUniqueConstraint;
import archimedes.legacy.meta.chops.ScriptSectionType;

/**
 * A representation of a drop complex unique constraint operation.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.12.2015 - Added.
 */

public class DropComplexUniqueConstraint extends AbstractComplexUniqueConstraintChangeOperation {

	/**
	 * Creates a new drop complex constraint change operation with the passed parameters.
	 *
	 * @param uniqueConstraint The unique constraint which is related to the change operation.
	 * 
	 * @changed OLI 17.12.2015 - Added.
	 */
	public DropComplexUniqueConstraint(MetaDataUniqueConstraint uniqueConstraint) {
		this(uniqueConstraint, ScriptSectionType.ADD_COLUMNS_AND_DROP_CONSTRAINTS);
	}

	/**
	 * Creates a new drop complex constraint change operation with the passed parameters.
	 *
	 * @param uniqueConstraint The unique constraint which is related to the change operation.
	 * @param section          The type of the section which the change operation is related to.
	 *
	 * @changed OLI 17.12.2015 - Added.
	 */
	public DropComplexUniqueConstraint(MetaDataUniqueConstraint uniqueConstraint, ScriptSectionType section) {
		super(uniqueConstraint, section);
	}

	/**
	 * @changed OLI 21.12.2015 - Added.
	 */
	@Override
	public String toString() {
		MetaDataUniqueConstraint uc = this.getUniqueConstraint();
		return "DropComplexUniqueConstraint(table=" + this.quote(uc.getTable()) + ", columns={"
				+ this.quote(uc.getColumns()) + "})";
	}

}