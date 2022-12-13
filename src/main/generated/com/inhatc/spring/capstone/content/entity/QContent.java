package com.inhatc.spring.capstone.content.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContent is a Querydsl query type for Content
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContent extends EntityPathBase<Content> {

    private static final long serialVersionUID = 1737527185L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContent content1 = new QContent("content1");

    public final com.inhatc.spring.capstone.entity.base.QCreatedAndUpdated _super = new com.inhatc.spring.capstone.entity.base.QCreatedAndUpdated(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> date_created = _super.date_created;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRecruit = createBoolean("isRecruit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> last_updated = _super.last_updated;

    public final StringPath title = createString("title");

    public final StringPath usedLanguage = createString("usedLanguage");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final NumberPath<Integer> voteCount = createNumber("voteCount", Integer.class);

    public final com.inhatc.spring.capstone.user.entity.QUsers writer;

    public QContent(String variable) {
        this(Content.class, forVariable(variable), INITS);
    }

    public QContent(Path<? extends Content> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContent(PathMetadata metadata, PathInits inits) {
        this(Content.class, metadata, inits);
    }

    public QContent(Class<? extends Content> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new com.inhatc.spring.capstone.user.entity.QUsers(forProperty("writer")) : null;
    }

}

