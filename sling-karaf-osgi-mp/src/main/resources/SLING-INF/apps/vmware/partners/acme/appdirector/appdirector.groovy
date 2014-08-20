import javax.jcr.query.Query
import javax.jcr.NodeIterator
import javax.jcr.Node
import groovy.xml.MarkupBuilder

response.setContentType "text/html"
response.setCharacterEncoding "UTF-8"

log.info "Node $currentNode"
log.info "Resource $resource"

def mb = new groovy.xml.MarkupBuilder(out)
mb.omitNullAttributes=true
//mb.escapeAttributes=true
mb.expandEmptyElements=true
mb.doubleQuotes=true
mb.omitEmptyAttributes=true
mb.html {
    def queryString = "/jcr:root/content/vmware/partners/acme/appdirector/element(*,vmw:app)"
    def query = currentNode.session.workspace.queryManager.createQuery(queryString, Query.XPATH)
    def nodes = query.execute().nodes
    meta {
    }
    head {
        title "AppDirector"
    }
    body {
        h2 "AppDirector"
        h3 "Apps"
        ul {
            nodes.each { node ->
                def currentApp = node.path.split('/').last()
                li {
                    a href: "${node.path}.html",currentApp
                }
            }
        }
    }
}

