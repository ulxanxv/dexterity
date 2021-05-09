package ru.dexterity.web.controllers.moderation.domain;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

public class TaskOwnerKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) {
        return new TaskOwner(s);
    }
}
