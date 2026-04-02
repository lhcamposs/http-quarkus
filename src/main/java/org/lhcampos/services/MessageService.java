package org.lhcampos.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.lhcampos.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class MessageService {
    private final List<Message> messages = new ArrayList<>();
    private final AtomicLong id = new AtomicLong(1);

    public List<Message> getMessages() {
        return messages;
    }

    public Optional<Message> getMessageById(Long id) {
        return messages.stream()
                .filter(m -> m.id.equals(id))
                .findFirst();
    }

    public Message createMessage(Message message) {
        message.id = id.getAndIncrement();
        message.timestamp = java.time.LocalDateTime.now();
        messages.add(message);
        return message;
    }

    public boolean deleteMessage(Long id) {
        return messages.removeIf(m -> m.id.equals(id));
    }
}
