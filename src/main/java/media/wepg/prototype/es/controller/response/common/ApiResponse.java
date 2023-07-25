package media.wepg.prototype.es.controller.response.common;

import lombok.*;

@Data
@Builder
@Getter
public class ApiResponse<T> {

   private ApiHeader header;
   private ApiBody<Object> body;

   public ApiResponse(ApiHeader header, ApiBody<Object> body) {
      this.header = header;
      this.body = body;
   }

   public ApiResponse(ApiHeader header){
      this.header = header;
   }


   public static <T> ApiResponse<T> ok(Object data){
      return new ApiResponse<>(
              new ApiHeader(StatusCode.OK, ResponseMessage.SUCCESS_MESSAGE),
              new ApiBody<>(data, null)
      );
   }

   public static <T> ApiResponse<T> ok(){
      return new ApiResponse<>(
              new ApiHeader(StatusCode.OK, ResponseMessage.SUCCESS_MESSAGE),
              new ApiBody<>(null, null)
      );
   }

   public static <T> ApiResponse<T> fail(String errorMessage){
      return new ApiResponse<>(new ApiHeader(StatusCode.NOT_FOUND, ResponseMessage.FAILED_MESSAGE),
              new ApiBody<>(null, errorMessage)
      );
   }

   public static <T> ApiResponse<T> fail(){
      return new ApiResponse<>(new ApiHeader(StatusCode.NOT_FOUND, ResponseMessage.FAILED_MESSAGE));
   }

}
