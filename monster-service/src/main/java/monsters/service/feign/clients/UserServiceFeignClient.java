package monsters.service.feign.clients;

import monsters.dto.answer.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient(name = "user-service", url = "http://localhost:8081/")
public interface UserServiceFeignClient {
    @GetMapping("/users-service/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<UserResponseDTO> findById(@PathVariable UUID id);
}
