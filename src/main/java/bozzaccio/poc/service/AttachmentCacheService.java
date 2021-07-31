package bozzaccio.poc.service;

import bozzaccio.poc.component.CacheComponent;
import bozzaccio.poc.entity.Attachment;
import bozzaccio.poc.repository.AttachmentRepository;
import com.hazelcast.core.Hazelcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Cache using hazelcast IMap
 */
@Service
public class AttachmentCacheService extends CacheComponent<Long, String> {

    private static final Logger _LOGGER = LoggerFactory.getLogger(AttachmentCacheService.class);

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentCacheService() {
        super(Hazelcast.newHazelcastInstance(), "documentMap");
    }

    public void updateDocumentString(Long documentID, String attachment) {
        putCacheData(documentID, attachment);
        _LOGGER.info("Document ID: {} , added in cache", documentID);


        TimerTask task = new TimerTask() {
            public void run() {
                persistCacheToEntity();
                _LOGGER.info("Cache data has been persist to db");
            }
        };
        Timer timer = new Timer("persist-cache");
        timer.schedule(task, 10000L);
    }

    public String getDocumentString(Long documentID) {

        String lob = getCacheData(documentID);

        if (Objects.isNull(lob)) {
            // force search and add new cache if lob is null
            Attachment attachment = attachmentRepository.getById(documentID);
            lob = attachment.getAttachment();
            updateDocumentString(documentID, lob);
            _LOGGER.info("Document ID: {} , updated in cache", documentID);
        }

        _LOGGER.info("Document ID: {} , get by the cache", documentID);
        return lob;
    }

    private void persistCacheToEntity() {

        for (Long documentID : getKeySet()) {

            String attachment = getDocumentString(documentID);
            Attachment entity = new Attachment(documentID, attachment);

            attachmentRepository.saveAndFlush(entity);
            removeCacheData(documentID);
        }
    }
}
