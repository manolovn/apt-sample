package com.manolovn.android.apt_sample.annotations.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
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
        return true;
    }

}
