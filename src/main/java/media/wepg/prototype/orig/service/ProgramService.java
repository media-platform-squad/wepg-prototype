package media.wepg.prototype.orig.service;

import lombok.RequiredArgsConstructor;
import media.wepg.prototype.orig.model.ProgramOrigin;
import media.wepg.prototype.orig.repository.ProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
    private final ProgramRepository programRepository;

    public List<ProgramOrigin> getProgramsByServiceId(Long serviceId) {
        return programRepository.findAllByServiceId(serviceId);
    }
}
