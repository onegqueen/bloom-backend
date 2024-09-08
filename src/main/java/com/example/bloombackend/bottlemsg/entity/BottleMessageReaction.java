package com.example.bloombackend.bottlemsg.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.bloombackend.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bottle-message-reaction")
public class BottleMessageReaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reaction_id")
	private Long reactionId;

	@ManyToOne
	@JoinColumn(name = "reactor_id")
	private UserEntity reactor;

	@ManyToOne
	@JoinColumn(name = "message_id")
	private BottleMessageEntity message;

	@Column(name = "reaction_type")
	private ReactionType reactionType;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Builder
	public BottleMessageReaction(UserEntity reactor, BottleMessageEntity message, ReactionType reactionType) {
		this.reactor = reactor;
		this.message = message;
		this.reactionType = reactionType;
	}
}
