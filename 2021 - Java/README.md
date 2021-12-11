# Advent of code year 2021

## 1 - Today I learned

```java
stream.toList();
```

Doesn't exist in Java 11, so to mitigate this I had to use the old way of doing it with a collector.

```java
List<String> list = stream.map(String::new).collect(Collectors.toList());
```

## 2 - Today I learned

I had an Integer overflow again, and people from the Discords said that other developers mostly used `Long` for that specific purpose in mind. So they never get an Integer overflow. A `Math.multiplyExact` can also be used in a stream to detect if it overflowed.

## 3 - Today I learned

That you could go further than a 2D java array, for example:

```java
int[][][] threeDimensions = int[][][];
```

Is valid in java.

## Why

Because I started working this year at Sogeti I wanted to make my java skills better.
While making a quick java file that could solve the puzzel, I would always refactor some of the code to be better afterwards.
While also getting all the stars this year and waking up early to try out my skills when I am completely 'awake'.

1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1. ⭐⭐
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
1.
