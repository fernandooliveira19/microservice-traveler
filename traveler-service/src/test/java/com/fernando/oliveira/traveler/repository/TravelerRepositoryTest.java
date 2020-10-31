package com.fernando.oliveira.traveler.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fernando.oliveira.traveler.domain.Traveler;
import org.springframework.test.context.web.WebAppConfiguration;

@Sql(value="classpath:load-db.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value="classpath:clear-db.sql", executionPhase=Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
@EnableJpaRepositories(basePackages = "com.fernando.oliveira.traveler")
@SpringBootTest
@ActiveProfiles("test")
public class TravelerRepositoryTest {
	
	private static final String TRAVELER_NAME= "TRAVELER 01";
	private static final String PART_TRAVELER_NAME= "TRAVELER";
	private static final String NOT_FOUND_NAME= "XPTO";
	
	@Autowired
	private TravelerRepository travelerRepository; 
	
	@Test
	public void shouldReturnTravelerByName() {
		
		Optional<Traveler> result = travelerRepository.findByName(TRAVELER_NAME);
		
		Assertions.assertThat(result.isPresent());
		
	}
	
	@Test
	public void shouldNotReturnTravelerByName() {
		
		Optional<Traveler> result = travelerRepository.findByName(NOT_FOUND_NAME);
		
		Assertions.assertThat(result.isPresent()).isFalse();
		
	}
	
	@Test
	public void shouldReturnTravelerByPartOfName() {
		
		Pageable pageable = PageRequest.of(1, 5);
		Page<Traveler> result = travelerRepository.findByNameContainingOrderByNameAsc(PART_TRAVELER_NAME, pageable);
		
		Assertions.assertThat(result.getTotalElements()).isEqualTo(4);
		
	}
	
	@Test
	public void shouldNotReturnTravelerByPartOfName() {
		String name = NOT_FOUND_NAME;
		Pageable pageable = PageRequest.of(1, 5);
		Page<Traveler> result = travelerRepository.findByNameContainingOrderByNameAsc(name, pageable);
		
		Assertions.assertThat(result.getTotalElements()).isEqualTo(0);
		
	}
	
	@Test
	public void shouldReturnTravelerListOrdenedByName() {
		
		List<Traveler> result = travelerRepository.findAllByOrderByName();
		
		Assertions.assertThat(result.size()).isEqualTo(4);
		Assertions.assertThat(result.get(0).getName()).isEqualTo("TRAVELER 01");
		Assertions.assertThat(result.get(1).getName()).isEqualTo("TRAVELER 02");
		Assertions.assertThat(result.get(2).getName()).isEqualTo("TRAVELER 03");
		Assertions.assertThat(result.get(3).getName()).isEqualTo("TRAVELER 04");

	}

	@Test
	public void shouldReturnActiveTravelerListOrdenedByName() {

		List<Traveler> result = travelerRepository.findAllActiveTravelersOrderByName();

		Assertions.assertThat(result.size()).isEqualTo(3);
		Assertions.assertThat(result.get(0).getName()).isEqualTo("TRAVELER 01");
		Assertions.assertThat(result.get(1).getName()).isEqualTo("TRAVELER 02");
		Assertions.assertThat(result.get(2).getName()).isEqualTo("TRAVELER 03");
	}
	
	@Test
	public void shouldReturnTravelerByPartOfNameNotPageable() {
		
		
		List<Traveler> result = travelerRepository.findByNameContainingOrderByNameAsc(PART_TRAVELER_NAME);
		
		Assertions.assertThat(result.size()).isEqualTo(4);
		
	}
		

}
