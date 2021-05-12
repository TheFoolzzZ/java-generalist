package org.geektimes.cache;

import java.io.Serializable;

class Foo implements Serializable {
    private String bar;

    public Foo() {
    }

    public Foo(String bar) {
        this.bar = bar;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
