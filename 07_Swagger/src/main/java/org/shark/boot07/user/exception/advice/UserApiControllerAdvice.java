package org.shark.boot07.user.exception.advice;

import org.shark.boot07.user.controller.UserApiController;
import org.shark.boot07.user.exception.ApiUserErrorResponseDTO;
import org.shark.boot07.user.exception.UserNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 응답코드 | 의미 및 세부 코드
 * 400     Bad Request : 잘못된 요청
 * 404     Not Found : 해당 정보 없음
 * 
 * */


@RestControllerAdvice(assignableTypes = {UserApiController.class})
public class UserApiControllerAdvice {
	
//  @ExceptionHandler(UserNotFoundException.class)
//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  public ErrorResponseDTO handleUserNotFound(UserNotFoundException ex) {
//      return ErrorResponseDTO.builder().
//      		status(404).
//      		errorMessage(ex.getMessage())
//      		.build();
//  }
	
	
  // 전달된 회원번호(uid)를 가진 회원이 존재하지 않는 경우
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleUserNotFoundException2 (UserNotFoundException ex) {
	  String errorDetailMessage = null;
	  switch (ex.getErrorCode()) {
	  case 1 : errorDetailMessage = "회원 정보를 조회할 수 없습니다."; break;
	  case 2 : errorDetailMessage = "회원 정보를 수정할 수 없습니다."; break;
	  case 3 : errorDetailMessage = "회원 정보를 삭제할 수 없습니다."; break;
	  }
    	ApiUserErrorResponseDTO errorDTO =  ApiUserErrorResponseDTO.builder()
       		.errorCode("UE-100")
       		.errorDetailMessage(errorDetailMessage)
       		.build();
    	return ResponseEntity.status(404).body(errorDTO);        		
  }
	    

  // 필수 값 누락, 입력 가능한 크기 벗어남, 잘못된 형식의 아이디(이메일)가 입력된 경우
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	  String errorCode = null;
	  String errorMessage = null;
	  String errorDetailMessage = null;
	  BindingResult bindingResult = ex.getBindingResult();
	  
	  if (bindingResult.hasErrors()) {
		  switch(bindingResult.getFieldError().getCode()) {
		  case "NotBlank":
			  errorCode = "20"; errorMessage = "필수 값 누락";
			  break;
		  case "Size":
			  errorCode = "21"; errorMessage = "입력 가능한 크기 벗어남";
			  break;
		  case "Pattern":
			  errorCode = "22"; errorMessage = "잘못된 형식의 아이디(이메일) 입력";
			  break;
		 
		  }
		  errorDetailMessage = bindingResult.getFieldError().getDefaultMessage();
	  }
	  
	  ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
			  .errorCode(errorCode)
			  .errorMessage(errorMessage)
			  .errorDetailMessage(errorDetailMessage)
			  .build();
	  return ResponseEntity.badRequest().body(errorDTO);
  }
  
  
  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleDuplicateKeyException(DuplicateKeyException ex) {
	  String errorCode = "10";
	  String errorMessage = "중복된 값";
	  String errorDetailMessage = "중복된 username";

	 
	  ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
			  .errorCode(errorCode)
			  .errorMessage(errorMessage)
			  .errorDetailMessage(errorDetailMessage)
			  .build();
	  return ResponseEntity.badRequest().body(errorDTO);
	  
  }	

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
	  String errorCode = null;
	  String errorMessage = null;
	  String errorDetailMessage = null;
	  
	  ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
			  .errorCode(errorCode)
			  .errorMessage(errorMessage)
			  .errorDetailMessage(errorDetailMessage)
			  .build();
	  return ResponseEntity.badRequest().body(errorDTO);
  }
  
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleNoResourceFoundException(NoResourceFoundException ex) {
	  String errorCode = "30";
	  String errorMessage = "경로변수 누락";
	  String errorDetailMessage = "사용자 수정시 경로변수 누락";
	  
	  ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
			  .errorCode(errorCode)
			  .errorMessage(errorMessage)
			  .errorDetailMessage(errorDetailMessage)
			  .build();
	  return ResponseEntity.badRequest().body(errorDTO);
  }
  
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
	  String errorCode = "31";
	  String errorMessage = "경로변수 타입 오류";
	  String errorDetailMessage = "사용자 정보를 수정하기 위한 식별값이 정수값이어야 합니다.";
	  
	  ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
			  .errorCode(errorCode)
			  .errorMessage(errorMessage)
			  .errorDetailMessage(errorDetailMessage)
			  .build();
	  return ResponseEntity.badRequest().body(errorDTO);

	  
  }
  
  // 정렬 시 sort 파라미터에 ASC/DESC 이외의 값이 젖달되는 경우
  @ExceptionHandler(BadSqlGrammarException.class)
  public ResponseEntity<ApiUserErrorResponseDTO> BadSqlGrammarException(BadSqlGrammarException ex) {
	  String errorCode = "42";
	  String errorMessage = "요청 파라미터 값 오류";
	  String errorDetailMessage = "페이징 처리시 sort 값에 ASC 또는 DESC중 하나 입니다";
	  
	  ApiUserErrorResponseDTO errorDTO = ApiUserErrorResponseDTO.builder()
			  .errorCode(errorCode)
			  .errorMessage(errorMessage)
			  .errorDetailMessage(errorDetailMessage)
			  .build();
	  return ResponseEntity.badRequest().body(errorDTO);

	  
  }
  
  
  
  
}
