package com.inhatc.spring.capstone.entity.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSavedFile is a Querydsl query type for SavedFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSavedFile extends EntityPathBase<SavedFile> {

    private static final long serialVersionUID = 1749593614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSavedFile savedFile = new QSavedFile("savedFile");

    public final NumberPath<Integer> byteSize = createNumber("byteSize", Integer.class);

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath originalName = createString("originalName");

    public final com.inhatc.spring.capstone.content.entity.QContent projectContent;

    public final EnumPath<com.inhatc.spring.capstone.constant.FileType> type = createEnum("type", com.inhatc.spring.capstone.constant.FileType.class);

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QSavedFile(String variable) {
        this(SavedFile.class, forVariable(variable), INITS);
    }

    public QSavedFile(Path<? extends SavedFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSavedFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSavedFile(PathMetadata metadata, PathInits inits) {
        this(SavedFile.class, metadata, inits);
    }

    public QSavedFile(Class<? extends SavedFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.projectContent = inits.isInitialized("projectContent") ? new com.inhatc.spring.capstone.content.entity.QContent(forProperty("projectContent"), inits.get("projectContent")) : null;
    }

}

