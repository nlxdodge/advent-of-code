# Advent of Code 2024 (Java)

I picked Java again because I really need to step up my game with the language I use for work. With this I force myself to
actually learn some new APIs and methods and to also try them out for the first time.

## Day 1

I always thought the Pair was a default class, but to no avail it's not available anymore in Java 23. It was in the JavaFX package
before. Luckily it's easy to make a class on your own.

This was also the first time I have used `Class.forName` to dynamically get a class and to call a method on it. It was easier than
I expected it to be.

Reminder for myself, don't forget `Math.abs` last time I forgot it was there and use the min and max functions in the Math class
as substitute 🤦🏻

`IntStream.rangeClosed` and `IntStream.range` are also useful if you just want to iterate over something with an index.

## Day 2

A wild day indeed, I had some difficulty getting up to speed as I was doing some thinking wrong. But just doing a sort after the
initial `sort()` and `sort(Collections.reverseOrder())` made me eliminate a check for going up or down.

I also learned again that `Collection.remove(object)` does not work with a list like this `[1,2,3,3,4]` as there are multiple `3`
in the collection.

## Day 3

Regex was king today, making everything easier. I must say I was pretty rusty with remembering how to program patterns. But a
quick Google was

```java
Pattern multiplier = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)");
Matcher matcher = multiplier.matcher(input);

List<String> captureGroups = matcher.results().map(MatchResult::group).toList();
```

## Day 4

I learned something valuable today, which I don't even know if other people in the Java community know where I work.

What do you think what the next code wil do?

```java
List<Integer> numbers = new ArrayList<>(List.of(1, 3));

numbers.add(1, 2);

System.out.println(numbers); // will print [1,2,3]
```

I DIDN'T KNOW! I didn't know that there was an easier way of inserting items in an existing array/list. I normally did this by
hand.
I thought that it will override the current existing index of 1, which was a 3. So this makes programming with insertions a lot
easier in the future.

Also instead of doing a lot of if statements and checking positions by hand I wanted to make it a bit smarter and use offsets, and
it worked out great in my
opinion.

## Day 5

Once again I keep forgetting that not every Regex engine uses single escape characters `\|` this for example wouldn't work. You
would need two `\\` to make it
work.

## Day 6

Do you want to reverse a collection? Well you better not use `Collections.reversOrder()` because this will also sort your list,
without you even wanting it!
What I was actually look for was the `Collections.reverse(list);` method!

## Day 7

I again was reminded that giving a List in a recursive function would update the list in the above stack as well. Which is
confusing if you don't know what to look out for.
And of course the new Switch that we can use in Java:
```java
return switch (operator) {
  case '+' -> left + right;
  case '*' -> left * right;
  case '|' -> Long.parseLong(String.format("%s%s", left, right));
  default -> throw new IllegalStateException("Unexpected value: " + operator);
};
```