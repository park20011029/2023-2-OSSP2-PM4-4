package project.manager.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.manager.server.domain.region.Gu;
import project.manager.server.domain.region.Si;
import project.manager.server.dto.reponse.region.GuDto;
import project.manager.server.dto.reponse.region.SiDto;
import project.manager.server.exception.ApiException;
import project.manager.server.exception.ErrorDefine;
import project.manager.server.repository.region.GuRepository;
import project.manager.server.repository.region.SiRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {
    private final GuRepository guRepository;
    private final SiRepository siRepository;

    public List<SiDto> readSiList() {
        List<Si> siList = siRepository.findAll();
        List<SiDto> siDtoList = new ArrayList<>();

        for (Si si : siList) {
            siDtoList.add(SiDto.builder().si(si.getSi()).siId(si.getId()).build());
        }

        return siDtoList;
    }

    public List<GuDto> readGuList(Long siId) {
        List<Gu> guList = guRepository.findGuBySi_Id(siId).orElseThrow(() -> new ApiException(ErrorDefine.ENTITY_NOT_FOUND));
        List<GuDto> guDtoList = new ArrayList<>();

        for (Gu gu : guList) {
            guDtoList.add(GuDto.builder().gu(gu).build());
        }

        return guDtoList;
    }


}
