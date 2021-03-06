/*
 * Copyright 2017 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com._8x8.cloud.platform.jaxrs.ext.search.elasticsearch;

import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Provides a class to make using FIQL to generate a {@link QueryBuilder}s less cumbersome.
 * <p>
 * Out of the box, the standard mechanism for using FIQL depends either on using the JAX-RS Search support, or using
 * a raw {@link FiqlParser} like so:
 * <pre>
 *  final FiqlParser&lt;MetadataRecord&gt; parser = new FiqlParser&lt;&gt;(MetadataRecord.class);
 *  final ElasticsearchQueryBuilderVisitor&lt;MetadataRecord&gt; visitor = new ElasticsearchQueryBuilderVisitor&lt;&gt;();
 *
 *  visitor.visit(parser.parse(fiqlFilter));
 *  final QueryBuilder builder = visitor.getQuery();
 * </pre>
 * <p>
 * Using this class you can create an instance that wraps the {@link FiqlParser}, which can be re-used in a threadsafe
 * manner, and handles the creation and manipulation of {@link ElasticsearchQueryBuilderVisitor} for you. Simply create
 * and hold onto an instance of this class via constructors, IoC etc, and call it later like so:
 * <pre>
 *     // Hold onto this.
 *     final ElasticsearchQueryBuilder&lt;MetadataRecord&gt; elasticSearchBuilder = new ElasticsearchQueryBuilder&lt;&gt;(MetadataRecord.class);
 *
 *     ...
 *     final QueryBuilder queryBuilder = elasticSearchBuilder.generateQueryBuilder("tenantName==taters,(containerName==delicious;tenantName==dinner)");
 * </pre>
 */
public class ElasticsearchQueryBuilder<T> {
    private final FiqlParser<T> fiqlParser;

    FiqlParser<T> getFiqlParser() {
        return fiqlParser;
    }

    /**
     * Constructs a query builder that allows for easy creation of Elasticsearch {@link QueryBuilder} instances
     * based on FIQL expressions.
     *
     * @param clazz The model class to use when generating {@link QueryBuilder}s from the given expression.
     */
    public ElasticsearchQueryBuilder(final Class<T> clazz) {
        this.fiqlParser = new FiqlParser<>(clazz);
    }

    /**
     * Creates an Elasticsearch {@link QueryBuilder} based on a given FIQL filter query string.
     *
     * @param filter The filter query string to transform into a {@link QueryBuilder}. Must not be null.
     * @return A non-null, valid and fully constructed {@link QueryBuilder} representing the input query string.
     */
    public QueryBuilder generateQueryBuilder(final String filter) {
        // While our parser is threadsafe and re-usable, there's no real value in keeping visitors around
        final ElasticsearchQueryBuilderVisitor<T> visitor = createVisitor();
        visitor.visit(getFiqlParser().parse(filter));

        return visitor.getQuery();
    }

    /**
     * Provides a test-friendly method for creating instances of {@link ElasticsearchQueryBuilderVisitor} handling
     * parsed FIQL expressions.
     *
     * @return A non-null, valid, and fully primed {@link ElasticsearchQueryBuilderVisitor}.
     */
    ElasticsearchQueryBuilderVisitor<T> createVisitor() {
        return new ElasticsearchQueryBuilderVisitor<>();
    }
}
