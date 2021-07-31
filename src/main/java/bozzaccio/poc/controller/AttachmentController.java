package bozzaccio.poc.controller;

import bozzaccio.poc.dto.AttachmentDTO;
import bozzaccio.poc.entity.Attachment;
import bozzaccio.poc.repository.AttachmentRepository;
import bozzaccio.poc.service.AttachmentCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = {"/api/attachment"}, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class AttachmentController {

    @Autowired
    private AttachmentCacheService attachmentCacheService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @GetMapping(path = "/{documentID}")
    public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable Long documentID) {

        String lob = attachmentCacheService.getDocumentString(documentID);

        if (Objects.isNull(lob)) {
            return ResponseEntity.notFound().build();
        }

        AttachmentDTO response = new AttachmentDTO(lob);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AttachmentDTO> updateAttachment(@RequestBody AttachmentDTO attachmentDTO) {

        if (Objects.isNull(attachmentDTO) || Objects.isNull(attachmentDTO.getAttachment())) {
            return ResponseEntity.badRequest().build();
        }

        AttachmentDTO localDTO = null;

        if (Objects.nonNull(attachmentDTO.getID())) {
            attachmentCacheService.updateDocumentString(attachmentDTO.getID(), attachmentDTO.getAttachment());
            localDTO = attachmentDTO;
        } else {
            Attachment attachment = new Attachment(attachmentDTO.getAttachment());
            Attachment savedAttachment = attachmentRepository.saveAndFlush(attachment);
            localDTO = new AttachmentDTO(savedAttachment);
        }

        return ResponseEntity.ok(localDTO);
    }

}
