package media.wepg.prototype.orig.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "IEIF_COMPO_INFO")
public class Program {
    @Id
    @Column(name = "ID_SVC", nullable = false)
    private Long serviceId;

    @Column(name = "ID_EVENT", nullable = false)
    private String eventId;

    @Column(name = "DT_EVNT_START", nullable = false)
    private LocalDateTime eventStartDate;

    @Column(name = "DT_EVNT_END")
    private LocalDateTime eventEndDate;

    @Column(name = "NM_TITLE")
    private String titleName;

    @Column(name = "NM_SYNOP")
    private String synopsisName;

    @Column(name = "NM_DIRECTOR")
    private String directorName;

    @Column(name = "NM_ACT")
    private String actorName;

    @Column(name = "NM_SONG")
    private String songName;

    @Column(name = "NM_SINGER")
    private String singerName;

    @Column(name = "NM_PROVIDER")
    private String providerName;

    @Column(name = "DT_INSERT")
    private LocalDateTime insertDate;

    @Column(name = "DT_UPDATE")
    private LocalDateTime updateDate;
}
