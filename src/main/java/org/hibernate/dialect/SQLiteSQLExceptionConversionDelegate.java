package org.hibernate.dialect;

import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.spi.SQLExceptionConversionDelegate;
import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
import org.hibernate.internal.util.JdbcExceptionHelper;

import java.sql.SQLException;


public class SQLiteSQLExceptionConversionDelegate implements SQLExceptionConversionDelegate {

    private static final int SQLITE_BUSY = 5;
    private static final int SQLITE_LOCKED = 6;
    private static final int SQLITE_IO_ERR = 10;
//    private static final int SQLITE_CORRUPT = 11;
//    private static final int SQLITE_NOT_FOUND = 12;
//    private static final int SQLITE_FULL = 13;
//    private static final int SQLITE_CANT_OPEN = 14;
    private static final int SQLITE_PROTOCOL = 15;
    private static final int SQLITE_TOO_BIG = 18;
    private static final int SQLITE_CONSTRAINT = 19;
    private static final int SQLITE_MISMATCH = 20;
    private static final int SQLITE_NOT_ADB = 26;

    @Override
    public JDBCException convert(SQLException sqlException, String message, String sql) {
        final int errorCode = JdbcExceptionHelper.extractErrorCode(sqlException);
        if (errorCode == SQLITE_CONSTRAINT) {
            final String constraintName = EXTRACTER.extractConstraintName(sqlException);
            return new ConstraintViolationException(message, sqlException, sql, constraintName);
        } else if (errorCode == SQLITE_TOO_BIG || errorCode == SQLITE_MISMATCH) {
            return new DataException(message, sqlException, sql);
        } else if (errorCode == SQLITE_BUSY || errorCode == SQLITE_LOCKED) {
            return new LockAcquisitionException(message, sqlException, sql);
        } else if ((errorCode >= SQLITE_IO_ERR && errorCode <= SQLITE_PROTOCOL) || errorCode == SQLITE_NOT_ADB) {
            return new JDBCConnectionException(message, sqlException, sql);
        }
        return new GenericJDBCException(message, sqlException, sql);
    }

    private static final ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter() {
        @Override
        protected String doExtractConstraintName(SQLException sqle) throws NumberFormatException {
            return extractUsingTemplate("constraint ", " failed", sqle.getMessage());
        }
    };
}
