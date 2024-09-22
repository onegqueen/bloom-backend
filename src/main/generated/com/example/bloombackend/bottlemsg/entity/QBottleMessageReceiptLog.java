package com.example.bloombackend.bottlemsg.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBottleMessageReceiptLog is a Querydsl query type for BottleMessageReceiptLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBottleMessageReceiptLog extends EntityPathBase<BottleMessageReceiptLog> {

    private static final long serialVersionUID = 1894114369L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBottleMessageReceiptLog bottleMessageReceiptLog = new QBottleMessageReceiptLog("bottleMessageReceiptLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSaved = createBoolean("isSaved");

    public final QBottleMessageEntity message;

    public final DateTimePath<java.time.LocalDateTime> receivedAt = createDateTime("receivedAt", java.time.LocalDateTime.class);

    public final com.example.bloombackend.user.entity.QUserEntity recipient;

    public QBottleMessageReceiptLog(String variable) {
        this(BottleMessageReceiptLog.class, forVariable(variable), INITS);
    }

    public QBottleMessageReceiptLog(Path<? extends BottleMessageReceiptLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBottleMessageReceiptLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBottleMessageReceiptLog(PathMetadata metadata, PathInits inits) {
        this(BottleMessageReceiptLog.class, metadata, inits);
    }

    public QBottleMessageReceiptLog(Class<? extends BottleMessageReceiptLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.message = inits.isInitialized("message") ? new QBottleMessageEntity(forProperty("message"), inits.get("message")) : null;
        this.recipient = inits.isInitialized("recipient") ? new com.example.bloombackend.user.entity.QUserEntity(forProperty("recipient")) : null;
    }

}

