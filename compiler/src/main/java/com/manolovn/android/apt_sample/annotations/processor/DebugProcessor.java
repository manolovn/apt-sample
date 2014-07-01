package com.manolovn.android.apt_sample.annotations.processor;

import com.manolovn.android.apt_sample.annotations.Debug;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

/**
 * Debug processor
 *
 * @author manolovn
 */
@SupportedAnnotationTypes("com.manolovn.android.apt_sample.annotations.Debug")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class DebugProcessor extends AbstractProcessor {

    public static final String SUFFIX = "$$ManoloVn";

    public DebugProcessor() {
        super();
    }

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;

    private Template vt;
    private VelocityContext vc;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        filer = env.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnvironment) {

        // template engine load
        try {
            Properties props = new Properties();
            URL url = this.getClass().getClassLoader().getResource("velocity.properties");
            props.load(url.openStream());
            VelocityEngine ve = new VelocityEngine(props);
            ve.init();

            vc = new VelocityContext();
            vt = ve.getTemplate("sampleclass.vm");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*if (!roundEnvironment.processingOver()) {
            for (Element e : roundEnvironment.getRootElements()) {
                TypeElement te = findEnclosingTypeElement(e);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Scanning Type " + te.getQualifiedName());

                for (ExecutableElement ee : ElementFilter.methodsIn(te.getEnclosedElements())) {
                    Debug action = ee.getAnnotation(Debug.class);

                    if (action == null) {
                        // Look for the overridden method
                        ExecutableElement oe = getExecutableElement(te, ee.getSimpleName());
                        if (oe != null) {
                            action = oe.getAnnotation(Debug.class);
                        }
                    }

                    String message = "annotation found in " + ee.getSimpleName();
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
                }
            }
        }*/

        for (Element elem : roundEnvironment.getElementsAnnotatedWith(Debug.class)) {
            Debug debug = elem.getAnnotation(Debug.class);
            String message = "annotation found in " + elem.getSimpleName();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

            if (elem.getKind() == ElementKind.CLASS) {

                TypeElement classElement = (TypeElement) elem;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                JavaFileObject jfo = null;
                try {

                    jfo = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + SUFFIX);
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

                ExecutableElement executableElement = (ExecutableElement) elem;
                String packageName = elementUtils.getPackageOf(elem).getQualifiedName().toString();
                String className = executableElement.getEnclosingElement().getSimpleName().toString();

                JavaFileObject jfo = null;
                try {

                    jfo = processingEnv.getFiler().createSourceFile(className + SUFFIX);
                    Writer writer = jfo.openWriter();

                    vc.put("className", className);
                    vc.put("packageName", packageName);
                    vc.put("classSuffix", SUFFIX);

                    vt.merge(vc, writer);
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return true;
    }

    /**
     * Returns class name
     *
     * @param type        : element type
     * @param packageName : package name as string
     * @return : class name as string
     */
    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private static void note(ProcessingEnvironment processingEnvironment, String message) {
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    public static TypeElement findEnclosingTypeElement(Element e) {
        while (e != null && !(e instanceof TypeElement)) {
            e = e.getEnclosingElement();
        }
        return TypeElement.class.cast(e);
    }

    public ExecutableElement getExecutableElement(final TypeElement typeElement, final Name name) {
        TypeElement te = typeElement;
        do {
            te = (TypeElement) processingEnv.getTypeUtils().asElement(te.getSuperclass());
            if (te != null) {
                for (ExecutableElement ee : ElementFilter.methodsIn(te.getEnclosedElements())) {
                    if (name.equals(ee.getSimpleName()) && ee.getParameters().isEmpty()) {
                        return ee;
                    }
                }
            }
        } while (te != null);
        return null;
    }

}
