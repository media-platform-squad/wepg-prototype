package media.wepg.prototype.repository;

import media.wepg.prototype.model.ChannelOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelOrigin, Long> {
}
