package org.hibernate.dialect;

import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.dialect.pagination.LimitHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Limit handler for the SQLite dialect.
 */
public class SQLiteLimitHandler implements LimitHandler {

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public String processSql(String sql, RowSelection selection) {
        if (LimitHelper.hasFirstRow(selection)) {
            return sql + " limit ? offset ?";
        } else {
            return sql + " limit ?";
        }
    }

    @Override
    public int bindLimitParametersAtStartOfQuery(RowSelection selection, PreparedStatement statement, int index) throws SQLException {
        return 0;
    }

    @Override
    public int bindLimitParametersAtEndOfQuery(RowSelection selection, PreparedStatement statement, int index) throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(RowSelection selection, PreparedStatement statement) throws SQLException {
    }
}
