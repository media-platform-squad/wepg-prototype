package media.wepg.prototype.orig.repository;

import media.wepg.prototype.orig.model.Program;
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
class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;

    @Test
    @DisplayName("채널 Id로 프로그램을 검색한다. (11번)")
    void findByServiceId() {
        List<Program> programs = programRepository.findAllByServiceId(11L);

        assertThat(programs).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 채널 Id로 프로그램을 검색하는 경우 프로그램 정보가 없다. (9999번)")
    void findProgram_fail_not_existing_serviceId() {
        List<Program> programs = programRepository.findAllByServiceId(999L);

        assertThat(programs).isEmpty();
    }
}