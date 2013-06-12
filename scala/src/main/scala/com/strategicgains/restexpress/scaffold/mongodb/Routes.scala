package com.strategicgains.restexpress.scaffold.mongodb

import org.jboss.netty.handler.codec.http.HttpMethod
import com.strategicgains.restexpress.RestExpress
import com.strategicgains.restexpress.scaffold.mongodb.config.Configuration

object Routes {
  def define(config: Configuration, server: RestExpress) = {
    server.uri("/your/route/here/{sampleId}.{format}", config.sampleController)
      .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
      .name(Constants.Routes.SINGLE_SAMPLE);
    server.uri("/your/collection/route/here.{format}", config.sampleController)
      .method(HttpMethod.POST)
      .name(Constants.Routes.SAMPLE_COLLECTION);
  }
}