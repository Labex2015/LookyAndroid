package labex.feevale.br.looky.model;

public class ServiceError {

    private Long timestamp;
    private Integer status;
    private String exception;
    private String message;
    private String path;

    public ServiceError() {}

    public ServiceError(Long timestamp, Integer status, String exception, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.exception = exception;
        this.message = message;
        this.path = path;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
