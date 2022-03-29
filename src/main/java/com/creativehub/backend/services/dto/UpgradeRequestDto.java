package com.creativehub.backend.services.dto;

import com.creativehub.backend.models.enums.UpgradeRequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UpgradeRequestDto implements Serializable {
    private final Long id;
    private final UserDto user;
    private final String name;
    private final String surname;
    private final String bio;
    private final String portfolio;
    private final String motivationalText;
    private final String artName;
    private final Date birthDate;
    private final String username;
    private final String avatar;
    private final UpgradeRequestStatus status;
}
