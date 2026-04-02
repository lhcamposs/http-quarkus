package org.lhcampos.model;

import java.time.LocalDateTime;

public class Message {
    public Long id;
    public String sender;
    public String content;
    public LocalDateTime timestamp;

    public Message() {}
}
