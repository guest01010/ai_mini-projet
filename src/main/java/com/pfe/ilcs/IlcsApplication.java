package com.pfe.ilcs;

import com.pfe.ilcs.Entities.Etudiant;
import com.pfe.ilcs.Reposetories.EtudiantRepo;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.model.mistralai.autoconfigure.MistralAiChatAutoConfiguration;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {MistralAiChatAutoConfiguration.class})
public class IlcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IlcsApplication.class, args);
    }

    @Bean
    CommandLineRunner init(EtudiantRepo ep) {
        return args -> {

            Etudiant et = new Etudiant();
            et.setApogee("123456");
            et.setNom("ouazzou");
                    et.setPrenom("mohamed amine");
            et.setFilliere("ilcs");
            et.setNiveau("2A");
            et.setNote(18.0);
            ep.save(et);
            Etudiant et1 = new Etudiant();
            et1.setApogee("123456");
            et1.setNom("el bikri");
            et1.setPrenom("reda");
            et1.setFilliere("aiid");
            et1.setNiveau("1A");
            et1.setNote(20.0);
            ep.save(et1);

        };
    }
    @Bean
    public SimpleVectorStore getVectorStore(MistralAiEmbeddingModel model) {
        SimpleVectorStore vectorStore= SimpleVectorStore.builder(model).build();
        return vectorStore;
    }
}
