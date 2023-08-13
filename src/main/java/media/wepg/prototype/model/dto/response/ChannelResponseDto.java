package media.wepg.prototype.model.dto.response;

import lombok.Data;
import media.wepg.prototype.model.es.Channel;

import java.time.LocalDateTime;

@Data
public class ChannelResponseDto {

    private Long id;
    private String channelName;
    private Long serviceType;
    private String providerName;
    private Long channelNumber;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    public ChannelResponseDto(Channel channel) {
        this.id = channel.getId();
        this.channelName = channel.getChannelName();
        this.serviceType = channel.getServiceType();
        this.providerName = channel.getProviderName();
        this.insertDate = channel.getInsertDate();
        this.updateDate = channel.getUpdateDate();
    }

}
