package example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class BepiAnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Bepi.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        /**
         * Annotation이 적절한 위치에 붙어있는지 확인
         */
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Bepi.class);
        for (Element element : elements) {
            Name elementName = element.getSimpleName();
            if (element.getKind() != ElementKind.INTERFACE) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Bepi Annotation can not be used on " + elementName);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing" + elementName);
            }

            /**
             * 어노테이션이 붙어있는 클래스의 이름 추출
             */
            TypeElement typeElement = (TypeElement) element;
            ClassName className = ClassName.get(typeElement);

            /**
             * 메서드 생성
             */
            MethodSpec getJobMethod = MethodSpec.methodBuilder("getJob")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "backend developer")
                    .build();

            MethodSpec printHobbyMethod = MethodSpec.methodBuilder("printHobby")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "hobby")
                .addStatement("System.out.println(\"bepi usually do \" + hobby)")
                .build();
            /**
             * 생성한 메서드를 가지고 있는 클래스 생성
             */
            TypeSpec bepiClass = TypeSpec.classBuilder("BepiPerson")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className)
                    .addMethod(getJobMethod)
                    .addMethod(printHobbyMethod)
                    .build();

            Filer filer = processingEnv.getFiler();
            try {
                JavaFile.builder(className.packageName(), bepiClass)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "ERROR: " + e);
                e.printStackTrace();
            }
        }
        return true;
    }
}
