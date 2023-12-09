# Advent of Code 2023 in Rust

## TIL (Today I learned)

### Day 1
I alreayd got some experience in 2015 by practicing some of the days there, but needless to say I still need to learn a lot.

The borrow and owner system is still kind of aliën to me, but I am starting to get the hang of it. You can better read what the compiler says, because most of the time that fixes the issue that I have.

I also followed a video to setup VSCode *Error Lens* which shows what lines gives the error in VSCode for easy debugging. I am also using the Devcontainers extension together with Podman Desktop to run Rust, so you can easily launch the code yourself if you open the `2023 - rust` folder.

Also HashMaps exist! I thought that it didn't but you need to import it with `use std::collections::HashMap;` same as getting a substring with `use substring::Substring;` but for this you need to install a new crate with Cargo called `substring` with `cargo add substring`. It's as easy as that.

And I really 😍 the println! macro (*yes that is called a macro*), an example that I use every day:

```rust 
println!("Day 1 ⭐1️⃣  result: {}", star1);
```

Where `star1` can be used within the curly brackets like so: `{star1}` or after the fact, like in the example above.

### Day 2
First time using a Regex in Rust, by default there is no implementation so going with the `regex` 📦. I also found out is is more limited then the parse selected by default on https://regex101.com/ the hard way. So I had to redo some of the code eventually.

### Day 3
Traits, traits and more traits. Comming from Java I wanted an equals (*eq in rust*) function. Seems like the `#[derive(PartialEq)]` does the job for me. So I then can use the contains function in an iterator.

And casting is also super easy just do `iter_y as i32` and most of the time it just works 🪄 like magic. 

And lastly the enumarator with `for (index, line) in lines.iter().enumerate() {` to get a free index with your iterator.

### Day 4

`"test".split_once()`
