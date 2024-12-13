package com.indra.marketplace.common.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El número de documento no puede ser vacio")
	@Size(min = 6, max = 10, message = "La longitud del numero de documento debe estar entre 6 y 10")
	@Column(unique = true, name = "number_document", nullable = false, length = 10)
	private String numberDocument;

	@NotEmpty(message = "El nombre no puede ser vacio")
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty(message = "El apellido no puede ser vacio")
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NotEmpty(message = "El correo no puede estar vacio")
	@Column(unique = true, nullable = false)
	@Email(message = "No es una dirección correcta")
	private String email;	

	private boolean status;
	
	@Column(name = "creation_date")	
	private LocalDateTime creationDate;
	
	@PrePersist
    protected void onCreate() {
        this.status = true;
        this.creationDate =  LocalDateTime.now(); 
    }

}
