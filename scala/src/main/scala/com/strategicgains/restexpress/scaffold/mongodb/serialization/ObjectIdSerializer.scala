package com.strategicgains.restexpress.scaffold.mongodb.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.bson.types.ObjectId

class ObjectIdSerializer
extends JsonSerializer[ObjectId]
{
  override def serialize(objectId: ObjectId, json: JsonGenerator, provider: SerializerProvider):Unit =
  {
    json.writeString(objectId.toString())
  }
}