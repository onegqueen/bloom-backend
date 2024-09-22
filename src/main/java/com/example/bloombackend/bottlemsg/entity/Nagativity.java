package com.example.bloombackend.bottlemsg.entity;

public enum Nagativity {
	UPPER("상"), MIDDLE("중"), LOWER("하");

	private final String label;

	Nagativity(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
