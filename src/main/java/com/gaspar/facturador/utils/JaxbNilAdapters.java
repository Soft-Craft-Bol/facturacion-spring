package com.gaspar.facturador.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class JaxbNilAdapters {

    // Adapter genérico para tipos básicos
    public static class NilAdapter<T> extends XmlAdapter<String, T> {
        private final ValueParser<T> parser;

        public NilAdapter(ValueParser<T> parser) {
            this.parser = parser;
        }

        @Override
        public T unmarshal(String v) throws Exception {
            return v == null || v.isEmpty() ? null : parser.parse(v);
        }

        @Override
        public String marshal(T v) throws Exception {
            return v == null ? null : v.toString();
        }
    }

    // Interface para el parser
    @FunctionalInterface
    public interface ValueParser<T> {
        T parse(String value);
    }

    // Implementaciones específicas
    public static class StringAdapter extends NilAdapter<String> {
        public StringAdapter() {
            super(v -> v);
        }
    }

    public static class IntegerAdapter extends NilAdapter<Integer> {
        public IntegerAdapter() {
            super(Integer::parseInt);
        }
    }

    public static class LongAdapter extends NilAdapter<Long> {
        public LongAdapter() {
            super(Long::parseLong);
        }
    }

    public static class BigDecimalAdapter extends NilAdapter<BigDecimal> {
        public BigDecimalAdapter() {
            super(BigDecimal::new);
        }
    }
}