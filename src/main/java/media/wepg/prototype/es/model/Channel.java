package media.wepg.prototype.es.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;

@Document(indexName = "prototype-channels")
@Setting(replicas = 2)
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {

    @Field(type = FieldType.Text, name = "channelName")
    private Long id;

    @Field(type = FieldType.Text, name = "channelName")
    private String channelName;

    @Field(type = FieldType.Long, name = "serviceType")
    private Long serviceType;

    @Field(type = FieldType.Text, name = "providerName")
    private String providerName;

    @Field(type = FieldType.Long, name = "channelNumber")
    private Long channelNumber;

    @Field(type = FieldType.Date, name = "insertDate")
    private LocalDateTime insertDate;

    @Field(type = FieldType.Date, name = "updateDate")
    private LocalDateTime updateDate;

}
