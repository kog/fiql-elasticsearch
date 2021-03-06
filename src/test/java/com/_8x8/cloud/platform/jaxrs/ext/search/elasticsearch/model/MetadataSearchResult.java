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

package com._8x8.cloud.platform.jaxrs.ext.search.elasticsearch.model;

import com._8x8.cloud.platform.jaxrs.ext.search.elasticsearch.TranslatingQueryBuilderVisitor;

/**
 * Provides another test model we can use for testing our {@link TranslatingQueryBuilderVisitor}.
 */
@SuppressWarnings("unused")
public class MetadataSearchResult {
    private ObjectMetadata objectMetadata;
    private OtherMetadata otherMetadata;

    public ObjectMetadata getObjectMetadata() {
        return objectMetadata;
    }

    public void setObjectMetadata(final ObjectMetadata objectMetadata) {
        this.objectMetadata = objectMetadata;
    }

    public OtherMetadata getOtherMetadata() {
        return otherMetadata;
    }

    public void setOtherMetadata(final OtherMetadata otherMetadata) {
        this.otherMetadata = otherMetadata;
    }
}
