package clone_project.demo.domain.member.mapper;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MemberMapper {
    void save(MemberDto.Signup member);
    Member findByAccountId(String accountId);
}
