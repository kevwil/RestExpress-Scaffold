package com.strategicgains.restexpress.scaffold.mongodb.config

import java.util.Properties
import org.slf4j._
import java.lang._
import com.strategicgains.restexpress.exception.ConfigurationException

class MetricsConfig(p: Properties)
{
  private val LOG = LoggerFactory.getLogger(classOf[MetricsConfig])
  private val METRICS_IS_ENABLED_PROPERTY = "metrics.isEnabled"
  private val METRICS_MACHINE_NAME_PROPERTY = "metrics.machineName"
  private val GRAPHITE_IS_ENABLED_PROPERTY = "metrics.graphite.isEnabled"
  private val GRAPHITE_HOST_PROPERTY = "metrics.graphite.host"
  private val GRAPHITE_PORT_PROPERTY = "metrics.graphite.port"
  private val GRAPHITE_PUBLISHING_SECONDS_PROPERTY = "metrics.graphite.publishSeconds"
  
  private var _isEnabled = Boolean.parseBoolean(p.getProperty(METRICS_IS_ENABLED_PROPERTY, "true"))
  def isEnabled = _isEnabled
  private var _isGraphiteEnabled = Boolean.parseBoolean(p.getProperty(GRAPHITE_IS_ENABLED_PROPERTY, "true"))
  def isGraphiteEnabled = _isGraphiteEnabled
  private var _machineName: String = null
  def machineName = _machineName
  private var _graphiteHost: String = null
  def graphiteHost = _graphiteHost
  private var _graphitePort: Int = -1
  def graphitePort = _graphitePort
  private var _publishSeconds: Int = -1
  def publishSeconds = _publishSeconds
  
  if(_isEnabled && _isGraphiteEnabled){
    _machineName = p.getProperty(METRICS_MACHINE_NAME_PROPERTY)
    require(_machineName != null, LOG.warn("*** Machine Name (" + METRICS_MACHINE_NAME_PROPERTY + ") is not set. ***"))
    _graphiteHost = p.getProperty(GRAPHITE_HOST_PROPERTY)
    require(_graphiteHost != null, throw new ConfigurationException("Please define a graphite host for property: " + GRAPHITE_HOST_PROPERTY))
    try{
      _graphitePort = Integer.parseInt(p.getProperty(GRAPHITE_PORT_PROPERTY))
    }catch{
      case e: NumberFormatException => throw new ConfigurationException("Please define a graphite port for property: " + GRAPHITE_PORT_PROPERTY, e)
    }
    try{
      _publishSeconds = Integer.parseInt(p.getProperty(GRAPHITE_PUBLISHING_SECONDS_PROPERTY))
    }catch{
      case e: NumberFormatException => throw new ConfigurationException("Please define how frequently (in seconds) to publish to graphite in property: "
                    + GRAPHITE_PUBLISHING_SECONDS_PROPERTY, e)
    }
  }
}