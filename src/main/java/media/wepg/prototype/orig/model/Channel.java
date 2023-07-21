package media.wepg.prototype.orig.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "IEIF_CHANNEL_INFO")
public class Channel {
    @Id
    @Column(name = "ID_SVC", nullable = false)
    private Long id;

    @Column(name = "NM_CH")
    private String channelName;

    @Column(name = "TP_SVC")
    private Long serviceType;

    @Column(name = "NM_PROVIDER")
    private String providerName;

    @Column(name = "NO_CH")
    private Long channelNumber;

    @Column(name = "DT_INSERT")
    private LocalDateTime insertDate;

    @Column(name = "DT_UPDATE")
    private LocalDateTime updateDate;
}
