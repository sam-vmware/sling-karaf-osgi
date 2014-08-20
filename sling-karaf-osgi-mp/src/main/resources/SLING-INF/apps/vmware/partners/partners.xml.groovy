import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import javax.jcr.query.Query
import javax.jcr.NodeIterator
import javax.jcr.Node
import groovy.xml.MarkupBuilder

response.setContentType("text/xml")
response.setCharacterEncoding("UTF-8")

def queryString = "/jcr:root/content/vmware//element(*,vmw:partner)"
def query = currentNode.session.workspace.queryManager.createQuery(queryString, Query.XPATH)
def nodes = query.execute().nodes
def builder = new StreamingMarkupBuilder()
builder.encoding = "UTF-8"
def partners = builder.bind {
    mkp.xmlDeclaration()
    namespaces << [meta:"http://vmware/partners"]

    partners {
        nodes.each { node ->
            def currentPartner = node.path.split("/").last()
            partner {
                name currentPartner
                path node.path
            }
        }
    }
}

println XmlUtil.serialize(partners)


