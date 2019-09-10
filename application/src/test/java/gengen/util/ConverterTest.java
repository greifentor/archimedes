/*
 * ConverterTest.java
 *
 * 09.09.2009
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.util;

import static org.junit.Assert.assertEquals;

import java.sql.Types;

import org.junit.Test;

/**
 * Testf&auml;lle zur Klasse Converter.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 09.09.2009 - Hinzugef&uuml;gt.
 *
 */

public class ConverterTest {

	@Test
	/** Test der Methode toJavaType(int, String, String). */
	public void testToJavaTypeIntStringString() {
		// Negativbeispiel.
		assertEquals("UNKNOWN", Converter.toJavaType(Integer.MAX_VALUE, null, null));
		// Ohne Domain.
		assertEquals("long", Converter.toJavaType(Types.BIGINT, null, null));
		assertEquals("boolean", Converter.toJavaType(Types.BIT, null, null));
		assertEquals("boolean", Converter.toJavaType(Types.BOOLEAN, null, null));
		assertEquals("byte[]", Converter.toJavaType(Types.BINARY, null, null));
		assertEquals("byte[]", Converter.toJavaType(Types.LONGVARBINARY, null, null));
		assertEquals("byte[]", Converter.toJavaType(Types.VARBINARY, null, null));
		assertEquals("String", Converter.toJavaType(Types.CHAR, null, null));
		assertEquals("String", Converter.toJavaType(Types.LONGVARCHAR, null, null));
		assertEquals("String", Converter.toJavaType(Types.VARCHAR, null, null));
		assertEquals("java.sql.Date", Converter.toJavaType(Types.DATE, null, null));
		assertEquals("double", Converter.toJavaType(Types.DECIMAL, null, null));
		assertEquals("double", Converter.toJavaType(Types.DOUBLE, null, null));
		assertEquals("double", Converter.toJavaType(Types.FLOAT, null, null));
		assertEquals("double", Converter.toJavaType(Types.NUMERIC, null, null));
		assertEquals("int", Converter.toJavaType(Types.INTEGER, null, null));
		assertEquals("int", Converter.toJavaType(Types.SMALLINT, null, null));
		assertEquals("int", Converter.toJavaType(Types.TINYINT, null, null));
		assertEquals("float", Converter.toJavaType(Types.REAL, null, null));
		assertEquals("java.sql.Time", Converter.toJavaType(Types.TIME, null, null));
		assertEquals("java.sql.Timestamp", Converter.toJavaType(Types.TIMESTAMP, null, null));
		// Mit Domain.
		// Boolean.
		assertEquals("boolean", Converter.toJavaType(Types.INTEGER, "Boolean", null));
		assertEquals("boolean", Converter.toJavaType(Types.BOOLEAN, "Boolean", null));
		assertEquals("boolean", Converter.toJavaType(Types.VARCHAR, "Boolean", null));
		// Date-Typen (ohne Namensraum).
		assertEquals("corentx.dates.PDate", Converter.toJavaType(Types.INTEGER, "PDate", null));
		assertEquals("corentx.dates.PDate", Converter.toJavaType(Types.BOOLEAN, "PDate", null));
		assertEquals("corentx.dates.PDate", Converter.toJavaType(Types.VARCHAR, "PDate", null));
		assertEquals("corentx.dates.PTime", Converter.toJavaType(Types.INTEGER, "PTime", null));
		assertEquals("corentx.dates.PTime", Converter.toJavaType(Types.BOOLEAN, "PTime", null));
		assertEquals("corentx.dates.PTime", Converter.toJavaType(Types.VARCHAR, "PTime", null));
		assertEquals("corentx.dates.PTimestamp", Converter.toJavaType(Types.INTEGER, "PTimestamp", null));
		assertEquals("corentx.dates.PTimestamp", Converter.toJavaType(Types.BOOLEAN, "PTimestamp", null));
		assertEquals("corentx.dates.PTimestamp", Converter.toJavaType(Types.VARCHAR, "PTimestamp", null));
		assertEquals("corentx.dates.LongPTimestamp", Converter.toJavaType(Types.INTEGER, "LongPTimestamp", null));
		assertEquals("corentx.dates.LongPTimestamp", Converter.toJavaType(Types.BOOLEAN, "LongPTimestamp", null));
		assertEquals("corentx.dates.LongPTimestamp", Converter.toJavaType(Types.VARCHAR, "LongPTimestamp", null));
		// Date-Typen (mit Namensraum).
		assertEquals("corent.dates.PDate", Converter.toJavaType(Types.INTEGER, "PDate", "corent.dates."));
		assertEquals("corent.dates.PDate", Converter.toJavaType(Types.INTEGER, "PDate", "corent.dates"));
		assertEquals("corent.dates.PDate", Converter.toJavaType(Types.BOOLEAN, "PDate", "corent.dates"));
		assertEquals("corent.dates.PDate", Converter.toJavaType(Types.VARCHAR, "PDate", "corent.dates"));
		assertEquals("corent.dates.PTimestamp", Converter.toJavaType(Types.INTEGER, "PTimestamp", "corent.dates"));
		assertEquals("corent.dates.PTimestamp", Converter.toJavaType(Types.BOOLEAN, "PTimestamp", "corent.dates"));
		assertEquals("corent.dates.PTimestamp", Converter.toJavaType(Types.VARCHAR, "PTimestamp", "corent.dates"));
		assertEquals("corent.dates.LongPTimestamp",
				Converter.toJavaType(Types.INTEGER, "LongPTimestamp", "corent.dates"));
		assertEquals("corent.dates.LongPTimestamp",
				Converter.toJavaType(Types.BOOLEAN, "LongPTimestamp", "corent.dates"));
		assertEquals("corent.dates.LongPTimestamp",
				Converter.toJavaType(Types.VARCHAR, "LongPTimestamp", "corent.dates"));
	}

}
