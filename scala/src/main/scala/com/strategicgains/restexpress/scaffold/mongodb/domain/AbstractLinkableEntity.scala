package com.strategicgains.restexpress.scaffold.mongodb.domain

import com.strategicgains.hyperexpress.domain._
import java.util._
import com.strategicgains.repoexpress.mongodb.AbstractMongodbEntity

abstract class AbstractLinkableEntity extends AbstractMongodbEntity with Linkable {

   private var _links: List[Link] = new ArrayList[Link]
   // scala-style true getter/setter for Links
   def links = Collections.unmodifiableList(_links)
   def links_= (value:List[Link]):Unit = _links = new ArrayList[Link](value)
   
   // java-style overrides of Java getter/setters for Links
   override def getLinks = links
   override def setLinks(value:List[Link]) { links = value }
   
   def addLink(value:Link) {
     _links.add( new Link(value))
   }
   
   def addAllLinks(value:Collection[Link]) {
     _links.addAll(value)
   }
}