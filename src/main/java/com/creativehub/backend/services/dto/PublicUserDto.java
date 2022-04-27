package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.CreatorType;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class PublicUserDto implements Serializable {
	private final UUID id;
	private final String username;
	private final String nickname;
	private final PublicCreatorDto creator;
	private final Set<UUID> inspirerIds;
	private final Set<UUID> fanIds;

	public PublicUserDto(UserDto user) {
		id = user.getId();
		username = user.getUsername();
		nickname = user.getNickname();
		inspirerIds = user.getInspirerIds();
		fanIds = user.getFanIds();
		if (user.getCreator() != null) {
			creator = new PublicCreatorDto(user.getCreator());
		} else creator = null;
	}

	public boolean isCreator() {
		return creator != null;
	}

	@Data
	private static class PublicCreatorDto implements Serializable {
		private final UUID id;
		private final String bio;
		private final CreatorType creatorType;
		private final String avatar;

		public PublicCreatorDto(CreatorDto creator) {
			id = creator.getId();
			bio = creator.getBio();
			creatorType = creator.getCreatorType();
			avatar = creator.getAvatar();
		}
	}
}
