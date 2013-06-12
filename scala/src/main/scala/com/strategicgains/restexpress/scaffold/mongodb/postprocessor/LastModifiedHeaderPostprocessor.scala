package com.strategicgains.restexpress.scaffold.mongodb.postprocessor

import org.jboss.netty.handler.codec.http.HttpHeaders.Names.LAST_MODIFIED
import com.strategicgains.restexpress.Request
import com.strategicgains.restexpress.pipeline.Postprocessor
import com.strategicgains.util.date.HttpHeaderTimestampAdapter
import com.strategicgains.restexpress.Response
import com.strategicgains.repoexpress.domain.Timestamped

class LastModifiedHeaderPostprocessor
extends Postprocessor
{
  private val fmt = new HttpHeaderTimestampAdapter()
  
  override def process(request: Request, response: Response):Unit =
  {
    if(!request.isMethodGet()) return
    if(!response.hasBody()) return
    val body = response.getBody()
    if(!response.hasHeader(LAST_MODIFIED) &&
        body.getClass().isAssignableFrom(classOf[Timestamped]))
    {
      val updatedAt = body.asInstanceOf[Timestamped].getUpdatedAt()
      response.addHeader(LAST_MODIFIED, fmt.format(updatedAt))
    }
  }
}