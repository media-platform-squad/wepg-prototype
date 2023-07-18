package media.wepg.prototype.orig.service;

import media.wepg.prototype.orig.model.Program;
import media.wepg.prototype.orig.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {
    private final ProgramRepository programRepository;

    @Autowired
    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> getProgramsByServiceId(Long serviceId){
       return programRepository.findByServiceId(serviceId);
    }
}
