package br.com.macrosxtreme.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@MappedSuperclass
public abstract class Entityy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

}
