package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import lombok.Getter;





@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@ActiveProfiles("test")

@Sql(
    scripts = {
    "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
     "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
      config = @SqlConfig(encoding = "utf-8"))





class FetchJeepTest {
  
//  @Nested
//  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//
//  @ActiveProfiles("test")
//
//  @Sql(
//      scripts = {
//      "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
//       "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
//        config = @SqlConfig(encoding = "utf-8"))
//
//  class TestsThatDoNotPolluteApplicationContext {
//    
//  }
//  @Nested
//  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//
//  @ActiveProfiles("test")
//
//  @Sql(
//      scripts = {
//      "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
//       "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
//        config = @SqlConfig(encoding = "utf-8"))
//
////  class TestsThatPolluteApplicationContext {
////    @MockBean
////    private JeepSalesService jeepSalesService;
////    @Test
////    void testThatAnUnplannedExceptionResultsInA500Status() {
////      
////      //Given: a valid model, trim, and URI
////      JeepModel model = JeepModel.WRANGLER;
////      String trim = "Invalid";
////      String uri = 
////          String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
////      
////      doThrow(new RuntimeException("Ouch!")).when(jeepSalesService)
////           .fetchJeeps(model, trim);
////      
////      
////      //System.out.println(uri);
////      
////      //When: a connection is made to the URI
////      ResponseEntity<Map<String, Object>> response = 
////          getRestTemplate().exchange(uri, HttpMethod.GET, null,
////              new ParameterizedTypeReference<>() {});
////      
////      
////      
////      
////      //Then: an internal server error (500) status code is returned
////      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
////      
////      //And: an error message is returned
////      Map<String, Object> error = response.getBody();
////      assertErrorMessageValid(error, HttpStatus.INTERNAL_SERVER_ERROR);
////    }
////    
////    
////  }
  
 @Autowired
 @Getter
  private TestRestTemplate restTemplate;
  
  @LocalServerPort
  private int serverPort;
  
  
  
  

  @Test
  void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
    
    //Given: a valid model, trim, and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Sport";
    String uri = 
        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
    
    //System.out.println(uri);
    
    //When: a connection is made to the URI
    ResponseEntity<List<Jeep>> response = 
        getRestTemplate().exchange(uri, HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {});
    
    
    
    
    //Then: a success (OK - 200 ) status code is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    
    //And: the actual list returned is the same as the expected list
    List<Jeep> actual = response.getBody();
    List<Jeep> expected = buildExpected();
    
    
    actual.forEach(jeep -> jeep.setModelPK(null));
    
    assertThat(response.getBody()).isEqualTo(expected);
    
  }
//  @Test
//  void testThatAnErrorMessageIsReturnedWhenAnUnknownTrimIsSupplied() {
//    
//    //Given: a valid model, trim, and URI
//    JeepModel model = JeepModel.WRANGLER;
//    String trim = "Unknown Value";
//    String uri = String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
//    
//    System.out.println(uri);
//    
//    //When: a connection is made to the URI
//    ResponseEntity<Map<String, Object>> response = 
//        getRestTemplate().exchange(uri, HttpMethod.GET, null,
//            new ParameterizedTypeReference<>() {});
//    
//    
//    
//    
//    //Then: a not found (404) status code is returned
//    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    
//    //And: an error message is returned
//    Map<String, Object> error = response.getBody();
//    assertErrorMessageValid(error, HttpStatus.NOT_FOUND);
//  }
//  @ParameterizedTest
//  @MethodSource("package com.promineotech.jeep.controller.FetchJeepTest#parametersForIvalidInput")
//  void testThatAnErrorMessageIsReturnedWhenAnInvalidValueIsSupplied(
//      String model, String trim, String reason) {
//    
//    //Given: a valid model, trim, and URI
//    String uri = 
//        String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
//    
//    System.out.println(uri);
//    
//    //When: a connection is made to the URI
//    ResponseEntity<Map<String, Object>> response = 
//        getRestTemplate().exchange(uri, HttpMethod.GET, null,
//            new ParameterizedTypeReference<>() {});
//    
//    
//    
//    
//    //Then: a bad request (400) status code is returned
//    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    
//    //And: an error message is returned
//    Map<String, Object> error = response.getBody();
//    assertErrorMessageValid(error, HttpStatus.BAD_REQUEST);
//  }
//  
//  
//  
//  
//  /**
//   * 
//   * @param error
//   * @param status
//   */
//  
//  
//  protected void assertErrorMessageValid(Map<String, Object> error, HttpStatus status) {
//    // @Formatter:off
//    assertThat(error)
//    .containsKey("message")
//    .containsEntry("status code", status.value())
//    .containsEntry("uri", "/jeeps")
//    .containsKey("timestamp")
//    .containsEntry("reason", status.getReasonPhrase());
//    // @Formatter:on
//  } 
//  
 




  protected List<Jeep> buildExpected() {
    List<Jeep> list = new LinkedList<>();
    
    // @formatter:off
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(2)
        .wheelSize(17)
        .basePrice(new BigDecimal("28475.00"))
        .build());
    
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(4)
        .wheelSize(17)
        .basePrice(new BigDecimal("31975.00"))
        .build());
    // @formatter:on
    
    //Collections.sort(list);
    return list;
  }
 
//  static Stream<Arguments> parametersForInvalidInput() {
//    // @Formatter:off
//    return Stream.of(
//        arguments("WRANGLER", "&^$%%^$", "Trim contains non-alpha-numeric characters"),
//        arguments("WRANGLER", "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC", "Trim length is too long"),
//        arguments("INVALID", "Sport", "Model is not enum value")
//    // @Formatter:on
//        );
//  }
//  
  
}
