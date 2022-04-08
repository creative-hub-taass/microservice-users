package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.CreatorType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Data
public class CreatorDto implements Serializable {
	private final UUID id;
	private final String name;
	private final String surname;
	private final Date birthDate;
	private final String bio;
	private final CreatorType creatorType;
	private final String avatarUrl;
	private final String paymentEmail;
}
