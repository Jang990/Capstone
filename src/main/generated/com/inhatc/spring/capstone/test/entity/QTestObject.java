package com.inhatc.spring.capstone.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestObject is a Querydsl query type for TestObject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestObject extends EntityPathBase<TestObject> {

    private static final long serialVersionUID = 1411661646L;

    public static final QTestObject testObject = new QTestObject("testObject");

    public final NumberPath<Long> testId = createNumber("testId", Long.class);

    public final StringPath testStr = createString("testStr");

    public QTestObject(String variable) {
        super(TestObject.class, forVariable(variable));
    }

    public QTestObject(Path<? extends TestObject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestObject(PathMetadata metadata) {
        super(TestObject.class, metadata);
    }

}

