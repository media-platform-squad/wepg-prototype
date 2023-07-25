package media.wepg.prototype.orig.repository;

import media.wepg.prototype.orig.model.ProgramOrigin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureDataJpa
@Transactional
class ProgramOriginRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    @DisplayName("채널 Id로 프로그램을 검색한다. (11번)")
    void findByServiceId() {
        List<ProgramOrigin> programOrigins = programRepository.findAllByServiceId(11L);

        assertThat(programOrigins).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 채널 Id로 프로그램을 검색하는 경우 프로그램 정보가 없다. (9999번)")
    void findProgram_fail_not_existing_serviceId() {
        List<ProgramOrigin> programOrigins = programRepository.findAllByServiceId(999L);

        assertThat(programOrigins).isEmpty();
    }
}