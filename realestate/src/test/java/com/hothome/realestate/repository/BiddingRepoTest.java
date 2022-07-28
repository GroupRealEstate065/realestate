package com.hothome.realestate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hothome.model.BiddingEntity;
import com.hothome.repository.BiddingRepository;

@DataJpaTest(showSql = true)
@Rollback(value = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BiddingRepoTest {

	@Autowired
	private BiddingRepository repository;
	
	@Test
	public void testSave() {
		BiddingEntity entity = new BiddingEntity();
		entity.setPrice(500.00);
		entity.setAprroved(false);
		
		BiddingEntity savedEntity = this.repository.save(entity);
		assertThat(savedEntity.getId() > 0);
	}
	
	@Test
	public void testFindById() {
		Optional<BiddingEntity> entity = this.repository.findById(3L);
		assertThat(entity.get() != null);
	}
	
	@Test
	public void testFindAll() {
		List<BiddingEntity> list = (List<BiddingEntity>) this.repository.findAll();
		assertThat(list.size() > 0);
	}
}
