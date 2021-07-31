package bozzaccio.poc.dto;

import bozzaccio.poc.entity.Document;

import java.io.Serializable;
import java.util.List;

public class DocumentDTO implements Serializable {

    private static final long serialVersionUID = 8016896406853156777L;

    List<Document> documentList;

    public DocumentDTO() {
    }

    public DocumentDTO(List<Document> documentList) {
        this.documentList = documentList;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }
}
