package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Attitude implements Serializable {
	private static final long serialVersionUID = 4490357475107769662L;
	UUID uuid;
	LocalDateTime datetime;
	String description;
	User user;
}