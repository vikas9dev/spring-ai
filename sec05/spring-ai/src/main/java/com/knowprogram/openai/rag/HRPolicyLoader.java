package com.knowprogram.openai.rag;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HRPolicyLoader {
    private final VectorStore vectorStore;

    @Value("classpath:/Eazybytes_HR_Policies.pdf")
    Resource hrPolicyPdf;

    @PostConstruct
    public void loadpdf(){
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(hrPolicyPdf);
        List<Document> documents = tikaDocumentReader.get();
        TokenTextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(400).build();
        List<Document> splittedDocs = textSplitter.split(documents);
        vectorStore.add(splittedDocs);
    }
}
