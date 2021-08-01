package bozzaccio.poc.controller;

import bozzaccio.poc.dto.DocumentDTO;
import bozzaccio.poc.service.DocumentCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = {"/api/document"}, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class DocumentController {

    @Autowired
    private DocumentCacheService documentCacheService;

    @GetMapping()
    public ResponseEntity<DocumentDTO> getAllDocuments() {

        return Optional
                .ofNullable(documentCacheService.getDocuments())
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{title}")
    public ResponseEntity<DocumentDTO> getDocumentsByTitle(@PathVariable String title) {

        return Optional
                .ofNullable(documentCacheService.getDocumentsByTitle(title))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {

        return ResponseEntity.ok(documentCacheService.deleteDocument(id));
    }
}
