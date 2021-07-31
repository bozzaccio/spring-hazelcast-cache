package bozzaccio.poc.service;

import bozzaccio.poc.dto.DocumentDTO;
import bozzaccio.poc.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cache using annotation based on Hazelcast bean config {@link bozzaccio.poc.config.HazelcastConfig}
 */
@Service
public class DocumentCacheService {

    @Autowired
    private DocumentRepository documentRepository;

    @Cacheable(cacheNames = {"document-cache"})
    public DocumentDTO getDocuments() {
        return new DocumentDTO(documentRepository.findAll());
    }

    @Cacheable(value = "document-cache", key = "#id", unless = "#result==null")
    public DocumentDTO getDocumentsByTitle(String title) {
        return new DocumentDTO(documentRepository.findByTitle(title));
    }

    @CacheEvict(value = "document-cache")
    public String deleteDocument(Long id) {
        documentRepository.deleteById(id);
        return "User deleted with id " + id;
    }
}
