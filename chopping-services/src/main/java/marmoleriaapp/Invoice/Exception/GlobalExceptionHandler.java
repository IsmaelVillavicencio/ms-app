package marmoleriaapp.Invoice.Exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import marmoleriaapp.Invoice.Dto.DtoExceptionApiResponse;
import marmoleriaapp.Invoice.Dto.DtoFieldResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<DtoExceptionApiResponse> handleNoResourceFoundException(NoResourceFoundException e,
			WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		String path = request.getDescription(false);

		return new ResponseEntity<>(
				new DtoExceptionApiResponse(
						status,
						"No se ha encontrado ningún recurso HTTP que coincida con la URI de la solicitud",
						path,
						null),
				status);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<DtoExceptionApiResponse> handleResourceNotFoundException(ResourceNotFoundException e,
			WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		String path = request.getDescription(false);

		return new ResponseEntity<>(
				new DtoExceptionApiResponse(
						status,
						e.getMessage(),
						path,
						null),
				status);
	}

	@ExceptionHandler(ResponseStatusException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<DtoExceptionApiResponse> handleMethodArgumentNotValid(ResponseStatusException e,
			WebRequest request) {
		
				HttpStatus status = HttpStatus.BAD_REQUEST; // 400
		String path = request.getDescription(false);
		String reason = e.getReason();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonReason = null;
		List<DtoFieldResponseError> fieldErrorList = null;
		try {
			jsonReason = mapper.readTree(reason);
			fieldErrorList = mapper.readValue(reason, mapper.getTypeFactory()
					.constructCollectionType(List.class, DtoFieldResponseError.class));
		} catch (Exception ex) {
			jsonReason = null;
		}

		if (jsonReason != null) {
			return new ResponseEntity<DtoExceptionApiResponse>(
					new DtoExceptionApiResponse(
							status,
							"Error de validación de campos",
							path,
							null,
							fieldErrorList),
					status);
		} else {
			return new ResponseEntity<DtoExceptionApiResponse>(
					new DtoExceptionApiResponse(
							status,
							e.getMessage(),
							path,
							null,
							null),
					status);
		}

	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<DtoExceptionApiResponse> handleAccessDeniedException(AccessDeniedException e,
			WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN; // 403
		String path = request.getDescription(false);

		return new ResponseEntity<>(
				new DtoExceptionApiResponse(
						status,
						e.getMessage(),
						path,
						null),
				status);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<DtoExceptionApiResponse> handleAllExceptions(Exception e, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
		String path = request.getDescription(false);

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stackTrace = sw.toString();

		return new ResponseEntity<>(
				new DtoExceptionApiResponse(
						status,
						e.getMessage(),
						path,
						stackTrace,
						null),
				status);
	}

}
