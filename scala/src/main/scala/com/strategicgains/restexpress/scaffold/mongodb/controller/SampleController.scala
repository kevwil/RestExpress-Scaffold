package com.strategicgains.restexpress.scaffold.mongodb.controller

import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository
import com.strategicgains.restexpress.scaffold.mongodb.domain.Sample
import com.strategicgains.restexpress.Request
import com.strategicgains.restexpress.Response
import com.strategicgains.syntaxe.ValidationEngine
import org.jboss.netty.handler.codec.http.HttpMethod
import com.strategicgains.restexpress.scaffold.mongodb.Constants
import com.strategicgains.hyperexpress.util.LinkUtils
import com.strategicgains.hyperexpress.domain.Link
import com.strategicgains.hyperexpress.RelTypes
import com.strategicgains.hyperexpress.domain.LinkableCollection
import com.strategicgains.restexpress.query._
import scala.collection.JavaConversions._
import com.strategicgains.restexpress.exception.BadRequestException

class SampleController(samples: MongodbEntityRepository[Sample])
{
  def create(request: Request, response: Response):String =
  {
    val order = request.getBodyAs(classOf[Sample], "Sample details not provided")
    ValidationEngine.validateAndThrow(order)
    val saved = samples.create(order)
    
    response.setResponseCreated()
    
    val locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_SAMPLE)
    response.addLocationHeader(LinkUtils.formatUrl(locationPattern, Constants.Url.SAMPLE_ID, saved.getId()))
    
    return saved.getId()
  }
  
  def read(request: Request, response: Response):Sample =
  {
    val id = request.getUrlDecodedHeader(Constants.Url.SAMPLE_ID, "No Sample ID supplied")
    val sample = samples.read(id)
    val selfPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_SAMPLE)
    val selfUrl = LinkUtils.formatUrl(selfPattern, Constants.Url.SAMPLE_ID, sample.getId())
    sample.addLink(new Link(RelTypes.SELF, selfUrl))
    return sample
  }
  
  def readAll(request: Request, response: Response):LinkableCollection[Sample] =
  {
    val filter = QueryFilters.parseFrom(request)
    val order = QueryOrders.parseFrom(request)
    val range = QueryRanges.parseFrom(request, 20)
    val results = samples.readAll(filter, range, order)
    val count = samples.count(filter)
    response.setCollectionResponse(range, results.size(), count)

    // Add 'self' links
    val orderSelfPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_SAMPLE)

    // scala List != java List
    for (result <- results.toList)
      result.addLink(
          new Link(
              RelTypes.SELF,
              LinkUtils.formatUrl(
                  orderSelfPattern,
                  Constants.Url.SAMPLE_ID,
                  result.getId())))

    val selfUrl = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SAMPLE_COLLECTION)
    val wrapper = new LinkableCollection[Sample](results)
    wrapper.addLink(new Link(RelTypes.SELF, selfUrl))
    return wrapper
  }
  
  def update(request: Request, response: Response):Unit =
  {
    val id = request.getUrlDecodedHeader(Constants.Url.SAMPLE_ID)
    val sample = request.getBodyAs(classOf[Sample], "Sample details not provided")

    if (!id.equals(sample.getId()))
    {
        throw new BadRequestException("ID in URL and ID in Sample must match")
    }

    ValidationEngine.validateAndThrow(sample)
    samples.update(sample)
    response.setResponseNoContent()
  }
  
  def delete(request: Request, response: Response):Unit =
  {
    val id = request.getUrlDecodedHeader(Constants.Url.SAMPLE_ID, "No Sample ID supplied")
    samples.delete(id)
    response.setResponseNoContent()
  }
}