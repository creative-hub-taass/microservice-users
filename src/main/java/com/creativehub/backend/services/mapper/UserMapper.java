package com.creativehub.backend.services.mapper;

import com.creativehub.backend.models.User;
import com.creativehub.backend.services.dto.UserDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
	User userDtoToUser(UserDto userDto);

	@Mapping(target = "inspirerIds", expression = "java(inspirersToInspirerIds(user.getInspirers()))")
	@Mapping(target = "fanIds", expression = "java(fansToFanIds(user.getFans()))")
	UserDto userToUserDto(User user);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUserFromUserDto(UserDto userDto, @MappingTarget User user);

	@SuppressWarnings("unused")
	default Set<UUID> inspirersToInspirerIds(Set<User> inspirers) {
		return inspirers.stream().map(User::getId).collect(Collectors.toSet());
	}

	@SuppressWarnings("unused")
	default Set<UUID> fansToFanIds(Set<User> fans) {
		return fans.stream().map(User::getId).collect(Collectors.toSet());
	}
}
