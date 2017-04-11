package org.hibernate.dialect;

import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.internal.util.JdbcExceptionHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JdbcExceptionHelper.class)
public class SQLiteSQLExceptionConversionDelegateTest {
    private SQLExceptionConversionDelegate conversionDelegate;
    private SQLException sqlException;

    @Before
    public void setup() {
        conversionDelegate = new SQLiteSQLExceptionConversionDelegate();
        sqlException = mock(SQLException.class);
        when(sqlException.getMessage()).thenReturn("message");

        PowerMockito.mockStatic(JdbcExceptionHelper.class);
    }

    @Test
    public void returnsConstraintViolationException() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(19);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof ConstraintViolationException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsDataExceptionForSqliteTooBigError() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(18);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof DataException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsDataExceptionForSqliteMismatch() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(20);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof DataException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsLockAcquisitionExceptionForSqliteBusy() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(5);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof LockAcquisitionException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsLockAcquisitionExceptionForSqliteLocked() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(6);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof LockAcquisitionException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsJDBCConnectionExceptionForSqliteCorrupt() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(11);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof JDBCConnectionException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsJDBCConnectionExceptionForSqliteNotAbd() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(26);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof JDBCConnectionException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }

    @Test
    public void returnsGenericJDBCExceptionForEverythingElse() {
        when(JdbcExceptionHelper.extractErrorCode(sqlException)).thenReturn(41);

        JDBCException exception = conversionDelegate.convert(sqlException, "message", "sql");
        assertTrue(exception instanceof GenericJDBCException);
        assertEquals("message", exception.getMessage());
        assertEquals("sql", exception.getSQL());
    }
}
