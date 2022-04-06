package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto implements Serializable {
	private final UUID id;
	private final String username;
	private final String nickname;
	private final String email;
	private final Role role;
	private final CreatorDto creator;
	private final Set<UUID> inspirerIds;
	private final Set<UUID> fanIds;
}
