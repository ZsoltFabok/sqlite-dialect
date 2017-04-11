package org.hibernate.dialect;

import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.engine.spi.RowSelection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LimitHelper.class)
public class SQLiteLimitHandlerTest {
    private LimitHandler limitHandler;
    private  RowSelection rowSelection;

    @Before
    public void setup() {
        limitHandler = new SQLiteLimitHandler();
        rowSelection = new RowSelection();

        PowerMockito.mockStatic(LimitHelper.class);
    }

    @Test
    public void supportsLimit() {
        assertTrue(limitHandler.supportsLimit());
    }

    @Test
    public void supportsLimitOffset() {
        assertTrue(limitHandler.supportsLimitOffset());
    }

    @Test
    public void processSqlHasOffset() {
        when(LimitHelper.hasFirstRow(rowSelection)).thenReturn(true);

        assertEquals("sql limit ? offset ?", limitHandler.processSql("sql", rowSelection));
    }

    @Test
    public void processSqlDoesNotHaveOffset() {
        when(LimitHelper.hasFirstRow(rowSelection)).thenReturn(false);

        assertEquals("sql limit ?", limitHandler.processSql("sql", rowSelection));
    }

    @Test
    public void bindLimitParametersAtStartOfQuery() throws SQLException {
        assertEquals(0, limitHandler.bindLimitParametersAtStartOfQuery(rowSelection, null, 0));
    }

    @Test
    public void bindLimitParametersAtEndOfQuery() throws SQLException {
        assertEquals(0, limitHandler.bindLimitParametersAtEndOfQuery(rowSelection, null, 0));
    }

    @Test
    public void setMaxRowsDoesNothing() throws SQLException {
        limitHandler.setMaxRows(rowSelection, null);
    }

}
