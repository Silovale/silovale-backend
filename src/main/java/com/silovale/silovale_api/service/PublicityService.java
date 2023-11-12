package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.Publicity;
import com.silovale.silovale_api.model.PublicityDTO;
import com.silovale.silovale_api.repos.PublicityRepository;
import com.silovale.silovale_api.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicityService {
    private final PublicityRepository publicityRepository;

    public PublicityService(final PublicityRepository publicityRepository){
        this.publicityRepository = publicityRepository;
    }

    public List<PublicityDTO> findAll(){
        final List<Publicity> publicities = publicityRepository.findAll(Sort.by("id"));
        return publicities.stream()
                .map(publicity -> mapToDTO(publicity, new PublicityDTO()))
                .toList();
    }

    public PublicityDTO get(final long id){
        return publicityRepository.findById(id)
                .map(publicity -> mapToDTO(publicity, new PublicityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PublicityDTO publicityDTO){
        final Publicity publicity = new Publicity();
        mapToEntity(publicityDTO, publicity);
        return publicityRepository.save(publicity).getId();
    }

    public void update(final Long id, final PublicityDTO publicityDTO){
        final Publicity publicity = publicityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(publicityDTO, publicity);
        publicityRepository.save(publicity);
    }

    public void delete(final Long id){
        publicityRepository.deleteById(id);
    }
    

    private PublicityDTO mapToDTO(final Publicity publicity, final PublicityDTO publicityDTO){
        publicityDTO.setNamePublicity(publicity.getNamePublicity());
        publicityDTO.setDescriptionPublicity(publicity.getDescriptionPublicity());
        publicityDTO.setStartDate(publicity.getstartDate());
        publicityDTO.setEndDate(publicity.getEndDate());
        return publicityDTO;
    }

    private Publicity mapToEntity(final PublicityDTO publicityDTO, final Publicity publicity){
        publicity.setNamePublicity(publicityDTO.getNamePublicity());
        publicity.setDescriptionPublicity(publicityDTO.getDescriptionPublicity());
        publicity.setStartDate(publicityDTO.getstartDate());
        publicity.setEndDate(publicityDTO.getEndDate());
        return publicity;
    }
}