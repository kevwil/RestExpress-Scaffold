package com.strategicgains.restexpress.scaffold.mongodb.serialization

import com.thoughtworks.xstream.converters.SingleValueConverter
import org.bson.types.ObjectId

class XstreamObjectIdConverter
extends SingleValueConverter
{
  override def canConvert(aClass: Class[_]):Boolean =
  {
    classOf[ObjectId].isAssignableFrom(aClass)
  }
  
  override def fromString(value:String):Object =
  {
    new ObjectId(value)
  }
  
  override def toString(objectId:Any):String =
  {
    objectId.asInstanceOf[ObjectId].toString()
  }
}