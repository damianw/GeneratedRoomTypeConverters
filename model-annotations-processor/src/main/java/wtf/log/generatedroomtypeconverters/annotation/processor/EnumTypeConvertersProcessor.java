package wtf.log.generatedroomtypeconverters.annotation.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import wtf.log.generatedroomtypeconverters.annotation.AutoEnum;

@AutoService(Processor.class)
@SupportedAnnotationTypes("wtf.log.generatedroomtypeconverters.annotation.AutoEnum")
public class EnumTypeConvertersProcessor extends AbstractProcessor {

    private static final ClassName ANNOTATION_TYPECONVERTER = ClassName.get(
            "android.arch.persistence.room", "TypeConverter");

    private boolean built = false;

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment roundEnvironment) {
        if (built) return false;

        final TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder("EnumConverters")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PRIVATE)
                        .addStatement("throw new $T(\"No instances!\")", UnsupportedOperationException.class)
                        .build());

        for (Element element : roundEnvironment.getElementsAnnotatedWith(AutoEnum.class)) {
            final String name = element.getSimpleName().toString();
            final TypeName type = TypeName.get(element.asType());
            typeSpecBuilder.addMethod(MethodSpec.methodBuilder("convert" + name + "ToString")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addAnnotation(ANNOTATION_TYPECONVERTER)
                    .returns(String.class)
                    .addParameter(type, "instance")
                    .addStatement("return instance == null ? null : instance.name()")
                    .build());
            typeSpecBuilder.addMethod(MethodSpec.methodBuilder("convertStringTo" + name)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addAnnotation(ANNOTATION_TYPECONVERTER)
                    .returns(type)
                    .addParameter(String.class, "value")
                    .addStatement("return value == null ? null : $T.valueOf(value)", type)
                    .build());
        }

        final TypeSpec typeSpec = typeSpecBuilder.build();
        final JavaFile javaFile = JavaFile
                .builder("wtf.log.generatedroomtypeconverters", typeSpec)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        built = true;
        return true;
    }

}
