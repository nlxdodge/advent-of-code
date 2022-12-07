# Advent of code 2022 - Kotlin

---

## Why

After getting on board with Java I found out that Kotlin exists, and even as we speak more and more apps are being made
in Kotlin
for Android. So might as well check out if Kotlin is any fun to program in right?

*⭐: One star for that day 🔄: Refactoring complete for that day*

1. ⭐⭐
2. ⭐⭐
3. ⭐⭐🔄
4. ⭐⭐
5. ⭐⭐
6. ⭐⭐🔄
7.
8.
9.
10.

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

But I found something else, if you then have a main method in this Some class it won't be run as it's not in the correct
scope like Java.
To fix this we need to add something on top of the main constructor.

```kotlin
@JvmStatic
fun main() {
    // ...
}
``` 

While the companion object makes the main method sort of static, it's not picked up by the kotlin compiler as entry. So
adding `@JvmStatic` makes it run-able.

### Day 1

#### Method calling for streams is easier

As example when trying to sum a hashMap I can simply do:

```kotlin
elfCalories.values.sum().toString()
```

#### Casting to other types

Same as converting Strings to Int

```kotlin
stringValue.toInt()
```

#### Or just initializing a new HashMap

But I still have to learn more syntax because I had no idea that you had to make a hashmap like this

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

### Day 4

I was refactoring some code to make it more dynamic, and with this little snippet I can get the called class
AOC01,AOC02 etc. from within another class because of the StackTracing.

```kotlin
var calledFromFilename: String? = Throwable().stackTrace[1].fileName
```

### Day 5

Day 5 was a big mess I did things twice and switched a lot of code around to make it work. Eventually I ended up using
Java labels which isn't the nicest. But I still have to refactor Day 5.

I also used an `ArrayDeque` instead of an `Stack` object as I thought it was not nice to use anymore.
One of the nice functions of an ArrayDeque is removing the first even when it's null.

```kotlin
val yourArrayDeque = ArrayDeque<String>()
yourArrayDeque.removeFirstOrNull() // this will not throw an error because of the inside null check
```

### Day 6

This helped me so much, I love this method when you need to check if there is any duplicate just use `distinct()` on a
list, and you're set.

```kotlin
if (list.distinct().size == markerSize) {
    break
}
```

This way I could easily break the for loop when all the items where unique, else I just continue to search.

### Day 7

This was a big day again where I didn't do something correctly and made an assumption so my time went up
But I did learn a couple of new things.

When you have the Folder structure like we had in Day 7 I first mapped the directory size to ints and then summed them.
But using the sumOf does the mapping for you as well.

*We had to search all folders where the size doesn't exceed 100.000*

```kotlin
return findDirSizes(rootFolder, 100_000, false).sumOf { folder -> folder.getDirSize() }.toString()
```

This also works for `minOf` and `maxOf`.

*We had to calculate the smallest folder (that met size criteria) to delete in order to continue*

```kotlin
return findDirSizes(
    rootFolder,
    30_000_000 - (70_000_000 - rootFolder.getDirSize()),
    true
).minOf { folder -> folder.getDirSize() }.toString()
```

Getting the size of sub folders I refactored to one line.

```kotlin
fun getDirSize(): Int {
    return subFolders.sumOf { it.getDirSize() } + files.sumOf { it.size }
}
```
