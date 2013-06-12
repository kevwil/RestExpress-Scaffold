package com.strategicgains.restexpress.scaffold.mongodb.serialization

import com.strategicgains.restexpress.serialization.xml.DefaultXmlProcessor
import com.strategicgains.restexpress.scaffold.mongodb.domain.Sample
import com.strategicgains.hyperexpress.domain.Link
import com.strategicgains.hyperexpress.domain.LinkableCollection

class XmlSerializationProcessor
extends DefaultXmlProcessor
{
  alias("sample", classOf[Sample])
  alias("link", classOf[Link])
  alias("collection", classOf[LinkableCollection[Sample]])
  registerConverter(new XstreamObjectIdConverter())
}