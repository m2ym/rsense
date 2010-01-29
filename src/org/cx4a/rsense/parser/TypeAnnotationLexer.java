// $ANTLR 3.2 Sep 23, 2009 12:02:23 src/org/cx4a/rsense/parser/TypeAnnotation.g 2010-01-28 18:58:05

package org.cx4a.rsense.parser;

import org.cx4a.rsense.typing.annotation.TypeAnnotation;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.typing.annotation.TypeExpression;
import org.cx4a.rsense.typing.annotation.TypeVariable;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TypeAnnotationLexer extends Lexer {
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

    public TypeAnnotationLexer() {;} 
    public TypeAnnotationLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TypeAnnotationLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "src/org/cx4a/rsense/parser/TypeAnnotation.g"; }

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:12:6: ( '.' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:12:8: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:13:6: ( '<' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:13:8: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:14:7: ( '>' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:14:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:15:7: ( ';' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:15:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:16:7: ( ':' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:16:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:17:7: ( '(' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:17:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:18:7: ( ')' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:18:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:19:7: ( '->' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:19:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:20:7: ( '{' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:20:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:21:7: ( '}' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:21:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:22:7: ( '=' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:22:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:23:7: ( ',' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:23:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:24:7: ( '?' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:24:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:25:7: ( '*' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:25:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:26:7: ( 'or' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:26:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:5: ( ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( '\\'' )* )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:7: ( 'a' .. 'z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( '\\'' )*
            {
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:7: ( 'a' .. 'z' )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:8: 'a' .. 'z'
            {
            matchRange('a','z'); 

            }

            // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:18: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // src/org/cx4a/rsense/parser/TypeAnnotation.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:52: ( '\\'' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\'') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // src/org/cx4a/rsense/parser/TypeAnnotation.g:161:52: '\\''
            	    {
            	    match('\''); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "CONST_ID"
    public final void mCONST_ID() throws RecognitionException {
        try {
            int _type = CONST_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:165:5: ( 'A' .. 'Z' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:165:7: 'A' .. 'Z' ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            {
            matchRange('A','Z'); 
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:165:16: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // src/org/cx4a/rsense/parser/TypeAnnotation.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST_ID"

    // $ANTLR start "METHOD_NAME"
    public final void mMETHOD_NAME() throws RecognitionException {
        try {
            int _type = METHOD_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:169:5: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( '!' | '?' | '=' )? | ( '..' | '...' ) | ( '+' | '+@' ) | ( '-' | '-@' ) | '\"' ( '*' | '**' ) '\"' | '/' | '%' | '|' | '^' | '&' | '\"' ( '<' | '<<' | '<=' | '<=>' ) '\"' | '\"' ( '>' | '>>' | '>=' ) '\"' | ( '=' | '==' | '===' | '=~' ) | ( '!' | '!=' | '!~' | '!@' ) | ( '~' | '~@' ) | ( '[]' | '[]=' ) | '::' | '`' )
            int alt16=18;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:169:7: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( '!' | '?' | '=' )?
                    {
                    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:169:27: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // src/org/cx4a/rsense/parser/TypeAnnotation.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:169:61: ( '!' | '?' | '=' )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='!'||LA5_0=='='||LA5_0=='?') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:
                            {
                            if ( input.LA(1)=='!'||input.LA(1)=='='||input.LA(1)=='?' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:170:7: ( '..' | '...' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:170:7: ( '..' | '...' )
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='.') ) {
                        int LA6_1 = input.LA(2);

                        if ( (LA6_1=='.') ) {
                            int LA6_2 = input.LA(3);

                            if ( (LA6_2=='.') ) {
                                alt6=2;
                            }
                            else {
                                alt6=1;}
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 6, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 0, input);

                        throw nvae;
                    }
                    switch (alt6) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:170:8: '..'
                            {
                            match(".."); 


                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:170:13: '...'
                            {
                            match("..."); 


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:171:7: ( '+' | '+@' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:171:7: ( '+' | '+@' )
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='+') ) {
                        int LA7_1 = input.LA(2);

                        if ( (LA7_1=='@') ) {
                            alt7=2;
                        }
                        else {
                            alt7=1;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 0, input);

                        throw nvae;
                    }
                    switch (alt7) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:171:8: '+'
                            {
                            match('+'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:171:12: '+@'
                            {
                            match("+@"); 


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:172:7: ( '-' | '-@' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:172:7: ( '-' | '-@' )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='-') ) {
                        int LA8_1 = input.LA(2);

                        if ( (LA8_1=='@') ) {
                            alt8=2;
                        }
                        else {
                            alt8=1;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 0, input);

                        throw nvae;
                    }
                    switch (alt8) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:172:8: '-'
                            {
                            match('-'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:172:12: '-@'
                            {
                            match("-@"); 


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:173:7: '\"' ( '*' | '**' ) '\"'
                    {
                    match('\"'); 
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:173:11: ( '*' | '**' )
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='*') ) {
                        int LA9_1 = input.LA(2);

                        if ( (LA9_1=='*') ) {
                            alt9=2;
                        }
                        else if ( (LA9_1=='\"') ) {
                            alt9=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 9, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 0, input);

                        throw nvae;
                    }
                    switch (alt9) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:173:12: '*'
                            {
                            match('*'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:173:16: '**'
                            {
                            match("**"); 


                            }
                            break;

                    }

                    match('\"'); 

                    }
                    break;
                case 6 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:174:7: '/'
                    {
                    match('/'); 

                    }
                    break;
                case 7 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:175:7: '%'
                    {
                    match('%'); 

                    }
                    break;
                case 8 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:176:7: '|'
                    {
                    match('|'); 

                    }
                    break;
                case 9 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:177:7: '^'
                    {
                    match('^'); 

                    }
                    break;
                case 10 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:178:7: '&'
                    {
                    match('&'); 

                    }
                    break;
                case 11 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:179:7: '\"' ( '<' | '<<' | '<=' | '<=>' ) '\"'
                    {
                    match('\"'); 
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:179:11: ( '<' | '<<' | '<=' | '<=>' )
                    int alt10=4;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='<') ) {
                        switch ( input.LA(2) ) {
                        case '<':
                            {
                            alt10=2;
                            }
                            break;
                        case '=':
                            {
                            int LA10_3 = input.LA(3);

                            if ( (LA10_3=='>') ) {
                                alt10=4;
                            }
                            else if ( (LA10_3=='\"') ) {
                                alt10=3;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 10, 3, input);

                                throw nvae;
                            }
                            }
                            break;
                        case '\"':
                            {
                            alt10=1;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 10, 1, input);

                            throw nvae;
                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 0, input);

                        throw nvae;
                    }
                    switch (alt10) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:179:12: '<'
                            {
                            match('<'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:179:16: '<<'
                            {
                            match("<<"); 


                            }
                            break;
                        case 3 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:179:21: '<='
                            {
                            match("<="); 


                            }
                            break;
                        case 4 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:179:26: '<=>'
                            {
                            match("<=>"); 


                            }
                            break;

                    }

                    match('\"'); 

                    }
                    break;
                case 12 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:180:7: '\"' ( '>' | '>>' | '>=' ) '\"'
                    {
                    match('\"'); 
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:180:11: ( '>' | '>>' | '>=' )
                    int alt11=3;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='>') ) {
                        switch ( input.LA(2) ) {
                        case '>':
                            {
                            alt11=2;
                            }
                            break;
                        case '=':
                            {
                            alt11=3;
                            }
                            break;
                        case '\"':
                            {
                            alt11=1;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 11, 1, input);

                            throw nvae;
                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 0, input);

                        throw nvae;
                    }
                    switch (alt11) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:180:12: '>'
                            {
                            match('>'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:180:16: '>>'
                            {
                            match(">>"); 


                            }
                            break;
                        case 3 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:180:21: '>='
                            {
                            match(">="); 


                            }
                            break;

                    }

                    match('\"'); 

                    }
                    break;
                case 13 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:181:7: ( '=' | '==' | '===' | '=~' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:181:7: ( '=' | '==' | '===' | '=~' )
                    int alt12=4;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='=') ) {
                        switch ( input.LA(2) ) {
                        case '=':
                            {
                            int LA12_2 = input.LA(3);

                            if ( (LA12_2=='=') ) {
                                alt12=3;
                            }
                            else {
                                alt12=2;}
                            }
                            break;
                        case '~':
                            {
                            alt12=4;
                            }
                            break;
                        default:
                            alt12=1;}

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:181:8: '='
                            {
                            match('='); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:181:12: '=='
                            {
                            match("=="); 


                            }
                            break;
                        case 3 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:181:17: '==='
                            {
                            match("==="); 


                            }
                            break;
                        case 4 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:181:23: '=~'
                            {
                            match("=~"); 


                            }
                            break;

                    }


                    }
                    break;
                case 14 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:182:7: ( '!' | '!=' | '!~' | '!@' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:182:7: ( '!' | '!=' | '!~' | '!@' )
                    int alt13=4;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='!') ) {
                        switch ( input.LA(2) ) {
                        case '=':
                            {
                            alt13=2;
                            }
                            break;
                        case '~':
                            {
                            alt13=3;
                            }
                            break;
                        case '@':
                            {
                            alt13=4;
                            }
                            break;
                        default:
                            alt13=1;}

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:182:8: '!'
                            {
                            match('!'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:182:12: '!='
                            {
                            match("!="); 


                            }
                            break;
                        case 3 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:182:17: '!~'
                            {
                            match("!~"); 


                            }
                            break;
                        case 4 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:182:22: '!@'
                            {
                            match("!@"); 


                            }
                            break;

                    }


                    }
                    break;
                case 15 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:183:7: ( '~' | '~@' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:183:7: ( '~' | '~@' )
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='~') ) {
                        int LA14_1 = input.LA(2);

                        if ( (LA14_1=='@') ) {
                            alt14=2;
                        }
                        else {
                            alt14=1;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 0, input);

                        throw nvae;
                    }
                    switch (alt14) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:183:8: '~'
                            {
                            match('~'); 

                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:183:12: '~@'
                            {
                            match("~@"); 


                            }
                            break;

                    }


                    }
                    break;
                case 16 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:184:7: ( '[]' | '[]=' )
                    {
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:184:7: ( '[]' | '[]=' )
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='[') ) {
                        int LA15_1 = input.LA(2);

                        if ( (LA15_1==']') ) {
                            int LA15_2 = input.LA(3);

                            if ( (LA15_2=='=') ) {
                                alt15=2;
                            }
                            else {
                                alt15=1;}
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 15, 1, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 0, input);

                        throw nvae;
                    }
                    switch (alt15) {
                        case 1 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:184:8: '[]'
                            {
                            match("[]"); 


                            }
                            break;
                        case 2 :
                            // src/org/cx4a/rsense/parser/TypeAnnotation.g:184:13: '[]='
                            {
                            match("[]="); 


                            }
                            break;

                    }


                    }
                    break;
                case 17 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:185:7: '::'
                    {
                    match("::"); 


                    }
                    break;
                case 18 :
                    // src/org/cx4a/rsense/parser/TypeAnnotation.g:186:7: '`'
                    {
                    match('`'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "METHOD_NAME"

    // $ANTLR start "WHITEESPACE"
    public final void mWHITEESPACE() throws RecognitionException {
        try {
            int _type = WHITEESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:189:13: ( ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' ) )
            // src/org/cx4a/rsense/parser/TypeAnnotation.g:189:15: ( ' ' | '\\t' | '\\f' | '\\n' | '\\r' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITEESPACE"

    public void mTokens() throws RecognitionException {
        // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:8: ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | ID | CONST_ID | METHOD_NAME | WHITEESPACE )
        int alt17=19;
        alt17 = dfa17.predict(input);
        switch (alt17) {
            case 1 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:10: T__8
                {
                mT__8(); 

                }
                break;
            case 2 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:15: T__9
                {
                mT__9(); 

                }
                break;
            case 3 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:20: T__10
                {
                mT__10(); 

                }
                break;
            case 4 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:26: T__11
                {
                mT__11(); 

                }
                break;
            case 5 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:32: T__12
                {
                mT__12(); 

                }
                break;
            case 6 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:38: T__13
                {
                mT__13(); 

                }
                break;
            case 7 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:44: T__14
                {
                mT__14(); 

                }
                break;
            case 8 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:50: T__15
                {
                mT__15(); 

                }
                break;
            case 9 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:56: T__16
                {
                mT__16(); 

                }
                break;
            case 10 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:62: T__17
                {
                mT__17(); 

                }
                break;
            case 11 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:68: T__18
                {
                mT__18(); 

                }
                break;
            case 12 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:74: T__19
                {
                mT__19(); 

                }
                break;
            case 13 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:80: T__20
                {
                mT__20(); 

                }
                break;
            case 14 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:86: T__21
                {
                mT__21(); 

                }
                break;
            case 15 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:92: T__22
                {
                mT__22(); 

                }
                break;
            case 16 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:98: ID
                {
                mID(); 

                }
                break;
            case 17 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:101: CONST_ID
                {
                mCONST_ID(); 

                }
                break;
            case 18 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:110: METHOD_NAME
                {
                mMETHOD_NAME(); 

                }
                break;
            case 19 :
                // src/org/cx4a/rsense/parser/TypeAnnotation.g:1:122: WHITEESPACE
                {
                mWHITEESPACE(); 

                }
                break;

        }

    }


    protected DFA16 dfa16 = new DFA16(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA16_eotS =
        "\24\uffff";
    static final String DFA16_eofS =
        "\24\uffff";
    static final String DFA16_minS =
        "\1\41\4\uffff\1\52\16\uffff";
    static final String DFA16_maxS =
        "\1\176\4\uffff\1\76\16\uffff";
    static final String DFA16_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\uffff\1\6\1\7\1\10\1\11\1\12\1\15\1\16"+
        "\1\17\1\20\1\21\1\22\1\14\1\13\1\5";
    static final String DFA16_specialS =
        "\24\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\14\1\5\2\uffff\1\7\1\12\4\uffff\1\3\1\uffff\1\4\1\2\1\6\12"+
            "\uffff\1\17\2\uffff\1\13\3\uffff\32\1\1\16\2\uffff\1\11\1\uffff"+
            "\1\20\32\1\1\uffff\1\10\1\uffff\1\15",
            "",
            "",
            "",
            "",
            "\1\23\21\uffff\1\22\1\uffff\1\21",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "168:1: METHOD_NAME : ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( '!' | '?' | '=' )? | ( '..' | '...' ) | ( '+' | '+@' ) | ( '-' | '-@' ) | '\"' ( '*' | '**' ) '\"' | '/' | '%' | '|' | '^' | '&' | '\"' ( '<' | '<<' | '<=' | '<=>' ) '\"' | '\"' ( '>' | '>>' | '>=' ) '\"' | ( '=' | '==' | '===' | '=~' ) | ( '!' | '!=' | '!~' | '!@' ) | ( '~' | '~@' ) | ( '[]' | '[]=' ) | '::' | '`' );";
        }
    }
    static final String DFA17_eotS =
        "\1\uffff\1\24\3\uffff\1\25\2\uffff\1\22\2\uffff\1\27\3\uffff\2\32"+
        "\1\34\6\uffff\1\35\1\32\1\uffff\1\34\2\uffff";
    static final String DFA17_eofS =
        "\36\uffff";
    static final String DFA17_minS =
        "\1\11\1\56\3\uffff\1\72\2\uffff\1\76\2\uffff\1\75\3\uffff\3\41\6"+
        "\uffff\2\41\1\uffff\1\41\2\uffff";
    static final String DFA17_maxS =
        "\1\176\1\56\3\uffff\1\72\2\uffff\1\76\2\uffff\1\176\3\uffff\3\172"+
        "\6\uffff\2\172\1\uffff\1\172\2\uffff";
    static final String DFA17_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\uffff\1\6\1\7\1\uffff\1\11\1\12\1\uffff\1"+
        "\14\1\15\1\16\3\uffff\1\22\1\23\1\1\1\5\1\10\1\13\2\uffff\1\20\1"+
        "\uffff\1\21\1\17";
    static final String DFA17_specialS =
        "\36\uffff}>";
    static final String[] DFA17_transitionS = {
            "\2\23\1\uffff\2\23\22\uffff\1\23\2\22\2\uffff\2\22\1\uffff\1"+
            "\6\1\7\1\16\1\22\1\14\1\10\1\1\1\22\12\uffff\1\5\1\4\1\2\1\13"+
            "\1\3\1\15\1\uffff\32\21\1\22\2\uffff\1\22\1\uffff\1\22\16\20"+
            "\1\17\13\20\1\11\1\22\1\12\1\22",
            "\1\22",
            "",
            "",
            "",
            "\1\22",
            "",
            "",
            "\1\26",
            "",
            "",
            "\1\22\100\uffff\1\22",
            "",
            "",
            "",
            "\1\22\16\uffff\12\31\3\uffff\1\22\1\uffff\1\22\1\uffff\32\31"+
            "\4\uffff\1\31\1\uffff\21\31\1\30\10\31",
            "\1\22\16\uffff\12\31\3\uffff\1\22\1\uffff\1\22\1\uffff\32\31"+
            "\4\uffff\1\31\1\uffff\32\31",
            "\1\22\16\uffff\12\33\3\uffff\1\22\1\uffff\1\22\1\uffff\32\33"+
            "\4\uffff\1\33\1\uffff\32\33",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\22\5\uffff\1\32\10\uffff\12\31\3\uffff\1\22\1\uffff\1\22"+
            "\1\uffff\32\31\4\uffff\1\31\1\uffff\32\31",
            "\1\22\16\uffff\12\31\3\uffff\1\22\1\uffff\1\22\1\uffff\32\31"+
            "\4\uffff\1\31\1\uffff\32\31",
            "",
            "\1\22\16\uffff\12\33\3\uffff\1\22\1\uffff\1\22\1\uffff\32\33"+
            "\4\uffff\1\33\1\uffff\32\33",
            "",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | ID | CONST_ID | METHOD_NAME | WHITEESPACE );";
        }
    }
 

}