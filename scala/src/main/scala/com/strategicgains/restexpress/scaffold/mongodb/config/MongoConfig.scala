package com.strategicgains.restexpress.scaffold.mongodb.config

import java.util.Properties
import com.mongodb.MongoClient
import com.strategicgains.restexpress.exception.ConfigurationException
import com.mongodb.MongoClientURI
import java.net.UnknownHostException

class MongoConfig(p: Properties)
{
  private val URI_PROPERTY = "mongodb.uri"
  
  private var uri = p.getProperty(URI_PROPERTY)
  require(uri != null, {throw new ConfigurationException("Please define a MongoDB URI for property: " + URI_PROPERTY)})
  
  private var mongoUri = new MongoClientURI(uri)
  var dbName = mongoUri.getDatabase()
  var client = {
    try{
      new MongoClient(mongoUri)
    }catch{
      case e: UnknownHostException => throw new ConfigurationException(e)
      case _: Throwable => throw new ConfigurationException("unknown error thrown when instantiating MongoClient")
    }
  }
}