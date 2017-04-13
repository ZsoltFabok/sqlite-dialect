package org.hibernate.dialect;

import org.hamcrest.Description;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.engine.jdbc.dialect.internal.DialectResolverSet;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class SQLiteMetadataBuilderInitializerTest {
    private MetadataBuilderInitializer initializer;
    private MetadataBuilder metadataBuilder;
    StandardServiceRegistry registry;
    DialectResolverSet resolver;

    @Before
    public void setup() {
        initializer = new SQLiteMetadataBuilderInitializer();

        metadataBuilder = mock(MetadataBuilder.class);
        registry = mock(StandardServiceRegistry.class);
        resolver = mock(DialectResolverSet.class);

        when(registry.getService(DialectResolver.class)).thenReturn(resolver);
    }

    @Test
    public void contributeRegistersSQLiteDialect() {
        initializer.contribute(metadataBuilder, registry);
        verify(resolver).addResolver(argThat(new DialectResolverMatcher("SQLite")));
    }

    @Test
    public void contributeDoesNotRegisterSQLiteDialect() {
        initializer.contribute(metadataBuilder, registry);
        verify(resolver).addResolver(argThat(new DialectResolverMatcher("MySql")));
    }

    private static class DialectResolverMatcher extends ArgumentMatcher<DialectResolver> {

        private String databaseName;

        public DialectResolverMatcher(String databaseName) {
            this.databaseName = databaseName;
        }

        @Override
        public boolean matches(Object object) {
            DialectResolver actual = (DialectResolver) object;
            DialectResolutionInfo info = mock(DialectResolutionInfo.class);
            when(info.getDatabaseName()).thenReturn(databaseName);

            if ("SQLite".equals(databaseName)) {
                return actual.resolveDialect(info) instanceof SQLiteDialect;
            } else {
                return actual.resolveDialect(info) == null;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(null);
        }
    }
}
