# Advent of Code 2024 (Java)

I picked Java again because I really need to step up my game with the language I use for work. With this I force myself to
actually learn some new API's and methods and to also try them out for the first time.

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

Regex was king today, making everything easier. I must say I was pretty rusty with remembering how to program patterns. But a quick Google was 

```
Pattern multiplier = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)");
Matcher matcher = multiplier.matcher(input);

List<String> captureGroups = matcher.results().map(MatchResult::group).toList();
```
