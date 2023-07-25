package media.wepg.prototype.es.controller.response.common;

import lombok.Getter;

@Getter
public class ApiHeader {
    private final int resultCode;
    private final String codeName;


    public ApiHeader(int resultCode, String codeName) {
        this.resultCode = resultCode;
        this.codeName = codeName;
    }
}
