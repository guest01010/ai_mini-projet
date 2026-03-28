package com.pfe.ilcs.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

//@Component
public class DocumentIndexor {
    @Value("classpath:/pdfs/cv.pdf")
    private Resource pdf;
    @Value("store.json")
    private String fileStore;

    //@Bean
    public SimpleVectorStore getvectorstore(MistralAiEmbeddingModel embedding) {
        Path p= Path.of("src","main","resources","store");
        File file=new File(p.toFile(),fileStore);
        SimpleVectorStore vectorStore= SimpleVectorStore.builder(embedding).build();
    if (!file.exists()) {
        PagePdfDocumentReader documentReader=new PagePdfDocumentReader(pdf);
        List<Document> documents=documentReader.get();
        TextSplitter textSplitter=new TokenTextSplitter();
        List<Document> chunks=textSplitter.apply(documents);
        vectorStore.add(chunks);
        vectorStore.save(file);
    }
    else {
        vectorStore.load(file);
    }
    return vectorStore;
    }
}
