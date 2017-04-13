package br.com.rf.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import br.com.rf.domain.Person;

@Mapper

public interface PersonMapper {

	@Options(useGeneratedKeys = true)
	@Insert("insert into person(firstName, lastName, age) values(#{firstName}, #{lastName}, #{age})")
	void insert(Person person);

	@Select("select * from person")
	Collection<Person> selectAll();

	@Delete("delete from person where id = #{id}")
	void deleteById(long id);

	Collection<Person> search(@Param("firstName") String firstname, @Param("lastName") String lastname,
			@Param("age") Integer age);
}
