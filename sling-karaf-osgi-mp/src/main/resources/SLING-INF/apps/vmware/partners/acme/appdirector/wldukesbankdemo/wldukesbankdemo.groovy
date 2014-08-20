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
    def queryString = "/jcr:root/content/vmware/partners/acme/appdirector/wldukesbankdemo/element(*,vmw:resources)"
    def query = currentNode.session.workspace.queryManager.createQuery(queryString, Query.XPATH)
    def nodes = query.execute().nodes
    meta {
    }
    head {
        title "WLDukesBankDemo"
    }
    body {
        h2 "WebLogic DukesBank Demo"
        h3 "Resources"
        ul {
            nodes.each { node ->
                def currentResources = node.path.split('/').last()
                li {
                    a href: "${node.path}.html",currentResources
                }
            }
        }
    }
}

