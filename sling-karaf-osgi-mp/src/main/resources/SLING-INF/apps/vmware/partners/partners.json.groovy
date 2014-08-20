import javax.jcr.query.Query
import javax.jcr.NodeIterator
import javax.jcr.Node
import groovy.json.JsonBuilder

response.setContentType "application/json"
response.setCharacterEncoding "UTF-8"

jsonBuilder = new JsonBuilder()
def queryString = "/jcr:root/content/vmware//element(*,vmw:partner)"
def query = currentNode.session.workspace.queryManager.createQuery(queryString, Query.XPATH)
def nodes = query.execute().nodes
jsonBuilder.json {
    partners {
        nodes.collect { node ->
            partner("path":node.path)
        }
    }
}

jsonBuilder.writeTo(out)
