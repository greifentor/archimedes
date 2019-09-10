/*
 * ArtifactBuilderUtils.java
 *
 * 26.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import archimedes.acf.util.*;
import archimedes.legacy.acf.util.CodeGeneratorUtil;
import archimedes.legacy.acf.util.TypeUtil;
import archimedes.legacy.model.DomainModel;
import archimedes.legacy.util.DescriptionGenerator;
import archimedes.model.*;
import archimedes.util.*;

import static corentx.util.Checks.*;

import gengen.util.*;

import java.util.*;

/**
 * A utility class for artifact builders.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 26.04.2018 - Added.
 */

public class ArtifactBuilderUtils {

    private DescriptionGenerator descriptionGenerator = new DescriptionGenerator();
    private CodeGeneratorUtil generatorUtil = new CodeGeneratorUtil();
    private String name = null;
    private TypeUtil typeUtil = new TypeUtil();

    /**
     * Creates a new artifact builder utils class with the passed parameters.
     *
     * @param name The name of the object which the code is to create for.
     * @throws IllegalArgumentException Passing a null or an empty name.
     */
    public ArtifactBuilderUtils(String name) {
        super();
        ensure(name != null, "name cannot be null.");
        ensure(!name.isEmpty(), "name cannot be empty.");
        this.name = name;
    }

    /**
     * Returns an attribute name for the name (e. g. for using in class source codes).
     *
     * @return An attribute name for the name (e. g. for using in class source codes).
     */
    public String getAttributeName() {
        return getAttributeName(this.name);
    }

    /**
     * Returns an attribute name for the passed string (e. g. for using in class source codes).
     *
     * @param s The string which the attribute name is to create.
     * @return An attribute name for the passed string (e. g. for using in class source codes).
     * @throws IllegalArgumentException Passing a null value.
     */
    public String getAttributeName(String s) {
        ensure(s != null, "string cannot be null.");
        ensure(!s.isEmpty(), "string cannot be empty.");
        return this.generatorUtil.getAttributeName(s);
    }

    /**
     * Returns a description for the name (e. g. to insert into comments).
     *
     * @return A description for the name (e. g. to insert into comments).
     */
    public String getDescription() {
        return getDescription(this.name);
    }

    /**
     * Returns a description for passed string (e. g. to insert into comments).
     *
     * @param s The string (usually a name) to convert into a description.
     * @return A description for passed string (e. g. to insert into comments).
     * @throws IllegalArgumentException Passing a null value.
     */
    public String getDescription(String s) {
        ensure(s != null, "string to convert to a description cannot be null.");
        if (s.equals(s.toUpperCase())) {
            return s;
        }
        return this.descriptionGenerator.getDescription(this.name);
    }

    /**
     * Returns a description for the name (e. g. to insert into comments) in plural.
     *
     * @return A description for the name (e. g. to insert into comments) in plural.
     */
    public String getDescriptionPlural() {
        String s = this.getDescription();
        if (s.toUpperCase().endsWith("Y")) {
            s = s.substring(0, s.length()-1).concat("ies");
        } else {
            s += "s";
        }
        return s;
    }

    /**
     * Returns the java type for the passed domain.
     *
     * @param domain The domain whose java type is to return.
     * @return The java type for the passed domain.
     */
    public String getJavaType(DomainModel domain) {
        if (isTimeType(domain.getName())) {
            return domain.getName();
        }
        return Converter.toJavaType(domain.getDataType(), domain.getName(), "corentx.dates");
    }

    /**
     * Returns the names of the time types.
     *
     * @return The names of the time types.
     */
    public List<String> getTimeTypes() {
        return Arrays.asList("LongPTimestamp", "PDate", "PTime", "PTimestamp");
    }

    /**
     * Returns the type util which is used by the artifact builder utils.
     *
     * @return The type util which is used by the artifact builder utils.
     */
    public TypeUtil getTypeUtil() {
        return this.typeUtil;
    }

    /**
     * Checks if the passed type name is a boolean type.
     *
     * @param typeName The name of the type to check.
     * @return "true" if the type is a boolean type.
     */
    public boolean isBooleanType(String typeName) {
        if ("boolean".equalsIgnoreCase(typeName)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the passed type name is a numeric type.
     *
     * @param typeName The name of the type to check.
     * @return "true" if the type is a numeric type.
     */
    public boolean isNumericType(String typeName) {
        String[] numericTypes = new String[] {"byte", "double", "float", "int", "Integer",
                "long", "short"};
        for (String tn : numericTypes) {
            if (tn.equalsIgnoreCase(typeName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the passed type name is a time type.
     *
     * @param typeName The name of the type to check.
     * @return "true" if the type is a time type.
     */
    public boolean isTimeType(String typeName) {
        return this.getTimeTypes().contains(typeName);
    }

    /**
     * Checks if the passed domain is a boolean type.
     *
     * @param domain The domain to check.
     * @return "true" if the domain is a boolean type.
     */
    public boolean isBooleanType(DomainModel domain) {
        return isBooleanType(getJavaType(domain));
    }

    /**
     * Checks if the passed domain is a numeric type.
     *
     * @param domain The domain to check.
     * @return "true" if the domain is a numeric type.
     */
    public boolean isNumericType(DomainModel domain) {
        return isNumericType(getJavaType(domain));
    }

    /**
     * Checks if the passed domain is a time type.
     *
     * @param domain The domain to check.
     * @return "true" if the domain is a time type.
     */
    public boolean isTimeType(DomainModel domain) {
        return isTimeType(getJavaType(domain));
    }

}