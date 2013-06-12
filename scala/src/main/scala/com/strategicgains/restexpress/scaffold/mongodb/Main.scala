package com.strategicgains.restexpress.scaffold.mongodb

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.strategicgains.restexpress.scaffold.mongodb.config.Configuration
import com.strategicgains.restexpress.util.Environment
import com.strategicgains.restexpress.RestExpress
import com.strategicgains.repoexpress.exception.DuplicateItemException
import com.strategicgains.restexpress.exception.ConflictException
import com.strategicgains.repoexpress.exception.ItemNotFoundException
import com.strategicgains.restexpress.exception.NotFoundException
import com.strategicgains.syntaxe.ValidationException
import com.strategicgains.restexpress.exception.BadRequestException
import com.strategicgains.restexpress.plugin.metrics.MetricsPlugin
import com.yammer.metrics.reporting.GraphiteReporter
import java.util.concurrent.TimeUnit
import com.strategicgains.restexpress.scaffold.mongodb.serialization.ResponseProcessors
import com.strategicgains.restexpress.Format
import com.strategicgains.restexpress.scaffold.mongodb.postprocessor.LastModifiedHeaderPostprocessor
import com.strategicgains.restexpress.pipeline.SimpleConsoleLogMessageObserver
import com.strategicgains.restexpress.plugin.route.RoutesMetadataPlugin
import com.strategicgains.restexpress.Parameters
import com.strategicgains.restexpress.plugin.cache.CacheControlPlugin

object Main
{
  private val SERVICE_NAME = "TODO: Enter Service Name"
  private val LOG:Logger = LoggerFactory.getLogger(SERVICE_NAME)
  
  def main(args: Array[String]): Unit =
  {
    val config = loadEnvironment(args)
    val server = new RestExpress()
        .setName(SERVICE_NAME)
        .setBaseUrl(config.baseUrl)
        .setDefaultFormat(config.defaultFormat)
        .setExecutorThreadCount(config.executorThreadPoolSize)
        .putResponseProcessor(Format.JSON, ResponseProcessors.json)
        .putResponseProcessor(Format.WRAPPED_JSON, ResponseProcessors.wrappedJson)
        .putResponseProcessor(Format.XML, ResponseProcessors.xml)
        .putResponseProcessor(Format.WRAPPED_XML, ResponseProcessors.wrappedXml)
        .addPostprocessor(new LastModifiedHeaderPostprocessor())
        .addMessageObserver(new SimpleConsoleLogMessageObserver())
    Routes.define(config, server)
    configureMetrics(config, server)
    new RoutesMetadataPlugin().register(server).parameter(Parameters.Cache.MAX_AGE, 86400)
    new CacheControlPlugin().register(server)
    mapExceptions(server)
    server.bind(config.port)
    server.awaitShutdown()
  }
  
  private def configureMetrics(config:Configuration, server:RestExpress):Unit =
  {
    val mc = config.metricsConfig
    if( mc.isEnabled )
    {
      new MetricsPlugin().virtualMachineId(mc.machineName).register(server)
      if( mc.isGraphiteEnabled )
      {
        GraphiteReporter.enable(
            mc.publishSeconds, TimeUnit.SECONDS,
            mc.graphiteHost, mc.graphitePort)
      } else {
        LOG.warn("*** Graphite Metrics Publishing is Disabled ***")
      }
    } else {
      LOG.warn("*** Metrics Generation is Disabled ***")
    }
  }
  
  private def mapExceptions(server:RestExpress):Unit =
  {
    server.mapException(classOf[ItemNotFoundException], classOf[NotFoundException])
        .mapException(classOf[DuplicateItemException], classOf[ConflictException])
        .mapException(classOf[ValidationException], classOf[BadRequestException])
  }

  private def loadEnvironment(args: Array[String]):Configuration =
  {
    return if(args.length > 0){
      Environment.from(args(0), classOf[Configuration])
    } else {
      Environment.fromDefault(classOf[Configuration])
    } 
  }
}