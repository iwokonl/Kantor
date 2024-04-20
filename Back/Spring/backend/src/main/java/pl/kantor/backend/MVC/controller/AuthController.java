package pl.kantor.backend.MVC.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.kantor.backend.MVC.config.UserAuthProvider;
import pl.kantor.backend.MVC.dto.CredentialsDto;
import pl.kantor.backend.MVC.dto.SignUpDto;
import pl.kantor.backend.MVC.dto.UserDto;
import pl.kantor.backend.MVC.service.UserService;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
@Tag(name = "Autoryzacja i informacje o użytkowniku")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserAuthProvider userAuthProvider;
    private final UserService userService;


    @Operation(
            description = "Pobranie informacji o użytkowniku",
            summary = "Pobranie informacji o użytkowniku",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    schema = @Schema(),
                                    examples = {
                                            @ExampleObject(
                                                    name = "SuccessResponseExample",
                                                    value = "{\n" +
                                                            "    \"firstName\": \"Iwo\",\n" +
                                                            "    \"lastName\": \"Stanisławski\",\n" +
                                                            "    \"role\": \"null\",\n" +
                                                            "    \"id\": \"1\",\n" +
                                                            "    \"email\": \"iwo@iwo.pl\",\n" +
                                                            "    \"username\": \"iwo\",\n" +
                                                            "    \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmaXJzdE5hbWUiOiJJd28iLCJsYXN0TmFtZSI6IlN0YW5pc8WCYXdza2kiLCJpc3MiOiJpd28iLCJpZCI6MSwiZXhwIjoxNzEzNTc5NTkxLCJpYXQiOjE3MTM1NzU5OTEsImVtYWlsIjoiaXdvQGl3by5wbCJ9.vmEwZQ0C5zII9TjUJP1OjGyxGzuV8v4BBaRSf9j6ZOQ\"\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    schema = @Schema(),
                                    examples = {
                                            @ExampleObject(
                                                    name = "SuccessResponseExample",
                                                    value = "{\n" +
                                                            "    \"timestamp\": 1713576398513,\n" +
                                                            "    \"status\": 403,\n" +
                                                            "    \"error\": \"Forbidden\",\n" +
                                                            "    \"message\": \"Access Denied\",\n" +
                                                            "    \"path\": \"/api/authorization/userinfo\"\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    schema = @Schema(),
                                    examples = {
                                            @ExampleObject(
                                                    name = "SuccessResponseExample",
                                                    value = "{\n" +
                                                            "    \"timestamp\": 1713576242463,\n" +
                                                            "    \"status\": 500,\n" +
                                                            "    \"error\": \"Internal Server Error\",\n" +
                                                            "    \"trace\": \"pl.kantor.backend.MVC.exeption.AppExeption: Does not apply to token\\r\\n\\tat pl.kantor.backend.MVC.config.UserAuthProvider.validateToken(UserAuthProvider.java:91)\\r\\n\\tat pl.kantor.backend.MVC.config.JwtAuthFilter.doFilterInternal(JwtAuthFilter.java:32)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:227)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:107)\\r\\n\\tat org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:93)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:227)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:227)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)\\r\\n\\tat org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:227)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:82)\\r\\n\\tat org.springframework.security.web.context.SecurityContextHolderFilter.doFilter(SecurityContextHolderFilter.java:69)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:227)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:62)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:227)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.security.web.session.DisableEncodeUrlFilter.doFilterInternal(DisableEncodeUrlFilter.java:42)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.wrapFilter(ObservationFilterChainDecorator.java:240)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$AroundFilterObservation$SimpleAroundFilterObservation.lambda$wrap$0(ObservationFilterChainDecorator.java:323)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$ObservationFilter.doFilter(ObservationFilterChainDecorator.java:224)\\r\\n\\tat org.springframework.security.web.ObservationFilterChainDecorator$VirtualFilterChain.doFilter(ObservationFilterChainDecorator.java:137)\\r\\n\\tat org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:233)\\r\\n\\tat org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:191)\\r\\n\\tat org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:113)\\r\\n\\tat org.springframework.web.servlet.handler.HandlerMappingIntrospector.lambda$createCacheFilter$3(HandlerMappingIntrospector.java:195)\\r\\n\\tat org.springframework.web.filter.CompositeFilter$VirtualFilterChain.doFilter(CompositeFilter.java:113)\\r\\n\\tat org.springframework.web.filter.CompositeFilter.doFilter(CompositeFilter.java:74)\\r\\n\\tat org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration$CompositeFilterChainProxy.doFilter(WebMvcSecurityConfiguration.java:230)\\r\\n\\tat org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:352)\\r\\n\\tat org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:268)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\\r\\n\\tat org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\\r\\n\\tat org.springframework.web.filter.ServerHttpObservationFilter.doFilterInternal(ServerHttpObservationFilter.java:109)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\\r\\n\\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\\r\\n\\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\\r\\n\\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\\r\\n\\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167)\\r\\n\\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\\r\\n\\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:482)\\r\\n\\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)\\r\\n\\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\\r\\n\\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\\r\\n\\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344)\\r\\n\\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:391)\\r\\n\\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\\r\\n\\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:896)\\r\\n\\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1744)\\r\\n\\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\\r\\n\\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\\r\\n\\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\\r\\n\\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63)\\r\\n\\tat java.base/java.lang.Thread.run(Thread.java:1570)\\r\\n\",\n" +
                                                            "    \"message\": \"Does not apply to token\",\n" +
                                                            "    \"path\": \"/api/authorization/userinfo\"\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
            }
    )
    @PostMapping("/userinfo")
    public ResponseEntity<Map<String,String>> currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        Map<String,String> response = userService.jwtInfo(currentUserName);
        return ResponseEntity.ok(response);
    }


    @Operation(
            description = "Logowanie użytkownika",
            summary = "Logowanie użytkownika",
            responses = {

                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    schema = @Schema(),
                                    examples = {
                                            @ExampleObject(
                                                    name = "SuccessResponseExample",
                                                    value = "{\n" +
                                                            "    \"id\": 1,\n" +
                                                            "    \"role\": \"USER\",\n" +
                                                            "    \"username\": \"iwo\",\n" +
                                                            "    \"firstName\": \"Iwo\",\n" +
                                                            "    \"lastName\": \"Stanisławski\",\n" +
                                                            "    \"email\": \"iwo@iwo.pl\",\n" +
                                                            "    \"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmaXJzdE5hbWUiOiJJd28iLCJsYXN0TmFtZSI6IlN0YW5pc8WCYXdza2kiLCJpc3MiOiJpd28iLCJpZCI6MSwiZXhwIjoxNzEzNTc5NTkxLCJpYXQiOjE3MTM1NzU5OTEsImVtYWlsIjoiaXdvQGl3by5wbCJ9.vmEwZQ0C5zII9TjUJP1OjGyxGzuV8v4BBaRSf9j6ZOQ\"\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    schema = @Schema(),
                                    examples = {
                                            @ExampleObject(
                                                    name = "ErrorResponseExample",
                                                    value = "{\"error\": \"Bad request\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Server error",
                            content = @Content(
                                    schema = @Schema(),
                                    examples = {
                                            @ExampleObject(
                                                    name = "ErrorResponseExample",
                                                    value = "{\"error\": \"Server error\"}"
                                            )
                                    }
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = CredentialsDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Logowanie użytkownika - Iwo Stanisławski",
                                            value = "{\"username\": \"iwo\", \"password\": \"iwo\"}"
                                    ),
                            }
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto coridentialsDto) {
//
        UserDto user = userService.login(coridentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }


    @Operation(
            description = "Rejestracja użytkownika",
            summary = "Rejestracja użytkownika",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pomyślnie zarejestrowano użytkownika"),
                    @ApiResponse(responseCode = "400", description = "Nieprawidłowe dane rejestracji"),
                    @ApiResponse(responseCode = "500", description = "Błąd serwera")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = SignUpDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Rejestracja użytkownika - Iwo Stanisławski",
                                            value = "{\"firstName\": \"Iwo\", \"lastName\": \"Stanisławski\", \"email\": \"iwo@iwo.pl\", \"username\": \"iwo\", \"password\": \"iwo\"}"
                                    )
                            }
                    )
            )
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

}