package com.inhatc.spring.capstone.content.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentVote is a Querydsl query type for ContentVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentVote extends EntityPathBase<ContentVote> {

    private static final long serialVersionUID = 114632219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentVote contentVote = new QContentVote("contentVote");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.inhatc.spring.capstone.user.entity.QUsers likedUser;

    public final QContent project;

    public final BooleanPath yn = createBoolean("yn");

    public QContentVote(String variable) {
        this(ContentVote.class, forVariable(variable), INITS);
    }

    public QContentVote(Path<? extends ContentVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentVote(PathMetadata metadata, PathInits inits) {
        this(ContentVote.class, metadata, inits);
    }

    public QContentVote(Class<? extends ContentVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.likedUser = inits.isInitialized("likedUser") ? new com.inhatc.spring.capstone.user.entity.QUsers(forProperty("likedUser")) : null;
        this.project = inits.isInitialized("project") ? new QContent(forProperty("project"), inits.get("project")) : null;
    }

}

