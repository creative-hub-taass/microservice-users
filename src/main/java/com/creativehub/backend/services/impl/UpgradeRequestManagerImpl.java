package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.Creator;
import com.creativehub.backend.models.UpgradeRequest;
import com.creativehub.backend.models.User;
import com.creativehub.backend.models.enums.UpgradeRequestStatus;
import com.creativehub.backend.repositories.UpgradeRequestRepository;
import com.creativehub.backend.repositories.UserRepository;
import com.creativehub.backend.services.UpgradeRequestManager;
import com.creativehub.backend.services.dto.UpgradeRequestDto;
import com.creativehub.backend.services.mapper.UpgradeRequestMapper;
import com.creativehub.backend.util.UpgradeRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpgradeRequestManagerImpl implements UpgradeRequestManager {
    private final UpgradeRequestRepository upgradeRequestRepository;
    private final UpgradeRequestMapper upgradeRequestMapper;
    private final UserRepository userRepository;

    public UpgradeRequestDto addRequest(UpgradeRequest ur) throws UpgradeRequestException {
        if (!userHasPendingRequests(ur.getUser().getId())) {
            ur.setStatus(UpgradeRequestStatus.OPEN);
            long userId = ur.getUser().getId();
            User user = userRepository.getById(userId);
            Creator creator = user.getCreator();
            if(creator == null) {
                return upgradeRequestMapper.upgradeRequestToUpgradeRequestDto(upgradeRequestRepository.save(ur));
            } else throw new UpgradeRequestException("User is already a creator");
        } else throw new UpgradeRequestException("Request already submitted");
    }

    @Override
    public Optional<UpgradeRequestDto> findById(long id) {
        return upgradeRequestRepository.findById(id).map(upgradeRequestMapper::upgradeRequestToUpgradeRequestDto);
    }

    public boolean existsById(Long id) {
        return upgradeRequestRepository.existsById(id);
    }

    @Override
    public List<UpgradeRequestDto> findByUserId(long id) {
        return upgradeRequestRepository.findByUserId(id).stream().map(upgradeRequestMapper::upgradeRequestToUpgradeRequestDto).collect(Collectors.toList());
    }

    public List<UpgradeRequestDto> findAll() {
        return upgradeRequestRepository.findAll().stream().map(upgradeRequestMapper::upgradeRequestToUpgradeRequestDto).collect(Collectors.toList());
    }

    @Override
    public void acceptRequest(long id) throws UpgradeRequestException {
        if (existsById(id)) {
            UpgradeRequest request = upgradeRequestRepository.getById(id);
            if (request.getStatus().toString().equals("OPEN")) {
                Creator creator = new Creator();
                creator.setName(request.getName());
                creator.setSurname(request.getSurname());
                creator.setBirthDate(request.getBirthDate());
                creator.setAvatar(request.getAvatar());
                creator.setCreatorType(request.getCreatorType());
                creator.setBio(request.getBio());
                long userId = request.getUser().getId();
                User user = userRepository.getById(userId);
                user.setCreator(creator);
                user.setUsername(request.getUsername());
                user.setNickname(request.getArtName());
                userRepository.save(user);
                upgradeRequestRepository.updateRequestStatus(id, UpgradeRequestStatus.ACCEPTED);
            } else { throw new UpgradeRequestException("Request already accepted/rejected.");}
        } else {throw new UpgradeRequestException("Request not found");}
    }

    @Override
    public void rejectRequest(long id) throws UpgradeRequestException {
        if (existsById(id)) {
            UpgradeRequest request = upgradeRequestRepository.getById(id);
            if ((request.getStatus().toString().equals("OPEN"))) {
                upgradeRequestRepository.updateRequestStatus(id, UpgradeRequestStatus.REJECTED);
            } else {throw  new UpgradeRequestException("Request already accepted/rejected");}
        } else {throw new UpgradeRequestException("Request not found");}
    }

    public boolean userHasPendingRequests(long id) {
        return !upgradeRequestRepository.userPendingRequests(id).isEmpty();
    }
}
