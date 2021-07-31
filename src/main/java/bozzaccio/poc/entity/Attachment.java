package bozzaccio.poc.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "ATTACHMENT")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @NotNull
    @Column(name = "FILE_LOB", nullable = false)
    private String attachment;

    public Attachment() {
    }

    public Attachment(String attachment) {
        this.attachment = attachment;
    }

    public Attachment(Long id, String attachment) {
        this.id = id;
        this.attachment = attachment;
    }

    public Long getId() {
        return id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
