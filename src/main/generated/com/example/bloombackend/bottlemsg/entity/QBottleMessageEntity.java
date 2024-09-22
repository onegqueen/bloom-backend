package com.example.bloombackend.bottlemsg.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBottleMessageEntity is a Querydsl query type for BottleMessageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBottleMessageEntity extends EntityPathBase<BottleMessageEntity> {

    private static final long serialVersionUID = 1418939576L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBottleMessageEntity bottleMessageEntity = new QBottleMessageEntity("bottleMessageEntity");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Nagativity> nagativity = createEnum("nagativity", Nagativity.class);

    public final StringPath postcardUrl = createString("postcardUrl");

    public final com.example.bloombackend.user.entity.QUserEntity sender;

    public final StringPath title = createString("title");

    public QBottleMessageEntity(String variable) {
        this(BottleMessageEntity.class, forVariable(variable), INITS);
    }

    public QBottleMessageEntity(Path<? extends BottleMessageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBottleMessageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBottleMessageEntity(PathMetadata metadata, PathInits inits) {
        this(BottleMessageEntity.class, metadata, inits);
    }

    public QBottleMessageEntity(Class<? extends BottleMessageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sender = inits.isInitialized("sender") ? new com.example.bloombackend.user.entity.QUserEntity(forProperty("sender")) : null;
    }

}

