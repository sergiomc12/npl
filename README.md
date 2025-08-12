# Stanford CoreNLP - KBP Optimized

[![Run Tests](https://github.com/stanfordnlp/CoreNLP/actions/workflows/run-tests.yaml/badge.svg)](https://github.com/stanfordnlp/CoreNLP/actions/workflows/run-tests.yaml)
[![Maven Central](https://img.shields.io/maven-central/v/edu.stanford.nlp/stanford-corenlp.svg)](https://mvnrepository.com/artifact/edu.stanford.nlp/stanford-corenlp)
[![Twitter](https://img.shields.io/twitter/follow/stanfordnlp.svg?style=social&label=Follow)](https://twitter.com/stanfordnlp/)

**IMPORTANT: This repository has been optimized specifically for KBP (Knowledge Base Population) functionality only.**

## KBP Optimization

This version of Stanford CoreNLP has been streamlined to include only the components necessary for Knowledge Base Population (KBP). The following changes have been made:

### Removed Components
- **GUI/Swing Components**: All Swing-based graphical user interfaces have been removed (javax.swing, edu.stanford.nlp.swing, parser UI, NER UI, etc.)
- **Web Server Components**: All servlet, webapp, and Tomcat-related code has been removed
- **Unnecessary Dependencies**: Removed AppleJavaExtensions, ant-contrib, jflex, jollyday, and servlet dependencies that are not required for KBP functionality

### Retained Components
- **Core Stanford CoreNLP**: Essential NLP pipeline components
- **KBP Modules**: All KBP relation extractors and annotators
- **Essential Dependencies**: protobuf, ejml, xom, joda-time, slf4j logging, junit for testing
- **Required Models**: Support for CoreNLP models needed for KBP functionality

### Current KBP Functionality
This optimized version maintains full KBP (Knowledge Base Population) capabilities including:
- KBPAnnotator for relation extraction
- KBPRelationExtractor, KBPSemgrexExtractor, KBPTokensregexExtractor
- Entity and relation extraction from text
- Basic relation triple generation

### Build Instructions

#### Build with Maven
1. Make sure you have Maven installed: [https://maven.apache.org/](https://maven.apache.org/)
2. Run: `mvn package` to build the optimized jar
3. For KBP functionality, download the [english-kbp-models](http://nlp.stanford.edu/software/stanford-english-kbp-corenlp-models-current.jar) and include in your CLASSPATH

#### KBP Example Usage

```java
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

// Create pipeline with KBP
Properties props = new Properties();
props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref,kbp");
StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

// Process text
String text = "Barack Obama was born in Hawaii. He was the 44th President of the United States.";
Annotation document = new Annotation(text);
pipeline.annotate(document);

// Extract relations
for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
    Collection<RelationTriple> triples = sentence.get(CoreAnnotations.KBPTriplesAnnotation.class);
    for (RelationTriple triple : triples) {
        System.out.println("Relation: " + triple);
    }
}
```

### License

The Stanford CoreNLP code is written in Java and licensed under the GNU General Public License (v2 or later). Note that this is the full GPL, which allows many free uses, but not its use in proprietary software that you distribute to others.

### Questions and Support

Questions about CoreNLP can be posted on StackOverflow with the tag [stanford-nlp](http://stackoverflow.com/questions/tagged/stanford-nlp), or on the [mailing lists](https://nlp.stanford.edu/software/#Mail).

For the original full-featured Stanford CoreNLP, visit: [https://stanfordnlp.github.io/CoreNLP/](https://stanfordnlp.github.io/CoreNLP/)
