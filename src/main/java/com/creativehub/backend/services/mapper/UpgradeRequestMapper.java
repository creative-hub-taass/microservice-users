package com.creativehub.backend.services.mapper;

import com.creativehub.backend.models.UpgradeRequest;
import com.creativehub.backend.services.dto.UpgradeRequestDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UpgradeRequestMapper {
	UpgradeRequest upgradeRequestDtoToUpgradeRequest(UpgradeRequestDto upgradeRequestDto);

	UpgradeRequestDto upgradeRequestToUpgradeRequestDto(UpgradeRequest upgradeRequest);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUpgradeRequestFromUpgradeRequestDto(UpgradeRequestDto upgradeRequestDto, @MappingTarget UpgradeRequest upgradeRequest);
}
