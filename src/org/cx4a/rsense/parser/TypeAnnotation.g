grammar TypeAnnotation;

options {
    backtrack=true;
}

@header {
package org.cx4a.rsense.parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.annotation.ClassType;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.TypeUnion;
import org.cx4a.rsense.typing.annotation.TypeIdentity;
import org.cx4a.rsense.typing.annotation.TypeAny;
import org.cx4a.rsense.typing.annotation.TypeOptional;
import org.cx4a.rsense.typing.annotation.TypeTuple;
import org.cx4a.rsense.typing.annotation.TypeSplat;
import org.cx4a.rsense.typing.annotation.TypeApplication;
import org.cx4a.rsense.typing.annotation.TypeConstraint;
import org.cx4a.rsense.typing.annotation.TypePragma;
}

@lexer::header {
package org.cx4a.rsense.parser;

import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;
}

@members {
}

type returns [TypeAnnotation value]
    : method_type { $value = $method_type.value; }
    | class_type { $value = $class_type.value; }
    ;

method_type returns [MethodType value]
    : name=('::'? ((ID|CONST_ID) ('.'|'::'))* method_name) ('<' type_var_list? ('|' constraint_list)? '>')? method_sig {
            $value = new MethodType($name.text, $type_var_list.value, $constraint_list.value, $method_sig.value);
        }
    ;

method_sig returns [MethodType.Signature value]
    : '(' ')' block? '->' type_expr { $value = new MethodType.Signature(null, $block.value, $type_expr.value); }
    | t1=type_expr block? '->' t2=type_expr { $value = new MethodType.Signature($t1.value, $block.value, $t2.value); }
    ;

block returns [MethodType.Block value]
    : '{' method_sig '}' { $value = new MethodType.Block($method_sig.value); }
    ;

method_name
    : METHOD_NAME
    | ID
    | CONST_ID
    ;

class_type returns [ClassType value]
    : name=('::'? (CONST_ID '::')* CONST_ID) ('<' type_var_list? ('|' constraint_list)? '>')? pragma_list? {
            $value = new ClassType($name.text, $type_var_list.value, $constraint_list.value, $pragma_list.value);
        }
    ;

constraint_list returns [List<TypeConstraint> value]
    : e1=type_expr '<=' e2=type_expr (',' rest=constraint_list)? {
            $value = new ArrayList<TypeConstraint>();
            $value.add(new TypeConstraint(TypeExpression.Type.SUBTYPE_CONS, $e1.value, $e2.value));
            if ($rest.value != null) {
                $value.addAll($rest.value);
            }
        }
    ;

pragma_list returns [List<TypePragma> value]
    : pragma (',' rest=pragma_list)? {
            $value = new ArrayList<TypePragma>();
            $value.add($pragma.value);
            if ($rest.value != null) {
                $value.addAll($rest.value);
            }
        }
    ;

pragma returns [TypePragma value]
    : '!nobody' { $value = new TypePragma(TypeExpression.Type.NOBODY_PRAGMA); }
    ;

type_expr returns [TypeExpression value]
    : single_type_expr or_type_list? {
            if ($or_type_list.value != null) {
                TypeUnion union = new TypeUnion();
                union.add($single_type_expr.value);
                union.addAll($or_type_list.value);
                $value = union;
            } else {
                $value = $single_type_expr.value;
            }
        }
    ;

type_expr_comma_list returns [List<TypeExpression> value]
    : type_expr (',' rest=type_expr_comma_list)? {
            $value = new ArrayList<TypeExpression>();
            $value.add($type_expr.value);
            if ($rest.value != null) {
                $value.addAll($rest.value);
            }
        }
    ;
        
tuple returns [TypeTuple value]
    : '(' type_expr_comma_list ')' {
            $value = new TypeTuple($type_expr_comma_list.value);
        }
    | '[' type_expr_comma_list ']' { // syntax sugar
            $value = new TypeTuple($type_expr_comma_list.value);
        }
    ;

single_type_expr returns [TypeExpression value]
    : type_var { $value = $type_var.value; }
    | type_ident ('<' type_expr_comma_list '>')? {
            if ($type_expr_comma_list.value != null) {
                $value = new TypeApplication($type_ident.value, $type_expr_comma_list.value);
            } else {
                $value = $type_ident.value;
            }
        }
    | tuple { $value = $tuple.value; }
    | '?' (expr=single_type_expr? | '(' expr=type_expr ')') {
            if ($expr.value != null) {
                $value = new TypeOptional($expr.value);
            } else {
                $value = new TypeAny();
            }
        }
    | '*' (expr=single_type_expr | '(' expr=type_expr ')') { $value = new TypeSplat($expr.value); }
    ;

or_type_list returns [List<TypeExpression> value]
    : 'or' single_type_expr rest=or_type_list? {
            $value = new ArrayList<TypeExpression>();
            $value.add($single_type_expr.value);
            if ($rest.value != null) {
                $value.addAll($rest.value);
            }
        }
    ;

type_ident returns [TypeIdentity value]
    : CONST_ID ('::' id=type_ident)? {
            if ($id.value != null) {
                $value = TypeIdentity.newScopedIdentity($CONST_ID.text, $id.value);
            } else {
                $value = TypeIdentity.newRelativeIdentity($CONST_ID.text);
            }
        }
    | '::' id=type_ident {
            $value = TypeIdentity.newAbsoluteIdentity($id.value);
        }
    ;

type_var returns [TypeVariable value]
    : ID { $value = new TypeVariable($ID.text); }
    ;

type_var_list returns [List<TypeVariable> value]
    : type_var (',' rest=type_var_list)? {
            $value = new ArrayList<TypeVariable>();
            $value.add($type_var.value);
            if ($rest.value != null) {
                $value.addAll($rest.value);
            }
        }
    ;

ID
    : ('a'..'z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* '\''*
    ;

CONST_ID
    : 'A'..'Z' ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;

METHOD_NAME
    : '"' METHOD_NAME_UNQUOTED '"'
    | METHOD_NAME_UNQUOTED
    ;

fragment METHOD_NAME_UNQUOTED
    : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ('!'|'?'|'=')?
    | ('..'|'...')
    | ('+'|'+@')
    | ('-'|'-@')
    | ('*'|'**')
    | '/'
    | '%'
    | '|'
    | '^'
    | '&'
    | ('<'|'<<'|'<='|'<=>')
    | ('>'|'>>'|'>=')
    | ('='|'=='|'==='|'=~')
    | ('!'|'!='|'!~'|'!@')
    | ('~'|'~@')
    | ('[]'|'[]=')
    | '::'
    | '`'
    ;

WHITEESPACE : (' '|'\t'|'\f'|'\n'|'\r') { $channel=HIDDEN; } ;
