package com.strategicgains.restexpress.scaffold.mongodb.config

import com.strategicgains.restexpress.scaffold.mongodb.domain.Sample
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository
import com.strategicgains.restexpress.util.Environment
import com.strategicgains.restexpress.scaffold.mongodb.controller.SampleController
import java.util.Properties
import com.strategicgains.restexpress.RestExpress
import com.strategicgains.restexpress.Format

class Configuration extends Environment
{
  private val DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20"
  private val PORT_PROPERTY = "port"
  private val DEFAULT_FORMAT_PROPERTY = "default.format"
  private val BASE_URL_PROPERTY = "base.url"
  private val EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size"
  
  private var _port = RestExpress.DEFAULT_PORT
  def port = _port
  
  private var _defaultFormat = Format.JSON
  def defaultFormat = _defaultFormat
  
  private var _baseUrl = "http://localhost:"+String.valueOf(port)
  def baseUrl = _baseUrl
  
  private var _executorThreadPoolSize = Integer.parseInt(DEFAULT_EXECUTOR_THREAD_POOL_SIZE)
  def executorThreadPoolSize = _executorThreadPoolSize

  private var _metricsSettings: MetricsConfig = null
  def metricsConfig = _metricsSettings
  
  private var _sampleController: SampleController = null
  def sampleController = _sampleController
  
  override def fillValues(p: Properties)
  {
    _port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)))
    _defaultFormat = p.getProperty(DEFAULT_FORMAT_PROPERTY, Format.JSON)
    _baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:"+String.valueOf(port))
    _executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE))
    _metricsSettings = new MetricsConfig(p)
    var mongo = new MongoConfig(p)
    initialize(mongo)
  }
  
  private def initialize(mongo: MongoConfig)
  {
    var repo = new MongodbEntityRepository[Sample](mongo.client, mongo.dbName, classOf[Sample])
    _sampleController = new SampleController(repo)
  }
}