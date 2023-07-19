package media.wepg.prototype.es.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "programs")
public class Program {
    @Id
    private Long serviceId;

    @Field(type = FieldType.Text, name = "eventId")
    private String eventId;

    @Field(type = FieldType.Date, name = "eventStartDate")
    private LocalDateTime eventStartDate;

    @Field(type = FieldType.Date, name = "eventEndDate")
    private LocalDateTime eventEndDate;

    @Field(type = FieldType.Text, name = "titleName")
    private String titleName;

    @Field(type = FieldType.Text, name = "synopsisName")
    private String synopsisName;

    @Field(type = FieldType.Text, name = "directorName")
    private String directorName;

    @Field(type = FieldType.Text, name = "actorName")
    private String actorName;

    @Field(type = FieldType.Text, name = "songName")
    private String songName;

    @Field(type = FieldType.Text, name = "singerName")
    private String singerName;

    @Field(type = FieldType.Text, name = "providerName")
    private String providerName;

    @Field(type = FieldType.Date, name = "insertDate")
    private LocalDateTime insertDate;

    @Field(type = FieldType.Date, name = "updateDate")
    private LocalDateTime updateDate;
}
