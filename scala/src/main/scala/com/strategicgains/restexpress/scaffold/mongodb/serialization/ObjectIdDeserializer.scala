package com.strategicgains.restexpress.scaffold.mongodb.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.DeserializationContext
import org.bson.types.ObjectId

class ObjectIdDeserializer
extends JsonDeserializer[ObjectId]
{
  override def deserialize(json: JsonParser, context: DeserializationContext):ObjectId =
  {
    new ObjectId(json.getText())
  }
}