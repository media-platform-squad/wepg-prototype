package media.wepg.prototype.es.controller.response.common;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@Getter
public class ApiResponse<T> {

    private ApiBody<Object> body;

    public ApiResponse(ApiBody<Object> body) {
        this.body = body;
    }

    public static ResponseEntity<Object> ok(Object data) {
        return new ResponseEntity<>(
                new ApiBody<>(data, ResponseMessage.SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    public static ResponseEntity<Object> created() {
        return new ResponseEntity<>(
                new ApiBody<>(null, ResponseMessage.SUCCESS_MESSAGE),
                HttpStatus.CREATED);
    }

    public static ResponseEntity<Object> ok() {
        return new ResponseEntity<>(
                new ApiBody<>(null, ResponseMessage.SUCCESS_MESSAGE),
                HttpStatus.OK);
    }

    public static ResponseEntity<Object> notFound(String errorMessage) {
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> fail() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> fail(String errorMessage) {
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

}
