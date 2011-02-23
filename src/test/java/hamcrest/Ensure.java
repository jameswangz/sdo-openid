package hamcrest;


import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;

public abstract class Ensure {
	
	public static <T> void ensureThat(T actual, Matcher<? super T> matcher) {
		MatcherAssert.assertThat(actual, matcher);
	}
	
	public static <T> Matcher<T> shouldBe(T expected) {
		return new IsEqual<T>(expected);
	}
	
	public static <T> Matcher<T> isNotNull() {
		return new IsNot<T>(new IsNull<T>());
	}
}
