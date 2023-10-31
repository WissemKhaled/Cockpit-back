package com.example.demo.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubcontractorServiceTests {

//	@Mock
//	private SubcontractorMapper subcontractorMapper;
//
//	@InjectMocks
//	private SubcontractorServiceImpl subcontractorService;
//
//	@BeforeEach
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//	}
//
//	@Test
//	public void testSaveSubcontractor() {
//		// Setup
//		Subcontractor subcontractor = new Subcontractor();
//		subcontractor.setSId(1);
//		subcontractor.setSName("Test Name");
//		subcontractor.setSEmail("test@example.com");
//		Status status = new Status();
//		status.setStId(1);
//		subcontractor.setStatus(status);
//
//		Mockito.doNothing().when(subcontractorMapper).insertSubcontractor(subcontractor);
//
//		// Test
//		int savedId = subcontractorService.saveSubcontractor(subcontractor);
//
//		// Assert
//		assertEquals(subcontractor.getSId(), savedId);
//	}
//
//	@Test
//	public void testGetSubcontractorWithStatus() {
//		// Setup
//		Subcontractor existingSubcontractor = new Subcontractor();
//		existingSubcontractor.setSId(3);
//		existingSubcontractor.setSName("Existing Name");
//		existingSubcontractor.setSEmail("existing@example.com");
//		Status status = new Status();
//		status.setStId(1);
//		existingSubcontractor.setStatus(status);
//
//		Mockito.when(subcontractorMapper.getSubcontractorWithStatus(1)).thenReturn(existingSubcontractor);
//
//		// Test
//		Subcontractor fetchedSubcontractor = subcontractorService.getSubcontractorWithStatus(3);
//
//		// Assert
//		assertNotNull(fetchedSubcontractor);
//		assertEquals(existingSubcontractor.getSId(), fetchedSubcontractor.getSId());
//		assertEquals(existingSubcontractor.getSName(), fetchedSubcontractor.getSName());
//	}
//
//	@Test
//	public void testGetSubcontractorWithStatusNonExisting() {
//		Mockito.when(subcontractorMapper.getSubcontractorWithStatus(1)).thenReturn(null);
//
//		assertThrows(SubcontractorNotFoundException.class, () -> {
//			subcontractorService.getSubcontractorWithStatus(1);
//		});
//	}
//
//	@Test
//	public void testUpdateSubcontractor() {
//		// Setup
//		Subcontractor subcontractor = new Subcontractor();
//		subcontractor.setSId(1);
//		subcontractor.setSName("Test Name");
//		subcontractor.setSEmail("test@example.com");
//		Status status = new Status();
//		status.setStId(1);
//		subcontractor.setStatus(status);
//
//		Mockito.doNothing().when(subcontractorMapper).updateSubcontractor(subcontractor);
//
//		// Test
//		subcontractorService.updateSubcontractor(subcontractor);
//
//		// Verify
//		Mockito.verify(subcontractorMapper, Mockito.times(1)).updateSubcontractor(subcontractor);
//	}
}
