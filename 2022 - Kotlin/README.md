# Advent of code 2022 - Kotlin

---

## Why

After getting on board with Java I found out that Kotlin exists, and even as we speak more and more apps are being made in Kotlin
for Android. So might as well check out if Kotlin is any fun to program in right?

1. ⭐⭐
2. ⭐⭐
3. ⭐⭐
4. 

---

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

While the companion object makes the main method sort of static, it's not picked up by the kotlin compiler as entry. So
adding `@JvmStatic` makes it runable.

### Day 1

#### Method calling for streams is easier

As example when trying to sum a hashMap I can simply do:

```kotlin
elfCalories.values.sum().toString()
```

#### Casting to other types

Same as converting Strings to Int(eger)

```kotlin
stringValue.toInt()
```

#### Or just initializing a new HashMap

But I still have to learn more sytax because I had no idea that you had to make a hashmap like this

```kotlin
var theMap = hashMapOf(-3 to 0, -2 to 0, -1 to 0)
```

The syntax is just way easier, but has a learning curve to it 😉

### Day 2

Working with chars, fascinating! Want to get the number of a char? Use `charVar.code`


### Day 3

Day 3 was more search work and I finally got my Kotlin compiler working fast on my new work laptop.
Things I learned today where:

Rounding a double is as easy as ever
```kotlin
(line.length / 2).toDouble().roundToInt()
```

An inline if statement with a question mark doesn't exist and is now normal code
```kotlin
val multiplier = if (chr.isUpperCase()) 70 else 96
```

Want to split a list or array into chunks of three?
```kotlin
val splitList = yourList.chunk(3)
```

And lastly how to get the upper or lowercase version of some character
```kotlin
chr.lowercaseChar() // or
chr.uppercaseChar()
```




