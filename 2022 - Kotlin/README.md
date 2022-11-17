# Advent of code year 2022

## Today I Learned

### Preparation phase:

---

#### Static Methods in Kotlin work differently
To have the behaviour to be the same you need to use companion objects which can optionally be named:

```kotlin
class Some {
    companion object Name {
        val someValue = 1
        fun someMethods() {
            // etc.
        }
    }
}
```

But I found something else, if you then have a main method in this Some class it won't be run as it's not in the correct scope like Java.
To fix this we need to add something on top of the main constructor.

```kotlin
@JvmStatic
fun main() {
    // ...
}
```

While the companion object makes the main method sort of static, it's not picked up by the kotlin compiler as entry. So adding `@JvmStatic` makes it runable.

---


---

## Why

After getting on board with Java I found out that Kotlin exists, and even as we speak more and more apps are being made in Kotlin for Android. So might as well check out if Kotlin is any fun to program in right?

1. ⭐
