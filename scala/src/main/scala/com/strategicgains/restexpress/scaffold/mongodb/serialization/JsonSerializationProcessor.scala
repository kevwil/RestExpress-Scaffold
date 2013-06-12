package com.strategicgains.restexpress.scaffold.mongodb.serialization

import com.strategicgains.restexpress.serialization.json.DefaultJsonProcessor
import com.fasterxml.jackson.databind.module.SimpleModule
import org.bson.types.ObjectId

class JsonSerializationProcessor
extends DefaultJsonProcessor
{
  override protected def initializeModule(module: SimpleModule):Unit =
  {
    super.initializeModule(module)
    module.addDeserializer(classOf[ObjectId], new ObjectIdDeserializer())
    module.addSerializer(classOf[ObjectId], new ObjectIdSerializer())
  }
}