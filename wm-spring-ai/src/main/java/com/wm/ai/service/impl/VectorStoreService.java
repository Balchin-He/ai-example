package com.wm.ai.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class VectorStoreService {
    private final VectorStore vectorStore;
    private final DocumentService documentService;

    /**
     * 添加文本信息
     * @return
     */
    public String userPortraits() {
        List<Document> documents = documentService.loadUserPortraits();
//        vectorStore.add(documents);

        // 分批处理，每批不超过10个文档
        int batchSize = 10;
        for (int i = 0; i < documents.size(); i += batchSize) {
            int end = Math.min(i + batchSize, documents.size());
            List<Document> batch = documents.subList(i, end);
            vectorStore.add(batch);
        }
        return "ok";

    }
}
