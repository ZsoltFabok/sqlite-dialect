package org.hibernate.dialect;

import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.spi.MetadataBuilderInitializer;
import org.hibernate.engine.jdbc.dialect.internal.DialectResolverSet;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

public class SQLiteMetadataBuilderInitializer implements MetadataBuilderInitializer {

    private static final SQLiteDialect dialect = new SQLiteDialect();

    @Override
    public void contribute(MetadataBuilder metadataBuilder, StandardServiceRegistry serviceRegistry) {
        DialectResolver dialectResolver = serviceRegistry.getService(DialectResolver.class);

        if ((dialectResolver instanceof DialectResolverSet)) {
            ((DialectResolverSet) dialectResolver).addResolver(resolver);
        }
    }

    static private final DialectResolver resolver = new DialectResolver() {
        @Override
        public Dialect resolveDialect(DialectResolutionInfo info) {
            if (info.getDatabaseName().equals("SQLite"))
                return dialect;
            return null;
        }
    };
}


