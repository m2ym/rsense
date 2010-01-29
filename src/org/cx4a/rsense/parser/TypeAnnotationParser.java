// $ANTLR 3.2 Sep 23, 2009 12:02:23 src/org/cx4a/rsense/parser/TypeAnnotation.g 2010-01-28 18:58:05

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class TypeAnnotationParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "CONST_ID", "METHOD_NAME", "WHITEESPACE", "'.'", "'<'", "'>'", "';'", "':'", "'('", "')'", "'->'", "'{'", "'}'", "'='", "','", "'?'", "'*'", "'or'"
    };
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int WHITEESPACE=7;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int CONST_ID=5;
    public static final int T__8=8;
    public static final int METHOD_NAME=6;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;

    // delegates
    // delegators


        public TypeAnnotationParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TypeAnnotationParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return TypeAnnotationParser.tokenNames; }
    public String getGrammarFileName() { return "src/org/cx4a/rsense/parser/TypeAnnotation.g"; }





    // $ANTLR start "type"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:41:1: type returns [TypeAnnotation value] : ( method_type | class_type );
    public final TypeAnnotation type() throws RecognitionException {
        TypeAnnotation value = null;

        MethodType method_type1 = null;

        ClassType class_type2 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:42:5: ( method_type | class_type )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==CONST_ID) ) {
                int LA1_1 = input.LA(2);

                if ( (synpred1_TypeAnnotation()) ) {
                    alt1=1;
                }
                else if ( (true) ) {
                    alt1=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return value;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA1_0==ID||LA1_0==METHOD_NAME) ) {
                alt1=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:42:7: method_type
                    {
                    pushFollow(FOLLOW_method_type_in_type53);
                    method_type1=method_type();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = method_type1; 
                    }

                    }
                    break;
                case 2 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:43:7: class_type
                    {
                    pushFollow(FOLLOW_class_type_in_type63);
                    class_type2=class_type();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = class_type2; 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "type"


    // $ANTLR start "method_type"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:46:1: method_type returns [MethodType value] : ( ( ID | CONST_ID ) '.' )* method_name ( '<' type_var_list '>' ( ';' constraint_list )? )? ':' method_sig ;
    public final MethodType method_type() throws RecognitionException {
        MethodType value = null;

        TypeAnnotationParser.method_name_return method_name3 = null;

        List<TypeVariable> type_var_list4 = null;

        List<TypeConstraint> constraint_list5 = null;

        MethodType.Signature method_sig6 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:5: ( ( ( ID | CONST_ID ) '.' )* method_name ( '<' type_var_list '>' ( ';' constraint_list )? )? ':' method_sig )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:7: ( ( ID | CONST_ID ) '.' )* method_name ( '<' type_var_list '>' ( ';' constraint_list )? )? ':' method_sig
            {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:7: ( ( ID | CONST_ID ) '.' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=ID && LA2_0<=CONST_ID)) ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1==8) ) {
                        alt2=1;
                    }


                }


                switch (alt2) {
            	case 1 :
            	    // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:8: ( ID | CONST_ID ) '.'
            	    {
            	    if ( (input.LA(1)>=ID && input.LA(1)<=CONST_ID) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return value;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    match(input,8,FOLLOW_8_in_method_type93); if (state.failed) return value;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            pushFollow(FOLLOW_method_name_in_method_type97);
            method_name3=method_name();

            state._fsp--;
            if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:40: ( '<' type_var_list '>' ( ';' constraint_list )? )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==9) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:41: '<' type_var_list '>' ( ';' constraint_list )?
                    {
                    match(input,9,FOLLOW_9_in_method_type100); if (state.failed) return value;
                    pushFollow(FOLLOW_type_var_list_in_method_type102);
                    type_var_list4=type_var_list();

                    state._fsp--;
                    if (state.failed) return value;
                    match(input,10,FOLLOW_10_in_method_type104); if (state.failed) return value;
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:63: ( ';' constraint_list )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==11) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:47:64: ';' constraint_list
                            {
                            match(input,11,FOLLOW_11_in_method_type107); if (state.failed) return value;
                            pushFollow(FOLLOW_constraint_list_in_method_type109);
                            constraint_list5=constraint_list();

                            state._fsp--;
                            if (state.failed) return value;

                            }
                            break;

                    }


                    }
                    break;

            }

            match(input,12,FOLLOW_12_in_method_type115); if (state.failed) return value;
            pushFollow(FOLLOW_method_sig_in_method_type117);
            method_sig6=method_sig();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new MethodType((method_name3!=null?input.toString(method_name3.start,method_name3.stop):null), type_var_list4, constraint_list5, method_sig6);
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "method_type"


    // $ANTLR start "method_sig"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:52:1: method_sig returns [MethodType.Signature value] : ( '(' ')' ( block )? '->' type_expr | t1= type_expr ( block )? '->' t2= type_expr );
    public final MethodType.Signature method_sig() throws RecognitionException {
        MethodType.Signature value = null;

        TypeExpression t1 = null;

        TypeExpression t2 = null;

        MethodType.Block block7 = null;

        TypeExpression type_expr8 = null;

        MethodType.Block block9 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:53:5: ( '(' ')' ( block )? '->' type_expr | t1= type_expr ( block )? '->' t2= type_expr )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==13) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==14) ) {
                    alt7=1;
                }
                else if ( ((LA7_1>=ID && LA7_1<=CONST_ID)||LA7_1==13||(LA7_1>=20 && LA7_1<=21)) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return value;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else if ( ((LA7_0>=ID && LA7_0<=CONST_ID)||(LA7_0>=20 && LA7_0<=21)) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:53:7: '(' ')' ( block )? '->' type_expr
                    {
                    match(input,13,FOLLOW_13_in_method_sig140); if (state.failed) return value;
                    match(input,14,FOLLOW_14_in_method_sig142); if (state.failed) return value;
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:53:15: ( block )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==16) ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:0:0: block
                            {
                            pushFollow(FOLLOW_block_in_method_sig144);
                            block7=block();

                            state._fsp--;
                            if (state.failed) return value;

                            }
                            break;

                    }

                    match(input,15,FOLLOW_15_in_method_sig147); if (state.failed) return value;
                    pushFollow(FOLLOW_type_expr_in_method_sig149);
                    type_expr8=type_expr();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new MethodType.Signature(null, block7, type_expr8); 
                    }

                    }
                    break;
                case 2 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:54:7: t1= type_expr ( block )? '->' t2= type_expr
                    {
                    pushFollow(FOLLOW_type_expr_in_method_sig161);
                    t1=type_expr();

                    state._fsp--;
                    if (state.failed) return value;
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:54:20: ( block )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==16) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:0:0: block
                            {
                            pushFollow(FOLLOW_block_in_method_sig163);
                            block9=block();

                            state._fsp--;
                            if (state.failed) return value;

                            }
                            break;

                    }

                    match(input,15,FOLLOW_15_in_method_sig166); if (state.failed) return value;
                    pushFollow(FOLLOW_type_expr_in_method_sig170);
                    t2=type_expr();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new MethodType.Signature(t1, block9, t2); 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "method_sig"


    // $ANTLR start "block"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:57:1: block returns [MethodType.Block value] : '{' method_sig '}' ;
    public final MethodType.Block block() throws RecognitionException {
        MethodType.Block value = null;

        MethodType.Signature method_sig10 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:58:5: ( '{' method_sig '}' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:58:7: '{' method_sig '}'
            {
            match(input,16,FOLLOW_16_in_block193); if (state.failed) return value;
            pushFollow(FOLLOW_method_sig_in_block195);
            method_sig10=method_sig();

            state._fsp--;
            if (state.failed) return value;
            match(input,17,FOLLOW_17_in_block197); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new MethodType.Block(method_sig10); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "block"

    public static class method_name_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "method_name"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:61:1: method_name : ( METHOD_NAME | ID | CONST_ID );
    public final TypeAnnotationParser.method_name_return method_name() throws RecognitionException {
        TypeAnnotationParser.method_name_return retval = new TypeAnnotationParser.method_name_return();
        retval.start = input.LT(1);

        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:62:5: ( METHOD_NAME | ID | CONST_ID )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:
            {
            if ( (input.LA(1)>=ID && input.LA(1)<=METHOD_NAME) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "method_name"


    // $ANTLR start "class_type"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:67:1: class_type returns [ClassType value] : CONST_ID ( '<' type_var_list '>' )? ( ';' constraint_list )? ;
    public final ClassType class_type() throws RecognitionException {
        ClassType value = null;

        Token CONST_ID11=null;
        List<TypeVariable> type_var_list12 = null;

        List<TypeConstraint> constraint_list13 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:68:5: ( CONST_ID ( '<' type_var_list '>' )? ( ';' constraint_list )? )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:68:7: CONST_ID ( '<' type_var_list '>' )? ( ';' constraint_list )?
            {
            CONST_ID11=(Token)match(input,CONST_ID,FOLLOW_CONST_ID_in_class_type253); if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:68:16: ( '<' type_var_list '>' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==9) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:68:17: '<' type_var_list '>'
                    {
                    match(input,9,FOLLOW_9_in_class_type256); if (state.failed) return value;
                    pushFollow(FOLLOW_type_var_list_in_class_type258);
                    type_var_list12=type_var_list();

                    state._fsp--;
                    if (state.failed) return value;
                    match(input,10,FOLLOW_10_in_class_type260); if (state.failed) return value;

                    }
                    break;

            }

            // src/org/cx4a/rsense/parser/TypeAnnotation.g:68:41: ( ';' constraint_list )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==11) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:68:42: ';' constraint_list
                    {
                    match(input,11,FOLLOW_11_in_class_type265); if (state.failed) return value;
                    pushFollow(FOLLOW_constraint_list_in_class_type267);
                    constraint_list13=constraint_list();

                    state._fsp--;
                    if (state.failed) return value;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                          value = new ClassType((CONST_ID11!=null?CONST_ID11.getText():null), type_var_list12, constraint_list13);
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "class_type"


    // $ANTLR start "constraint_list"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:73:1: constraint_list returns [List<TypeConstraint> value] : type_var '<' '=' type_expr ( ',' rest= constraint_list )? ;
    public final List<TypeConstraint> constraint_list() throws RecognitionException {
        List<TypeConstraint> value = null;

        List<TypeConstraint> rest = null;

        TypeVariable type_var14 = null;

        TypeExpression type_expr15 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:74:5: ( type_var '<' '=' type_expr ( ',' rest= constraint_list )? )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:74:7: type_var '<' '=' type_expr ( ',' rest= constraint_list )?
            {
            pushFollow(FOLLOW_type_var_in_constraint_list292);
            type_var14=type_var();

            state._fsp--;
            if (state.failed) return value;
            match(input,9,FOLLOW_9_in_constraint_list294); if (state.failed) return value;
            match(input,18,FOLLOW_18_in_constraint_list296); if (state.failed) return value;
            pushFollow(FOLLOW_type_expr_in_constraint_list298);
            type_expr15=type_expr();

            state._fsp--;
            if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:74:34: ( ',' rest= constraint_list )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==19) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:74:35: ',' rest= constraint_list
                    {
                    match(input,19,FOLLOW_19_in_constraint_list301); if (state.failed) return value;
                    pushFollow(FOLLOW_constraint_list_in_constraint_list305);
                    rest=constraint_list();

                    state._fsp--;
                    if (state.failed) return value;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                          value = new ArrayList<TypeConstraint>();
                          value.add(new TypeConstraint(TypeExpression.Type.SUBTYPE_CONS, type_var14, type_expr15));
                          if (rest != null) {
                              value.addAll(rest);
                          }
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "constraint_list"


    // $ANTLR start "type_expr"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:83:1: type_expr returns [TypeExpression value] : single_type_expr ( or_type_list )? ;
    public final TypeExpression type_expr() throws RecognitionException {
        TypeExpression value = null;

        List<TypeExpression> or_type_list16 = null;

        TypeExpression single_type_expr17 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:84:5: ( single_type_expr ( or_type_list )? )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:84:7: single_type_expr ( or_type_list )?
            {
            pushFollow(FOLLOW_single_type_expr_in_type_expr330);
            single_type_expr17=single_type_expr();

            state._fsp--;
            if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:84:24: ( or_type_list )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==22) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:0:0: or_type_list
                    {
                    pushFollow(FOLLOW_or_type_list_in_type_expr332);
                    or_type_list16=or_type_list();

                    state._fsp--;
                    if (state.failed) return value;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                          if (or_type_list16 != null) {
                              TypeUnion union = new TypeUnion();
                              union.add(single_type_expr17);
                              union.addAll(or_type_list16);
                              value = union;
                          } else {
                              value = single_type_expr17;
                          }
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "type_expr"


    // $ANTLR start "type_expr_comma_list"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:96:1: type_expr_comma_list returns [List<TypeExpression> value] : type_expr ( ',' rest= type_expr_comma_list )? ;
    public final List<TypeExpression> type_expr_comma_list() throws RecognitionException {
        List<TypeExpression> value = null;

        List<TypeExpression> rest = null;

        TypeExpression type_expr18 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:97:5: ( type_expr ( ',' rest= type_expr_comma_list )? )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:97:7: type_expr ( ',' rest= type_expr_comma_list )?
            {
            pushFollow(FOLLOW_type_expr_in_type_expr_comma_list356);
            type_expr18=type_expr();

            state._fsp--;
            if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:97:17: ( ',' rest= type_expr_comma_list )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==19) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:97:18: ',' rest= type_expr_comma_list
                    {
                    match(input,19,FOLLOW_19_in_type_expr_comma_list359); if (state.failed) return value;
                    pushFollow(FOLLOW_type_expr_comma_list_in_type_expr_comma_list363);
                    rest=type_expr_comma_list();

                    state._fsp--;
                    if (state.failed) return value;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                          value = new ArrayList<TypeExpression>();
                          value.add(type_expr18);
                          if (rest != null) {
                              value.addAll(rest);
                          }
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "type_expr_comma_list"


    // $ANTLR start "tuple"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:106:1: tuple returns [TypeTuple value] : '(' type_expr_comma_list ')' ;
    public final TypeTuple tuple() throws RecognitionException {
        TypeTuple value = null;

        List<TypeExpression> type_expr_comma_list19 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:107:5: ( '(' type_expr_comma_list ')' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:107:7: '(' type_expr_comma_list ')'
            {
            match(input,13,FOLLOW_13_in_tuple396); if (state.failed) return value;
            pushFollow(FOLLOW_type_expr_comma_list_in_tuple398);
            type_expr_comma_list19=type_expr_comma_list();

            state._fsp--;
            if (state.failed) return value;
            match(input,14,FOLLOW_14_in_tuple400); if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = new TypeTuple(type_expr_comma_list19);
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "tuple"


    // $ANTLR start "single_type_expr"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:112:1: single_type_expr returns [TypeExpression value] : ( type_var | type_ident ( '<' type_expr_comma_list '>' )? | tuple | '?' (expr= single_type_expr )? | '*' expr= single_type_expr );
    public final TypeExpression single_type_expr() throws RecognitionException {
        TypeExpression value = null;

        TypeExpression expr = null;

        TypeVariable type_var20 = null;

        List<TypeExpression> type_expr_comma_list21 = null;

        TypeIdentity type_ident22 = null;

        TypeTuple tuple23 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:113:5: ( type_var | type_ident ( '<' type_expr_comma_list '>' )? | tuple | '?' (expr= single_type_expr )? | '*' expr= single_type_expr )
            int alt15=5;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt15=1;
                }
                break;
            case CONST_ID:
                {
                alt15=2;
                }
                break;
            case 13:
                {
                alt15=3;
                }
                break;
            case 20:
                {
                alt15=4;
                }
                break;
            case 21:
                {
                alt15=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:113:7: type_var
                    {
                    pushFollow(FOLLOW_type_var_in_single_type_expr423);
                    type_var20=type_var();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = type_var20; 
                    }

                    }
                    break;
                case 2 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:114:7: type_ident ( '<' type_expr_comma_list '>' )?
                    {
                    pushFollow(FOLLOW_type_ident_in_single_type_expr433);
                    type_ident22=type_ident();

                    state._fsp--;
                    if (state.failed) return value;
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:114:18: ( '<' type_expr_comma_list '>' )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==9) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:114:19: '<' type_expr_comma_list '>'
                            {
                            match(input,9,FOLLOW_9_in_single_type_expr436); if (state.failed) return value;
                            pushFollow(FOLLOW_type_expr_comma_list_in_single_type_expr438);
                            type_expr_comma_list21=type_expr_comma_list();

                            state._fsp--;
                            if (state.failed) return value;
                            match(input,10,FOLLOW_10_in_single_type_expr440); if (state.failed) return value;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                  if (type_expr_comma_list21 != null) {
                                      value = new TypeApplication(type_ident22, type_expr_comma_list21);
                                  } else {
                                      value = type_ident22;
                                  }
                              
                    }

                    }
                    break;
                case 3 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:121:7: tuple
                    {
                    pushFollow(FOLLOW_tuple_in_single_type_expr452);
                    tuple23=tuple();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = tuple23; 
                    }

                    }
                    break;
                case 4 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:122:7: '?' (expr= single_type_expr )?
                    {
                    match(input,20,FOLLOW_20_in_single_type_expr462); if (state.failed) return value;
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:122:15: (expr= single_type_expr )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( ((LA14_0>=ID && LA14_0<=CONST_ID)||LA14_0==13||(LA14_0>=20 && LA14_0<=21)) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:0:0: expr= single_type_expr
                            {
                            pushFollow(FOLLOW_single_type_expr_in_single_type_expr466);
                            expr=single_type_expr();

                            state._fsp--;
                            if (state.failed) return value;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                  if (expr != null) {
                                      value = new TypeOptional(expr);
                                  } else {
                                      value = new TypeAny();
                                  }
                              
                    }

                    }
                    break;
                case 5 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:129:7: '*' expr= single_type_expr
                    {
                    match(input,21,FOLLOW_21_in_single_type_expr477); if (state.failed) return value;
                    pushFollow(FOLLOW_single_type_expr_in_single_type_expr481);
                    expr=single_type_expr();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = new TypeSplat(expr); 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "single_type_expr"


    // $ANTLR start "or_type_list"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:132:1: or_type_list returns [List<TypeExpression> value] : 'or' single_type_expr (rest= or_type_list )? ;
    public final List<TypeExpression> or_type_list() throws RecognitionException {
        List<TypeExpression> value = null;

        List<TypeExpression> rest = null;

        TypeExpression single_type_expr24 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:133:5: ( 'or' single_type_expr (rest= or_type_list )? )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:133:7: 'or' single_type_expr (rest= or_type_list )?
            {
            match(input,22,FOLLOW_22_in_or_type_list504); if (state.failed) return value;
            pushFollow(FOLLOW_single_type_expr_in_or_type_list506);
            single_type_expr24=single_type_expr();

            state._fsp--;
            if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:133:33: (rest= or_type_list )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==22) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:0:0: rest= or_type_list
                    {
                    pushFollow(FOLLOW_or_type_list_in_or_type_list510);
                    rest=or_type_list();

                    state._fsp--;
                    if (state.failed) return value;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                          value = new ArrayList<TypeExpression>();
                          value.add(single_type_expr24);
                          if (rest != null) {
                              value.addAll(rest);
                          }
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "or_type_list"


    // $ANTLR start "type_ident"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:142:1: type_ident returns [TypeIdentity value] : CONST_ID ;
    public final TypeIdentity type_ident() throws RecognitionException {
        TypeIdentity value = null;

        Token CONST_ID25=null;

        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:143:5: ( CONST_ID )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:143:7: CONST_ID
            {
            CONST_ID25=(Token)match(input,CONST_ID,FOLLOW_CONST_ID_in_type_ident534); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = TypeIdentity.newRelativeIdentity((CONST_ID25!=null?CONST_ID25.getText():null)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "type_ident"


    // $ANTLR start "type_var"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:146:1: type_var returns [TypeVariable value] : ID ;
    public final TypeVariable type_var() throws RecognitionException {
        TypeVariable value = null;

        Token ID26=null;

        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:147:5: ( ID )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:147:7: ID
            {
            ID26=(Token)match(input,ID,FOLLOW_ID_in_type_var557); if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = new TypeVariable((ID26!=null?ID26.getText():null)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "type_var"


    // $ANTLR start "type_var_list"
    // src/org/cx4a/rsense/parser/TypeAnnotation.g:150:1: type_var_list returns [List<TypeVariable> value] : type_var ( ',' rest= type_var_list )? ;
    public final List<TypeVariable> type_var_list() throws RecognitionException {
        List<TypeVariable> value = null;

        List<TypeVariable> rest = null;

        TypeVariable type_var27 = null;


        try {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:151:5: ( type_var ( ',' rest= type_var_list )? )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:151:7: type_var ( ',' rest= type_var_list )?
            {
            pushFollow(FOLLOW_type_var_in_type_var_list580);
            type_var27=type_var();

            state._fsp--;
            if (state.failed) return value;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:151:16: ( ',' rest= type_var_list )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==19) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:151:17: ',' rest= type_var_list
                    {
                    match(input,19,FOLLOW_19_in_type_var_list583); if (state.failed) return value;
                    pushFollow(FOLLOW_type_var_list_in_type_var_list587);
                    rest=type_var_list();

                    state._fsp--;
                    if (state.failed) return value;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                          value = new ArrayList<TypeVariable>();
                          value.add(type_var27);
                          if (rest != null) {
                              value.addAll(rest);
                          }
                      
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "type_var_list"

    // $ANTLR start synpred1_TypeAnnotation
    public final void synpred1_TypeAnnotation_fragment() throws RecognitionException {   
        // src/org/cx4a/rsense/parser/TypeAnnotation.g:42:7: ( method_type )
        // src/org/cx4a/rsense/parser/TypeAnnotation.g:42:7: method_type
        {
        pushFollow(FOLLOW_method_type_in_synpred1_TypeAnnotation53);
        method_type();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_TypeAnnotation

    // Delegated rules

    public final boolean synpred1_TypeAnnotation() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_TypeAnnotation_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_method_type_in_type53 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_class_type_in_type63 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_method_type87 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_method_type93 = new BitSet(new long[]{0x0000000000000070L});
    public static final BitSet FOLLOW_method_name_in_method_type97 = new BitSet(new long[]{0x0000000000001200L});
    public static final BitSet FOLLOW_9_in_method_type100 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_type_var_list_in_method_type102 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_method_type104 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_11_in_method_type107 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constraint_list_in_method_type109 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_method_type115 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_method_sig_in_method_type117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_method_sig140 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_method_sig142 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_block_in_method_sig144 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_method_sig147 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_type_expr_in_method_sig149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_expr_in_method_sig161 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_block_in_method_sig163 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_method_sig166 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_type_expr_in_method_sig170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_block193 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_method_sig_in_block195 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_block197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_method_name0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONST_ID_in_class_type253 = new BitSet(new long[]{0x0000000000000A02L});
    public static final BitSet FOLLOW_9_in_class_type256 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_type_var_list_in_class_type258 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_class_type260 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_11_in_class_type265 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constraint_list_in_class_type267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_var_in_constraint_list292 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_constraint_list294 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_constraint_list296 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_type_expr_in_constraint_list298 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_constraint_list301 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_constraint_list_in_constraint_list305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_single_type_expr_in_type_expr330 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_or_type_list_in_type_expr332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_expr_in_type_expr_comma_list356 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_type_expr_comma_list359 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_type_expr_comma_list_in_type_expr_comma_list363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_tuple396 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_type_expr_comma_list_in_tuple398 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_tuple400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_var_in_single_type_expr423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_ident_in_single_type_expr433 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_9_in_single_type_expr436 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_type_expr_comma_list_in_single_type_expr438 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_single_type_expr440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tuple_in_single_type_expr452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_single_type_expr462 = new BitSet(new long[]{0x0000000000302032L});
    public static final BitSet FOLLOW_single_type_expr_in_single_type_expr466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_single_type_expr477 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_single_type_expr_in_single_type_expr481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_or_type_list504 = new BitSet(new long[]{0x0000000000302030L});
    public static final BitSet FOLLOW_single_type_expr_in_or_type_list506 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_or_type_list_in_or_type_list510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONST_ID_in_type_ident534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_type_var557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_var_in_type_var_list580 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_19_in_type_var_list583 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_type_var_list_in_type_var_list587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_method_type_in_synpred1_TypeAnnotation53 = new BitSet(new long[]{0x0000000000000002L});

}