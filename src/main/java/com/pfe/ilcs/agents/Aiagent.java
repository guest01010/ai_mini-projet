package com.pfe.ilcs.agents;

import com.pfe.ilcs.Tools.AgentTools;
import org.aspectj.bridge.Message;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class Aiagent {
    private ChatClient c;

    public Aiagent(ChatClient.Builder c, ChatMemory memory, SimpleVectorStore vs, AgentTools agentTools) {
        this.c = c.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(agentTools)
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vs).build())
                .build();
    }

    public Flux<String> askAgent(String message) {
//        List<? extends AbstractMessage> m = List.of(
//                new UserMessage("je suis yaya"),
//                new AssistantMessage("oke bjr yahya")
//        );

        return
                c.prompt()
                        //.messages().system()
        .user(message).stream().content();
    }
}
