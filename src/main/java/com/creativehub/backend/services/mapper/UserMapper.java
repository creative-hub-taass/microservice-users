package com.creativehub.backend.services.mapper;

import com.creativehub.backend.models.User;
import com.creativehub.backend.services.dto.UserDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
	User userDtoToUser(UserDto userDto);

	@Mapping(source = "creator.avatar", target = "creator.avatarUrl")
	@Mapping(target = "inspirerIds", expression = "java(inspirersToInspirerIds(user.getInspirers()))")
	@Mapping(target = "fanIds", expression = "java(fansToFanIds(user.getFans()))")
	UserDto userToUserDto(User user);

	@Mapping(source = "creator.avatarUrl", target = "creator.avatar")
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUserFromUserDto(UserDto userDto, @MappingTarget User user);

	default Set<Long> inspirersToInspirerIds(Set<User> inspirers) {
		return inspirers.stream().map(User::getId).collect(Collectors.toSet());
	}

	default Set<Long> fansToFanIds(Set<User> fans) {
		return fans.stream().map(User::getId).collect(Collectors.toSet());
	}
}
