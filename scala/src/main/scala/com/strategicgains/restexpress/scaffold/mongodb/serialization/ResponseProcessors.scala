package com.strategicgains.restexpress.scaffold.mongodb.serialization

import com.strategicgains.restexpress.serialization.SerializationProcessor
import com.strategicgains.restexpress.response.ResponseWrapper
import com.strategicgains.restexpress.response.RawResponseWrapper
import com.strategicgains.restexpress.response.DefaultResponseWrapper
import com.strategicgains.restexpress.response.ResponseProcessor

object ResponseProcessors
{
  private val JSON_SERIALIZER:SerializationProcessor = new JsonSerializationProcessor()
  private val XML_SERIALIZER:SerializationProcessor = new XmlSerializationProcessor()
  private val RAW_WRAPPER:ResponseWrapper = new RawResponseWrapper()
  private val WRAPPING_WRAPPER = new DefaultResponseWrapper()
  
  def json():ResponseProcessor =
  {
    new ResponseProcessor(JSON_SERIALIZER, RAW_WRAPPER)
  }
  
  def wrappedJson():ResponseProcessor =
  {
    new ResponseProcessor(JSON_SERIALIZER, WRAPPING_WRAPPER)
  }
  
  def xml():ResponseProcessor =
  {
    new ResponseProcessor(XML_SERIALIZER, RAW_WRAPPER)
  }
  
  def wrappedXml():ResponseProcessor =
  {
    new ResponseProcessor(XML_SERIALIZER, WRAPPING_WRAPPER)
  }
}