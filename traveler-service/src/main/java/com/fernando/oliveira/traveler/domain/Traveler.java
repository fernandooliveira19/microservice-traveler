package com.fernando.oliveira.traveler.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fernando.oliveira.traveler.domain.enums.Status;
import com.fernando.oliveira.traveler.dto.TravelerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="traveler")
@Table(name="traveler" )
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traveler implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="DOCUMENT")
	private String document;

	@Column(name="STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToOne(mappedBy="traveler", cascade=CascadeType.ALL)
	private Phone phone;
	
	
	public TravelerDTO convertToDTO() {
		TravelerDTO travelerDTO = TravelerDTO.builder()
									.id(id)					
									.name(name)
									.email(email)
									.document(document)
									.status(convertStatus(status))
									.prefixPhone(phone.getPrefix())
									.numberPhone(phone.getNumber())
									.build();
		return travelerDTO;
	}

	private String convertStatus(Status status){
		if(status != null){
			return status.getCode();
		}
		return Status.ACTIVE.getCode();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Traveler other = (Traveler) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

	
	
}
