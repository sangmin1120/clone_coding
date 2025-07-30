package clone_project.demo.infra.jwt.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "tokenRedis", timeToLive = 3600 * 24 * 14)
@AllArgsConstructor
@Data
public class TokenRedis {
    @Id // jakarta 가 아닌, springframework 사용해야 된다.
    private String email;
    private String refreshToken;
}
