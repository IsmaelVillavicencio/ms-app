package marmoleriaapp.product.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(value = Include.NON_NULL)
public class DtoExceptionApiResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String trace;
    private String path;
    private List<DtoFieldResponseError> fieldError;
    private Object data;

    public DtoExceptionApiResponse() {
        timestamp = new Date();
    }

    public DtoExceptionApiResponse(
            HttpStatus httpStatus,
            String message, 
            String path,
            List<DtoFieldResponseError> fieldError) {
        this();

        this.status = httpStatus.value();
        this.error = httpStatus.name();
        this.message = message;
        this.path = path;
        this.fieldError = fieldError;
    }

    public DtoExceptionApiResponse(
            HttpStatus httpStatus,
            String message,
            String path,
            String trace,
            List<DtoFieldResponseError> fieldError) {
        this(
                httpStatus,
                message,
                path,
                fieldError);

        this.trace = trace;
    }

    public DtoExceptionApiResponse(
            HttpStatus httpStatus,
            String message,
            String trace,
            Object data,
            List<DtoFieldResponseError> fieldError) {
        this(
                httpStatus,
                message,
                trace,
                fieldError);

        this.data = data;
    }
}
