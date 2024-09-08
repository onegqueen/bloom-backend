package com.example.bloombackend.bottlemsg.entity;

public enum ReactionType {
	LIKE("좋아요"), EMPATHY("공감해요"), CHEER("응원해요");

	private final String label;

	ReactionType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
