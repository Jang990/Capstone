package com.inhatc.spring.capstone.entity.recuit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecruitedMember is a Querydsl query type for RecruitedMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecruitedMember extends EntityPathBase<RecruitedMember> {

    private static final long serialVersionUID = -1833232490L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecruitedMember recruitedMember = new QRecruitedMember("recruitedMember");

    public final com.inhatc.spring.capstone.content.entity.QContent content;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> rec_count = createNumber("rec_count", Integer.class);

    public QRecruitedMember(String variable) {
        this(RecruitedMember.class, forVariable(variable), INITS);
    }

    public QRecruitedMember(Path<? extends RecruitedMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecruitedMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecruitedMember(PathMetadata metadata, PathInits inits) {
        this(RecruitedMember.class, metadata, inits);
    }

    public QRecruitedMember(Class<? extends RecruitedMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new com.inhatc.spring.capstone.content.entity.QContent(forProperty("content"), inits.get("content")) : null;
    }

}

