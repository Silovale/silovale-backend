package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.Business;
import com.silovale.silovale_api.domain.User;
import com.silovale.silovale_api.model.BusinessDTO;
import com.silovale.silovale_api.repos.BusinessRepository;
import com.silovale.silovale_api.repos.UserRepository;
import com.silovale.silovale_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    public BusinessService(final BusinessRepository businessRepository,
            final UserRepository userRepository) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
    }

    public List<BusinessDTO> findAll() {
        final List<Business> businesses = businessRepository.findAll(Sort.by("id"));
        return businesses.stream()
                .map(business -> mapToDTO(business, new BusinessDTO()))
                .toList();
    }

    public BusinessDTO get(final Long id) {
        return businessRepository.findById(id)
                .map(business -> mapToDTO(business, new BusinessDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BusinessDTO businessDTO) {
        final Business business = new Business();
        mapToEntity(businessDTO, business);
        return businessRepository.save(business).getId();
    }

    public void update(final Long id, final BusinessDTO businessDTO) {
        final Business business = businessRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(businessDTO, business);
        businessRepository.save(business);
    }

    public void delete(final Long id) {
        businessRepository.deleteById(id);
    }

    private BusinessDTO mapToDTO(final Business business, final BusinessDTO businessDTO) {
        businessDTO.setId(business.getId());
        businessDTO.setBusinessName(business.getBusinessName());
        businessDTO.setDescription(business.getDescription());
        businessDTO.setAddress(business.getAddress());
        businessDTO.setPhone(business.getPhone());
        businessDTO.setUserId(business.getUserId() == null ? null : business.getUserId().getId());
        return businessDTO;
    }

    private Business mapToEntity(final BusinessDTO businessDTO, final Business business) {
        business.setBusinessName(businessDTO.getBusinessName());
        business.setDescription(businessDTO.getDescription());
        business.setAddress(businessDTO.getAddress());
        business.setPhone(businessDTO.getPhone());
        final User userId = businessDTO.getUserId() == null ? null : userRepository.findById(businessDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("userId not found"));
        business.setUserId(userId);
        return business;
    }

}
