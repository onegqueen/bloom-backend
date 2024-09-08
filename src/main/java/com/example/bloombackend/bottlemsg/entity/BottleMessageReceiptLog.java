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
@Table(name = "bottle_message_receipt_log")
public class BottleMessageReceiptLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "receipt_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "message_id", nullable = false)
	private BottleMessageEntity message;

	@ManyToOne
	@JoinColumn(name = "recipient_id", nullable = false)
	private UserEntity recipient;

	@CreationTimestamp
	@Column(name = "received_at")
	private LocalDateTime receivedAt;

	@Column(name = "is_saved", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	private boolean isSaved;

	@Builder
	public BottleMessageReceiptLog(BottleMessageEntity message, UserEntity recipient) {
		this.message = message;
		this.recipient = recipient;
	}

	public void updateSaved(boolean saved) {
		this.isSaved = saved;
	}
}
