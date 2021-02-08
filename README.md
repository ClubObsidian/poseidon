# poseidon

A fork of [PF4J](https://github.com/pf4j/pf4j).

## Changes

* Incorporates yaml description finder instead of using the manifest
* Uses [Guice](https://github.com/google/guice) for injection instead of having the plugin wrapper in the constructor

## Why fork

We will be making a number of breaking changes that would likely not be accepted by upstream. If you want a good plugin platform go with pf4j but poseidon is an opinionated version of pf4j but that does not mean it is the correct opinion.