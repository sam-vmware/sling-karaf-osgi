/* This file handles all GET requests for the blueprint, this ultimately returns the
   the real blueprint which can be thought of as really a template with urls to real content
   in place of tags.

   This is a sample script, meant to act as a guide it will vary per blueprint which should be replaced
   by a generic version which is fed the map of replacement properties and values
*/

import java.net.URL
import javax.jcr.Node
import org.apache.sling.api.resource.ResourceResolverFactory
import org.apache.sling.api.resource.Resource

response.setContentType "text/xml"
response.setCharacterEncoding "UTF-8"

def urlPath = request.requestURL.toString().split("/blueprint")[0]
def resourcePath = currentNode.path.split("/blueprint")[0]
def target = getNodePropery(currentNode) {
    it.name == "name"
}.value.string

def adapted = [:]
getResourceFromPath("$resourcePath/$target") { resource ->
    // See http://dev.day.com/docs/en/cq/current/developing/sling-adapters.html on Adapters
    adapted << ["node": (resource?.adaptTo(Node.class))]
}
def bpFileContent = adapted["node"].getNode("jcr:content")
def contentText = bpFileContent.getProperty("jcr:data").binary.stream.text

def replaceStr = "[$urlPath/mod_wl.so]"
def bpReplacementContent = [
    /\[mod_wl.so\]/: replaceStr
]
out.write substituteProperties(contentText, bpReplacementContent)

def getResourceFromPath(path, Closure processResource) {
    def resource = request.resourceResolver.resolve(path)
    processResource resource
}

def getNodePropery(node, Closure constraint) {
    node?.properties?.find constraint
}

def dumpNodeProperties(node) {
    log.info "node: $node"
    node.properties.each {
        log.info "name: $it.name, type: $it.type, value: $it.string"
    }
}

String substituteProperties(String text = "", Map replaceMents = [:]) {
    replaceMents.each { k, v ->
        text = text.replaceAll k, v
    }
    text
}
