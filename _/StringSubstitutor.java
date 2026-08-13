
package org.apache.commons.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;

public class StringSubstitutor {
    private static final class Result {

        public final boolean altered;

        public final int lengthChange;

        private Result(final boolean altered, final int lengthChange) {
            this.altered = altered;
            this.lengthChange = lengthChange;
        }

        @Override
        public String toString() {
            return "Result [altered=" + altered + ", lengthChange=" + lengthChange + "]";
        }
    }
    public static final char DEFAULT_ESCAPE = '$';

    public static final String DEFAULT_VAR_DEFAULT = ":-";

    public static final String DEFAULT_VAR_END = "}";

    public static final String DEFAULT_VAR_START = "${";

    public static final StringMatcher DEFAULT_PREFIX = StringMatcherFactory.INSTANCE.stringMatcher(DEFAULT_VAR_START);

    public static final StringMatcher DEFAULT_SUFFIX = StringMatcherFactory.INSTANCE.stringMatcher(DEFAULT_VAR_END);

    public static final StringMatcher DEFAULT_VALUE_DELIMITER = StringMatcherFactory.INSTANCE
        .stringMatcher(DEFAULT_VAR_DEFAULT);

    public static StringSubstitutor createInterpolator() {
        return new StringSubstitutor(StringLookupFactory.INSTANCE.interpolatorStringLookup());
    }

    public static <V> String replace(final Object source, final Map<String, V> valueMap) {
        return new StringSubstitutor(valueMap).replace(source);
    }

    public static <V> String replace(final Object source, final Map<String, V> valueMap, final String prefix,
        final String suffix) {
        return new StringSubstitutor(valueMap, prefix, suffix).replace(source);
    }
    public static String replace(final Object source, final Properties valueProperties) {
        if (valueProperties == null) {
            return source.toString();
        }
        return StringSubstitutor.replace(source,
                valueProperties.stringPropertyNames().stream().collect(Collectors.toMap(Function.identity(), valueProperties::getProperty)));
    }

    public static String replaceSystemProperties(final Object source) {
        return new StringSubstitutor(StringLookupFactory.INSTANCE.systemPropertyStringLookup()).replace(source);
    }

    private boolean disableSubstitutionInValues;
    private boolean enableSubstitutionInVariables;
    private boolean failOnUndefinedVariable;
    private char escapeChar;
    private StringMatcher prefixMatcher;
    private boolean preserveEscapes;
    private StringMatcher suffixMatcher;
    private StringMatcher valueDelimiterMatcher;
    private StringLookup variableResolver;
    public StringSubstitutor() {
        this(null, DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_ESCAPE);
    }
    public <V> StringSubstitutor(final Map<String, V> valueMap) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(valueMap), DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_ESCAPE);
    }
    public <V> StringSubstitutor(final Map<String, V> valueMap, final String prefix, final String suffix) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(valueMap), prefix, suffix, DEFAULT_ESCAPE);
    }
    public <V> StringSubstitutor(final Map<String, V> valueMap, final String prefix, final String suffix,
        final char escape) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(valueMap), prefix, suffix, escape);
    }

    public <V> StringSubstitutor(final Map<String, V> valueMap, final String prefix, final String suffix,
        final char escape, final String valueDelimiter) {
        this(StringLookupFactory.INSTANCE.mapStringLookup(valueMap), prefix, suffix, escape, valueDelimiter);
    }

    public StringSubstitutor(final StringLookup variableResolver) {
        this(variableResolver, DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_ESCAPE);
    }

    public StringSubstitutor(final StringLookup variableResolver, final String prefix, final String suffix,
        final char escape) {
        setVariableResolver(variableResolver);
        setVariablePrefix(prefix);
        setVariableSuffix(suffix);
        setEscapeChar(escape);
        setValueDelimiterMatcher(DEFAULT_VALUE_DELIMITER);
    }

    public StringSubstitutor(final StringLookup variableResolver, final String prefix, final String suffix,
        final char escape, final String valueDelimiter) {
        setVariableResolver(variableResolver);
        setVariablePrefix(prefix);
        setVariableSuffix(suffix);
        setEscapeChar(escape);
        setValueDelimiter(valueDelimiter);
    }

    public StringSubstitutor(final StringLookup variableResolver, final StringMatcher prefixMatcher,
        final StringMatcher suffixMatcher, final char escape) {
        this(variableResolver, prefixMatcher, suffixMatcher, escape, DEFAULT_VALUE_DELIMITER);
    }

    public StringSubstitutor(final StringLookup variableResolver, final StringMatcher prefixMatcher,
        final StringMatcher suffixMatcher, final char escape, final StringMatcher valueDelimiterMatcher) {
        setVariableResolver(variableResolver);
        setVariablePrefixMatcher(prefixMatcher);
        setVariableSuffixMatcher(suffixMatcher);
        setEscapeChar(escape);
        setValueDelimiterMatcher(valueDelimiterMatcher);
    }

    public StringSubstitutor(final StringSubstitutor other) {
        disableSubstitutionInValues = other.isDisableSubstitutionInValues();
        enableSubstitutionInVariables = other.isEnableSubstitutionInVariables();
        failOnUndefinedVariable = other.isEnableUndefinedVariableException();
        escapeChar = other.getEscapeChar();
        prefixMatcher = other.getVariablePrefixMatcher();
        preserveEscapes = other.isPreserveEscapes();
        suffixMatcher = other.getVariableSuffixMatcher();
        valueDelimiterMatcher = other.getValueDelimiterMatcher();
        variableResolver = other.getStringLookup();
    }

    private void checkCyclicSubstitution(final String varName, final List<String> priorVariables) {
        if (!priorVariables.contains(varName)) {
            return;
        }
        final TextStringBuilder buf = new TextStringBuilder(256);
        buf.append("Infinite loop in property interpolation of ");
        buf.append(priorVariables.remove(0));
        buf.append(": ");
        buf.appendWithSeparators(priorVariables, "->");
        throw new IllegalStateException(buf.toString());
    }

    public char getEscapeChar() {
        return escapeChar;
    }
    public StringLookup getStringLookup() {
        return variableResolver;
    }

    public StringMatcher getValueDelimiterMatcher() {
        return valueDelimiterMatcher;
    }

    public StringMatcher getVariablePrefixMatcher() {
        return prefixMatcher;
    }

    public StringMatcher getVariableSuffixMatcher() {
        return suffixMatcher;
    }

    public boolean isDisableSubstitutionInValues() {
        return disableSubstitutionInValues;
    }

    public boolean isEnableSubstitutionInVariables() {
        return enableSubstitutionInVariables;
    }

    public boolean isEnableUndefinedVariableException() {
        return failOnUndefinedVariable;
    }

    public boolean isPreserveEscapes() {
        return preserveEscapes;
    }

    public String replace(final char[] source) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(source.length).append(source);
        substitute(buf, 0, source.length);
        return buf.toString();
    }

    public String replace(final char[] source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source, offset, length);
        substitute(buf, 0, length);
        return buf.toString();
    }

    public String replace(final CharSequence source) {
        if (source == null) {
            return null;
        }
        return replace(source, 0, source.length());
    }

    public String replace(final CharSequence source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source.toString(), offset, length);
        substitute(buf, 0, length);
        return buf.toString();
    }

    public String replace(final Object source) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder().append(source);
        substitute(buf, 0, buf.length());
        return buf.toString();
    }

    public String replace(final String source) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(source);
        if (!substitute(buf, 0, source.length())) {
            return source;
        }
        return buf.toString();
    }

    public String replace(final String source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source, offset, length);
        if (!substitute(buf, 0, length)) {
            return source.substring(offset, offset + length);
        }
        return buf.toString();
    }

    public String replace(final StringBuffer source) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(source.length()).append(source);
        substitute(buf, 0, buf.length());
        return buf.toString();
    }

    public String replace(final StringBuffer source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source, offset, length);
        substitute(buf, 0, length);
        return buf.toString();
    }

    public String replace(final TextStringBuilder source) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder builder = new TextStringBuilder(source.length()).append(source);
        substitute(builder, 0, builder.length());
        return builder.toString();
    }

    public String replace(final TextStringBuilder source, final int offset, final int length) {
        if (source == null) {
            return null;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source, offset, length);
        substitute(buf, 0, length);
        return buf.toString();
    }

    public boolean replaceIn(final StringBuffer source) {
        if (source == null) {
            return false;
        }
        return replaceIn(source, 0, source.length());
    }

    public boolean replaceIn(final StringBuffer source, final int offset, final int length) {
        if (source == null) {
            return false;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source, offset, length);
        if (!substitute(buf, 0, length)) {
            return false;
        }
        source.replace(offset, offset + length, buf.toString());
        return true;
    }

    public boolean replaceIn(final StringBuilder source) {
        if (source == null) {
            return false;
        }
        return replaceIn(source, 0, source.length());
    }

    public boolean replaceIn(final StringBuilder source, final int offset, final int length) {
        if (source == null) {
            return false;
        }
        final TextStringBuilder buf = new TextStringBuilder(length).append(source, offset, length);
        if (!substitute(buf, 0, length)) {
            return false;
        }
        source.replace(offset, offset + length, buf.toString());
        return true;
    }

    public boolean replaceIn(final TextStringBuilder source) {
        if (source == null) {
            return false;
        }
        return substitute(source, 0, source.length());
    }

    public boolean replaceIn(final TextStringBuilder source, final int offset, final int length) {
        if (source == null) {
            return false;
        }
        return substitute(source, offset, length);
    }

    protected String resolveVariable(final String variableName, final TextStringBuilder buf, final int startPos,
        final int endPos) {
        final StringLookup resolver = getStringLookup();
        if (resolver == null) {
            return null;
        }
        return resolver.lookup(variableName);
    }

    public StringSubstitutor setDisableSubstitutionInValues(final boolean disableSubstitutionInValues) {
        this.disableSubstitutionInValues = disableSubstitutionInValues;
        return this;
    }

    public StringSubstitutor setEnableSubstitutionInVariables(final boolean enableSubstitutionInVariables) {
        this.enableSubstitutionInVariables = enableSubstitutionInVariables;
        return this;
    }

    public StringSubstitutor setEnableUndefinedVariableException(final boolean failOnUndefinedVariable) {
        this.failOnUndefinedVariable = failOnUndefinedVariable;
        return this;
    }

    public StringSubstitutor setEscapeChar(final char escapeChar) {
        this.escapeChar = escapeChar;
        return this;
    }

    public StringSubstitutor setPreserveEscapes(final boolean preserveEscapes) {
        this.preserveEscapes = preserveEscapes;
        return this;
    }

    public StringSubstitutor setValueDelimiter(final char valueDelimiter) {
        return setValueDelimiterMatcher(StringMatcherFactory.INSTANCE.charMatcher(valueDelimiter));
    }

    public StringSubstitutor setValueDelimiter(final String valueDelimiter) {
        if (valueDelimiter == null || valueDelimiter.isEmpty()) {
            setValueDelimiterMatcher(null);
            return this;
        }
        return setValueDelimiterMatcher(StringMatcherFactory.INSTANCE.stringMatcher(valueDelimiter));
    }

    public StringSubstitutor setValueDelimiterMatcher(final StringMatcher valueDelimiterMatcher) {
        this.valueDelimiterMatcher = valueDelimiterMatcher;
        return this;
    }

    public StringSubstitutor setVariablePrefix(final char prefix) {
        return setVariablePrefixMatcher(StringMatcherFactory.INSTANCE.charMatcher(prefix));
    }

    public StringSubstitutor setVariablePrefix(final String prefix) {
        Validate.isTrue(prefix != null, "Variable prefix must not be null!");
        return setVariablePrefixMatcher(StringMatcherFactory.INSTANCE.stringMatcher(prefix));
    }

    public StringSubstitutor setVariablePrefixMatcher(final StringMatcher prefixMatcher) {
        Validate.isTrue(prefixMatcher != null, "Variable prefix matcher must not be null!");
        this.prefixMatcher = prefixMatcher;
        return this;
    }
    public StringSubstitutor setVariableResolver(final StringLookup variableResolver) {
        this.variableResolver = variableResolver;
        return this;
    }

    public StringSubstitutor setVariableSuffix(final char suffix) {
        return setVariableSuffixMatcher(StringMatcherFactory.INSTANCE.charMatcher(suffix));
    }

    public StringSubstitutor setVariableSuffix(final String suffix) {
        Validate.isTrue(suffix != null, "Variable suffix must not be null!");
        return setVariableSuffixMatcher(StringMatcherFactory.INSTANCE.stringMatcher(suffix));
    }

    public StringSubstitutor setVariableSuffixMatcher(final StringMatcher suffixMatcher) {
        Validate.isTrue(suffixMatcher != null, "Variable suffix matcher must not be null!");
        this.suffixMatcher = suffixMatcher;
        return this;
    }
    protected boolean substitute(final TextStringBuilder builder, final int offset, final int length) {
        return substitute(builder, offset, length, null).altered;
    }
    private Result substitute(final TextStringBuilder builder, final int offset, final int length,
        List<String> priorVariables) {
        Objects.requireNonNull(builder, "builder");
        final StringMatcher prefixMatcher = getVariablePrefixMatcher();
        final StringMatcher suffixMatcher = getVariableSuffixMatcher();
        final char escapeCh = getEscapeChar();
        final StringMatcher valueDelimMatcher = getValueDelimiterMatcher();
        final boolean substitutionInVariablesEnabled = isEnableSubstitutionInVariables();
        final boolean substitutionInValuesDisabled = isDisableSubstitutionInValues();
        final boolean undefinedVariableException = isEnableUndefinedVariableException();
        final boolean preserveEscapes = isPreserveEscapes();

        boolean altered = false;
        int lengthChange = 0;
        int bufEnd = offset + length;
        int pos = offset;
        int escPos = -1;
        outer: while (pos < bufEnd) {
            final int startMatchLen = prefixMatcher.isMatch(builder, pos, offset, bufEnd);
            if (startMatchLen == 0) {
                pos++;
            } else {
                if (pos > offset && builder.charAt(pos - 1) == escapeCh) {
                    if (preserveEscapes) {
                        pos++;
                        continue;
                    }
                    escPos = pos - 1;
                }
                int startPos = pos;
                pos += startMatchLen;
                int endMatchLen = 0;
                int nestedVarCount = 0;
                while (pos < bufEnd) {
                    if (substitutionInVariablesEnabled && prefixMatcher.isMatch(builder, pos, offset, bufEnd) != 0) {
                        endMatchLen = prefixMatcher.isMatch(builder, pos, offset, bufEnd);
                        nestedVarCount++;
                        pos += endMatchLen;
                        continue;
                    }

                    endMatchLen = suffixMatcher.isMatch(builder, pos, offset, bufEnd);
                    if (endMatchLen == 0) {
                        pos++;
                    } else {
                        if (nestedVarCount == 0) {
                            if (escPos >= 0) {
                                // delete escape
                                builder.deleteCharAt(escPos);
                                escPos = -1;
                                lengthChange--;
                                altered = true;
                                bufEnd--;
                                pos = startPos + 1;
                                startPos--;
                                continue outer;
                            }
                            String varNameExpr = builder.midString(startPos + startMatchLen,
                                pos - startPos - startMatchLen);
                            if (substitutionInVariablesEnabled) {
                                final TextStringBuilder bufName = new TextStringBuilder(varNameExpr);
                                substitute(bufName, 0, bufName.length());
                                varNameExpr = bufName.toString();
                            }
                            pos += endMatchLen;
                            final int endPos = pos;

                            String varName = varNameExpr;
                            String varDefaultValue = null;

                            if (valueDelimMatcher != null) {
                                final char[] varNameExprChars = varNameExpr.toCharArray();
                                int valueDelimiterMatchLen = 0;
                                for (int i = 0; i < varNameExprChars.length; i++) {
                                    if (!substitutionInVariablesEnabled && prefixMatcher.isMatch(varNameExprChars, i, i,
                                        varNameExprChars.length) != 0) {
                                        break;
                                    }
                                    if (valueDelimMatcher.isMatch(varNameExprChars, i, 0,
                                        varNameExprChars.length) != 0) {
                                        valueDelimiterMatchLen = valueDelimMatcher.isMatch(varNameExprChars, i, 0,
                                            varNameExprChars.length);
                                        varName = varNameExpr.substring(0, i);
                                        varDefaultValue = varNameExpr.substring(i + valueDelimiterMatchLen);
                                        break;
                                    }
                                }
                            }

                            if (priorVariables == null) {
                                priorVariables = new ArrayList<>();
                                priorVariables.add(builder.midString(offset, length));
                            }

                            checkCyclicSubstitution(varName, priorVariables);
                            priorVariables.add(varName);

                            String varValue = resolveVariable(varName, builder, startPos, endPos);
                            if (varValue == null) {
                                varValue = varDefaultValue;
                            }
                            if (varValue != null) {
                                final int varLen = varValue.length();
                                builder.replace(startPos, endPos, varValue);
                                altered = true;
                                int change = 0;
                                if (!substitutionInValuesDisabled) {
                                    change = substitute(builder, startPos, varLen, priorVariables).lengthChange;
                                }
                                change = change + varLen - (endPos - startPos);
                                pos += change;
                                bufEnd += change;
                                lengthChange += change;
                            } else if (undefinedVariableException) {
                                throw new IllegalArgumentException(
                                    String.format("Cannot resolve variable '%s' (enableSubstitutionInVariables=%s).",
                                        varName, substitutionInVariablesEnabled));
                            }

                            priorVariables.remove(priorVariables.size() - 1);
                            break;
                        }
                        nestedVarCount--;
                        pos += endMatchLen;
                    }
                }
            }
        }
        return new Result(altered, lengthChange);
    }

    public String toString() {
        return new StringBuilder()
            .append("StringSubstitutor [disableSubstitutionInValues=")
            .append(disableSubstitutionInValues)
            .append(", enableSubstitutionInVariables=")
            .append(enableSubstitutionInVariables)
            .append(", enableUndefinedVariableException=")
            .append(failOnUndefinedVariable)
            .append(", escapeChar=")
            .append(escapeChar)
            .append(", prefixMatcher=")
            .append(prefixMatcher)
            .append(", preserveEscapes=")
            .append(preserveEscapes)
            .append(", suffixMatcher=")
            .append(suffixMatcher)
            .append(", valueDelimiterMatcher=")
            .append(valueDelimiterMatcher)
            .append(", variableResolver=")
            .append(variableResolver)
            .append("]")
            .toString();
    }
}
