package br.com.rf.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import br.com.rf.domain.Person;

@Mapper

public interface PersonMapper {

	@Options(useGeneratedKeys = true)
	@Insert("insert into person(firstName, lastName, age) values(#{firstName}, #{lastName}, #{age})")
	@SelectKey(keyProperty = "id", resultType = Long.class, before = false, statement = "CALL SCOPE_IDENTITY()")
	Long insert(Person person);

	@Select("select * from person")
	Collection<Person> selectAll();

	@Delete("delete from person where id = #{id}")
	void deleteById(Long id);

	@Delete("delete from person")
	void deleteAll();

	Collection<Person> search(@Param("firstName") String firstname, @Param("lastName") String lastname,
			@Param("age") Integer age);
}
