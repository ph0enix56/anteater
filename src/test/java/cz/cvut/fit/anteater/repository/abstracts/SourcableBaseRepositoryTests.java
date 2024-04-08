package cz.cvut.fit.anteater.repository.abstracts;

import cz.cvut.fit.anteater.model.entity.SourceableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SourcableBaseRepositoryTests {

	@Mock
	private MongoTemplate mongoTemplate;

	private SourcableBaseRepository<SourceableEntity> repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		repository = new SourcableBaseRepository<SourceableEntity>(mongoTemplate, SourceableEntity.class) {};
	}

	@Test
	public void testCount() {
		when(mongoTemplate.count(any(Query.class), eq(SourceableEntity.class))).thenReturn(10L);
		long count = repository.count();
		assertEquals(10L, count);
		verify(mongoTemplate, times(1)).count(any(Query.class), eq(SourceableEntity.class));
	}

	@Test
	public void testFindAll() {
		when(mongoTemplate.findAll(SourceableEntity.class)).thenReturn(Collections.emptyList());
		List<SourceableEntity> entities = repository.findAll();
		assertNotNull(entities);
		verify(mongoTemplate, times(1)).findAll(SourceableEntity.class);
	}
}