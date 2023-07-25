package media.wepg.prototype.orig.repository;

import media.wepg.prototype.orig.model.ChannelOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelOrigin, Long> {
}
