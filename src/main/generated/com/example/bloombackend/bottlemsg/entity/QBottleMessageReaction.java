package com.example.bloombackend.bottlemsg.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBottleMessageReaction is a Querydsl query type for BottleMessageReaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBottleMessageReaction extends EntityPathBase<BottleMessageReaction> {

    private static final long serialVersionUID = -964075074L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBottleMessageReaction bottleMessageReaction = new QBottleMessageReaction("bottleMessageReaction");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QBottleMessageEntity message;

    public final EnumPath<ReactionType> reactionType = createEnum("reactionType", ReactionType.class);

    public final com.example.bloombackend.user.entity.QUserEntity reactor;

    public QBottleMessageReaction(String variable) {
        this(BottleMessageReaction.class, forVariable(variable), INITS);
    }

    public QBottleMessageReaction(Path<? extends BottleMessageReaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBottleMessageReaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBottleMessageReaction(PathMetadata metadata, PathInits inits) {
        this(BottleMessageReaction.class, metadata, inits);
    }

    public QBottleMessageReaction(Class<? extends BottleMessageReaction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.message = inits.isInitialized("message") ? new QBottleMessageEntity(forProperty("message"), inits.get("message")) : null;
        this.reactor = inits.isInitialized("reactor") ? new com.example.bloombackend.user.entity.QUserEntity(forProperty("reactor")) : null;
    }

}

