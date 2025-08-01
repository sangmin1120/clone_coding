package clone_project.demo.domain.member.mapper;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Member findByEmail(String email);
}
