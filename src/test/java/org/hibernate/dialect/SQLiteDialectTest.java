package org.hibernate.dialect;


import org.junit.Before;
import org.junit.Test;

import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SQLiteDialectTest {
    static {
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "ERROR");
    }

    private Dialect dialect;

    @Before
    public void setup() {
        dialect = new SQLiteDialect();

        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    @Test
    public void getSelectGUIDString() {
        assertEquals("select hex(randomblob(16))", dialect.getSelectGUIDString());
    }

    @Test
    public void supportsTupleDistinctCounts() {
        assertFalse(dialect.supportsTupleDistinctCounts());
    }

    @Test
    public void supportsIfExistsBeforeTableName() {
        assertTrue(dialect.supportsIfExistsBeforeTableName());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAddPrimaryKeyConstraintString() {
        dialect.getAddPrimaryKeyConstraintString(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAddForeignKeyConstraintString() {
        dialect.getAddForeignKeyConstraintString(null, new String[]{}, null, null, false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getDropForeignKeyString() {
        dialect.getDropForeignKeyString();
    }

    @Test
    public void supportsOuterJoinForUpdate() {
        assertFalse(dialect.supportsOuterJoinForUpdate());
    }

    @Test
    public void getForUpdateString() {
        assertEquals("", dialect.getForUpdateString());
    }

    @Test
    public void dropConstraints() {
        assertFalse(dialect.dropConstraints());
    }

    @Test
    public void hasAlterTable() {
        assertFalse(dialect.hasAlterTable());
    }

    @Test
    public void supportsUnionAll() {
        assertTrue(dialect.supportsUnionAll());
    }

    @Test
    public void buildSQLExceptionConversionDelegate() {
        assertTrue(dialect.buildSQLExceptionConversionDelegate() instanceof SQLiteSQLExceptionConversionDelegate);
    }

    @Test
    public void getCurrentTimestampSelectString() {
        assertEquals("select current_timestamp", dialect.getCurrentTimestampSelectString());
    }

    @Test
    public void isCurrentTimestampSelectStringCallable() {
        assertFalse(dialect.isCurrentTimestampSelectStringCallable());
    }

    @Test
    public void supportsCurrentTimestampSelection() {
        assertTrue(dialect.supportsCurrentTimestampSelection());
    }
    
    @Test
    public void typeNames() {
        assertEquals("boolean", dialect.getTypeName(Types.BIT));
        assertEquals("tinyint", dialect.getTypeName(Types.TINYINT));
        assertEquals("smallint", dialect.getTypeName(Types.SMALLINT));
        assertEquals("integer", dialect.getTypeName(Types.INTEGER));
        assertEquals("bigint", dialect.getTypeName(Types.BIGINT));
        assertEquals("float", dialect.getTypeName(Types.FLOAT));
        assertEquals("real", dialect.getTypeName(Types.REAL));
        assertEquals("double", dialect.getTypeName(Types.DOUBLE));
        assertEquals("numeric($p, $s)", dialect.getTypeName(Types.NUMERIC));
        assertEquals("decimal", dialect.getTypeName(Types.DECIMAL));
        assertEquals("char", dialect.getTypeName(Types.CHAR));
        assertEquals("varchar($l)", dialect.getTypeName(Types.VARCHAR));
        assertEquals("longvarchar", dialect.getTypeName(Types.LONGVARCHAR));
        assertEquals("date", dialect.getTypeName(Types.DATE));
        assertEquals("time", dialect.getTypeName(Types.TIME));
        assertEquals("datetime", dialect.getTypeName(Types.TIMESTAMP));
        assertEquals("blob", dialect.getTypeName(Types.BINARY));
        assertEquals("blob", dialect.getTypeName(Types.VARBINARY));
        assertEquals("blob", dialect.getTypeName(Types.LONGVARBINARY));
        assertEquals("blob", dialect.getTypeName(Types.BLOB));
        assertEquals("clob", dialect.getTypeName(Types.CLOB));
        assertEquals("boolean", dialect.getTypeName(Types.BOOLEAN));
    }
}
