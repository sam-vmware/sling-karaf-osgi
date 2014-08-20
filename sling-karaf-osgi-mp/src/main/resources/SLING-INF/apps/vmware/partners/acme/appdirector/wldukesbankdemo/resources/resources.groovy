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
    def queryString = "/jcr:root/content/vmware/partners/acme/appdirector/wldukesbankdemo/resources/*"
    def query = currentNode.session.workspace.queryManager.createQuery(queryString, Query.XPATH)
    def nodes = query.execute().nodes
    meta {
    }
    head {
        title "WLDukesBankDemo Resources"
    }
    body {
        h2 "WebLogic DukesBank Demo Resources"
        h3 "Each Resource"
        ul {
            nodes.each { node ->
                def currentResource = node.path.split('/').last()
                li {
                    a href: "${node.path}",currentResource
                }
            }
        }
    }
}

