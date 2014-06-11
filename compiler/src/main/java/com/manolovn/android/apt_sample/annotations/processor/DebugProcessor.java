package com.manolovn.android.apt_sample.annotations.processor;

import com.manolovn.android.apt_sample.annotations.Debug;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Debug processor
 *
 * @author manolovn
 */
@SupportedAnnotationTypes("com.manolovn.android.ap_sample.annotations.Debug")
public class DebugProcessor extends AbstractProcessor {

    public DebugProcessor() {
        super();
    }

    @Override
    public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnvironment) {
        for (Element elem : roundEnvironment.getElementsAnnotatedWith(Debug.class)) {
            Debug debug = elem.getAnnotation(Debug.class);
            String message = "annotation found in " + elem.getSimpleName();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
        }
        return true;
    }

}
