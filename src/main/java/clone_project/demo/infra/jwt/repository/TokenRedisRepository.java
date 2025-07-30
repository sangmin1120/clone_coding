package clone_project.demo.infra.jwt.repository;


import org.springframework.data.repository.CrudRepository;


public interface TokenRedisRepository extends CrudRepository<TokenRedis, String> {
}
