package org.example;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {

    // map
    List<String> animalsList = List.of("monkey", "gorilla", "duck", "dog", "cat");
    Stream<String> animalStrings = animalsList.stream();

    Function<String, Boolean> greaterThan3Function = new Function<String, Boolean>() {
      @Override
      public Boolean apply(String string) {
        return string.length() > 3;
      }
    };
    // using some mapper method which is already available in JDK
//    animalStrings.map(String::length).forEach(System.out::print); // 6743
    // use some custom mapper
    animalStrings.map(greaterThan3Function).forEach(System.out::print); //truetruetruefalse

    // flatMap
    System.out.println();
    List<String> zero = List.of();
    var one = List.of("monkey");
    var two = List.of("gorilla", "cat", "cat");
    var three = List.of("monkey");
    Stream<List<String>> animalsStream = Stream.of(zero, one, two, three);
    Function<List<String>, Stream<String>> flatteningFunction = new Function<List<String>, Stream<String>>() {
      @Override
      public Stream<String> apply(List<String> strings) {
        return strings.stream();
      }
    };
    // flatMap is transforming Stream<List<String>> into the Stream<String>
//    animalsStream.flatMap(Collection::stream).distinct().forEach(System.out::println);
    Stream<String> animalsStreamFlattened = animalsStream.flatMap(Collection::stream);
    animalsStreamFlattened.forEach(System.out::println);

    // sorted
    Stream<Integer> integerStream = Stream.of(4, 1, 7, 2, 99, 12);
    integerStream.sorted().forEach(System.out::println);

    // peek - used for debugging
    Stream<String> bears = Stream.of("black bear", "white bear", "grizzly");
    Predicate<String> startsWithGPredicate = new Predicate<String>() {
      @Override
      public boolean test(String string) {
        return string.startsWith("g");
      }
    };
    System.out.println(bears.filter(startsWithGPredicate).peek(System.out::println).count());

    // Primitive streams - better performace
    IntStream intStream = IntStream.of(4, 1, 7, 2, 99, 12);
    intStream.sorted().forEach(System.out::println);

    IntStream.rangeClosed(1, 10).forEach(System.out::println);

    // collect - details, we can collect result from pipeline in different forms
    // by providing different Collector objects to the collect method
    Stream<List<String>> animals2 = Stream.of(zero, one, two);
    System.out.println(animals2.collect(Collectors.counting()));

    System.out.println(animalsList.stream().collect(Collectors.groupingBy(String::length)));

    System.out.println(animalsList.stream().filter(startsWithGPredicate).collect(Collectors.toList()));

    System.out.println(animalsList.stream().collect(Collectors.joining(",")));

    System.out.println(animalsList.stream().collect(Collectors.minBy(Comparator.naturalOrder())));

    // Primitive functional interfaces
    // creating ToIntFunction which is doing the same as String::length
    ToIntFunction<String> stringLengthFunction = new ToIntFunction<String>() {
      @Override
      public int applyAsInt(String value) {
        return value.length();
      }
    };

    // methods to create primitive streams
    Stream<Integer> integerStream2 = Stream.of(4, 1, 7, 2, 99, 12);
    integerStream2.mapToInt(Integer::intValue).forEach(System.out::println);

    animalsList.stream().mapToInt(stringLengthFunction).forEach(System.out::println);
  }
}