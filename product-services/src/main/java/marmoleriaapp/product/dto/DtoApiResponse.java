package marmoleriaapp.product.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class DtoApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status;
    private T data;
}
