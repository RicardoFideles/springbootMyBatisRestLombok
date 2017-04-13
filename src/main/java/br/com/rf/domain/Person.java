package br.com.rf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {

	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
}
