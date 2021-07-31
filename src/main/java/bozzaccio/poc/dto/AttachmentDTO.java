package bozzaccio.poc.dto;

import bozzaccio.poc.entity.Attachment;

import java.io.Serializable;

public class AttachmentDTO implements Serializable {

    private static final long serialVersionUID = -2544826769811356598L;

    private String attachment;
    private Long ID;

    public AttachmentDTO() {
    }

    public AttachmentDTO(String attachment) {
        this.attachment = attachment;
    }

    public AttachmentDTO(Attachment entity) {
        this.attachment = entity.getAttachment();
        this.ID = entity.getId();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
