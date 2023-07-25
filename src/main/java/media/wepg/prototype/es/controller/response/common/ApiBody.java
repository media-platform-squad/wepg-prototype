package media.wepg.prototype.es.controller.response.common;

import lombok.Getter;

@Getter
public class ApiBody<T> {
    private final T data;
    private final T message;


    public ApiBody(T data, T message) {
        this.data = data;
        this.message = message;
    }
}
