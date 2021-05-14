package ru.dexterity.web.deserializer;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import ru.dexterity.web.controllers.moderation.domain.TaskOwner;

public class TaskOwnerKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) {
        return new TaskOwner(s);
    }

}
