package com.manolovn.android.apt_sample.annotations.processor;

import com.manolovn.android.apt_sample.annotations.Debug;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Debug processor
 *
 * @author manolovn
 */
@SupportedAnnotationTypes("com.manolovn.android.apt_sample.annotations.Debug")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
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

            TypeElement classElement = (TypeElement) elem;
            PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

            JavaFileObject jfo = null;
            try {

                jfo = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + "BeanInfo");
                BufferedWriter bw = new BufferedWriter(jfo.openWriter());
                bw.append("package ");
                bw.append(packageElement.getQualifiedName());
                bw.append(";");
                bw.newLine();
                bw.newLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

}
