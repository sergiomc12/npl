package edu.stanford.nlp.ie;

import edu.stanford.nlp.ie.KBPRelationExtractor;
import edu.stanford.nlp.ie.KBPRelationExtractor.KBPInput;
import edu.stanford.nlp.ie.KBPRelationExtractor.NERTag;
import edu.stanford.nlp.ie.machinereading.structure.Span;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Pair;

import java.util.Arrays;
import java.util.List;

public class AdvancedKBPRelationExtractorExample {

    // Implementación concreta del extractor
    static class RuleBasedKBPRelationExtractor implements KBPRelationExtractor {
        @Override
        public Pair<String, Double> classify(KBPInput input) {
            // Regla simple: Si la frase contiene "nació en" y el sujeto es PERSON, objeto es CITY
            String text = input.getSentence().text().toLowerCase();
            if (text.contains("nació en")
                    && input.subjectType == NERTag.PERSON
                    && input.objectType == NERTag.CITY) {
                return new Pair<>("per:city_of_birth", 0.95);
            }
            // Agrega otras reglas para otros tipos de relaciones
            return new Pair<>(NO_RELATION, 0.5);
        }
    }

    public static void main(String[] args) {
        KBPRelationExtractor extractor = new RuleBasedKBPRelationExtractor();

        // Lista de ejemplos: cada uno es una oración con spans manuales
        List<String> textos = Arrays.asList(
                "Barack Obama nació en Honolulu.",
                "Lionel Messi nació en Rosario.",
                "Bill Gates fundó Microsoft.",
                "Shakira vive en Barcelona."
        );

        // Para cada oración, define los spans y tipos manualmente (en producción, sería por NER)
        for (String texto : textos) {
            Sentence sentence = new Sentence(texto);

            // Simulación: encontrar spans de persona y ciudad (en la práctica usarías NER y regex)
            String[] palabras = sentence.words().toArray(new String[0]);
            Span subjectSpan = null, objectSpan = null;
            NERTag subjectType = null, objectType = null;

            if (texto.contains("Barack Obama")) {
                subjectSpan = new Span(0, 2);
                subjectType = NERTag.PERSON;
            } else if (texto.contains("Lionel Messi")) {
                subjectSpan = new Span(0, 2);
                subjectType = NERTag.PERSON;
            } else if (texto.contains("Bill Gates")) {
                subjectSpan = new Span(0, 2);
                subjectType = NERTag.PERSON;
            } else if (texto.contains("Shakira")) {
                subjectSpan = new Span(0, 1);
                subjectType = NERTag.PERSON;
            }

            if (texto.contains("Honolulu")) {
                objectSpan = new Span(4, 5);
                objectType = NERTag.CITY;
            } else if (texto.contains("Rosario")) {
                objectSpan = new Span(4, 5);
                objectType = NERTag.CITY;
            } else if (texto.contains("Microsoft")) {
                objectSpan = new Span(4, 5);
                objectType = NERTag.ORGANIZATION;
            } else if (texto.contains("Barcelona")) {
                objectSpan = new Span(4, 5);
                objectType = NERTag.CITY;
            }

            if (subjectSpan != null && objectSpan != null) {
                KBPInput input = new KBPInput(subjectSpan, objectSpan, subjectType, objectType, sentence);
                Pair<String, Double> resultado = extractor.classify(input);

                System.out.println("Texto: " + texto);
                System.out.println("Relación detectada: " + resultado.first);
                System.out.println("Confianza: " + resultado.second);
                System.out.println("------");
            }
        }
    }
}
