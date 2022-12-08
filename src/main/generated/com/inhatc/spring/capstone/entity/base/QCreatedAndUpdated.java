package com.inhatc.spring.capstone.entity.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCreatedAndUpdated is a Querydsl query type for CreatedAndUpdated
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QCreatedAndUpdated extends EntityPathBase<CreatedAndUpdated> {

    private static final long serialVersionUID = -1936554900L;

    public static final QCreatedAndUpdated createdAndUpdated = new QCreatedAndUpdated("createdAndUpdated");

    public final DateTimePath<java.time.LocalDateTime> date_created = createDateTime("date_created", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> last_updated = createDateTime("last_updated", java.time.LocalDateTime.class);

    public QCreatedAndUpdated(String variable) {
        super(CreatedAndUpdated.class, forVariable(variable));
    }

    public QCreatedAndUpdated(Path<? extends CreatedAndUpdated> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCreatedAndUpdated(PathMetadata metadata) {
        super(CreatedAndUpdated.class, metadata);
    }

}

