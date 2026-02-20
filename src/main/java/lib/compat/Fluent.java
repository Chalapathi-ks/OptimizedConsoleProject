package lib.compat;

/**
 * Marker type used in Function signatures for await().until(Function).
 * Also provides chain methods like clear() for FluentLenium compatibility.
 * Replaces org.fluentlenium.core.Fluent.
 */
public interface Fluent {
    default Fluent clear() {
        return this;
    }
}
