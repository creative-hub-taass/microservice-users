package com.creativehub.backend.services.impl;

import com.creativehub.backend.models.UpgradeRequest;
import com.creativehub.backend.models.enums.UpgradeRequestStatus;
import com.creativehub.backend.repositories.UpgradeRequestRepository;
import com.creativehub.backend.services.UpgradeRequestManager;
import com.creativehub.backend.services.dto.UpgradeRequestDto;
import com.creativehub.backend.services.mapper.UpgradeRequestMapper;
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

    public UpgradeRequestDto addRequest(UpgradeRequest ur) {
        ur.setStatus(UpgradeRequestStatus.OPEN); //FIXME
        return upgradeRequestMapper.upgradeRequestToUpgradeRequestDto(upgradeRequestRepository.save(ur));
    }

    @Override
    public Optional<UpgradeRequestDto> findById(long id) {
        return upgradeRequestRepository.findById(id).map(upgradeRequestMapper::upgradeRequestToUpgradeRequestDto);
    }

    public boolean existsById(Long id) {
        return upgradeRequestRepository.existsById(id);
    }

    // TODO
    @Override
    public UpgradeRequestDto findByUserId(long id) {
        return upgradeRequestMapper.upgradeRequestToUpgradeRequestDto(upgradeRequestRepository.findByUserId(id));
        //return Optional.empty();
    }

    public List<UpgradeRequestDto> findAll() {
        return upgradeRequestRepository.findAll().stream().map(upgradeRequestMapper::upgradeRequestToUpgradeRequestDto).collect(Collectors.toList());
    }

    @Override
    public void acceptRequest(long id) throws Exception {
        if (existsById(id)) {
            upgradeRequestRepository.updateRequestStatus(id, UpgradeRequestStatus.ACCEPTED);
        } else {
            throw new Exception("UPGRADE REQUEST NOT FOUND");
        }
        // TODO: controllare che ci sia la richiesta
        // TODO: cambiare lo status della richiesta in accepted
        // cambiare l'utente per farlo diventare creator e asscoiare i vari dettagli allegati alla richiesta

    }

    @Override
    public void rejectRequest(long id) {
        upgradeRequestRepository.updateRequestStatus(id, UpgradeRequestStatus.REJECTED);
    }
}
