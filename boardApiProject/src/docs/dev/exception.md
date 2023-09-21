## Spring 예외 처리

### Situation
서버 운영중에는 예기치 못한 여러 예외들이 발생.
이에 적절한 예외처리를 하지 못하면 결국 서버는 500Error를 발생시키게 되는데, 각 영역이 역할, 책임으로 분리되어 있는 상황에서 클라이언트는 서버의 에러에 대해서 적절한 처리를 할 수 없게 된다. 

### Task
따라서, 클라이언트에서 어떤 상황인지 인식하고 적절하게 예외를 처리하기 위해서는 서버에서 적절한 응답을 제공해야 한다.

### Action
```
Http Request -> WAS -> Filter -> DispatcherServlet -> Interceptor -> Controller(Spring MVC)
```
Spring에서는 위와 같은 방식으로 Http 요청이 전파되고 예외 발생시 서버에서는 역순으로 진행하면서 에러가 전파된다. 
이때, `@ControllerAdvice`와    `@ExceptionHandler`를 사용해서 중앙 집중화된 예외 처리를 진행할 수 있다.

```Java
@Getter
public class GlobalBaseException extends RuntimeException {

    private final ExceptionType exceptionType;

    public GlobalBaseException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
```
프로젝트 내부에서 발생하는 예외에 대해서 `RuntimeException`을 상속받아 정의하였고

```Java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalBaseException.class)
    public ResponseEntity<ExceptionResponse> handlerException(GlobalBaseException e) {

        log.error("[EXCEPTION: BASE EXCEPTION]");

        ExceptionType exceptionType = e.getExceptionType();
        GeneralExceptionResponse exceptionResponse = GeneralExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(exceptionType.status().value())
                .message(exceptionType.exceptionMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionType.status());
    }

    ... 다른 예외들 ...
```
`GlobalExceptionHandler` 클래스를 정의해서 발생할 수 있는 예외를 받아 처리하고 클라이언트에게 적절한 응답을 할 수 있게 한다.

### 결과
```json
{
    "timestamp": "...TIME...",
    "status": ...STATUS CODE...,
    "message": "...EXCEPTION MESSAGE..."
}
```
클라이언트에게 Json 형식으로 에러 정보를 응답할 수 있다.