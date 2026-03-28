package com.pfe.ilcs.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class DocumentLoaderIndexor {

    @Value("store.json")
    private String fileStore;
    @Autowired
    private SimpleVectorStore vectorStore;

    public void loadFile(MultipartFile pdffile) {
        Path p= Path.of("src","main","resources","store");
        File file=new File(p.toFile(),fileStore);

        if (file.exists()){
            vectorStore.load(file);
        }
        PagePdfDocumentReader documentReader=new PagePdfDocumentReader(pdffile.getResource());
        List<Document> documents=documentReader.get();
        TextSplitter textSplitter=new TokenTextSplitter();
        List<Document> chunks=textSplitter.apply(documents);
        vectorStore.add(chunks);
        vectorStore.save(file);
    }

}
