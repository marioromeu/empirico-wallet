package br.com.itads.empirico.application.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Attitude {
	UUID uuid;
	LocalDateTime datetime;
	String description;
	User user;
}