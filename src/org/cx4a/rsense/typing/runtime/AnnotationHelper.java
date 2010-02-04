package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.ArrayList;

import org.jruby.util.ByteList;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.Context;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.DynamicScope;
import org.cx4a.rsense.ruby.LocalScope;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Visibility;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.AnnotationHelper;
import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.ArrayVertex;
import org.cx4a.rsense.typing.vertex.CallVertex;
import org.cx4a.rsense.typing.vertex.MultipleAsgnVertex;
import org.cx4a.rsense.typing.vertex.ToAryVertex;
import org.cx4a.rsense.typing.vertex.YieldVertex;
import org.cx4a.rsense.parser.TypeAnnotationLexer;
import org.cx4a.rsense.parser.TypeAnnotationParser;

public class AnnotationHelper {
    private AnnotationHelper() {}

    public static TypeAnnotation parseAnnotation(String annot, int lineno) {
        if (annot.startsWith("#%")) {
            ANTLRStringStream in = new ANTLRStringStream(annot.substring(2));
            in.setLine(lineno);
            TypeAnnotationLexer lex = new TypeAnnotationLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lex);
            TypeAnnotationParser parser = new TypeAnnotationParser(tokens);
            try {
                return parser.type();
            } catch (RecognitionException e)  {}
        }
        return null;
    }

    public static List<TypeAnnotation> parseAnnotations(List<ByteList> commentList, int lineno) {
        List<TypeAnnotation> annots = new ArrayList<TypeAnnotation>();
        for (ByteList comment : commentList) {
            TypeAnnotation annot = parseAnnotation(comment.toString(), lineno);
            if (annot != null) {
                annots.add(annot);
            }
        }
        return annots;
    }

    public static boolean resolveMethodAnnotation(Graph graph, Template template) {
        return new AnnotationResolver(graph).resolveMethodAnnotation(template);
    }

    public static TypeVarMap getTypeVarMap(IRubyObject object) {
        if (object.getTag() instanceof TypeVarMap) {
            return (TypeVarMap) object.getTag();
        } else {
            return null;
        }
    }

    public static ClassType getClassAnnotation(RubyModule klass) {
        if (klass.getTag() instanceof ClassType) {
            return (ClassType) klass.getTag();
        } else {
            return null;
        }
    }

    public static void setClassAnnotation(RubyModule klass, ClassType type) {
        klass.setTag(type);
    }
    
    public static void setClassAnnotation(RubyModule klass, List<TypeAnnotation> annotations) {
        for (TypeAnnotation annot : annotations) {
            if (annot instanceof ClassType) {
                if (klass.getTag() == null) {
                    setClassAnnotation(klass, (ClassType) annot);
                } else {
                    Logger.warn("Class already has annotation");
                }
                break;
            }
        }
    }

    public static void setMethodAnnotation(Method method, List<TypeAnnotation> annotations) {
        if (!annotations.isEmpty()) {
            List<MethodType> types = new ArrayList<MethodType>();
            for (TypeAnnotation annot : annotations) {
                if (annot instanceof MethodType) {
                    types.add((MethodType) annot);
                }
            }
            method.setAnnotations(types);
        }
    }

    public static ClassType getEnclosingClassAnnotation(RubyModule module) {
        ClassType type;
        while (module != null) {
            type = getClassAnnotation(module);
            if (type != null) {
                return type;
            }
            module = module.getParent();
        }
        return null;
    }

    public static void mergeAffectedMap(Template template, IRubyObject receiver) {
        TypeVarMap map = getTypeVarMap(receiver);
        if (map != null && template.getAffectedMap() != null) {
            map.putAll(template.getAffectedMap());
        }
    }
}
