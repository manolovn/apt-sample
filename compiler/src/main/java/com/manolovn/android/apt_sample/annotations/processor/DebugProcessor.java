package com.manolovn.android.apt_sample.annotations.processor;

import com.manolovn.android.apt_sample.annotations.Debug;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
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

            if (elem.getKind() == ElementKind.CLASS) {

                TypeElement classElement = (TypeElement) elem;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                JavaFileObject jfo = null;
                try {

                    jfo = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + "$$ManoloVn");
                    BufferedWriter bw = new BufferedWriter(jfo.openWriter());
                    bw.append("package ");
                    bw.append(packageElement.getQualifiedName());
                    bw.append(";");
                    bw.newLine();
                    bw.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (elem.getKind() == ElementKind.FIELD) {

            } else if (elem.getKind() == ElementKind.METHOD) {

                JavaFileObject jfo = null;
                try {

                    jfo = processingEnv.getFiler().createSourceFile(elem.getSimpleName().toString() + "$$ManoloVn");
                    Writer writer = jfo.openWriter();

                    writer.append("package ");
                    writer.append("com.manolovn.android.apt_sample");
                    writer.append(";");
                    writer.append("\n");

                    writer.flush();
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return true;
    }

}
