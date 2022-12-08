package com.inhatc.spring.capstone.entity.Tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLanguageTag is a Querydsl query type for LanguageTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLanguageTag extends EntityPathBase<LanguageTag> {

    private static final long serialVersionUID = 2078593201L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLanguageTag languageTag = new QLanguageTag("languageTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.inhatc.spring.capstone.content.entity.QContent project;

    public final QTag tag;

    public QLanguageTag(String variable) {
        this(LanguageTag.class, forVariable(variable), INITS);
    }

    public QLanguageTag(Path<? extends LanguageTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLanguageTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLanguageTag(PathMetadata metadata, PathInits inits) {
        this(LanguageTag.class, metadata, inits);
    }

    public QLanguageTag(Class<? extends LanguageTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new com.inhatc.spring.capstone.content.entity.QContent(forProperty("project"), inits.get("project")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

