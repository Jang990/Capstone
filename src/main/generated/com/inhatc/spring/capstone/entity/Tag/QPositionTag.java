package com.inhatc.spring.capstone.entity.Tag;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPositionTag is a Querydsl query type for PositionTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPositionTag extends EntityPathBase<PositionTag> {

    private static final long serialVersionUID = -1177965248L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPositionTag positionTag = new QPositionTag("positionTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.inhatc.spring.capstone.entity.recuit.QRecruitedMember member;

    public final QTag tag;

    public QPositionTag(String variable) {
        this(PositionTag.class, forVariable(variable), INITS);
    }

    public QPositionTag(Path<? extends PositionTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPositionTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPositionTag(PathMetadata metadata, PathInits inits) {
        this(PositionTag.class, metadata, inits);
    }

    public QPositionTag(Class<? extends PositionTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.inhatc.spring.capstone.entity.recuit.QRecruitedMember(forProperty("member"), inits.get("member")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag")) : null;
    }

}

