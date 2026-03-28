package com.pfe.ilcs.Controller;


import com.pfe.ilcs.agents.Aiagent;
import com.pfe.ilcs.rag.DocumentLoaderIndexor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.awt.*;

@RestController
@CrossOrigin("*")
public class Aicontroler {

    @Autowired
    Aiagent a;

    @Autowired
    private DocumentLoaderIndexor indexor;

   @GetMapping(value = "/askAgent",produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> askAgent(@RequestParam(defaultValue = "Bonjour") String message) {
        return
                a.askAgent(message);
   }
   @PostMapping(value = "/loadfile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadfile(@RequestPart("file") MultipartFile file) {
       indexor.loadFile(file);
   }

}
